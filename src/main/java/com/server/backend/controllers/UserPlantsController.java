package com.server.backend.controllers;

import com.server.backend.dto.UserPlantDto;
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

    private final PlantsService plantsService;
    private final UserInfoService userInfoService;
    private final UserAssessmentService assessmentService;
    private final UserPlantService userPlantService;

    public UserPlantsController(PlantsService plantsService, UserInfoService userInfoService,
                                UserAssessmentService assessmentService, UserPlantService userPlantService) {
        this.plantsService = plantsService;
        this.userInfoService = userInfoService;
        this.assessmentService = assessmentService;
        this.userPlantService = userPlantService;
    }

    @PostMapping("{hostUserId}/{plantId}")
    public ResponseEntity<Integer> addPlantToUser(HttpServletRequest request,
                                                 @PathVariable("hostUserId") int hostUserId,
                                                 @PathVariable("plantId") int plantId,
                                                 @RequestBody UserPlantDto plantToUserDto) throws IllegalAccessException {
        if (assessmentService.isUserValid(hostUserId, request)) {
            UserInfo hostUser = userInfoService.getById(hostUserId);
            UserPlant userPlant = new UserPlant();
            userPlant.setProvidedName(plantToUserDto.getProvidedName());
            userPlant.setWaterPeriod(plantToUserDto.getWaterPeriod());
            userPlant.setUserOwner(hostUser);
            userPlant.setPlant(plantsService.getById(plantId));
            UserPlant savedUserPlant = userPlantService.addPlantToUserOwnCollection(userPlant);
            return new ResponseEntity<Integer>(savedUserPlant.getId(), HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @DeleteMapping("{hostUserId}/{plantId}")
    public ResponseEntity<String> deletePlantFromUser(HttpServletRequest request,
                                                 @PathVariable("hostUserId") int hostUserId,
                                                 @PathVariable("plantId") int plantId) throws IllegalAccessException {
        if (assessmentService.isUserValid(hostUserId, request)) {
            userPlantService.removePlantFromUserOwnCollection(hostUserId, plantId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @PostMapping("{hostUserId}/{plantId}/{recepientUserId}")
    public ResponseEntity<String> assignPlantToAnotherUser(HttpServletRequest request,
                                                             @PathVariable("hostUserId") int hostUserId,
                                                             @PathVariable("plantId") int userPlantId,
                                                             @PathVariable("recepientUserId") int recepientUserId) throws IllegalAccessException {

        if (assessmentService.isUserValid(hostUserId, request)) {
            userPlantService.addPlantToUserAssignedCollection(hostUserId, userPlantId, recepientUserId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @PutMapping("remove/{plantId}/{recepientUserId}")
    public ResponseEntity<String> unassignPlantFromUser(HttpServletRequest request,
                                                        @PathVariable("plantId") int userPlantId,
                                                        @PathVariable("recepientUserId") int recepientUserId) throws IllegalAccessException{
        if (assessmentService.isUserValid(recepientUserId, request)) {
            userPlantService.removePlantFromUserAssignedCollection(recepientUserId, userPlantId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }
}
