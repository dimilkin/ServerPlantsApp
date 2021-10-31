package com.server.backend.controllers;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import com.server.backend.models.PotentialPlantProblems;
import com.server.backend.services.PlantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.server.backend.controllers.RouteConstants.FIRST_API_VERION_PATH;

@RestController
@RequestMapping(FIRST_API_VERION_PATH + "plants/")
public class PlantsController {

    private final PlantsService plantsService;

    public PlantsController(PlantsService plantsService) {
        this.plantsService = plantsService;
    }

    @PostMapping("new")
    public ResponseEntity<PlantModel> createNewPlant(@RequestBody PlantModel plantModel) {
        plantsService.create(plantModel);
        return new ResponseEntity<PlantModel>(HttpStatus.OK);
    }

    @PostMapping("collection")
    public ResponseEntity<String> createPlantsList(@RequestBody List<PlantModel> plantModels) {
        plantsService.insertAllToDb(plantModels);
        return new ResponseEntity<String>("Success!", HttpStatus.OK);
    }

    @GetMapping("{plantId}")
    public ResponseEntity<PlantModel> getInfoForPlantById(@PathVariable("plantId") int plantId) {
        PlantModel plant = plantsService.getById(plantId);
        return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
    }

    @PutMapping("{additionalInfo/plantId}")
    public ResponseEntity<PlantModel> addInfoForPlant(@PathVariable("plantId") int plantId,
                                                      @RequestBody AdditionalPlantInfo additionalPlantInfo) {
        PlantModel plant = plantsService.getById(plantId);
        plant.addAdditionalInfo(additionalPlantInfo);
        plantsService.update(plant);
        return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
    }

    @PutMapping("{plantProblem/plantId}")
    public ResponseEntity<PlantModel> addProblemInfoForPlant(@PathVariable("plantId") int plantId,
                                                             @RequestBody PotentialPlantProblems plantProblem) {
        PlantModel plant = plantsService.getById(plantId);
        plant.addPotentialProblem(plantProblem);
        plantsService.update(plant);
        return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
    }
}
