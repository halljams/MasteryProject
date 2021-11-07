package learn.data;

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

    public List<Reservation> findByHostEmail(String hostEmail) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostEmail));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }

    public List<Reservation> findByHostID(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {
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
        List<Reservation> all = findByHostEmail(reservation.getHost().getHostEmail());
        reservation.setReservationId(getNextId(all));
        all.add(reservation);
        writeAll(all, reservation.getHost().getHostEmail());
        return reservation;
    }

    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostEmail(reservation.getHost().getHostEmail());
        for (int i = 0; i <all.size(); i++) {
            if (Objects.equals(all.get(i).getReservationId(), reservation.getReservationId())) {
                all.set(i, reservation);
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
                reservation.getGuestId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String hostId) {
        Reservation result = new Reservation();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setGuestId(Integer.parseInt(fields[3]));
        result.setTotal(BigDecimal.valueOf(Integer.parseInt(fields[4])));
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
