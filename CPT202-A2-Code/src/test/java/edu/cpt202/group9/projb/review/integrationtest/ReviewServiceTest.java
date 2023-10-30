package edu.cpt202.group9.projb.review.integrationtest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import edu.cpt202.group9.projb.review.Review;
import edu.cpt202.group9.projb.review.ReviewRepository;
import edu.cpt202.group9.projb.review.ReviewService;
import edu.cpt202.group9.projb.service.Service;
import edu.cpt202.group9.projb.service.ServiceRepo;

@SpringBootTest(properties = "spring.profiles.active=test")
@Sql(scripts = "test-clear-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewRepository repo;

    @Autowired
    ServiceRepo repo2;

    @Autowired
    private ReviewService reviewService;

    @Test
    public void testCreateReviewSuccess() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);
        assertTrue(reviewService.createReview(1, "These are some comments", "false", service));
    }

    @Test
    public void testCreateReviewFailureInvalidRank() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);
        assertThrows(IllegalArgumentException.class,
                () -> reviewService.createReview(-1, "These are some comments", "false", service),
                "Invalid argument: rank can only be an integer between 0 to 5.");
    }

    @Test
    public void testCreateReviewFailureNoComment() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(1, null, "false", service),
                "Content cannot be null or empty.");
    }

    @Test
    public void testCreateReviewFailureNoService() {
        assertThrows(IllegalArgumentException.class,
                () -> reviewService.createReview(1, "These are some comments", "false", null), "Service cannot be null.");
    }

    @Test
    public void testFindReviewByServiceNoReview() {
        Service service1 = new Service("name1", "some description", 1.5, "type");
        Service service2 = new Service("name2", "some description", 1.5, "type");
        repo2.save(service1);
        repo2.save(service2);

        /* when the database is empty */
        assertThrows(RuntimeException.class, () -> reviewService.findReviewByService(service1),
                "No comments yet, please leave a comment here.");

        Review review = new Review(0, "These are some comments", "false", service1);
        repo.save(review);

        /* when the database is not empty */
        assertThrows(RuntimeException.class, () -> reviewService.findReviewByService(service2),
                "No comments yet, please leave a comment here.");
    }

    @Test
    public void testFindReviewByServiceTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        Review review1 = new Review(0, "These are some comments", "false", service);
        Review review2 = new Review(1, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);

        assertEquals(reviewList, reviewService.findReviewByService(service));
    }

    @Test
    public void testFindReviewByServiceAndRankInvalidRank() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        /* when the database is empty */
        assertThrows(IllegalArgumentException.class, () -> reviewService.findReviewByServiceAndRank(service, -1),
                "Invalid argument: rank can only be an integer from 0 to 5.");

        Review review = new Review(0, "These are some comments", "false", service);
        repo.save(review);

        /* when the database is not empty */
        assertThrows(IllegalArgumentException.class, () -> reviewService.findReviewByServiceAndRank(service, -1),
                "Invalid argument: rank can only be an integer from 0 to 5.");
    }

    @Test
    public void testFindReviewByServiceAndRankNoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        /* when the database is empty */
        assertThrows(RuntimeException.class, () -> {
            reviewService.findReviewByServiceAndRank(service, 2);
        }, "No comments of rank " + 1 + " .");

        Review review = new Review(0, "These are some comments", "false", service);
        repo.save(review);

        /* when the database is not empty */
        assertThrows(RuntimeException.class, () -> reviewService.findReviewByServiceAndRank(service, 2),
                "\"No comments of rank " + 1 + " .\"");
    }

    @Test
    public void testFindReviewByServiceAndRankTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        Review review1 = new Review(0, "These are some comments", "false", service);
        Review review2 = new Review(1, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);

        assertEquals(reviewList, reviewService.findReviewByServiceAndRank(service, 0));
    }

    @Test
    public void testFindReviewByServiceAndSortByRankAscNoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        assertThrows(RuntimeException.class, () -> reviewService.findReviewByServiceAndSortByRankAsc(service),
                "No comments yet, please leave a comment here.");
    }

    @Test
    public void testFindReviewByServiceAndSortByRankAscTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        Review review1 = new Review(1, "These are some comments", "false", service);
        Review review2 = new Review(2, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.sort(Comparator.comparing(Review::getRank));

        assertEquals(reviewList, reviewService.findReviewByServiceAndSortByRankAsc(service));
    }

    @Test
    public void testFindReviewByServiceAndSortByRankDescNoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        assertThrows(RuntimeException.class, () -> reviewService.findReviewByServiceAndSortByRankDesc(service),
                "No comments yet, please leave a comment here.");
    }

    @Test
    public void testFindReviewByServiceAndSortByRankDescTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        Review review1 = new Review(1, "These are some comments", "false", service);
        Review review2 = new Review(2, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.sort(Comparator.comparing(Review::getRank).reversed());

        assertEquals(reviewList, reviewService.findReviewByServiceAndSortByRankDesc(service));
    }

    @Test
    public void testFindReviewByServiceAndSortByTimeAscNoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        assertThrows(RuntimeException.class, () -> reviewService.findReviewByServiceAndSortByTimeAsc(service),
                "No comments yet, please leave a comment here.");
    }

    @Test
    public void testFindReviewByServiceAndSortByTimeAscTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);
        
        Review review1 = new Review(1, "These are some comments", "false", service);
        Review review2 = new Review(2, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);
        Collections.sort(reviewList, Comparator.comparing(Review::getCreateTime));

        assertEquals(reviewList, reviewService.findReviewByServiceAndSortByTimeAsc(service));
    }

    @Test
    public void testFindReviewByServiceAndSortByTimeDescNoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        assertThrows(RuntimeException.class, () -> reviewService.findReviewByServiceAndSortByTimeDesc(service),
                "No comments yet, please leave a comment here.");
    }

    @Test
    public void testFindReviewByServiceAndSortByTimeDescTwoReview() {
        Service service = new Service("name", "some description", 1.5, "type");
        repo2.save(service);

        Review review1 = new Review(1, "These are some comments", "false", service);
        Review review2 = new Review(2, "These are some comments", "true", service);
        repo.save(review1);
        repo.save(review2);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);
        Collections.sort(reviewList, Comparator.comparing(Review::getCreateTime).reversed());

        assertEquals(reviewList, reviewService.findReviewByServiceAndSortByTimeDesc(service));
    }

}
