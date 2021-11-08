package learn.data;

import learn.model.Guest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    public final static Guest GUEST = makeGuest();
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);

    }
    private static Guest makeGuest() {
        Guest guest = new Guest();
        guest.setGuestId(1);
        guest.setGuestEmail("guesttest@email.com");
        guest.setGuestState("NY");
        guest.setLastName("McGuestTest");
        guest.setFirstName("GTest");
        guest.setGuestPhone("(123) 3125465");
        guest.setGuestState("Testers");
        return guest;
    }

    @Override
    public Guest findByGuestEmail(String guestEmail) {
        return findAll().stream()
                .filter(i -> i.getGuestEmail().equalsIgnoreCase(guestEmail))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Guest findByGuestId(int guestId) {
        return findAll().stream().filter(i -> i.getGuestId() == guestId)
                .findFirst()
                .orElse(null);
    }
}
