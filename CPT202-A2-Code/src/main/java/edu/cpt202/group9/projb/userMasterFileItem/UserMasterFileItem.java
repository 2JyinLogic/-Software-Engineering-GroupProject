package edu.cpt202.group9.projb.userMasterFileItem;

import edu.cpt202.group9.projb.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user_master_file_item")
public class UserMasterFileItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;


    public UserMasterFileItem() {
    }

    public UserMasterFileItem(int id) {
        this.id = id;
    }

    public UserMasterFileItem(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMasterFileItem{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMasterFileItem that = (UserMasterFileItem) o;

        if (id != that.id) return false;
        return Objects.equals(user, that.user);
    }

}
