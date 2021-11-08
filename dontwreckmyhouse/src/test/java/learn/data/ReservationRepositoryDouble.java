package learn.data;

import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {

    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();

        reservation.setStartDate(LocalDate.of(2021,10,12));
        reservation.setEndDate(LocalDate.of(2021,10, 15));
        reservation.setGuestId(5);
        reservation.setTotal(BigDecimal.valueOf(500));


    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
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