/**
 * SupportArticleRepository.java
 * authored by Guanyuming He and Hanyu Zhang
 */

package edu.cpt202.group9.projb.support;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for SupportArticle
 * 
 * @author Guanyuming He, Hanyu Zhang
 * @version 2023.4.26
 * @since 2023.4.24
 */
public interface SupportArticleRepository extends JpaRepository<SupportArticle, String> {
    
    /**
     * Find all support articles in the database.
     */
    public List<SupportArticle> findAll();
}
