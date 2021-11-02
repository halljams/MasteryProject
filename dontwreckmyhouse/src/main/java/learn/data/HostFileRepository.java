package learn.data;

import learn.model.Host;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostFileRepository implements HostRepository {

    private final String filePath;
    private static final String HEADER = "reservationId,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";

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
    public List<Host> findByHostEmail(String hostEmail) {
        return findAll().stream()
                .filter(i -> i.getHostEmail().equalsIgnoreCase(hostEmail))
                .collect(Collectors.toList());
    }
    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getReservationId(),
                host.getLast_name(),
                host.getHostEmail(),
                host.getPhone(),
                host.getAddress(),
                host.getCity(),
                host.getState(),
                host.getPostal_code(),
                host.getStandard_rate(),
                host.getStandard_rate());
    }
    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setReservationId(String.valueOf(fields[0]));
        result.setLast_name(String.valueOf(fields[1]));
        result.setHostEmail(String.valueOf(fields[2]));
        result.setPhone(Integer.parseInt(fields[3]));
        result.setAddress(String.valueOf(fields[4]));
        result.setCity(String.valueOf(fields[5]));
        result.setState(String.valueOf(fields[6]));
        result.setPostal_code(Integer.parseInt(fields[7]));
        result.setStandard_rate(BigDecimal.valueOf(Long.parseLong(fields[8]), 2));
        result.setWeekend_rate(BigDecimal.valueOf(Long.parseLong(fields[9]), 2));
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
