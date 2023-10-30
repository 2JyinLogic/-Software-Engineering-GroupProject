/*
 * AccountRepository.java
 * Last modified 2023.4.6
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Abstracts MySQL queries for entity Account into java methods.
 * CRUD refers to Create, Read, Update, and Delete.
 * 
 * @author Guanyuming He
 * @version 2023.4.6
 * @since 2023.4.5
 */
public interface AccountRepository extends CrudRepository<Account, String> {
    
    /**
     * Finds the accounts whose role is userRole. Generally should only be used to find the manager account.
     * 
     * @param userRole should only be one of "MANAGER" and "USER"
     * @returns a list of users whose role = userRole
     */
    public List<Account> findByUserRole(String userRole);

    /* Adding an account is done by save() */
    /* Deleting an account is done by delete() */

}
