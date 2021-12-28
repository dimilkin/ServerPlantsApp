package com.server.backend.security;

import com.server.backend.models.UserInfo;
import com.server.backend.security.jwt.JwtTokenUtil;
import com.server.backend.services.UserInfoService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserAssessmentService {

    UserInfoService userInfoService;
    private JwtTokenUtil jwtTokenUtil;

    public UserAssessmentService(UserInfoService userInfoService, JwtTokenUtil jwtTokenUtil) {
        this.userInfoService = userInfoService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean isUserValid(int currentUserId, HttpServletRequest request) {
        String email = JwtTokenUtil.getUserEmail(request, jwtTokenUtil);
        UserInfo requestUser = userInfoService.getByEmail(email);

        UserInfo currentUser = userInfoService.getById(currentUserId);
        return requestUser.getId() == currentUser.getId();
    }
}
