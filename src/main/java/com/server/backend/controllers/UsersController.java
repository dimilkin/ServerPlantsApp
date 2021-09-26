package com.server.backend.controllers;


import com.server.backend.dto.AccountRegDto;
import com.server.backend.exceptions.DuplicateEntityException;
import com.server.backend.exceptions.EntityNotFoundException;
import com.server.backend.models.UserInfo;
import com.server.backend.security.UserRegistrationHandler;
import com.server.backend.security.jwt.JwtResponse;
import com.server.backend.security.jwt.JwtTokenUtil;
import com.server.backend.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    public UsersController(UserInfoService userInfoService, UserRegistrationHandler registrationHandler,
                           AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userInfoService = userInfoService;
        this.registrationHandler = registrationHandler;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("ping")
    public String returnTestResult() {
        return "Pong";
    }

    @PostMapping("/registration")
    public String createNewUser(@RequestBody final AccountRegDto accountRegDto,
                                HttpServletRequest request) {

        try {
            String token = UUID.randomUUID().toString();
            registrationHandler.startUserRegistration(accountRegDto, token, request);
            return "Success!";
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @RequestMapping("/authentication")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("userMail") String email,
                                                       @RequestHeader("userPass") String password) {

        try {
            authenticate(email, password);
            final UserInfo userDetails = userInfoService.getByEmail(email);
            final String token = jwtTokenUtil.generateToken(userDetails);
            JwtResponse response = new JwtResponse(userDetails.getId());
            return ResponseEntity.ok().header("authToken", token).body(response);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("userId") int userId) {
        try {
            UserInfo userInfo = userInfoService.getById(userId);
            return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);

        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<AccountRegDto> updateUserProfile(HttpServletRequest request,
                                                           @PathVariable("userId") int userId) {

        try {
            String email = JwtTokenUtil.getUserEmail(request, jwtTokenUtil);
            UserInfo userInfo = userInfoService.getByEmail(email);

            if (userInfo.getId() == userId) {
                userInfoService.update(userInfo);
                return new ResponseEntity<AccountRegDto>(HttpStatus.OK);
            } else {
                throw new IllegalAccessException(" User can't change other users data");
            }

        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't change this profile");
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}
