/*
 * Account.java
 * Last modified 2023.4.5
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security;

import java.security.Principal;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/** 
 * Represents an account entity.
 * Designed to be used as an immutable type. After creation (including attainment from database) it should be read-only. 
 * This decision is made with multiple considerations. For example, the username and password cannot be any String and 
 * allowing to modify them is very dangerous.
 *  
 * @author Guanyuming He
 * @version 2023.4.7
 * @since 2023.4.5
 */
@Entity
@Table(name = "account")
public class Account {  
    @Id
    @Column(name="username", length = 32)
    private String username;
    /* Cannot call the following two simply password and role because they are keywords in MySQL. */
    @Column(name="user_password", length = 32, nullable = false)
    private String userPassword;
    @Column(name="user_role", length = 8, nullable = false)
    private String userRole;

    private static final String passwordRegex = "[a-zA-Z0-9\\.\\,\\?\\!\\_\\-\\+]{8,31}";

    private static final String mgrUsername = "realshopmanager";
	private static final String mgrPassword = "9js91.hn7tf-lk1mu";
    /**
     * the manager's account
     */
    public static final Account mgrAccount = new Account(mgrUsername, mgrPassword, Account.getRoleString(true));
    /**
     * a user account for development use only
     */
    public static final Account devUserAccount = new Account("user", "password", Account.getRoleString(false));

    /**
     * Default constructor. Required by JPA.
     */
    public Account() {}

    /** 
     * The only constructor for Account
     * 
     * @param username The username of the account. 
     * Length bigger than 0 but is smaller than 31 (inclusive).
     * @param password The password of the account. 
     * Length is between 8 to 31 (inclusive). Can only contain letters, numbers, and .,?!_-+
     * @param role  The role of the account. Can only be one of "MANAGER" and "USER"
     * 
     * @throws IllegalArgumentException when any of the parameters is incorrect.
     */
    public Account(String username, String password, String role) 
    {
        /* Verify username */
        if(!isUsernameLegal(username)) {
            throw new IllegalArgumentException("Illegal username.");
        }

        /* Verify password */
        if(!isPasswordLegal(password)) {
            throw new IllegalArgumentException("Illegal password.");
        }

        /* Verify role */
        if(!role.equals("MANAGER") && !role.equals("USER")) {
            throw new IllegalArgumentException("the role can only be one of \"MANAGER\" and \"USER\".");
        }

        this.username = username;
        this.userPassword = password;
        this.userRole = role;
    }

    /** 
     * The username of the account. 
     * 
     * Length is between 5 and 31 (inclusive).
     * 
     * @returns username
     */
    public String getUsername() {
        return username;
    }

    /** 
     * The password of the account. 
     * 
     * Length is between 8 to 31 (inclusive). Can only contain letters, numbers, and .,?!_-+
     * 
     * @returns password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * The role of the account.
     * 
     * Can only be one of "MANAGER" and "USER"
     * 
     * @returns role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Used by JPA in creation or used by caller for editing the username.
     * 
     * @param username username
     * @throws IllegalArgumentException if username is illegal.
     */
    public void setUsername(String username) {
        if(!isUsernameLegal(username)) {
            throw new IllegalArgumentException("Illegal username.");
        }
        
        this.username = username;
    }

    /**
     * Used by JPA only. Users should not call it.
     * 
     * @param userPassword user password
     * @throws IllegalArgumentException if userPassword is illegal.
     */
    public void setUserPassword(String userPassword) {
        /* Verify password */
        if(!isPasswordLegal(userPassword)) {
            throw new IllegalArgumentException("Illegal password.");
        }

        this.userPassword = userPassword;
    }

    /**
     * Used by JPA only. Users should not call it.
     * 
     * @param userRole user role
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Tells if two accounts are the same one.
     * @param other another account
     * @return true iff this and other are the same.
     */
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        else if(other == null) {
            return false;
        }
        else if(!(other instanceof Account)) {
            return false;
        }

        Account otherAcc = (Account)other;
        /* Doesn't need to care about id since username is also unique */
        return this.username.equals(otherAcc.username) 
        && this.userPassword.equals(otherAcc.userPassword) 
        && this.userRole.equals(otherAcc.userRole);    
    }

    /**
     * Gives the role string for a specific role
     * 
     * @param isMgr ture: the role is the shop manager; false: a common user
     * 
     * @returns "MANAGER" if isMgr is true, otherwise "USER".
     */
    static public String getRoleString(boolean isMgr) {
        return isMgr ? "MANAGER" : "USER";
    }

    static public boolean isUsernameLegal(String username) {
        return username.length() > 0 && username.length() <= 31;
    }

    /**
     * Caller should call this before constructing a new account or setting the password of an account.
     * @param pwd
     * @return true iff the password is legal.
     */
    static public boolean isPasswordLegal(String pwd) {
        return Pattern.matches(passwordRegex, pwd);
    }

    /**
     * @returns the username of the currently logged in user, or null if no user is logged in.
     */
    static public String getCurrentLoggedInUsername() {
        /* get the currently authenticated principle. the return value should be the username string */
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    /**
     * Gets the role of authentication
     * @param auth currently logged in user, which can be obtained in controllers.
     * @return "ROLE_USER" or "ROLE_MANAGER" depending on the logged in user
     * or "" if no user is logged in.
     */
    static public String getAuthenticatedRole(Authentication a) {
        var pip = a.getPrincipal();

        if(pip instanceof AccountUserDetails) {
            var ud = (AccountUserDetails)pip;
            var auths = ud.getAuthorities();
            if(auths.isEmpty()) {
                return "";
            }
            else {
                return ((GrantedAuthority)auths.toArray()[0]).toString();
            }
        }
        else {
            return "";
        }
    }

    /**
     * Gets the account of authentication.
     * @param auth currently logged in user, which can be obtained in controllers.
     * @return currently logged in Account or null if none.
     */
    static public Account getAuthenticatedAccount(Authentication a) {    
        var pip = a.getPrincipal();

        if(pip instanceof AccountUserDetails) {
            var ud = (AccountUserDetails)pip;
            return ud.getAccount();
        }
        else {
            return null;
        }
    }
}
