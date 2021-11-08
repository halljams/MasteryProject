package learn.data;

import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {
    private final static Reservation RESERVATION = makeReservation();

    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        reservations.add(RESERVATION);
    }
    private static Reservation makeReservation (){
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setStartDate(LocalDate.of(2022,10,12));
        reservation.setEndDate(LocalDate.of(2022,10, 15));
        reservation.setGuestId(1);
        reservation.setTotal(BigDecimal.valueOf(500));

        return reservation;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostID(reservation.getHost().getReservationId());
        int nextId = all.stream().mapToInt(Reservation::getReservationId)
                        .max().orElse(0) +1;
        reservation.setReservationId(nextId);
        all.add(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findByHostID(String hostId) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getHost().getReservationId() == hostId) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return true;
    }

    @Override
    public boolean cancelReservation(Reservation reservation) throws DataException {
        return true;
    }


}