package edu.cpt202.group9.projb.pet;

import edu.cpt202.group9.projb.user.User;
import java.util.List;
import java.util.Optional;

public interface PetServices {
    public Pet addPet(String petName, String petBreed, String petAge, String petDescription, String petSize, User userId);

    public void deletePet(Long id);

    public Pet updatePet(Long id, String petName, String petBreed, String petAge, String petDescription, String petSize, User userId);

    public Optional<Pet> findPetByPetName(String petName);

    public List<Pet> findAllPet();
}
