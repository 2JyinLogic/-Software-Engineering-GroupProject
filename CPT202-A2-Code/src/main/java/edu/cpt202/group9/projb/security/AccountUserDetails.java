package edu.cpt202.group9.projb.security;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountUserDetails implements UserDetails {
    private Account user;
     
    public AccountUserDetails(Account user) {
        this.user = user;
    }
 
    /**
     * @returns a list of only role "USER" if this.user is only a user, or a list of "USER" & "MANAGER" otherwise
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        /*
         * if it's user then return "ROLE_USER" 
         * otherwise return "ROLE_MANAGER".
         * 
         * Prefix ROLE_ is required for Spring to recognizing an authority token as a role.
         */
        list.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));

        return list;
    }
 
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 
     * @returns the underlying user account
     */
    public Account getAccount() {
        return user;
    }
}
