package edu.cpt202.group9.projb.user.integrationtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.cpt202.group9.projb.user.*;
import edu.cpt202.group9.projb.security.Account;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureTestEntityManager
@Transactional
public class UserServicesTest {
    @Autowired
    private UserServicesImpl userService;
    @Autowired
    private UserRepo userRepo;
    private User testUser;
    private Account testAccount;

    @BeforeEach
    public void setUp() {
        userRepo.deleteAll();
        testAccount = new Account("test", "password", "USER");
        testUser = new User("test@test.com", testAccount);
        userRepo.save(testUser);
    }

    @Test
    @Transactional
    public void testAddUser() {
        testAccount = new Account("add", "password", "USER");
        User newUser = new User("add@test.com", testAccount);

        User addUser = userService.addUser("add@test.com", testAccount);

        assertNotNull(addUser.getId());
        assertEquals(newUser.getUserEmail(), addUser.getUserEmail());
    }

    // @Test
    // @Transactional
    // public void testDeleteUser() {
    //     userService.deleteUser(testUser.getId());

    //     assertEquals(0, userRepo.count());
    // }

    @Test
    @Transactional
    public void testUpdateUserEmail() {
        String newEmail = "update@test.com";
        User updatedUser = userService.updateUserEmail(testUser.getId(), newEmail);

        assertNotNull(updatedUser);
        assertEquals(newEmail, updatedUser.getUserEmail());
    }

    // @Test
    // @Transactional
    // public void testFindAllUsers() {
    //     List<User> users = userService.findAllUsers();

    //     assertEquals(1, users.size());
    //     assertEquals(testUser.getEmail(), users.get(0).getEmail());
    // }

    // @Test
    // @Transactional
    // public void testFindUserByEmail() {
    //     try {
    //         Optional<User> foundUser = userService.findUserByEmail(testUser.getEmail());
    //         assertTrue(foundUser.isPresent());
    //         assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    //     } catch (NoSuchElementException e) {
    //         fail("User not found by email address");
    //     } catch (IllegalArgumentException e) {
    //         fail("Invalid email address argument");
    //     }
    // }
}