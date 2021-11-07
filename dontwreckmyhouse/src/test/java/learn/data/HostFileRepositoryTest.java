package learn.data;

import learn.model.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    HostFileRepository repository = new HostFileRepository("./reservation-test/host-test.csv");


    @Test
    void shouldFindAll() {
        assertTrue(repository.findAll().size() > 0);

    }
    @Test
    void shouldFindYearnes() {
        Host yearnes = repository.findByHostEmail("eyearnes0@sfgate.com");
        assertNotNull(yearnes);
        assertEquals("Yearnes" , yearnes.getLast_name());
        assertEquals("Amarillo", yearnes.getCity());
    }

    @Test
    void shouldFindRhodes() {
        Host rhodes = repository.findHostById("a0d911e7-4fde-4e4a-bdb7-f047f15615e8");
        assertNotNull(rhodes);
        assertEquals("Rhodes", rhodes.getLast_name());
    }

}