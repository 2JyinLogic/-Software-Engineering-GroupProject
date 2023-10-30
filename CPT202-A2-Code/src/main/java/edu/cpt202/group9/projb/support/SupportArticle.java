/**
 * SupportArticle.java
 * authored by Guanyuming He and Hanyu Zhang
 * Last modified 2023.4.26
 */

package edu.cpt202.group9.projb.support;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Repository for SupportArticle
 * 
 * @author Guanyuming He, Hanyu Zhang
 * @version 2023.4.26
 * @since 2023.4.24
 */
@Entity
public class SupportArticle {

    @Id
    private String name;

    private String title;
    private String content;

    /**
     * Used by JPA
     */
    public SupportArticle() {

    }

    public SupportArticle(String t, String c, String n) {
        title = t;
        content = c;
        name = n;
    }

    /**
     * used by JPA
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * used by JPA
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * used by JPA
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * used by JPA
     */
    public String getName() {
        return name;
    }

    /**
     * used by JPA
     */
    public String getTitle() {
        return title;
    }

    /**
     * used by JPA
     */
    public String getContent() {
        return content;
    }
}
