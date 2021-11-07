package learn.data;

import learn.model.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HostRepositoryDouble implements HostRepository{
    public final static Host HOST = makeHost();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(HOST);
    }

    private static Host makeHost() {
        Host host = new Host();
        host.setReservationId("test-host-id");
        host.setLast_name("test");
        host.setHostEmail("test@email.com");
        host.setPhone("(123) 4567899");
        host.setAddress("7 Test Rd");
        host.setCity("Test");
        host.setState("TS");
        host.setPostal_code("12345");
        host.setStandard_rate(new BigDecimal("123.00"));
        host.setWeekend_rate(new BigDecimal("234.00"));
        return host;
    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findByHostEmail(String hostEmail) {
        return hosts.get(0);
    }

    @Override
    public Host findHostById(String hostId) {
        return hosts.get(0);
    }
}
