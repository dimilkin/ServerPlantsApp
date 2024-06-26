package com.server.backend.controllers;

import com.server.backend.dto.ResponseBody;
import com.server.backend.dto.UserPlantDto;
import com.server.backend.exceptions.GlobalExceptionsHandler;
import com.server.backend.models.UserInfo;
import com.server.backend.models.UserPlant;
import com.server.backend.security.UserAssessmentService;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserPlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    public UserPlantsController(PlantsService plantsService, UserInfoService userInfoService,
                                UserAssessmentService assessmentService, UserPlantService userPlantService) {
        this.plantsService = plantsService;
        this.userInfoService = userInfoService;
        this.assessmentService = assessmentService;
        this.userPlantService = userPlantService;
    }

    @PostMapping("{hostUserId}/{plantId}")
    public ResponseEntity<UserPlant> addPlantToUser(HttpServletRequest request,
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
            logger.info("UserPlant with id: " + savedUserPlant.getId() + "And name: " + savedUserPlant.getProvidedName()
                    + " was created by user with id: " + savedUserPlant.getUserOwner().getId());
            return new ResponseEntity<UserPlant>(savedUserPlant, HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @PutMapping("{hostUserId}/{plantId}")
    public ResponseEntity<UserPlant> updateUserPlant(HttpServletRequest request,
                                                     @PathVariable("hostUserId") int hostUserId,
                                                     @PathVariable("plantId") int plantId,
                                                     @RequestBody UserPlantDto plantToUserDto) throws IllegalAccessException {
        if (assessmentService.isUserValid(hostUserId, request)) {
            UserPlant userPlant = userPlantService.getById(plantToUserDto.getId());
            userPlant.setProvidedName(plantToUserDto.getProvidedName());
            userPlant.setWaterPeriod(plantToUserDto.getWaterPeriod());
            userPlant.setPlant(plantsService.getById(plantId));
            UserPlant updatedUserPlant = userPlantService.updateUserPlant(userPlant);
            return new ResponseEntity<UserPlant>(updatedUserPlant, HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @DeleteMapping("{hostUserId}/{userPlantId}")
    public ResponseEntity<ResponseBody> deletePlantFromUser(HttpServletRequest request,
                                                            @PathVariable("hostUserId") int hostUserId,
                                                            @PathVariable("userPlantId") int userPlantId
                                                            ) throws IllegalAccessException {
        if (assessmentService.isUserValid(hostUserId, request)) {
            userPlantService.removePlantFromUserOwnCollection(userPlantId);
            ResponseBody responseBody = new ResponseBody(HttpStatus.OK.toString(), "Deleted");
            return new ResponseEntity<ResponseBody>(responseBody, HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @PostMapping("{hostUserId}/{userPlantId}/{recepientUserId}")
    public ResponseEntity<String> assignPlantToAnotherUser(HttpServletRequest request,
                                                           @PathVariable("hostUserId") int hostUserId,
                                                           @PathVariable("userPlantId") int userPlantId,
                                                           @PathVariable("recepientUserId") int recepientUserId) throws IllegalAccessException {

        if (assessmentService.isUserValid(hostUserId, request)) {
            userPlantService.addPlantToUserAssignedCollection(userPlantId, recepientUserId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }

    @PutMapping("remove/{plantId}/{recepientUserId}")
    public ResponseEntity<String> unassignPlantFromUser(HttpServletRequest request,
                                                        @PathVariable("plantId") int userPlantId,
                                                        @PathVariable("recepientUserId") int recepientUserId) throws IllegalAccessException {
        if (assessmentService.isUserValid(recepientUserId, request)) {
            userPlantService.removePlantFromUserAssignedCollection(recepientUserId, userPlantId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } else {
            throw new IllegalAccessException(" User can't change other users data");
        }
    }
}
