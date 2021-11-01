package learn.data;

import learn.model.Host;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class HostFileRepository implements HostRepository {

    private final String filePath;
    private static final String HEADER = "hostEmail,location,rent/day";

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 4) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }
    private String serialize(Host host) {
        return String.format("%s,%s,%s",
                host.getHostEmail(),
                host.getLocation(),
                host.getRentPerDay());
    }
    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setHostEmail(fields[0]);
        result.setLocation(fields[1]);
        result.setRentPerDay(BigDecimal.valueOf(Long.parseLong(fields[2])));
        return result;
    }

    protected void writeAll(List<Host> hosts) throws DataException {
        try(PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);

            if (hosts == null) {
                return;
            }
            for (Host host:hosts) {
                writer.println(serialize(host));
            }
        }catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
