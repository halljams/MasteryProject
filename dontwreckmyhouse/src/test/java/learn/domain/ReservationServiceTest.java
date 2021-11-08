package learn.domain;

import learn.data.*;
import learn.model.Host;
import learn.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

        ReservationService service;
        ReservationRepositoryDouble reservationRepository = new ReservationRepositoryDouble();
        GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();
        HostRepositoryDouble hostRepository = new HostRepositoryDouble();

    @BeforeEach
    void setUp() {
         service = new ReservationService(reservationRepository, guestRepository, hostRepository );
    }
    @Test
    void weekendRateShouldBeHigherRentPerDay() throws DataException {
        LocalDate start = LocalDate.of(2021,10,15); //1 days at 123
        LocalDate end = LocalDate.of(2021, 10, 17); // 2 day at 234

        BigDecimal costPerStay = service.costPerStay(start, end, hostRepository.findHostById("3edda6bc-ab95-49a8-8962-d50b53f84b15"));

        assertEquals(BigDecimal.valueOf(591.00).setScale(2), costPerStay);


    }

    @Test
    void standardRateRentPerDay() throws DataException {
    LocalDate start = LocalDate.of(2021,10,18); // 3 days at 123
    LocalDate end = LocalDate.of(2021, 10, 20);


    BigDecimal costPerStay = service.costPerStay(start, end, hostRepository.findHostById("3edda6bc-ab95-49a8-8962-d50b53f84b15"));

    assertEquals(BigDecimal.valueOf(369).setScale(2), costPerStay);



    }
    @Test
    void shouldNotOverLapBookingDates() {

    }
    @Test
    void shouldNotCreateReservationPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(hostRepository.findHostById("3edda6bc-ab95-49a8-8962-d50b53f84b15"));

        reservation.setStartDate(LocalDate.of(2019,10,12));
        reservation.setEndDate(LocalDate.of(2019,10, 15));
        reservation.setGuestId(5);
        reservation.setTotal(BigDecimal.valueOf(500));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());

    }
    @Test
    void shouldUpdateReservation() {


    }


//    @Test
//    void shouldAdd() throws DataException{
//        Reservation reservation = new Reservation();
//        reservation.setHost(hostRepository.findHostById("test-host-id"));
//        reservation.setReservationId(1);
//        reservation.setStartDate(LocalDate.of(2022,10,20));
//        reservation.setEndDate(LocalDate.of(2022,10, 25));
//        reservation.setGuestId(1);
//        reservation.setTotal(BigDecimal.valueOf(500));
//        Result<Reservation> expected = new Result<>();
//        expected.setPayload(reservation);
//
//        Result<Reservation> actual = service.add(reservation);
//
//        assertEquals(expected, actual);
//
//    }

}