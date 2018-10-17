package com.doy.pointmanagementservice.repository;

import com.doy.pointmanagementservice.domain.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, String> {
    List<Review> findByPlaceIdAndDeletedFalse(String placeId);
}
