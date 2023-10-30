package edu.cpt202.group9.projb.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cpt202.group9.projb.service.Service;





/** 
 * Abstracts MySQL queries for entity Review into java methods.
 *  
 * @version 2023.4.13
 * @since 2023.4.11
 * @author Hanyu Zhang
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    /**
     * Finds all reviews of a specific service.
     * 
     * @param service the service provided.
     * @return a list of all reviews of the specific service.
     */
    public List<Review> findByService(Service service);

    /**
     * Find all reviews of a particular rank for a specific service.
     * 
     * @param service the service provided.
     * @param rank the rank customer wants to review.
     * @return a list of all reviews of the specific service and rank.
     */
    public List<Review> findByServiceAndRank(Service service, int rank);
    
}

