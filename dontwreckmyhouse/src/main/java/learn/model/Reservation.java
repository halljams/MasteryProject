package learn.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guestEmail;
    private Guest guestName;
    private Host host;
    private BigDecimal total;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Guest getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(Guest guestEmail) {
        this.guestEmail = guestEmail;
    }



    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }


    public Guest getGuest() {
        return guestName;
    }

    public void setGuest(Guest guestName) {
        this.guestName = guestName;
    }

    public Host getHost() {
        return host;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setGuest(String valueOf) {
    }
}
