package learn.data;

import learn.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation add(Reservation reservation) throws DataException;

    List<Reservation> findByHostID(String hostId) throws DataException;
    boolean update(Reservation reservation) throws DataException;
}
