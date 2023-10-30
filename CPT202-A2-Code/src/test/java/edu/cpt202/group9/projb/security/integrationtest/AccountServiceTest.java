/*
 * AccountServiceTest.java
 * Last modified 2023.4.7
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import edu.cpt202.group9.projb.security.Account;
import edu.cpt202.group9.projb.security.AccountRepository;
import edu.cpt202.group9.projb.security.AccountService;

/**
 * Contains unit tests for account accesser's interaction with the database.
 * 
 * @version 2023.4.7
 * @since 2023.4.7
 * @author Guanyuming He
 */
@AutoConfigureTestEntityManager
@SpringBootTest(properties = "spring.profiles.active=test")
@Sql(scripts = "test-clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AccountServiceTest {

    @Autowired
    private AccountService acc;

    @Autowired
    private AccountRepository repo;
    
    /**
     * Tests findAccountByUsername when the user is present
     */
    @Test
    public void testFindAccountByUsernamePresent() {
        String name = "thisissomeone";
        Account a = new Account(name, "123456abc", Account.getRoleString(false));
        repo.save(a);

        var ret = acc.findAccountByUsername(name);

        assertTrue(ret.isPresent());
        assertEquals(ret.get(), a);
    }

    /**
     * Tests findAccountByUsername when the user is not present
     */
    @Test
    public void testFindAccountByUsernameAbsent() {
        /* when the database is empty */
        var ret = acc.findAccountByUsername("thisissomeone");
        assertFalse(ret.isPresent());

        Account a = new Account("thisissomeone2", "123456abc", Account.getRoleString(true));
        repo.save(a);

        /* when the database is not empty */
        ret = acc.findAccountByUsername("thisissomeone");
        assertFalse(ret.isPresent());
    }

    /**
     * Tests findManagerAccount when the manager is present
     */
    @Test
    public void testFindManagerAccountPresent() {
        Account a = new Account("Realshopmanager", "123456789", Account.getRoleString(true));
        repo.save(a);

        Account mgr = acc.findManagerAccount();

        assertTrue(mgr != null);
        assertEquals(mgr, a);
    }

    /**
     * Tests findManagerAccount when the manager is not present
     */
    @Test
    public void testFindManagerAccountAbsent() {
        /* when the database is empty */
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            acc.findManagerAccount();
        });

        Account a = new Account("Fakeshopmanager", "123456abc", Account.getRoleString(false));
        repo.save(a);

        /* when the database is not empty */
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            acc.findManagerAccount();
        });
    }

    /*
     * Whitebox testing: hasUsername actually is exactly return findAccountByUsername(username).isPresent();
     * Therefore, no tests are needed for it.
     */

    /**
     * Tests TryAddNewAccount when there are no account with the username so it's expected to succeed.
     * 
     * Requires that hasUsername(), i.e. FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryAddNewAccountSuccess() {
        String name1 = "iamaname...",
        name2 = "iamanothername...";

        /* when the database is empty */
        assertTrue(acc.tryAddNewAccount(name1, "iamapassword"));
        assertTrue(acc.hasUsername(name1));

        /* when the database is not empty */
        assertTrue(acc.tryAddNewAccount(name2, "iamanotherpassword"));
        assertTrue(acc.hasUsername(name2));
    }

    /**
     * Tests TryAddNewAccount when there are already an account with username so it's expected to fail.
     * Requires that hasUsername(), i.e. FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryAddNewAccountFailure() {
        String name = "iamaname...";
        /* expected to add the account with username */
        acc.tryAddNewAccount(name, "iamapassword");

        assertFalse(acc.tryAddNewAccount(name, "iamanotherpassword"));
    }

    /**
     * Tests TryDeleteAccount when there are already an account with username so it's expected to succeed.
     * Requires that hasUsername(), i.e. FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryDeleteAccountSuccess() {
        String name = "28ha;.  -129m";
        repo.save(new Account(name, "asdhf823j4klf", Account.getRoleString(false)));

        assertTrue(acc.tryDeleteAccount(name));
        assertFalse(acc.hasUsername(name));
    }

    /**
     * Tests TryAddNewAccount when there are no account with the username so it's expected to fail.
     * Requires that hasUsername(), i.e. FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryDeleteAccountFailure() {
        String name = "28ha;.  -129m";

        /* when the database is empty */
        assertFalse(acc.tryDeleteAccount(name));

        repo.save(new Account(name, "asdhf823j4klf", Account.getRoleString(false)));

        /* Expected to remove it */
        acc.tryDeleteAccount(name);

        /* when the database is empty */
        assertFalse(acc.tryDeleteAccount(name));

        repo.save(new Account(name + "123", "asdhf823j4klf", Account.getRoleString(false)));

        /* when the database is not empty */
        assertFalse(acc.tryDeleteAccount(name));
    }

    /**
     * Tests TryAddNewAccount when there are already an account with username so it's expected to succeed.
     * Requires that hasUsername(), i.e. FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryEditAccountUsernameSuccess() {
        String name = "to be changed.";
        String newName = "new ........";
        String pass = "81jn08912j";
        repo.save(new Account(name, pass, Account.getRoleString(false)));

        assertTrue(acc.tryEditAccountUsername(name, newName));
        var ret = acc.findAccountByUsername(newName);
        assertTrue(ret.isPresent());
        assertEquals(pass, ret.get().getUserPassword());
        assertEquals(Account.getRoleString(false), ret.get().getUserRole());
        /* The previous one should no long exist */
        assertFalse(acc.hasUsername(name));
    }

    /**
     * Tests TryAddNewAccount when there are no account with the username so it's expected to fail.
     */
    @Test
    public void testTryEditAccountUsernameFailure() {
        String name = "k0d8h1in?208";
        String pass = "81jn08912j";

        /* when the database is empty */
        assertFalse(acc.tryEditAccountUsername(name, "si01j2i0hdl"));

        repo.save(new Account(name, pass, Account.getRoleString(false)));

        /* when the database is not empty */
        assertFalse(acc.tryEditAccountUsername("01jkj" + name, "si01j2i0hdl"));
    }

        /**
     * Tests TryAddNewAccount when there are already an account with username so it's expected to succeed.
     * Requires that FindAccountByUsername() works correctly.
     */
    @Test
    public void testTryEditAccountPasswordSuccess() {
        String name = "i can live now!";
        String pass = "NOOOOOOOOO";
        String newPass = ".....new...";
        repo.save(new Account(name, pass, Account.getRoleString(false)));

        assertTrue(acc.tryEditAccountPassword(name, newPass));
        var ret = acc.findAccountByUsername(name);
        assertTrue(ret.isPresent());
        assertEquals(newPass, ret.get().getUserPassword());
    }

    /**
     * Tests TryAddNewAccount when there are no account with the username so it's expected to fail.
     */
    @Test
    public void testTryEditAccountPasswordFailure() {
        String name = "k0d8h1in?208";
        String pass = "81jn08912j";

        /* when the database is empty */
        assertFalse(acc.tryEditAccountPassword(name, "si01j2i0hdl"));

        repo.save(new Account(name, pass, Account.getRoleString(false)));

        /* when the database is not empty */
        assertFalse(acc.tryEditAccountPassword("01jkj" + name, "si01j2i0hdl"));
    }
}