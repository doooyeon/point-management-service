package com.doy.pointmanagementservice.service;

import com.doy.pointmanagementservice.domain.Point;
import com.doy.pointmanagementservice.domain.Review;
import com.doy.pointmanagementservice.domain.User;
import com.doy.pointmanagementservice.dto.ReviewDTO;
import com.doy.pointmanagementservice.repository.PointRepository;
import com.doy.pointmanagementservice.repository.ReviewRepository;
import com.doy.pointmanagementservice.support.PointType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PointService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PointRepository pointRepository;

    @Transactional
    public void savePointHistories(ReviewDTO reviewDTO, User loginUser) {
        Review review = reviewRepository.findById(reviewDTO.getReviewId()).get();

        switch (reviewDTO.getAction()) {
            case "ADD" :
                savePointWhenAddReview(review, loginUser); // add written review point
                break;
            case "MOD" :
                savePointWhenModifyReview(review, loginUser); // calculate modified review point
                break;
            case "DELETE":
                savePointWhenDeleteReview(review, loginUser); // withdraw deleted review point
                break;
        }
    }

    private void savePointWhenAddReview(Review review, User loginUser) {
        if (review.getContent().length() > 1) {
            savePointHistory(new Point(PointType.CONTENT_TEXT, 1), review, loginUser);
        }

        if (!review.getReviewPhotos().isEmpty()) {
            savePointHistory(new Point(PointType.CONTENT_PHOTO, 1), review, loginUser);
        }

        List<Review> reviewList = reviewRepository.findByPlaceIdAndDeletedFalse(review.getPlace().getId());
        if (reviewList.isEmpty()) {
            savePointHistory(new Point(PointType.BONUS, 1), review, loginUser);
        }
    }

    private void savePointWhenModifyReview(Review review, User loginUser) {
        int contentTextPoint = 0, contentPhotoPoint = 0;
        for (Point p : review.getPointHistories()) {
            switch (p.getType()) {
                case CONTENT_TEXT:
                    contentTextPoint += p.getValue();
                    break;
                case CONTENT_PHOTO:
                    contentPhotoPoint += p.getValue();
                    break;
            }
        }

        if (contentTextPoint == 1 && review.getContent().length() < 1) {
            savePointHistory(new Point(PointType.CONTENT_TEXT, -1), review, loginUser);
        } else if (contentTextPoint == 0 && review.getContent().length() > 1) {
            savePointHistory(new Point(PointType.CONTENT_TEXT, 1), review, loginUser);
        }

        if (contentPhotoPoint == 1 && review.getReviewPhotos().isEmpty()) {
            savePointHistory(new Point(PointType.CONTENT_PHOTO, -1), review, loginUser);
        } else if (contentPhotoPoint == 0 && !review.getReviewPhotos().isEmpty()) {
            savePointHistory(new Point(PointType.CONTENT_PHOTO, 1), review, loginUser);
        }
    }

    private void savePointWhenDeleteReview(Review review, User loginUser) {
        int contentTextPoint = 0, contentPhotoPoint = 0, bonusPoint = 0;
        for (Point p : review.getPointHistories()) {
            switch (p.getType()) {
                case CONTENT_TEXT:
                    contentTextPoint += p.getValue();
                    break;
                case CONTENT_PHOTO:
                    contentPhotoPoint += p.getValue();
                    break;
                case BONUS:
                    bonusPoint += p.getValue();
                    break;
            }
        }

        if (contentTextPoint == 1) {
            savePointHistory(new Point(PointType.CONTENT_TEXT, -1), review, loginUser);
        }
        if (contentPhotoPoint == 1) {
            savePointHistory(new Point(PointType.CONTENT_PHOTO, -1), review, loginUser);
        }
        if (bonusPoint == 1) {
            savePointHistory(new Point(PointType.BONUS, -1), review, loginUser);
        }
    }

    private void savePointHistory(Point pointHistory, Review review, User user) {
        pointRepository.save(pointHistory);
        review.addPointHistory(pointHistory);
        user.addPointHistory(pointHistory);
        pointHistory.addReview(review);
        pointHistory.addUser(user);
    }

    public List<Point> getPointHistories(User loginUser) {
        return loginUser.getPointHistories();
    }

    public List<Point> getAllPointHistories() {
        return pointRepository.findAll();
    }
}