package learn.data;

import learn.model.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findByHostEmail(String hostEmail);

    Host findHostById(String hostId);
}
