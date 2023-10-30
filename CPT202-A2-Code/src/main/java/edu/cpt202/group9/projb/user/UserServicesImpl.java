package edu.cpt202.group9.projb.user;

import edu.cpt202.group9.projb.security.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    private UserRepo userRepo;

    @Override
    public User addUser(String email, Account account) {
        User user = new User(email, account);
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: id cannot be null!");
        }
        Optional<User> appointmentOptional = userRepo.findById(id);
        if (appointmentOptional.isEmpty()) {
            throw new IllegalStateException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public User updateUserEmail(Long id, String email) {
        if (id == null || email == null) {
            throw new IllegalArgumentException("Invalid argument: id or email address cannot be null!");
        }
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserEmail(email);
        return userRepo.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("No users found!");
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Invalid argument: email address cannot be null!");
        }
        Optional<User> userOptional = userRepo.findByUserEmail(email);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User not found with email address: " + email);
        }
        return userOptional;
    }

    @Override
    public Optional<User> findUserByAccount(Account account) {
        Optional<User> userOptional = userRepo.findByAccount(account);
        return userOptional;
    }

    @Override
    public Optional<Object> findUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: id cannot be null!");
        }
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        return Optional.of(userOptional);
    }


}
