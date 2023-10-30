package edu.cpt202.group9.projb.service;

import java.util.List;
import java.util.Optional;

public interface ServiceServices {

    public Service addService(String serviceName, double servicePrice, String serviceType);

    public List<Service> findAllServices();

    public List<Service> findServicesByType(String serviceType);

    public Optional<Service> findServiceByName(String serviceName);
}
