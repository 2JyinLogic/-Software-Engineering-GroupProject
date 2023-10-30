/*
 * AccountService.java
 * Last modified 2023.4.7
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security;

import java.util.Optional;

/**
 * Abstracts interactions with AccountRepository into more caller-friendly methods.
 * In another word, contains business logic for handling accounts.
 * 
 * Expected operations to define:
 * 
 * Find an account by username
 * Find the manager's account
 * 
 * Edit an account's password
 * Edit an account's username
 * 
 * Add an account
 * Delete an account.
 * 
 * Because the manager's account is designed to be always present in the database and should only have one instance, 
 * the accesser does not provide anyway to edit an account's role. 
 * In addition, adding a new account through this class can only result in an account whose role is USER.
 * 
 * @since 2023.4.6
 * @version 2023.4.7
 * @author Guanyuming He
 */
public interface AccountService {

    /**
     * Finds the account by username
     * 
     * @param username
     * @return An Optional of the result.
     */
    public Optional<Account> findAccountByUsername(String username);

    /**
     * Finds the manager's account
     * 
     * @returns the manager's account
     * @throws NoSuchElementException if there is no such an account
     */
    public Account findManagerAccount();

    /**
     * Finds if there is an account with username
     * 
     * @param username
     * @returns if there is an account with username
     */
    public boolean hasUsername(String username);

    /**
     * Attempts to add a new user account with username and password
     * 
     * @param username
     * @param password
     *
     * @returns ture if the operation succeeds. false if there is already a user with username.
     * Because CrudRepo doesn't provide an atomic operation that saves a new entity and returns it, 
     * the method only saves a new account.
     */
    public boolean tryAddNewAccount(String username, String password);

    /**
     * Attempts to delete the user account with username
     * @param username
     * 
     * @returns ture if the operation succeeds. false if there is no such an account
     */
    public boolean tryDeleteAccount(String username);

    /**
     * Replaces the user name of the account whose username is param oldName with param newName
     * @returns ture if the operation succeeds. false if there is no such an account
     */
    public boolean tryEditAccountUsername(String oldName, String newName);

    /**
     * Replaces the password of the account whose username is param username with param newPass
     * @returns ture if the operation succeeds. false if there is no such an account
     */
    public boolean tryEditAccountPassword(String username, String newPass);
}
