package edu.cpt202.group9.projb.schedule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import edu.cpt202.group9.projb.groomer.Groomer;

@Entity
@Table(name = "groomer_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Groomer getGroomer() {
        return groomer;
    }

    public void setGroomer(Groomer groomer) {
        this.groomer = groomer;
    }



    @ManyToOne
    @JoinColumn(name = "groomer_id")
    private Groomer groomer;

    public Schedule() {}

    public Schedule(String day) {
        this.day = day;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Schedule))
            return false;
        Schedule other = (Schedule) obj;
        return day.equals(other.day);
    }

}
