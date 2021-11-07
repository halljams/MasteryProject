package learn.ui;

import learn.model.Guest;
import learn.model.Host;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


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
        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    //gets
    public String getHostEmail(Host host) {
        return null;
    }
    public String getHostEmail() {
        return io.readRequiredString("Enter Host's Email:  ");
    }
    public String getGuestEmail() {
        return io.readRequiredString("Enter Guest's Email: ");
    }
    public LocalDate getStartDate() {
        return io.readLocalDate("Input the date for start of stay [MM/dd/yyyy]: ");
    }
    public LocalDate getEndDate() {
        return io.readLocalDate("Input the date for end of stay [MM/dd/yyyy]: ");
    }

    //support gets
    public String getHostwithAidOfPrefixCity() {
        return io.readRequiredString("Host located in City starting with: ");
    }
    public String getGuestLastNamePrefix() {
        return io.readRequiredString("Guest last name starts with: ");
    }

    //choose
    public Host chooseLocation(List<Host> locations) {
        if (locations.size() == 0) {
            io.println("Location not found.");
            return null;
        }
        int index = 1;
        for (Host host : locations.stream().limit(10).collect(Collectors.toList())) {
            io.printf("%s: %s %s%n", index++, host.getCity(), host.getHostEmail());
        }
        index--;

        if (locations.size() > 10) {
            io.println("More than 10 options found in this location. Please enter your desired location more accurately.");
        }
        io.println("0: Exit");
        String message = String.format("Select host by value [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return locations.get(index - 1);

    }
    public Guest chooseGuest(List<Guest> guests) {
        if (guests.size() == 0) {
            io.println("Name not found.");
            return null;
        }
        int index = 1;
        for (Guest guest : guests.stream().limit(10).collect(Collectors.toList())) {
            io.printf("%s: %s %s%n", index++, guest.getLastName(), guest.getGuestEmail());
        }
        index--;

        if (guests.size() > 10) {
            io.println("More than 10 guests with these characters found. Please enter the name more accurately.");
        }
        io.println("0: Exit");
        String message = String.format("Select a name by the index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return guests.get(index - 1);
    }


    //creation
    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        return reservation;
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

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }

    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("%s: %s - %s  - %s, %s  - %s%n",
                    reservation.getReservationId(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getGuestEmail()
            );
        }
    }
    public void displayCostSummary(LocalDate start, LocalDate end, BigDecimal total) {
        if(start == null || end == null || total == null){
            io.println("Inputs needed.");
            return;
        }
        io.println("Summary");
        io.println("=".repeat(15));{
            io.printf("Start date: %s%nEnd date: %s%nTotal cost of stay = $ %s%n",
                    start,
                    end,
                    total);
        }


    }


    public void displayByHost(List<Host> hosts) {
        if (hosts == null || hosts.isEmpty()) {
            io.println("No hosts found.");
        }
        for (Host host : hosts) {
            io.printf("%s - %s - %s - %s, %s%n",
                    host.getReservationId(),
                    host.getHostEmail(),
                    host.getLast_name(),
                    host.getCity(),
                    host.getState());
        }
    }

    public void displayGuest(List<Guest> guests) {
        if (guests == null || guests.isEmpty()) {
            io.println("No guest found.");
        }
        for (Guest guest : guests) {
            io.printf("%s: %s,%s %s",
                    guest.getGuestId(),
                    guest.getLastName(),
                    guest.getFirstName(),
                    guest.getGuestEmail());
        }
    }
    //continue
    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }



}
