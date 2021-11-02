package learn.data;

import learn.model.Guest;

import java.util.List;

public interface GuestRepository {
    public List<Guest> findByGuestEmail(String guestEmail);
    public List<Guest> findAll();
    public Guest findByGuestId(int guestId);

}
