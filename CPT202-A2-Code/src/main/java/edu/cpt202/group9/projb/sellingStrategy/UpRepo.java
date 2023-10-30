package edu.cpt202.group9.projb.sellingStrategy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UpRepo extends JpaRepository<UpSellingStrategy, Integer>{

    // @Query(value="SELECT * " +
    // "FROM " +
    // "    up_selling_strategy u " +
    // "left Join appointment a ON u.high_service_name = a.serviceName" +
    // "WHERE " +
    // "  a.Datetime = (SELECT MAX(Datetime) " +
    // "FROM appointment) and u.id=?1",nativeQuery = true)
Optional<UpSellingStrategy> findById(int id);



}   
    



