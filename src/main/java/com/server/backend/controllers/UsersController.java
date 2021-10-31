package com.server.backend.controllers;


import com.server.backend.dto.AccountRegDto;
import com.server.backend.models.UserInfo;
import com.server.backend.security.UserAssessmentService;
import com.server.backend.security.UserRegistrationHandler;
import com.server.backend.security.jwt.JwtResponse;
import com.server.backend.security.jwt.JwtTokenUtil;
import com.server.backend.services.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.server.backend.controllers.RouteConstants.FIRST_API_VERION_PATH;

@RestController
@RequestMapping(FIRST_API_VERION_PATH + "user/")
public class UsersController {

    private final UserInfoService userInfoService;
    private final UserRegistrationHandler registrationHandler;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserAssessmentService assessmentService;

    public UsersController(UserInfoService userInfoService, UserRegistrationHandler registrationHandler,
                           AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                           UserAssessmentService assessmentService) {
        this.userInfoService = userInfoService;
        this.registrationHandler = registrationHandler;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.assessmentService = assessmentService;
    }

    @GetMapping("ping")
    public String returnTestResult() {
        return "Pong From PlantsServer";
    }

    @PostMapping("/registration")
    public String createNewUser(@RequestBody final AccountRegDto accountRegDto,
                                HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        registrationHandler.startUserRegistration(accountRegDto, token, request);
        return "Success!";
    }

    @RequestMapping("/authentication")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("userMail") String email,
                                                       @RequestHeader("userPass") String password) {
        authenticate(email, password);
        final UserInfo userDetails = userInfoService.getByEmail(email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        JwtResponse response = new JwtResponse(userDetails.getId());
        return ResponseEntity.ok().header("authToken", token).body(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("userId") int userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<AccountRegDto> updateUserProfile(HttpServletRequest request,
                                                           @PathVariable("userId") int userId) throws IllegalAccessException {
        UserInfo currentUser = userInfoService.getById(userId);
        if (assessmentService.isUserValid(currentUser, request)) {
            userInfoService.update(currentUser);
            return new ResponseEntity<AccountRegDto>(HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
