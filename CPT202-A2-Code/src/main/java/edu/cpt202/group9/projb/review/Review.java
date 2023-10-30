package edu.cpt202.group9.projb.review;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.cpt202.group9.projb.service.Service;

/**
 * Represents a review entity.
 * 
 * @version 2023.4.13
 * @since 2023.4.11
 * @author Hanyu Zhang
 */
@Entity
@Table(name = "service_reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "review_rank", nullable = false)
    private int rank;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private String isAnonymous;

    // @Transient
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    public Review() {}

    public Review(int rank, String content, String isAnonymous, Service service) {
        this.rank = rank;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.createTime = LocalDateTime.now();
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Review))
            return false;
        Review other = (Review) obj;
        return rank == other.rank &&
                content.equals(other.content) && isAnonymous == other.isAnonymous &&
                createTime.equals(other.createTime) && service.equals(other.service);
    }

}
