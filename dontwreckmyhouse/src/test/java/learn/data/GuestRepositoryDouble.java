package learn.data;

import learn.model.Guest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    public GuestRepositoryDouble() {


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
