package edu.cpt202.group9.projb.user;

import edu.cpt202.group9.projb.security.Account;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    public User addUser(String email, Account account);

    public void deleteUser(Long id);

    public User updateUserEmail(Long id, String email);

    public List<User> findAllUsers();

    public Optional<User> findUserByEmail(String email);

    public Optional<User> findUserByAccount(Account account);

    public Optional<Object> findUserById(Long id);
}
