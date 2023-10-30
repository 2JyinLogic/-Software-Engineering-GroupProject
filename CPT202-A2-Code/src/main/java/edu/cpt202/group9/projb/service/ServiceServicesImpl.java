package edu.cpt202.group9.projb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServicesImpl implements ServiceServices {
    @Autowired
    private ServiceRepo serviceRepo;

    @Override
    public edu.cpt202.group9.projb.service.Service addService(String serviceName, double servicePrice, String serviceType) {
        edu.cpt202.group9.projb.service.Service service = new edu.cpt202.group9.projb.service.Service(serviceName, servicePrice, serviceType);
        return serviceRepo.save(service);
    }

    @Override
    public List<edu.cpt202.group9.projb.service.Service> findAllServices() {
        return serviceRepo.findAll();
    }

    @Override
    public List<edu.cpt202.group9.projb.service.Service> findServicesByType(String serviceType) {
        return serviceRepo.findByServiceType(serviceType);
    }

    @Override
    public Optional<edu.cpt202.group9.projb.service.Service> findServiceByName(String serviceName) {
        return serviceRepo.findByServiceName(serviceName);
    }

}
