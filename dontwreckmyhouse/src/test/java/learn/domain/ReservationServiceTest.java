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
//        LocalDate start = LocalDate.of(2021,10,15);
//        LocalDate end = LocalDate.of(2021, 10, 17);
//        BigDecimal cost = BigDecimal.valueOf(hostRepository.findByHostEmail("eyearnes0@sfgate.com").getWeekend_rate().doubleValue());
//        List<LocalDate> datesStayed = service.dateRange(start,end);
//        BigDecimal costPerStay = service.costPerStay(datesStayed, cost);
//
//        assertEquals(BigDecimal.valueOf(1275), costPerStay);


    }

    @Test
    void standardRateRentPerDay() throws DataException {
//    LocalDate start = LocalDate.of(2021,10,18);
//    LocalDate end = LocalDate.of(2021, 10, 20);
//
//    BigDecimal cost = service.findByHostEmail("eyearnes0@sfgate.com").get().getHost().getWeekend_rate(); // wk 340, wknd 425
//    List<LocalDate> datesStayed = service.dateRange(start,end);
//    BigDecimal costPerStay = service.costPerStay(datesStayed, cost);
//
//    assertEquals(BigDecimal.valueOf(75), costPerStay);



    }
    @Test
    void shouldNotOverLapBookingDates() {

    }
    @Test
    void shouldNotCreateReservationPast() throws DataException {
        Reservation reservation = new Reservation();

        reservation.setStartDate(LocalDate.of(2019,10,12));
        reservation.setEndDate(LocalDate.of(2019,10, 15));
        reservation.setGuestId(5);
        reservation.setTotal(BigDecimal.valueOf(500));

        Result<Reservation> result = service.add(reservation);

    }
    @Test
    void shouldUpdateReservation() {

    }
    @Test
    void startDateShouldBeBeforeEndDate() {
//        LocalDate end= LocalDate.of(2020, 10, 15);
//        LocalDate start = LocalDate.of(2020, 10, 25);
//        List<LocalDate> result = service.dateRange(start,end);
//        assertNull(result);
//        //throws IllegalArgumentException

    }

    @Test
    void dateRange() {
        LocalDate start = LocalDate.of(2020, 10, 15);
        LocalDate end = LocalDate.of(2020, 10, 25);
        List<LocalDate> result = service.dateRange(start,end);
        assertNotNull(result);

    }

    @Test
    void rentPerDay() {
    }
    @Test
    void shouldAdd() throws DataException{
//        Reservation reservation = new Reservation();
//        reservation.setHostId("AAaa-BBBB_CCCC-DDDD");
//        reservation.setStartDate(LocalDate.of(2021,10,12));
//        reservation.setEndDate(LocalDate.of(2021,10, 15));
//        reservation.setGuestId(5);
//        reservation.setTotal(BigDecimal.valueOf(500));
//
//        Result<Reservation> result = service.add(reservation);
//
//
//        assertEquals(1, result.getPayload().getReservationId());

    }

}