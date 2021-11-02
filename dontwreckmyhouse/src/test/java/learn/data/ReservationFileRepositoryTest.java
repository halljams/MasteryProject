package learn.data;

import learn.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    
    private static final String SEED_PATH = "./reservations-test/reservation-seed.csv";
    static final String TEST_PATH = "./reservations-test/reservation-test.csv";


    ReservationRepository repository = new ReservationFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(
                Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }
    @Test
    void findByHostEmail() {
    }

    @Test
    void shouldCreateReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(5);
        reservation.setStartDate(LocalDate.of(2021,10,12));
        reservation.setEndDate(LocalDate.of(2021,10, 15));
        reservation.setGuestId(5);
        reservation.setTotal(BigDecimal.valueOf(500));

        Reservation actual = repository.add(reservation);
        assertNotNull(actual);
        assertEquals(5, actual.getReservationId());

    }
    @Test
    void shouldFindByHostEmail() {

    }
}