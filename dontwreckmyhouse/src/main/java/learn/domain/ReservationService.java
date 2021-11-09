package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
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


    public List<Reservation> findByHostID(String hostId) throws DataException {

        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuestId(), i -> i));
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getReservationId(), i -> i));

        List<Reservation> result = reservationRepository.findByHostID(hostId);
        for (Reservation reservation : result) {
            reservation.setGuest(guestMap.get(reservation.getGuestId()));
            reservation.setHost(hostMap.get(reservation.getHost().getReservationId()));
        }
        return result;
    }

    public List<Reservation> findByHostIdAndGuestId(Host host, Guest guest) throws DataException {
        List<Reservation> result = new ArrayList<>();
        if (host == null) {
            return null;
        }
        if (guest == null) {
            return null;
        }
        List<Reservation> reservations = findByHostID(host.getReservationId()); // amountc@ehow.com  charley4@apple.com
        for (Reservation reservation : reservations) {
            if (reservation.getGuestId() == guest.getGuestId()) {
                reservation.setHost(host);
                reservation.setGuest(guest);
                result.add(reservation);
            }
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

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        List<Reservation> exists = reservationRepository.findByHostID(reservation.getHost().getReservationId());
        if (exists == null) {
            result.addErrorMessage("Reservation Id: " + reservation.getHost().getReservationId() + " does not exist.");
        }

        boolean success = reservationRepository.update(reservation);
        if (!success) {
            result.addErrorMessage("Could not find reservation" + reservation.getReservationId());
        }
        return result;

    }

    public Result<Reservation> cancelReservation(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<Reservation>();
        if (reservation == null) {
            result.addErrorMessage("Could not find reservation");
        }
        boolean success = reservationRepository.cancelReservation(reservation);
        if (!success) {
            result.addErrorMessage("Could not find reservation.");
        }
        return result;

    }


    public BigDecimal costPerStay(LocalDate start, LocalDate end, Host hostId) {
//        //get id //loop days of the week / count weekdays count weekends / multiply type of day by respective rates
        BigDecimal result = BigDecimal.ZERO;
        int countWeekday = 0;
        int countWeekend = 0;

        do {

            if (DayOfWeek.SATURDAY == start.getDayOfWeek()
                    || DayOfWeek.FRIDAY == start.getDayOfWeek()) {
                countWeekend++;
            } else {
                countWeekday++;
            }
            start = start.plusDays(1);
        } while (!(start.isEqual(end.plusDays(1))));
        result = (hostId.getWeekend_rate().multiply(BigDecimal.valueOf(countWeekend))
                .add(hostId.getStandard_rate().multiply(BigDecimal.valueOf(countWeekday)))).setScale(2, RoundingMode.HALF_UP);
        return result;
    }


    private Result<Reservation> validate(Reservation reservation) throws DataException {
        Result<Reservation> result = validateNullInput(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        validateField(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }
        validateChildrenNotNull(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }
        validateNotOverlap(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        String hostID = reservation.getHost().getHostEmail();
        validNotDuplicate(reservation, result, hostID);
        return result;
    }
    private void validateNotOverlap(Reservation reservation , Result<Reservation> result) throws DataException {

        List<Reservation>  reservations = reservationRepository.findByHostID(reservation.getHost().getReservationId());
        LocalDate startReservation = reservation.getStartDate();
        LocalDate endReservation = reservation.getEndDate();
        for (Reservation r : reservations) {
            LocalDate startExisting = r.getStartDate();
            LocalDate endExisting = r.getEndDate();
            if (startReservation.isBefore(startExisting) && endReservation.isAfter(startExisting)) {
                result.addErrorMessage("Dates must not overlap with other reservation dates.");
            }
            if (startReservation.isAfter(startExisting) && startReservation.isBefore(endExisting)) {
                result.addErrorMessage("Dates must not overlap with other reservation dates.");
            }

        }
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
        if (reservation.getGuestId() <= 0) {
            result.addErrorMessage("Guest Id must be greater than 0.");
        }
        return result;
    }

    private void validateField(Reservation reservation, Result<Reservation> result) {
        LocalDate today = LocalDate.now();
        if (reservation.getStartDate().isBefore(today)
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot create reservations for the past.");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start date must be before end date.");
        }



    }

    private void validateChildrenNotNull(Reservation reservation, Result<Reservation> result) throws DataException {
        if (reservation.getGuestId() <= 0
                || guestRepository.findByGuestEmail(reservation.getGuest().getGuestEmail()) == null) {
            result.addErrorMessage("Guest doesn't exist.");
        }
        if (hostRepository.findByHostEmail(reservation.getHost().getHostEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
    }

    private void validNotDuplicate(Reservation reservation, Result<Reservation> result, String hostId) throws DataException {
        List<Reservation> reservations = reservationRepository.findByHostID(hostId);
        for (Reservation r : reservations) {
            if (Objects.equals(reservation.getGuest().getGuestEmail(), r.getGuest().getGuestEmail())
                    && Objects.equals(reservation.getStartDate(), r.getStartDate())) ;
            {
                result.addErrorMessage("Double Booking not allowed");
            }
            if (reservation.getStartDate().isBefore(r.getEndDate())) {
                result.addErrorMessage("You cannot overlap other bookings.");
            }
        }
    }

}
