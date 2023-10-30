package edu.cpt202.group9.projb.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cpt202.group9.projb.service.Service;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/createReview")
    public ResponseEntity<Void> createReview(@RequestParam("rank") int rank,
                                             @RequestParam("content") String content,
                                             @RequestParam("isAnonymous") String isAnonymous,
                                             @RequestBody Service service) {
        reviewService.createReview(rank, content, isAnonymous, service);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByService")
    public ResponseEntity<List<Review>> findReviewByService(@RequestBody Service service) {
        List<Review> reviewList = reviewService.findReviewByService(service);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/findByRank")
    public ResponseEntity<List<Review>> findReviewByServiceAndRank(@RequestBody Service service,
                                                                   @RequestParam("rank") int rank) {
        List<Review> reviewList = reviewService.findReviewByServiceAndRank(service, rank);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/findByRankAsc")
    public ResponseEntity<List<Review>> findReviewByServiceAndSortByRankAsc(@RequestBody Service service) {
        List<Review> reviewList = reviewService.findReviewByServiceAndSortByRankAsc(service);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/findByRankDesc")
    public ResponseEntity<List<Review>> findReviewByServiceAndSortByRankDesc(@RequestBody Service service) {
        List<Review> reviewList = reviewService.findReviewByServiceAndSortByRankDesc(service);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/findByTimeAsc")
    public ResponseEntity<List<Review>> findReviewByServiceAndSortByTimeAsc(@RequestBody Service service) {
        List<Review> reviewList = reviewService.findReviewByServiceAndSortByTimeAsc(service);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/findByTimeDesc")
    public ResponseEntity<List<Review>> findReviewByServiceAndSortByTimeDesc(@RequestBody Service service) {
        List<Review> reviewList = reviewService.findReviewByServiceAndSortByTimeDesc(service);
        return ResponseEntity.ok(reviewList);
    }
    
}
