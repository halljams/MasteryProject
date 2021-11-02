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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHostEmail(Host hostEmail) {
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i-> i.getHostEmail(), i -> i));

        List<Reservation> result = reservationRepository.findByHostEmail(hostEmail);
        for (Reservation reservation:result) {
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
            reservation.setHost(hostMap.get(reservation.getHost().getHostEmail()));
        }
        return result;
    }

    public BigDecimal rentPerDay(BigDecimal rate, LocalDate date) {
        Map<BigDecimal, Host> standardRateHostMap = hostRepository.findAll()
                .stream().collect(Collectors.toMap(Host::getStandard_rate, i -> i));
        Map<BigDecimal, Host> weekendRateHostMap = hostRepository.findAll()
                .stream().collect(Collectors.toMap(Host::getWeekend_rate, i -> i));

        if (DayOfWeek.FRIDAY == date.getDayOfWeek()
                || date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY ) {
            rate = weekendRateHostMap.get(rate).getWeekend_rate();
        } else {
            rate = standardRateHostMap.get(rate).getStandard_rate();
        }
        return rate;
    }

    private Result<Reservation> validate(Reservation reservation) {
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
        Host hostEmail = reservation.getHost();
        validNotDuplicate(reservation,result, hostEmail);
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
        if (reservation.getHost() == null) {
            result.addErrorMessage("A host is required.");
        }
        if (reservation.getGuest() == null) {
            result.addErrorMessage("A guest is required.");
        }
        return result;
    }
    private void validateField(Reservation reservation, Result<Reservation> result) {
        if (reservation.getStartDate().isBefore(LocalDate.now())
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot create reservations for the past.");
        }
    }
    private void validateChildrenNotNull(Reservation reservation, Result<Reservation> result) {
        if (reservation.getGuest().getGuestEmail() == null
                || reservationRepository.findByHostEmail(reservation.getGuest().getGuestEmail()) == null) {
            result.addErrorMessage("Guest doesn't exist.");
        }
        if (hostRepository.findByHostEmail(reservation.getHost().getHostEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
    }
    private void validNotDuplicate(Reservation reservation, Result<Reservation> result, Host hostEmail) {
        List<Reservation> reservations = reservationRepository.findByHostEmail(hostEmail);
        for (Reservation r: reservations) {
            if (  Objects.equals(reservation.getGuest().getGuestEmail(), r.getGuest().getGuestEmail())
                    && Objects.equals(reservation.getStartDate(), r.getStartDate())); {
                        result.addErrorMessage("Double Booking not allowed");
            }
        }
    }

}
