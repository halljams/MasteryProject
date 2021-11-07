package learn.data;

import learn.model.Guest;
import learn.model.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    GuestFileRepository repository = new GuestFileRepository("./reservation-test/guest-test.csv");


    @Test
    void shouldFindAll() {
        assertTrue(repository.findAll().size() > 0);

    }
    @Test
    void shouldFindBerta() {
        Guest berta = repository.findByGuestEmail("bseppey4@yahoo.com");
        assertNotNull(berta);
        assertEquals("Seppey" , berta.getLastName());
        assertEquals("DC", berta.getGuestState());
    }

    @Test
    void shouldFindKenn() {
        Guest kenn = repository.findByGuestId(6);
        assertNotNull(kenn);
        assertEquals("Curson", kenn.getLastName());
        assertEquals("FL", kenn.getGuestState());
    }
}