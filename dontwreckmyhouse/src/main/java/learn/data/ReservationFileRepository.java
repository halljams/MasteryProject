package learn.data;

import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ReservationFileRepository implements ReservationRepository {
    private final String directory;
    private static final String HEADER = "reservationId,start_date,end_date,guest_id,total";


    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }


    public List<Reservation> findByHostID(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }


    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostID(reservation.getHost().getReservationId());
        reservation.setReservationId(getNextId(all));
        all.add(reservation);
        writeAll(all, reservation.getHost().getReservationId());
        return reservation;
    }
    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostID(reservation.getHost().getReservationId());
        for (int i = 0; i <all.size(); i++) {
            if (Objects.equals(all.get(i).getReservationId(), reservation.getReservationId())) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getReservationId());
                return true;
            }
        }
        return false;

    }
    @Override
    public boolean cancelReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostID(reservation.getHost().getReservationId());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReservationId() == reservation.getReservationId()) {
                all.remove(i);
                writeAll(all, reservation.getHost().getReservationId());
                return true;
            }
        }
        return false;
    }

    private int getNextId(List<Reservation> allReservations) {
        int nextId = 0;
        for (Reservation r : allReservations) {
            nextId = Math.max(nextId, r.getReservationId());
        }
        return nextId + 1;
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String hostId) {

        //field 0 is reservationId field 1 start_date, field 2 end_date, field 3 guest_id, field 4 total
        Reservation result = new Reservation();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        Host host = new Host();
        host.setReservationId(hostId);
        result.setHost(host);

        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        return result;
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation r : reservations) {
                writer.println(serialize(r));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
