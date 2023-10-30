package edu.cpt202.group9.projb.pet;

import edu.cpt202.group9.projb.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PetServicesImpl implements PetServices{
    @Autowired
    private PetRepo petRepo;

    @Override
    public Pet addPet(String petName, String petBreed, String petAge, String petDescription, String petSize, User userId) {
        Pet pet = new Pet(petName, petBreed, petAge, petDescription, petSize, userId);
        return petRepo.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        petRepo.deleteById(id);
    }

    @Override
    public Pet updatePet(Long id, String petName, String petBreed, String petAge, String petDescription, String petSize, User userId) {
        Pet pet = petRepo.findById(id).get();
        pet.setPetName(petName);
        pet.setPetBreed(petBreed);
        pet.setPetAge(petAge);
        pet.setPetDescription(petDescription);
        pet.setPetSize(petSize);
        pet.setUserId(userId);
        return petRepo.save(pet);
    }

    @Override
    public Optional<Pet> findPetByPetName(String petName) {
        Optional<Pet> petOptional = petRepo.findByPetName(petName);
        if (petOptional.isEmpty()) {
            throw new RuntimeException("Pet not found with name: " + petName);
        }
        return petOptional;
    }

    @Override
    public List<Pet> findAllPet() {
        return petRepo.findAll();
    }
}
