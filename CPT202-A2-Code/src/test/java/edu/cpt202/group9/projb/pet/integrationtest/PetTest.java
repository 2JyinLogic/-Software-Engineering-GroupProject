package edu.cpt202.group9.projb.pet.integrationtest;

import static org.junit.jupiter.api.Assertions.*;

import edu.cpt202.group9.projb.pet.Pet;
import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    public void testGetId() {
        Pet pet = new Pet();
        pet.setId(1L);
        Long id = pet.getId();

        assertNotNull(id);
        assertEquals(Long.valueOf(1), id);
    }

    @Test
    public void testSetId() {
        Pet pet = new Pet();
        pet.setId(1L);
        Long id = pet.getId();

        assertNotNull(id);
        assertEquals(Long.valueOf(1), id);

        try {
            pet.setId(-1L);
        } catch (IllegalArgumentException e) {
            assertEquals("id must be greater than zero", e.getMessage());
        }
    }

    @Test
    public void testGetPetName() {
        Pet pet = new Pet();
        pet.setPetName("A Xiao");
        String petName = pet.getPetName();

        assertNotNull(petName);
        assertEquals("A Xiao", petName);
    }
}
