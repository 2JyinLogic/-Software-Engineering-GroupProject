package edu.cpt202.group9.projb.appointment;

import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.user.User;

import java.util.List;

public interface AppointmentServices {

    public List<Appointment> getAllAppointments();

    public Appointment getAppointmentById(Long id);

    public Appointment saveAppointment(Appointment appointment);

    public void deleteAppointmentById(Long id);

    public List<Appointment> getAppointmentsByEmployee(Groomer employee);

    public List<Appointment> getAppointmentsByUser(User user);
}