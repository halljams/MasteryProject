package learn.data;

import learn.model.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestFileRepository implements GuestRepository{
    private final String filePath;
    private static String header = "id,firstname,lastname,email";

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 4) {
                    result.add(deserialize(fields));
                }
            }
        }catch (IOException ex) {

        }
        return result;
    }
    public List<Guest> findByEmail(String guestEmail) {
        return findAll().stream()
                .filter(i -> i.getGuestEmail().equalsIgnoreCase(guestEmail))
                .collect(Collectors.toList());
    }

    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s",
                guest.getGuestId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getGuestEmail()
                );
    }
    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setGuestEmail(fields[3]);
        return result;
    }
}
