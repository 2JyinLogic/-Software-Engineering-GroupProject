/*
 * AccountTest.java
 * Last modified 2023.4.7
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.cpt202.group9.projb.security.Account;

/**
 * Tests class Account in units.
 * In the end, all methods of Account will be put together in a test.
 * 
 * @author Guanyuming He
 * @version 2023.4.7
 * @since 2023.4.5
 */
public class AccountTest {

    /* The username used when username is not the parameter to test */
    static String genearlUsername = null;
    /* The password used when password is not the parameter to test */
    static String generalPassword = null;

    /*
     * These are used for boundary tests.
     * Each string contains exactly a number of characters
     */
    static String fiveChars = null;
    static String sevenChars = null;
    static String eightChars = null;
    static String thirtyOneChars = null;
    static String thirtyTwoChars = null;

    @BeforeAll
    static void setValues() {
        genearlUsername = "iam238hfoa.";
        generalPassword = "a7jj12b+jf.a-";

        fiveChars = "a_?0d";
        sevenChars = "+123456";
        eightChars = "a+b-c999";
        thirtyOneChars = 
        "dsu812n0.9" +
        "134-a883h4" +
        "9a?1i!9h12" +
        "c";
        thirtyTwoChars = 
        "92.hf81jah" +
        "91ln.6-2m!" +
        "4819375mnn" +
        "?!";
    }

    /**
     * Tests Account's constructor when all parameters are correct.
     */
    @Test
    public void testConstructorAllCorrect() {
        /* Boundary situations */

        /* username boundary */
        new Account(fiveChars, generalPassword, "MANAGER");
        new Account(thirtyOneChars, generalPassword, "USER");

        /* password boundary */
        new Account(genearlUsername, eightChars, "USER");
        new Account(genearlUsername, thirtyOneChars, "MANAGER");

        /* role boundary */
        new Account(genearlUsername, generalPassword, "USER");
        new Account(genearlUsername, generalPassword, "MANAGER");

        /* General situations */
        new Account("dumbstudent", "123456789", "USER");
        new Account("who am i?", "81jsjb7.087b", "MANAGER");
    }

    /**
     * Tests Account's constructor when only username is invalid
     */
    @Test
    public void testConstructorInvalidUsername() {
        /* Boundary situations */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(new String(), generalPassword, "USER");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(thirtyTwoChars, generalPassword, "USER");
        });

        /* Other general cases (too long) */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, "189hs0j1h8y1820hnd8s1902jdh8018nh0f8dh813h091h80h108rh180h8fnh01h830r", "USER");
        });
    }

    /**
     * Tests Account's constructor when only password is invalid
     */
    @Test
    public void testConstructorInvalidPassword() {
        /* Boundary situations */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, sevenChars, "USER");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, thirtyTwoChars, "USER");
        });

        /* Contains illegal characters */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, "()abcabcabc", "USER");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, "how are you?", "USER");
        });

        /* Other general cases */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, fiveChars, "USER");
        });
    }

     /**
     * Tests Account's constructor when only role is invalid
     */
    @Test
    public void testConstructorInvalidRole() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, generalPassword, "ADMIN");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Account(genearlUsername, generalPassword, genearlUsername);
        });
    }

    /**
     * Tests the role strings given by Account.getRoleString()
     */
    @Test
    public void testGetRoleString() {
        assertEquals("MANAGER", Account.getRoleString(true));
        assertEquals("USER", Account.getRoleString(false));
    }

    /**
     * Tests if setUsername() sets the username correctly when the argument is legal.
     * 
     * Requires that the constructor, getRoleString, and getters work properly.
     */
    @Test
    public void testSetUsernameLegal() {
        Account acc = new Account("1amwh01am", "pass......", Account.getRoleString(true));

        /* Boundary cases */
        acc.setUsername(fiveChars);
        assertEquals(fiveChars, acc.getUsername());

        acc.setUsername(thirtyOneChars);
        assertEquals(thirtyOneChars, acc.getUsername());

        /* Genera-l case */
        String newName = "nowthisismynewname";
        acc.setUsername(newName);
        assertEquals(newName, acc.getUsername());
    }

    /**
     * Tests if setUserPassword() throws the correct exception if the argument is illegal
     * 
     * Requires that the constructor and getRoleString work properly.
     */
    @Test
    public void testSetUserPasswordIllegal() {
        Account acc = new Account("1amwh01am", "pass......", Account.getRoleString(true));

        /* Boundary cases */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            acc.setUserPassword(sevenChars);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            acc.setUserPassword(thirtyTwoChars);
        });

        /* general cases */
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            acc.setUserPassword(fiveChars);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            acc.setUserPassword("{}()...1234");
        });
    }

    /**
     * Tests if setUserPassword() sets the password correctly when the argument is legal.
     * 
     * Requires that the constructor, getRoleString, and getters work properly.
     */
    @Test
    public void testSetUserPasswordLegal() {
        Account acc = new Account("1amwh01am", "pass......", Account.getRoleString(true));

        /* Boundary cases */
        acc.setUserPassword(eightChars);
        assertEquals(eightChars, acc.getUserPassword());

        acc.setUserPassword(thirtyOneChars);
        assertEquals(thirtyOneChars, acc.getUserPassword());

        /* General case */
        String newPass = "abcdef123456.";
        acc.setUserPassword(newPass);
        assertEquals(newPass, acc.getUserPassword());
    }

    /**
     * Tests if equals returns true when two accounts are equal
     */
    @Test
    public void testAccountEqualsTrue() {
        Account a1 = new Account("abcde123", "123456abc", Account.getRoleString(false));
        Account a2 = new Account("abcde123", "123456abc", Account.getRoleString(false));

        assertTrue(a1.equals(a1));
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
    }

    /**
     * Tests if equals returns false when two accounts are not equal
     */
    @Test
    public void testAccountEqualsFalse() {
        Account a1 = new Account("abcde123", "123456abc", Account.getRoleString(false));
        Account a2 = new Account("abcde123", "123456abc", Account.getRoleString(true));

        assertFalse(a1.equals(a2));
        assertFalse(a2.equals(a1));

        a1 = new Account("abcde123", "8h17sdgb1", Account.getRoleString(true));
        a2 = new Account("abcde123", "123456abc", Account.getRoleString(true));

        assertFalse(a1.equals(a2));
        assertFalse(a2.equals(a1));

        a1 = new Account("username1", ".+i0912j_", Account.getRoleString(false));
        a2 = new Account("username2", ".+i0912j_", Account.getRoleString(false));

        assertFalse(a1.equals(a2));
        assertFalse(a2.equals(a1));
    }

    /**
     * Tests Account when all are put together. Kind of an integration test.
     */
    @Test
    public void testAccountAll() {
        Account mgr = new Account("shop manager", "administrator", Account.getRoleString(true));
        Account usr = new Account("common user", "abc123456", Account.getRoleString(false));

        assertEquals("shop manager", mgr.getUsername());
        assertEquals("administrator", mgr.getUserPassword());
        assertEquals("MANAGER", mgr.getUserRole());

        assertEquals("common user", usr.getUsername());
        assertEquals("abc123456", usr.getUserPassword());
        assertEquals("USER", usr.getUserRole());

        mgr.setUsername(usr.getUsername());
        usr.setUserPassword(mgr.getUserPassword());

        assertEquals(usr.getUsername(), mgr.getUsername());
        assertEquals(mgr.getUserPassword(), usr.getUserPassword());

        assertFalse(usr.equals(mgr));
    }
}
