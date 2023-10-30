package edu.cpt202.group9.projb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {
    
	private final AccountRepository acc;
    /**
     * Instead of using Autowired, let Spring inject the repository 
     * so that it can be ensured to be inited before construction,
     * which cannot be done by using Autowired.
     * @param acc
     */
	public AccountUserDetailsService(AccountRepository acc) {
		this.acc = acc;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = acc.findById(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        else {
            return new AccountUserDetails(user.get());
        }

    }

    /**
     * Creates a user account and put it in the database
     * @param user
     * @returns true on success; false if the name is already used.
     */
    public boolean createUser(Account user) {
        if(acc.findById(user.getUsername()).isPresent()) {
            return false;
        }
        else {
            acc.save(user);
            return true;
        }
    }
}
