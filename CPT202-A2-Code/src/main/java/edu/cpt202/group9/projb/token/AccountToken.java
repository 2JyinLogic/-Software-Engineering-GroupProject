package edu.cpt202.group9.projb.token;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountToken {
    @Id
    private Long id;
    private String token;

    public AccountToken() {
    }

    public AccountToken(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
