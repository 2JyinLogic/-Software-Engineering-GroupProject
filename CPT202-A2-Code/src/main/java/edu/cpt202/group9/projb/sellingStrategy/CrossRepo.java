package edu.cpt202.group9.projb.sellingStrategy;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

 public interface CrossRepo extends JpaRepository<CrossSellingStrategy, Integer>{
    // CrossSellingStrategy DeleteByCrossSellingStrategyName (String name);

//     @Query(value="SELECT * "+
//     "FROM cross_selling_strategy c "+
//     "left JOIN appointment a ON c.serviceA = a.serviceName "+
//     "WHERE (a.Datetime = (SELECT MAX(Datetime) FROM appointment)) "+
//    " AND (a.serviceName = c.serviceA OR a.serviceName = c.serviceB OR a.serviceName = c.serviceC OR a.serviceName = c.serviceD OR a.serviceName = c.serviceE) and c.id=?1"
//     ,nativeQuery = true)

    Optional<CrossSellingStrategy> findById(int id);
    List<CrossSellingStrategy> findByName(String name);

}



