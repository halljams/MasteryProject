package learn.data;

import learn.model.Guest;

import java.util.List;

public interface GuestRepository {
    public List<Guest> findByEmail(String guestEmail);
    public List<Guest> findAll();

}
