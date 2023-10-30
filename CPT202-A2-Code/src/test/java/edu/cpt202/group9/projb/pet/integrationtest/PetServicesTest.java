package edu.cpt202.group9.projb.pet.integrationtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.cpt202.group9.projb.pet.*;
import edu.cpt202.group9.projb.user.*;
import edu.cpt202.group9.projb.security.Account;

import java.util.Optional;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureTestEntityManager
@Transactional
public class PetServicesTest {
    @Autowired
    private PetServices petServices;
    @Autowired
    private PetRepo petRepo;
    @Autowired
    private UserRepo userRepo;
    private Pet testPet;
    private User testUser;
    private Account testAccount;

    @BeforeEach
    public void setUp(){
        petRepo.deleteAll();
        testUser = new User("test@test.com", testAccount);
        testPet = new Pet("A Xiao", "Teddy", "5", "A healthy teddy lives in Liverpool", "XL", testUser);
        userRepo.save(testUser);
        petRepo.save(testPet);
    }

    @Test
    @Transactional
    public void testAddPet(){
        Pet newPet = new Pet("Wenhao", "Garfield", "6", "A lazy garfield lives in Liverpool", "XXL", testUser);

        Pet addPet = petServices.addPet("Wenhao", "Garfield", "6", "A lazy garfield lives in Liverpool", "XXL", testUser);

        assertEquals(newPet.getPetName(), addPet.getPetName());
    }

    @Test
    @Transactional
    public void testDeletePet(){
        petServices.deletePet(testPet.getId());

        assertEquals(0, petRepo.count());
    }

    @Test
    @Transactional
    public void testUpdatePet(){
        String newPetName = "B Xiao";
        Pet updatedPet = petServices.updatePet(testPet.getId(), newPetName, testPet.getPetBreed(), testPet.getPetAge(), testPet.getPetDescription(), testPet.getPetSize(), testPet.getUserId());

        assertNotNull(updatedPet);
        assertEquals(newPetName, updatedPet.getPetName());
    }

    @Test
    @Transactional
    public void testFindPetByPetName(){
        Optional<Pet> foundPet = petServices.findPetByPetName(testPet.getPetName());
        assertEquals(testPet.getPetName(), foundPet.get().getPetName());
    }
}
