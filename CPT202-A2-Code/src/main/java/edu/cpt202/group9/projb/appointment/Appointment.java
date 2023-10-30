package edu.cpt202.group9.projb.appointment;

import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.service.Service;
import edu.cpt202.group9.projb.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.util.Date;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Long id;
    private Date date;
    private String status;
    private double fee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Groomer employeeId;
    @ManyToOne
    @JoinColumn(name = "service_name")
    private Service serviceName;

    public Appointment() {
    }

    public Appointment(Date date, String status, double fee, User user, Groomer employeeId, Service serviceName) {
        this.date = date;
        this.status = status;
        this.fee = fee;
        this.user = user;
        this.employeeId = employeeId;
        this.serviceName = serviceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Groomer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Groomer employeeId) {
        this.employeeId = employeeId;
    }

    public Service getServiceName() {
        return serviceName;
    }

    public void setServiceName(Service serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", dateTime=" + date +
                ", type='" + status + '\'' +
                ", fee=" + fee +
                ", user=" + user +
                ", employeeId=" + employeeId +
                ", serviceName=" + serviceName +
                '}';
    }
}