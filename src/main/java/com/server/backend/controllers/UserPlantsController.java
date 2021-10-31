package com.server.backend.controllers;

import com.server.backend.dto.PlantToUserDto;
import com.server.backend.models.UserInfo;
import com.server.backend.models.UserPlant;
import com.server.backend.security.UserAssessmentService;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserPlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.server.backend.controllers.RouteConstants.FIRST_API_VERION_PATH;

@RestController
@RequestMapping(FIRST_API_VERION_PATH + "userplants/")
public class UserPlantsController {

    private PlantsService plantsService;
    private UserInfoService userInfoService;
    private UserAssessmentService assessmentService;
    private UserPlantService userPlantService;

    public UserPlantsController(PlantsService plantsService, UserInfoService userInfoService,
                                UserAssessmentService assessmentService, UserPlantService userPlantService) {
        this.plantsService = plantsService;
        this.userInfoService = userInfoService;
        this.assessmentService = assessmentService;
        this.userPlantService = userPlantService;
    }

    @PostMapping("{hostUserId}/{plantId}")
    public ResponseEntity<String> addPlantToUser(HttpServletRequest request,
                                                 @PathVariable("hostUserId") int userId,
                                                 @PathVariable("plantId") int plantId,
                                                 @RequestBody PlantToUserDto plantToUserDto) throws IllegalAccessException {
        UserInfo hostUser = userInfoService.getById(userId);


        if (assessmentService.isUserValid(hostUser, request)) {
            UserPlant userPlant = new UserPlant();
            userPlant.setProvidedName(plantToUserDto.getProvidedName());
            userPlant.setWaterPeriod(plantToUserDto.getWaterPeriod());
            userPlant.setUserOwner(hostUser);
            userPlant.setUserAssigned(hostUser);
            userPlant.setPlant(plantsService.getById(plantId));
            userPlantService.createUserPlant(userPlant);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }

    }

    //TODO -> transfer plant from one user to another
    @PostMapping("{hostUserId}/{plantId/{recepientUserId}")
    public ResponseEntity<String> transferPlantToAnotherUser(HttpServletRequest request,
                                                             @PathVariable("hostUserId") int hostUserId,
                                                             @PathVariable("plantId") int userPlantId,
                                                             @PathVariable("hostUserId") int recepientUserId) throws IllegalAccessException {
        UserInfo hostUser = userInfoService.getById(hostUserId);

        if (assessmentService.isUserValid(hostUser, request)) {
            UserPlant userPlant = userPlantService.getById(userPlantId);
            userPlantService.update(userPlant);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }
}
