package learn.domain;

import learn.data.HostFileRepository;
import learn.data.HostRepository;
import learn.model.Host;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HostService {
    private final HostRepository repository;

    public HostService(HostFileRepository repository) {this.repository = repository;}

    public List<Host> findByHostEmail(String hostEmail) {return repository.findByHostEmail(hostEmail);
    }

}
