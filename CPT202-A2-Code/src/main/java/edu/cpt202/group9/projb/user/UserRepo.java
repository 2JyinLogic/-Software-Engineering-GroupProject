package edu.cpt202.group9.projb.user;

import edu.cpt202.group9.projb.security.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByAccount(Account account);

    Optional<User> findById(Long id);
}