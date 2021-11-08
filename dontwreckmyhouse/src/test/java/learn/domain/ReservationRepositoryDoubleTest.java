package learn.domain;

import learn.data.DataException;
import learn.data.ReservationRepository;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDoubleTest implements ReservationRepository {
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDoubleTest() {
        Reservation reservation = new Reservation();

        reservation.setStartDate(LocalDate.of(2021, 10, 12));
        reservation.setEndDate(LocalDate.of(2021, 10, 15));
        reservation.setGuestId(5);
        reservation.setTotal(BigDecimal.valueOf(500));


    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return reservation;
    }

//    @Override
//    public List<Reservation> findByHostEmail(String hostEmail) throws DataException{
//        ArrayList<Reservation> result = new ArrayList<>();
//        for (Reservation reservation : reservations) {
//            if (reservation.getHost().getHostEmail() == hostEmail) {
//                result.add(reservation);
//            }
//        }
//        return result;
//}

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

//    @Override
//    public List<Reservation> findByHostID(String hostId) throws DataException {
//        ArrayList<Reservation> result = new ArrayList<>();
//        for (Reservation reservation : reservations) {
//            if (reservation.getHost().getReservationId() == hostId) {
//                result.add(reservation);
//            }
//
//
//        }
//        return result;
//    }
}