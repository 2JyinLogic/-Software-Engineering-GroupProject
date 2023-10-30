/*
 * ImportantSecurityDataConfig.java
 * Last modified 2023.4.14
 * Authored by Guanyuming He
 *
 * Copyright (C) CPT202 Group 9
 */
package edu.cpt202.group9.projb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.cpt202.group9.projb.security.Account;
import edu.cpt202.group9.projb.security.AccountRepository;
import edu.cpt202.group9.projb.user.User;
import edu.cpt202.group9.projb.user.UserRepo;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItem;
import edu.cpt202.group9.projb.userMasterFileItem.UserMasterFileItemRepo;

/**
 * loads important security data into the database at startup.
 *
 * @author Guanyuming He
 * @version 2023.4.14
 * @since 2023.4.14
 */
@Component
public class ImportantSecurityDataConfig {

    private AccountRepository repo;
    private UserRepo userRepo;
    private UserMasterFileItemRepo userMasterFileItemRepo;

    // repo is injected by Spring. No need for autowired.
    public ImportantSecurityDataConfig(AccountRepository repo, UserRepo userRepo, 
    UserMasterFileItemRepo userMasterFileItemRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.userMasterFileItemRepo = userMasterFileItemRepo;
        loadImportantSecurityData();
    }

    private void loadImportantSecurityData() {
        repo.save(Account.mgrAccount);
        repo.save(Account.devUserAccount);

        if(userRepo.findByAccount(Account.devUserAccount).isEmpty()) {
            User defaultUser = new User(1l, 
            "development", "use only", 
            12345678l, "123@465.com", Account.devUserAccount);
            defaultUser = userRepo.save(defaultUser);

            userMasterFileItemRepo.save(new UserMasterFileItem(defaultUser));
        }
    }

}
