package com.doy.pointmanagementservice.controller;

import com.doy.pointmanagementservice.domain.User;
import com.doy.pointmanagementservice.dto.ReviewDTO;
import com.doy.pointmanagementservice.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
public class PointRestController {
    @Autowired
    private PointService pointService;

    // 개인 포인트 적립
    @PostMapping
    public ResponseEntity savePointHistories(@RequestBody ReviewDTO reviewDto, User loginUser) { // stored in the session
        pointService.savePointHistories(reviewDto, loginUser);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 개인 포인트 부여 히스토리 조회
    @GetMapping
    public ResponseEntity getPointHistories(User loginUser) {
        return ResponseEntity.ok().body(pointService.getPointHistories(loginUser));
    }

    // 전체 포인트 부여 히스토리 조회
    @GetMapping("/all")
    public ResponseEntity getAllPointHistories() {
        return ResponseEntity.ok().body(pointService.getAllPointHistories());
    }
}
