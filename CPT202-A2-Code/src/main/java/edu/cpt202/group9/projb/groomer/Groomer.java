package edu.cpt202.group9.projb.groomer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.cpt202.group9.projb.schedule.Schedule;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Represents a groomer entity.
 * 
 * @version 2023.4.10
 * @since 2023.4.7
 * @author Hanyu Zhang
 */
@Entity
@Table(name = "groomer")
public class Groomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * This is for the reference made by users who don't know the primary key of the
     * entity.
     * The real name could be duplicated, so this additional field is added.
     */
    @Column(name = "employee_id", unique = true)
    private Long employeeId;

    private String name;
    @Column(name = "groomer_rank")
    private int rank;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "groomer_id")
    private List<Schedule> scheduleList;

    /**
     * Default constructor. Required by JPA.
     */
    public Groomer() {
    }

    /**
     * The constructor for Groomer.
     * 
     * @param employeeId     The employee Id of the groomer.
     * @param name           The name of the groomer.
     * @param rank           The rank of the groomer.
     * @param scheduleString The string of the schedule.
     */
    public Groomer(Long employeeId, String name, int rank) {
        this.employeeId = employeeId;
        this.name = name;
        this.rank = rank;

        this.scheduleList = new ArrayList<>();
    }

    /**
     * Used by JPA only. Users should not call it.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Used by JPA only. Users should not call it.
     * 
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The employee id of the groomer.
     * 
     * @returns employeeId
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * Used by JPA in creation or used by caller for editing the employee id.
     * 
     * @param employeeId employee id
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * The name of the groomer.
     * 
     * @returns name
     */
    public String getName() {
        return name;
    }

    /**
     * Used by JPA in creation or used by caller for editing the name of the
     * groomer.
     * 
     * @param name the name of the groomer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The rank of the groomer.
     * 
     * @returns rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Used by JPA in creation or used by caller for editing the rank of the
     * groomer.
     * 
     * @param name the rank of the groomer
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Groomer))
            return false;
        Groomer other = (Groomer) obj;
        return employeeId.equals(other.employeeId)
        && name.equals(other.name)
        && rank == other.rank
        && Objects.equals(scheduleList, other.scheduleList);
    }

}