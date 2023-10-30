package edu.cpt202.group9.projb.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenRepo extends JpaRepository<AccountToken, Long> {
}
