package edu.cpt202.group9.projb.service.integrationtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.cpt202.group9.projb.service.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureTestEntityManager
@Transactional
public class ServiceServicesTest {
    @Autowired
    private ServiceServicesImpl serviceServices;
    @Autowired
    private ServiceRepo serviceRepo;
    private edu.cpt202.group9.projb.service.Service testService;
    @BeforeEach
    public void setUp(){
        serviceRepo.deleteAll();
        testService = new edu.cpt202.group9.projb.service.Service("Haircut", 20.0, "Haircut");
        serviceRepo.save(testService);
    }

    @Test
    @Transactional
    public void testAddService() {
        Service newService = new Service("PoorHaircut", 10.0, "Haircut");

        Service addService = serviceServices.addService("PoorHaircut", 10.0, "Haircut");

        assertNotNull(addService.getServiceName());
        assertEquals(newService.getServiceName(), addService.getServiceName());
    }
}
