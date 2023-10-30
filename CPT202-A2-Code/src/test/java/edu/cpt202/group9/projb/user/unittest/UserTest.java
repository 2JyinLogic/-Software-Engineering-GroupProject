package edu.cpt202.group9.projb.user.unittest;

import static org.junit.jupiter.api.Assertions.*;

import edu.cpt202.group9.projb.user.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testGetId() {
        User user = new User();
        user.setId(1L);
        Long id = user.getId();

        assertNotNull(id);
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void testSetId() {
        User user = new User();
        user.setId(1L);
        Long id = user.getId();

        assertNotNull(id);
        assertEquals(Long.valueOf(1), id);

        try {
            user.setId(-1L);
        } catch (IllegalArgumentException e) {
            assertEquals("id must be greater than zero", e.getMessage());
        }
    }

    @Test
    public void testGetFirstName() {
        User user = new User();
        user.setFirstName("John");
        String firstName = user.getFirstName();

        assertNotNull(firstName);
        assertEquals("John", firstName);
    }

    @Test
    public void testSetFirstName() {
        User user = new User();
        user.setFirstName("John");
        String firstName = user.getFirstName();

        assertNotNull(firstName);
        assertEquals("John", firstName);
    }

    @Test
    public void testGetLastName() {
        User user = new User();
        user.setLastName("Doe");
        String lastName = user.getLastName();

        assertNotNull(lastName);
        assertEquals("Doe", lastName);
    }

    @Test
    public void testSetLastName() {
        User user = new User();
        user.setLastName("Doe");
        String lastName = user.getLastName();

        assertNotNull(lastName);
        assertEquals("Doe", lastName);
    }

    @Test
    public void testGetPhoneNum() {
        User user = new User();
        user.setPhoneNum(123456789L);
        Long phoneNum = user.getPhoneNum();

        assertNotNull(phoneNum);
        assertEquals(Long.valueOf(123456789), phoneNum);
    }

    @Test
    public void testSetPhoneNum() {
        User user = new User();
        user.setPhoneNum(123456789L);
        Long phoneNum = user.getPhoneNum();

        assertNotNull(phoneNum);
        assertEquals(Long.valueOf(123456789), phoneNum);
    }

    @Test
    public void testGetEmail() {
        User user = new User();
        user.setUserEmail("john.doe@example.com");
        String email = user.getUserEmail();

        assertNotNull(email);
        assertEquals("john.doe@example.com", email);
    }

    @Test
    public void testSetEmail() {
        User user = new User();
        user.setUserEmail("john.doe@example.com");
        String email = user.getUserEmail();

        assertNotNull(email);
        assertEquals("john.doe@example.com", email);

        try {
            user.setUserEmail("invalid-email");
        } catch (IllegalArgumentException e) {
            assertFalse(user.isValidEmail("invalid-email"));
        }
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUserEmail("john.doe@example.com");

        String expected = "User{id=1, email'john.doe@example.com'}";
        String actual = user.toString();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}