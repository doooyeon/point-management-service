package com.doy.pointmanagementservice.service;

import com.doy.pointmanagementservice.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public int getPoint(User loginUser) {
        return loginUser.getPoint();
    }
}
