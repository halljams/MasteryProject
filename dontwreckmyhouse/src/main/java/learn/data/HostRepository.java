package learn.data;

import learn.model.Host;

import java.util.List;

public interface HostRepository {
    public List<Host> findAll();
}
