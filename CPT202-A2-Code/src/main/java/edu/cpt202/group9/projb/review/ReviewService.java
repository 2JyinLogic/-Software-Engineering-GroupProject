package edu.cpt202.group9.projb.review;

import java.util.List;

import edu.cpt202.group9.projb.service.Service;

/**
 * Abstracts interactions with ReviewRepository into more caller-friendly methods.
 * In another word, contains business logic for handling reviews.
 * 
 * Expected operations to define:
 * 
 * Create a review
 * 
 * Find all reviews of a specific service
 * Find all reviews of a particular rank for a specific service
 * Find all reviews for a particular service with ascending order based on their rank
 * Find all reviews for a particular service with descending order based on their rank
 * Find all reviews for a particular service with ascending order based on their created time
 * Find all reviews for a particular service with descending order based on their created time
 * 
 * @since 2023.4.7.13
 * @version 2023.4.11
 * @author Hanyu Zhang
 */
public interface ReviewService {

    /**
     * Creates a review with account, availableService, groomer, rank, content, isAnonymous, createTime
     * 
     * @param rank the rank user wants to give
     * @param content the content of review
     * @param isAnonymous whether post the review anonymously
     * @returns true iff the review is created successfully.
     */
    public boolean createReview(int rank, String content, String isAnonymous, Service service);

    /** 
     * Finds a list of reviews with a specific service.
     * 
     * @param service service
     * @returns a list of reviews of a specific service.
     */
    public List<Review> findReviewByService(Service service);

    /** 
     * Finds a list of reviews of a particular rank for a specific service.
     * 
     * @param service service
     * @returns a list of reviews of a particular rank for a specific service.
     */
    public List<Review> findReviewByServiceAndRank(Service service, int rank);

    /** 
     * Finds a list of reviews for a particular service with ascending order based on their rank.
     * 
     * @param service service
     * @returns a list of reviews for a particular service with ascending order based on their rank.
     */
    public List<Review> findReviewByServiceAndSortByRankAsc(Service service);

    /** 
     * Finds a list of reviews for a particular service with descending order based on their rank.
     * 
     * @param service service
     * @returns a list of reviews for a particular service with descending order based on their rank.
     */
    public List<Review> findReviewByServiceAndSortByRankDesc(Service service);

    /** 
     * Finds a list of reviews for a particular service with ascending order based on their created time.
     * 
     * @param service service
     * @returns a list of reviews for a particular service with ascending order based on their created time
     */
    public List<Review> findReviewByServiceAndSortByTimeAsc(Service service);

    /** 
     * Finds a list of reviews for a particular service with descending order based on their created time.
     * 
     * @param service service
     * @returns a list of reviews for a particular service with descending order based on their created time
     */
    public List<Review> findReviewByServiceAndSortByTimeDesc(Service service);
}
