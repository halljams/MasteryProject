package learn.domain;

import learn.data.GuestRepository;
import learn.model.Guest;

import java.util.List;

public class GuestService {
    private final GuestRepository repository;
    public GuestService(GuestRepository repository) {this.repository = repository;}

    public Guest findByGuestEmail(String guestEmail) {return  repository.findByGuestEmail(guestEmail);}
    public Guest findByGuestId(int guestId) {return repository.findByGuestId(guestId);
    }
}
