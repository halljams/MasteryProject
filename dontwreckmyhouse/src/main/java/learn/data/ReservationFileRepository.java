package learn.data;

import learn.model.Host;
import learn.model.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository{
    private final String directory;
    private static final String HEADER = "reservationId,start_date,end_date,guest_id,total";


    public ReservationFileRepository(String directory) {this.directory = directory;}

    public List<Reservation> findByHostEmail(Host hostEmail) {
        ArrayList<Reservation> result = new ArrayList<>(0);
        try(BufferedReader reader = new BufferedReader(new FileReader(directory))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",",-1);
                if (fields.length == 4) {
                    result.add(deserialize(fields,hostEmail));
                }
            }
        }catch (IOException ex) {

        }return result;
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s-%s,%s,%s,",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, Host hostEmail) {
        Reservation result = new Reservation();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setGuest(String.valueOf(fields[3]));
        result.setTotal(BigDecimal.valueOf(Long.parseLong(fields[4])));
        return result;
    }
}
