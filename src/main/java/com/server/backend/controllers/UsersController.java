package com.server.backend.controllers;


import com.server.backend.dto.AccountActivationDto;
import com.server.backend.dto.AccountRegDto;
import com.server.backend.dto.AuthResponse;
import com.server.backend.models.Token;
import com.server.backend.models.UserInfo;
import com.server.backend.security.UserAssessmentService;
import com.server.backend.security.UserRegistrationHandler;
import com.server.backend.security.jwt.JwtTokenUtil;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private VerificationTokenService tokenService;

    public UsersController(UserInfoService userInfoService,
                           UserRegistrationHandler registrationHandler,
                           AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil,
                           UserAssessmentService assessmentService,
                           VerificationTokenService tokenService) {
        this.userInfoService = userInfoService;
        this.registrationHandler = registrationHandler;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.assessmentService = assessmentService;
        this.tokenService = tokenService;
    }

    @GetMapping("ping")
    public String returnTestResult() {
        return "Pong From PlantsServer";
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody final AccountRegDto accountRegDto) throws SendFailedException {
        String token = UUID.randomUUID().toString();
        if(userInfoService.emailAlreadyExists(accountRegDto.getEmail())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        registrationHandler.startUserRegistration(accountRegDto, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/authentication")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("userMail") String email,
                                                       @RequestHeader("userPass") String password) {
        if (email.isBlank() || email.isEmpty() || password.isEmpty() || password.isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        authenticate(email, password);
        final UserInfo userDetails = userInfoService.getByEmail(email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok().header("authToken", token).body(userDetails.getId());
    }

    @PostMapping("/activation")
    public ResponseEntity<AuthResponse> activateUserProfile(@RequestBody AccountActivationDto accountActivationDto) {
        if (accountActivationDto.getUserEmail().isBlank() || accountActivationDto.getUserEmail().isEmpty() ||
                accountActivationDto.getActivationCode().isEmpty() ||  accountActivationDto.getActivationCode().isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (registrationHandler.accountActivated(accountActivationDto.getUserEmail(), accountActivationDto.getActivationCode())) {
            UserInfo user = userInfoService.getByEmail(accountActivationDto.getUserEmail());
            AuthResponse response = new AuthResponse(user.getId(), user.getEmail());
            return new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
        }

        AuthResponse response = new AuthResponse(-1, "");
        return new ResponseEntity<AuthResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("userId") int userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/allProfile")
    public ResponseEntity<List<UserInfo>> getAllUserInfo() {
        List<UserInfo> userInfoList = userInfoService.getAll();
        return new ResponseEntity<List<UserInfo>>(userInfoList, HttpStatus.OK);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<AccountRegDto> updateUserProfile(HttpServletRequest request,
                                                           @PathVariable("userId") int userId) throws IllegalAccessException {
        if (assessmentService.isUserValid(userId, request)) {
            UserInfo currentUser = userInfoService.getById(userId);
            userInfoService.update(currentUser);
            return new ResponseEntity<AccountRegDto>(HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @DeleteMapping("/profile/delete/{userId}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable("userId") int userId) {
        UserInfo user = userInfoService.getById(userId);
        Token token = user.getToken();
        tokenService.delete(token);
        userInfoService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
