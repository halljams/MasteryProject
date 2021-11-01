package learn.ui;

import learn.data.DataException;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;

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
                case VIEW_BY_HOST:
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

    private void viewByHost() {

    }
    private void makeReservation() {

    }
    private void editReservation() {

    }
    private void cancelReservation() {

    }
}
