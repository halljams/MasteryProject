package learn.ui;

import learn.model.Reservation;

import java.util.List;

public class View {
    private final ConsoleIO io;
    public View(ConsoleIO io) {
        this.io = io;
    }
    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }
        String message = String.format("Select [%s-%s]: ", min, max -1);
        return MainMenuOption.fromValue(io.readInt(message,min,max));
    }


    //displays
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }
    public void displayException(Exception ex) {
        displayHeader("A critical error has occurred.");
        io.println(ex.getMessage());
    }
    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation: reservations) {
            io.printf("",
                    reservation.getReservationId(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getGuest(),
                    reservation.getGuestEmail()
            );
        }
    }
}
