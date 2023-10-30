package edu.cpt202.group9.projb.groomer;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GroomerRepository extends JpaRepository<Groomer, Long> {

    /**
     * Finds the groomers whose rank is equal to the given parameter.
     * 
     * @param rank the groomer's rank to search for (0-5).
     * @return a list of groomers whose rank is equal to the given rank.
     */
    public List<Groomer> findByRank(int rank);


    /**
     * Finds the groomer with the given employee id.
     * 
     * @returns an empty list if the groomer is absent, or a list with the groomer if found.
     */
    public Optional<Groomer> findByEmployeeId(Long employeeId);

}