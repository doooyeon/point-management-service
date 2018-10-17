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
            pointRepository.save(new Point(PointType.CONTENT_TEXT, 1));
        }

        if (!review.getReviewPhotos().isEmpty()) {
            pointRepository.save(new Point(PointType.CONTENT_PHOTO, 1));
        }

        List<Review> reviewList = reviewRepository.findByPlaceIdAndDeletedFalse(review.getPlace().getId());
        if (reviewList.isEmpty()) {
            pointRepository.save(new Point(PointType.BONUS, 1));
        }

        // connection of reviews and newly stored point history is required.
        // connection of login user and newly stored point history is required.
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
            pointRepository.save(new Point(PointType.CONTENT_TEXT, -1));
        } else if (contentTextPoint == 0 && review.getContent().length() > 1) {
            pointRepository.save(new Point(PointType.CONTENT_TEXT, 1));
        }

        if (contentPhotoPoint == 1 && review.getReviewPhotos().isEmpty()) {
            pointRepository.save(new Point(PointType.CONTENT_PHOTO, -1));
        } else if (contentPhotoPoint == 0 && !review.getReviewPhotos().isEmpty()) {
            pointRepository.save(new Point(PointType.CONTENT_PHOTO, 1));
        }

        // connection of reviews and newly stored point history is required.
        // connection of login user and newly stored point history is required.
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
            pointRepository.save(new Point(PointType.CONTENT_TEXT, -1));
        }
        if (contentPhotoPoint == 1) {
            pointRepository.save(new Point(PointType.CONTENT_PHOTO, -1));
        }
        if (bonusPoint == 1) {
            pointRepository.save(new Point(PointType.BONUS, -1));
        }

        // connection of reviews and newly stored point history is required.
        // connection of login user and newly stored point history is required.
    }
}