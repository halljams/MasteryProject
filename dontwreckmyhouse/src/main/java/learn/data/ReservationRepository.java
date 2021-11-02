package learn.data;

import learn.model.Host;
import learn.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation add(Reservation reservation) throws DataException;
    List<Reservation> findByHostEmail(Host hostEmail);
}
