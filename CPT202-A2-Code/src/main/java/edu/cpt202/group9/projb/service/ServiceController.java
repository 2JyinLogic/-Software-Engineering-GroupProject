 package edu.cpt202.group9.projb.service;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/services")
 public class ServiceController {
     @Autowired
     private ServiceRepo serviceRepo;

 }
