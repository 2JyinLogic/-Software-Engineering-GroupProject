package edu.cpt202.group9.projb.appointment;

import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServicesImpl implements AppointmentServices{
    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        return appointment.orElse(null);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @Override
    public void deleteAppointmentById(Long id) {
        appointmentRepo.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByEmployee(Groomer employee) {
        return appointmentRepo.findByEmployeeId(employee);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(User user) {
        return appointmentRepo.findByUser(user);
    }
}