/*
 * AccountServiceImpl.java
 * Last modified 2023.4.7
 * Authored by Guanyuming He
 * 
 * Copyright (C) CPT202 Group 9
 */

package edu.cpt202.group9.projb.security;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of AccountService
 *
 * @since 2023.4.6
 * @version 2023.4.7
 * @author Guanyuming He
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountRepository accRepo;

    @Override
    public Optional<Account> findAccountByUsername(String username) {
        return accRepo.findById(username);
    }

    @Override
    public Account findManagerAccount() {
        var accList = accRepo.findByUserRole(Account.getRoleString(true));

        if(accList.isEmpty()) {
            throw new NoSuchElementException("The manager's account does not exist.");
        }
        else {
            return accList.get(0);
        }
    }

    @Override
    public boolean hasUsername(String username) {
        return findAccountByUsername(username).isPresent();
    }

    @Override
    public boolean tryAddNewAccount(String username, String password) {
        if(hasUsername(username)) {
            return false;
        }
        else {
            accRepo.save(new Account(username, password, Account.getRoleString(false)));
            return true;
        }
    }

    @Override
    public boolean tryDeleteAccount(String username) {
        var user = findAccountByUsername(username);
        if(!user.isPresent()) {
            return false;
        }
        else {
            accRepo.delete(user.get());
            return true;
        }
    }

    @Override
    public boolean tryEditAccountUsername(String oldName, String newName) {
        var user = findAccountByUsername(oldName);
        if(!user.isPresent()) {
            return false;
        }
        else {
            var acc = user.get();
            var newAcc = new Account(newName, acc.getUserPassword(), acc.getUserRole());

            accRepo.save(newAcc);
            accRepo.delete(acc);

            return true;
        }
    }

    @Override
    public boolean tryEditAccountPassword(String username, String newPass) {
        var user = findAccountByUsername(username);

        if(!user.isPresent()) {
            return false;
        }
        else {
            var acc = user.get();
            //accRepo.delete(acc);
            acc.setUserPassword(newPass);
            accRepo.save(acc);

            return true;
        }
    }
}
