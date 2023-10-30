package edu.cpt202.group9.projb.pet;

import edu.cpt202.group9.projb.user.User;

import javax.persistence.*;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String petName;
    private String petBreed;
    private String petAge;
    private String petDescription;
    private String petSize;
    @ManyToOne
    private User userId;

    public Pet() {
    }

    public Pet(String petName, String petBreed, String petAge, String petDescription, String petSize, User userId) {
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petDescription = petDescription;
        this.petSize = petSize;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }
}
