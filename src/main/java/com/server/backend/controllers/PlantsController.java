package com.server.backend.controllers;


import com.server.backend.exceptions.EntityNotFoundException;
import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import com.server.backend.models.PotentialPlantProblems;
import com.server.backend.services.PlantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.server.backend.controllers.RouteConstants.FIRST_API_VERION_PATH;

@RestController
@RequestMapping(FIRST_API_VERION_PATH + "plants/")
public class PlantsController {

    PlantsService plantsService;

    public PlantsController(PlantsService plantsService) {
        this.plantsService = plantsService;
    }

    @PostMapping("new")
    public ResponseEntity<PlantModel> createNewPlant(@RequestBody PlantModel plantModel) {
        try {
            plantsService.create(plantModel);
            return new ResponseEntity<PlantModel>(HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @PostMapping("collection")
    public ResponseEntity<String> createPlantsList(@RequestBody List<PlantModel> plantModels) {
        try {
            plantsService.insertAllToDb(plantModels);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @GetMapping("{plantId}")
    public ResponseEntity<PlantModel> getInfoForPlantById(@PathVariable("plantId") int plantId){
        try {
            PlantModel plant = plantsService.getById(plantId);
            return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @PutMapping("{additionalInfo/plantId}")
    public ResponseEntity<PlantModel> addInfoForPlant(@PathVariable("plantId") int plantId,
                                                      @RequestBody AdditionalPlantInfo additionalPlantInfo){
        try {
            PlantModel plant = plantsService.getById(plantId);
            plant.addAdditionalInfo(additionalPlantInfo);
            plantsService.update(plant);
            return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @PutMapping("{plantProblem/plantId}")
    public ResponseEntity<PlantModel> problemForPlant(@PathVariable("plantId") int plantId,
                                                      @RequestBody PotentialPlantProblems plantProblem){
        try {
            PlantModel plant = plantsService.getById(plantId);
            plant.addPotentialProblem(plantProblem);
            plantsService.update(plant);
            return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    @PostMapping("profile/{hostUserId}/{plantId}")
    public ResponseEntity<String> addPlantToUser(@PathVariable("hostUserId") int userId,
                                                 @PathVariable("plantId") int plantId){
        try {
            plantsService.addPlantToUserCollection(userId, plantId);
            return new ResponseEntity<String>("Success!", HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }

    //TODO -> transfer plant from one user to another

}
