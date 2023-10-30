package edu.cpt202.group9.projb.user;

import edu.cpt202.group9.projb.appointment.Appointment;
import edu.cpt202.group9.projb.security.Account;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Long phoneNum;
    @Column(unique = true)
    private String userEmail;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_name")
    private Account account;
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    public User() {

    }

    public User(String userEmail, Account account) {
        this.userEmail = userEmail;
        this.account = account;
    }

    public User(String firstName, String lastName, Long phoneNum, String userEmail, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.userEmail = userEmail;
        this.account = account;
    }

    public User(Long id, String firstName, String lastName, Long phoneNum, String userEmail, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.userEmail = userEmail;
        this.account = account;
    }

    public void setId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than zero");
        }
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email'" + userEmail + '\'' +
                '}';
    }

    public boolean isValidEmail(String email) {
        String regex = ".+@.+\\..+";
        return email.matches(regex);
    }
}

