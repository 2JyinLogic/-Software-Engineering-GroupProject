 package edu.cpt202.group9.projb.service;

 import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

 public interface ServiceRepo extends JpaRepository<Service, Long> {

     Optional<Service> findByServiceName(String serviceName);

     List<Service> findByServiceType(String serviceType);


 }