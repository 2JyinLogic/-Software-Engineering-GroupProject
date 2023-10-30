package edu.cpt202.group9.projb.groomer;

import java.util.List;
import java.util.Optional;

/**
 * Abstracts interactions with GroomerRepository into more caller-friendly methods.
 * In another word, contains business logic for handling groomers.
 * 
 * Expected operations to define:
 * 
 * Find all groomers
 * Find whether a groomer with a specific employee id exist
 * Find a groomer by employee id
 * Find a list of groomers with a specific rank
 * 
 * Add a groomer
 * Delete a groomer
 * 
 * Edit a groomer's employee id
 * Edit a groomer's name
 * Edit a groomer's rank 
 * 
 * @since 2023.4.7.10
 * @version 2023.4.10
 * @author Hanyu Zhang
 */
public interface GroomerService {
    /** 
     * Finds all groomers.
     * 
     * @returns a list of all groomers, or an empty list if there is no groomer yet.
     */
    public List<Groomer> findAllGroomers();

    /**
     * Find whether a groomer with a specific employee id exist.
     * 
     * @param employeeId
     * @returns true iff there is a groomer with employee id.
     */
    public boolean hasEmployeeId(Long employeeId);

    /**
     * Finds a groomer by employee id.
     * 
     * @param employeeId
     * @return An Optional of the result.
     */
    public Optional<Groomer> findGroomerByEmployeeId(Long employeeId);

    /** 
     * Finds a list of groomers with a specific rank.
     * 
     * @returns a list of groomers with the given rank, or an empty list if there is no groomer with this specific rank.
     */
    public List<Groomer> findGroomersByRank(int rank);

    /**
     * Adds a groomer with employee id, name and rank
     * 
     * @param employeeId the employee id of the new groomer
     * @param name the name of the new groomer
     * @param rank the rank of the new groomer
     * @returns true iff the groomer is added successfully.
     */
    public boolean addNewGroomer(Long employeeId, String name, int rank);

    /**
     * Deletes a groomer with employee id
     * 
     * @param employeeId the employee id
     * @returns true iff the groomer with the id exists and is deleted successfully.
     */
    public boolean deleteGroomerByEmployeeId(Long employeeId);

    /**
     * Replaces the previous employee id of the groomer with the new employee id.
     * 
     * @param employeeId the previous employee id
     * @param newEmployeeId the new employee id 
     * @returns true iff the groomer with the id exists and the edition is successfull.
     */
    public boolean editGroomerEmployeeId(Long employeeId, Long newEmployeeId);

    /**
     * Replaces the previous name of the groomer whose employee id is param employeeId with the new name.
     * 
     * @param employeeId the employee id
     * @param newEmployeeId the new ename 
     * @returns true iff the groomer with the id exists and the edition is successfull.
     */
    public boolean editGroomerName (Long employeeId, String newName);

    /**
     * Replaces the previous rank of the groomer whose employee id is param employeeId with the new rank.
     * 
     * @param employeeId the employee id
     * @param newEmployeeId the new rank
     * @returns true iff the groomer with the id exists and the edition is successfull.
     */
    public boolean editGroomerRank (Long employeeId, int newRank);

}
