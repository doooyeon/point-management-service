package com.doy.pointmanagementservice.repository;

import com.doy.pointmanagementservice.domain.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRepository extends CrudRepository<Point, String> {
    List<Point> findAll();
}
