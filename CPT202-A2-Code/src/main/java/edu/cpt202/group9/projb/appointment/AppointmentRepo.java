package edu.cpt202.group9.projb.appointment;

import edu.cpt202.group9.projb.groomer.Groomer;
import edu.cpt202.group9.projb.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByEmployeeId(Groomer employee);

    List<Appointment> findByUser(User user);

    @Query(value = "select sum(fee) from appointment where date between ?1 and ?2", nativeQuery = true)
    Optional<Double> findTotalSales(Date startTime, Date endTime);

    @Query(value = "select avg(fee) from appointment where date between ?1 and ?2", nativeQuery = true)
    Optional<Double> findMeanOfSales(Date startTime, Date endTime);

    @Query(value = "select max(fee) from appointment where date between ?1 and ?2", nativeQuery = true)
    Optional<Double> findMaxOfSales(Date startTime, Date endTime);

    @Query(value = "select min(fee) from appointment where date between ?1 and ?2", nativeQuery = true)
    Optional<Double> findMinOfSales(Date startTime, Date endTime);

    @Query(value = "select STDDEV(fee) from appointment where date between ?1 and ?2", nativeQuery = true)
    Optional<Double> findStdOfSales(Date startTime, Date endTime);
}
