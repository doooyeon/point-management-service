package com.doy.pointmanagementservice.controller;

import com.doy.pointmanagementservice.domain.User;
import com.doy.pointmanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    // 개인 누적 포인트 조회
    @GetMapping("/point")
    public ResponseEntity getPoint(User loginUser) { // stored in the session
        return ResponseEntity.ok().body(userService.getPoint(loginUser));
    }
}
