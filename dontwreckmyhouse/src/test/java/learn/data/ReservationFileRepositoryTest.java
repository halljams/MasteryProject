package learn.data;

import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {


    String host = "3edda6bc-ab95-49a8-8962-d50b53f84b15";


    @Test
    void shouldFindById() {
        ReservationFileRepository repo = new ReservationFileRepository("./reservation-test");
        List<Reservation> reservations = repo.findByHostID(host);
        assertNotNull(reservations);
        assertTrue(reservations.size() > 1);
    }

    @Test
    void shouldCreateReservation() throws DataException {
        Reservation reservation = new Reservation();

        reservation.setStartDate(LocalDate.of(2021, 10, 16));
        reservation.setEndDate(LocalDate.of(2021, 10, 18));

        reservation.setTotal(BigDecimal.valueOf(800));

        Host host = new Host();
        host.setReservationId("reservation-creation-test");

        reservation.setHost(host);
        Guest guest = new Guest();
        reservation.setGuestId(25);
        reservation.setGuest(guest);

        ReservationRepository repository = new ReservationFileRepository("./reservation-test");
        reservation = repository.add(reservation);

        assertNotNull(reservation);
        assertTrue(reservation.getReservationId() > 5);
    }

    @Test
    void shouldUpdateReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(6);

        reservation.setStartDate(LocalDate.of(2021, 10, 25));
        reservation.setEndDate(LocalDate.of(2021, 10, 30));

        reservation.setTotal(BigDecimal.valueOf(800));

        Host host = new Host();
        host.setReservationId("reservation-creation-test");
        reservation.setHost(host);

        reservation.setGuestId(25);

        ReservationRepository repository = new ReservationFileRepository("./reservation-test");

        boolean success = repository.update(reservation);

        assertTrue(success);
    }
}
