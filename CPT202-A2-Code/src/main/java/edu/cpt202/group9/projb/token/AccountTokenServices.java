package edu.cpt202.group9.projb.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTokenServices {
    @Autowired
    private AccountTokenRepo accountTokenRepo;

    public boolean hasToken(Long accountId) {
        return accountTokenRepo.findById(accountId).isPresent();
    }

    public void saveToken(Long accountId, String token) {
        AccountToken accountToken = new AccountToken();
        accountToken.setId(accountId);
        accountToken.setToken(token);
        accountTokenRepo.save(accountToken);
    }

    public void updateToken(Long accountId, String token) {
        AccountToken accountToken = accountTokenRepo.findById(accountId).get();
        accountToken.setToken(token);
        accountTokenRepo.save(accountToken);
    }

    public boolean checkToken(Long accountId, String token) {
        AccountToken accountToken = accountTokenRepo.findById(accountId).get();
        return accountToken.getToken().equals(token);
    }
}
