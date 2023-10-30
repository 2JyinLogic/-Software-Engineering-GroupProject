package edu.cpt202.group9.projb.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepo extends JpaRepository<Pet, Long> {
    Optional<Pet>findByPetName(String petName);
}
