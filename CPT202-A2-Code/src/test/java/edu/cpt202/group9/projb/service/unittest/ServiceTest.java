package edu.cpt202.group9.projb.service.unittest;

import org.junit.jupiter.api.Test;

public class ServiceTest {
    @Test
    public void testGetServiceName() {
        edu.cpt202.group9.projb.service.Service service = new edu.cpt202.group9.projb.service.Service();
        service.setServiceName("Haircut");
        String serviceName = service.getServiceName();

        assert(serviceName.equals("Haircut"));
    }
}
