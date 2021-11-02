package learn.domain;

import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
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
    public long getDateRange(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start,end);
    }

}
