package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }


    public List<Reservation> findByHostEmail(String hostEmail) throws DataException {

        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i-> i.getReservationId(), i -> i));

        List<Reservation> result = reservationRepository.findByHostEmail(hostEmail);
        for (Reservation reservation:result) {
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
            reservation.setHost(hostMap.get(reservation.getHost().getHostEmail()));
        }
        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public List<LocalDate> dateRange(LocalDate start, LocalDate end){
        return start.datesUntil(end).collect(Collectors.toList());
    }

//    public BigDecimal rentPerDay(BigDecimal rate, LocalDate date) {
//        Map<Host, BigDecimal> standardRateHostMap = hostRepository.findAll()
//                .stream().collect(Collectors.toMap(Host::getStandard_rate, i -> i));
//        Map<Host, BigDecimal> weekendRateHostMap = hostRepository.findAll()
//                .stream().collect(Collectors.toMap(Host::getWeekend_rate, i -> i));
//
//        if (DayOfWeek.FRIDAY == date.getDayOfWeek()
//                || date.getDayOfWeek() == DayOfWeek.SATURDAY
//                || date.getDayOfWeek() == DayOfWeek.SUNDAY ) {
//            rate = weekendRateHostMap.get(rate).getWeekend_rate();
//        } else {
//            rate = standardRateHostMap.get(rate).getStandard_rate();
//        }
//        return rate;
//    }
        public BigDecimal costPerStay(List<LocalDate> dates, Host hostId) {
//        //get id //loop days of the week / count weekdays count weekends / multiply type of day by respective rates
        BigDecimal result = BigDecimal.ZERO;
        int countWeekday = 0;
        int countWeekend = 0;

        for (LocalDate date : dates) {
        dateRange(dates.get(0), dates.get(dates.lastIndexOf(date)));
        if (DayOfWeek.SATURDAY == date.getDayOfWeek()
        || DayOfWeek.SUNDAY == date.getDayOfWeek()) {
            countWeekend++;
        } else {
            countWeekday++;
        }
        result = (hostId.getWeekend_rate().multiply(BigDecimal.valueOf(countWeekend))
                .add(hostId.getStandard_rate().multiply(BigDecimal.valueOf(countWeekday))));
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) throws DataException {
        Result<Reservation> result = validateNullInput(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        validateField(reservation,result);
        if (!result.isSuccess()) {
            return result;
        }
        validateChildrenNotNull(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }
        String hostID = reservation.getHost().getHostEmail();
        validNotDuplicate(reservation,result, hostID);
        return result;


    }

    private Result<Reservation> validateNullInput(Reservation reservation) {
        Result<Reservation> result = new Result<>();
        if (reservation == null) {
            result.addErrorMessage("Nothing to save");
            return result;
        }
        if (reservation.getStartDate() == null
                || reservation.getEndDate() == null) {
            result.addErrorMessage("A date is required.");
        }
        if (reservation.getHost().getHostEmail() == null) {
            result.addErrorMessage("A host is required.");
        }
        if (reservation.getGuestId() == Integer.parseInt(null)) {
            result.addErrorMessage("A guest is required.");
        }
        return result;
    }
    private void validateField(Reservation reservation, Result<Reservation> result) {
        if (reservation.getStartDate().isBefore(LocalDate.now())
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot create reservations for the past.");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start date must be before end date.");
        }
    }
    private void validateChildrenNotNull(Reservation reservation, Result<Reservation> result) throws DataException {
        if (reservation.getGuestId() == Integer.parseInt(null)
                || guestRepository.findByGuestEmail(reservation.getGuest().getGuestEmail()) == null) {
            result.addErrorMessage("Guest doesn't exist.");
        }
        if (hostRepository.findByHostEmail(reservation.getHost().getHostEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
    }
    private void validNotDuplicate(Reservation reservation, Result<Reservation> result, String hostId) throws DataException {
        List<Reservation> reservations = reservationRepository.findByHostEmail(String.valueOf(hostId));
        for (Reservation r: reservations) {
            if (Objects.equals(reservation.getGuest().getGuestEmail(), r.getGuest().getGuestEmail())
                    && Objects.equals(reservation.getStartDate(), r.getStartDate())); {
                        result.addErrorMessage("Double Booking not allowed");
            }
            if (reservation.getStartDate().isBefore(r.getEndDate())) {
                result.addErrorMessage("You cannot overlap other bookings.");
            }
        }
    }

}
