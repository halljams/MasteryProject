package learn.domain;

import learn.data.HostFileRepository;
import learn.data.HostRepository;
import learn.model.Host;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HostService {
    private final HostRepository repository;

    public HostService(HostFileRepository repository) {this.repository = repository;}

    public Host findByHostEmail(String hostEmail) {return repository.findByHostEmail(hostEmail);
    }

    public List<Host> findByCity(String prefix) {
        List<Host> hosts = new ArrayList<>();
        for (Host host : repository.findAll()) {
            if (host.getCity().contains(prefix)) {
                hosts.add(host);
            }
        }
        return hosts;
    }
}
