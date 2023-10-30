package edu.cpt202.group9.projb.review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import edu.cpt202.group9.projb.service.Service;

/**
 * Implementation of ReviewService
 *
 * @since 2023.4.13
 * @version 2023.4.12
 * @author Hanyu Zhang
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public boolean createReview(int rank, String content, String isAnonymous, edu.cpt202.group9.projb.service.Service service) {
        if (rank <= 0 || rank > 5) {
            throw new IllegalArgumentException("Invalid argument: rank can only be an integer between 0 to 5."); // 抛出异常，输入参数不能为空
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null.");
        }
        reviewRepo.save(new Review(rank, content, isAnonymous, service));
        return true;
    }

    @Override
    public List<Review> findReviewByService(edu.cpt202.group9.projb.service.Service service) {
        var reviewList = reviewRepo.findByService(service);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments yet, please leave a comment here.");
        }
        return reviewList;
    }

    @Override
    public List<Review> findReviewByServiceAndRank(edu.cpt202.group9.projb.service.Service service, int rank) {
        if (Arrays.binarySearch(new int[] { 0, 1, 2, 3, 4, 5 }, rank) < 0) {
            throw new IllegalArgumentException("Invalid argument: rank can only be an integer between 0 to 5.");
        }

        var reviewList = reviewRepo.findByServiceAndRank(service, rank);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments of rank " + rank + " .");
        }
        return reviewList;
    }

    @Override
    public List<Review> findReviewByServiceAndSortByRankAsc(edu.cpt202.group9.projb.service.Service service) {
        var reviewList = reviewRepo.findByService(service);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments yet, please leave a comment here.");
        } else {
            List<Review> sortedReviews = new ArrayList<>(reviewList);
            sortedReviews.sort(Comparator.comparingInt(Review::getRank));
            return sortedReviews;
        }
    }

    @Override
    public List<Review> findReviewByServiceAndSortByRankDesc(edu.cpt202.group9.projb.service.Service service) {
        var reviewList = reviewRepo.findByService(service);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments yet, please leave a comment here.");
        } else {
            List<Review> sortedReviews = new ArrayList<>(reviewList);
            sortedReviews.sort(Comparator.comparingInt(Review::getRank).reversed());
            return sortedReviews;
        }
    }

    @Override
    public List<Review> findReviewByServiceAndSortByTimeAsc(edu.cpt202.group9.projb.service.Service service) {
        var reviewList = reviewRepo.findByService(service);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments yet, please leave a comment here.");
        } else {
            List<Review> sortedReviews = new ArrayList<>(reviewList);
            sortedReviews.sort(Comparator.comparing(Review::getCreateTime));
            return sortedReviews;
        }
    }

    @Override
    public List<Review> findReviewByServiceAndSortByTimeDesc(edu.cpt202.group9.projb.service.Service service) {
        var reviewList = reviewRepo.findByService(service);

        if (reviewList.isEmpty()) {
            throw new RuntimeException("No comments yet, please leave a comment here.");
        } else {
            List<Review> sortedReviews = new ArrayList<>(reviewList);
            sortedReviews.sort(Comparator.comparing(Review::getCreateTime).reversed());
            return sortedReviews;
        }
    }

}