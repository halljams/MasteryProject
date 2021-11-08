package learn.ui;

import learn.data.DataException;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.domain.Result;
import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }
    public void run() {
        view.displayHeader("Welcome to the Reservation Service");
        try {
            runAppLoop();

        }catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye");
    }
    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    viewByHost();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        }while (option!= MainMenuOption.EXIT);
    }

    private void viewByHost() throws DataException {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByHostEmail(hostEmail);
        List<Reservation> reservations = reservationService.findByHostID(host.getReservationId());
        view.displayReservations(reservations);
        view.enterToContinue();
    }
    private void makeReservation() throws DataException {
        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByGuestEmail(guestEmail);
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByHostEmail(hostEmail);

        Reservation reservation = view.makeReservation(host, guest);
        reservation.setGuestId(guest.getGuestId());
        //prompt and response for date ranges and total cost
        LocalDate  startDate = view.getStartDate();
        LocalDate endDate = view.getEndDate();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        BigDecimal costOfStay = reservationService.costPerStay(startDate, endDate, host);
        reservation.setTotal(costOfStay);
        view.displayCostSummary(startDate,endDate,costOfStay);
        String confirmation = view.confirmation();
        {
            if (confirmation == null) {
                view.confirmation();
            }
            if (confirmation.equalsIgnoreCase("n")) {
                return;
            }
        }

        Result<Reservation> result = reservationService.add(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s has been made.", result.getPayload().getReservationId());
            view.displayStatus(true, successMessage);
        }

    }
    private void editReservation() throws DataException {

        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByGuestEmail(guestEmail);
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByHostEmail(hostEmail);
        Reservation reservation = reservationService.findByHostIdAndGuestId(host, guest);
        view.displayReservation(host,guest,reservation);
        // ^ reservation needs to be grabbed

        LocalDate newStart = view.editStartofReservation(reservation);
        if(newStart == null) {
            newStart = reservation.getStartDate();
        }
        LocalDate newEnd = view.editEndofReservation(reservation);
        reservation.setStartDate(newStart);
        reservation.setEndDate(newEnd);
        BigDecimal costOfStay = reservationService.costPerStay(newStart, newEnd, host);
        reservation.setTotal(costOfStay);
        view.displayCostSummary(newStart,newEnd,costOfStay);
        String confirmation = view.confirmation();
        {
            if (confirmation == null) {
                view.confirmation();
            }
            if (confirmation.equalsIgnoreCase("n")) {
                return;
            }
        }
        Result<Reservation> result = reservationService.update(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s has been edited.", result.getPayload().getReservationId());
            view.displayStatus(true, successMessage);
        }
    }
    private void cancelReservation() throws DataException {
        view.displayHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByGuestEmail(guestEmail);
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByHostEmail(hostEmail);
        Reservation reservation = reservationService.findByHostIdAndGuestId(host, guest);
        view.displayReservation(host,guest,reservation);
        // ^ reservation needs to be grabbed

        Result<Reservation> result = reservationService.cancelReservation(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s has been canceled.", result.getPayload().getReservationId());
            view.displayStatus(true, successMessage);
        }

    }
//support
        private Host getHostLocal() {
            String location = view.getHostwithAidOfPrefixCity();
            List<Host> hosts = hostService.findByCity(location);
            return view.chooseLocation(hosts);
        }
        private Guest getGuest() {
        String lastNamePrefix = view.getGuestLastNamePrefix();
        List<Guest> guests = Collections.singletonList(guestService.findByGuestEmail(lastNamePrefix));
        return view.chooseGuest(guests);
        }
    }

