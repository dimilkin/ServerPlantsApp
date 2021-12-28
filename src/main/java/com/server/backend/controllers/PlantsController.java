package com.server.backend.controllers;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import com.server.backend.models.PotentialPlantProblems;
import com.server.backend.services.PlantsInfoService;
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
    private final PlantsInfoService plantsInfoService;

    public PlantsController(PlantsService plantsService, PlantsInfoService plantsInfoService) {
        this.plantsService = plantsService;
        this.plantsInfoService = plantsInfoService;
    }

    @PostMapping("newPlant")
    public ResponseEntity<PlantModel> createNewPlant(@RequestBody PlantModel plantModel) {
        plantsService.create(plantModel);
        return new ResponseEntity<PlantModel>(HttpStatus.OK);
    }

    @PostMapping("collection")
    public ResponseEntity<String> createPlantsList(@RequestBody List<PlantModel> plantModels) {
        plantsService.insertAllToDb(plantModels);
        return new ResponseEntity<String>("Success!", HttpStatus.OK);
    }

    // TODO -> create update plant logic here

    @GetMapping("{plantId}")
    public ResponseEntity<PlantModel> getInfoForPlantById(@PathVariable("plantId") int plantId) {
        PlantModel plant = plantsService.getById(plantId);
        return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
    }

    @GetMapping("allPlants")
    public ResponseEntity<List<PlantModel>> getInfoForAllPlants() {
        List<PlantModel> plantsList = plantsService.getAll();
        return new ResponseEntity<List<PlantModel>>(plantsList, HttpStatus.OK);
    }

    @PutMapping("additionalInfo/{plantId}")
    public ResponseEntity<String> addInfoForPlant(@PathVariable("plantId") int plantId,
                                                  @RequestBody AdditionalPlantInfo additionalPlantInfo) {
        plantsInfoService.addAdditionalInfoToPlant(plantId, additionalPlantInfo);
        return new ResponseEntity<String>("Successfully added more info", HttpStatus.OK);
    }

    @PutMapping("plantProblem/{plantId}")
    public ResponseEntity<String> addProblemInfoForPlant(@PathVariable("plantId") int plantId,
                                                             @RequestBody PotentialPlantProblems plantProblem) {
        plantsInfoService.addPotentialProblemsInfoToPlant(plantId, plantProblem);
        return new ResponseEntity<String>("Successfully added info about potential problem for plant", HttpStatus.OK);
    }
}
