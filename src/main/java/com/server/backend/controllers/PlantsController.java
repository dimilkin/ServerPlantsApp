package com.server.backend.controllers;

import com.server.backend.dto.PlantInfoDto;
import com.server.backend.dto.PlantsSearchDtoInfo;
import com.server.backend.exceptions.GlobalExceptionsHandler;
import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import com.server.backend.models.PotentialPlantProblems;
import com.server.backend.services.PlantsInfoService;
import com.server.backend.services.PlantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);


    public PlantsController(PlantsService plantsService, PlantsInfoService plantsInfoService) {
        this.plantsService = plantsService;
        this.plantsInfoService = plantsInfoService;
    }

    @PostMapping("newPlant")
    public ResponseEntity<PlantModel> createNewPlant(@RequestBody PlantModel plantModel) {
        plantsService.create(plantModel);
        return new ResponseEntity<PlantModel>(HttpStatus.OK);
    }

    @PostMapping("addAll/plant")
    public ResponseEntity<String> createPlantsList(@RequestBody List<PlantModel> plantModels) {
        plantsService.insertAllToDb(plantModels);
        return new ResponseEntity<String>( HttpStatus.OK);
    }

    @PutMapping("updatePlant/{plantId}")
    public ResponseEntity<PlantModel> updatePlant(@PathVariable("plantId") int plantId,
                                                  @RequestBody PlantInfoDto plantInfoDto) {
        plantsService.update(plantInfoDto, plantId);
        return new ResponseEntity<PlantModel>(HttpStatus.OK);
    }

    @GetMapping("{plantId}")
    public ResponseEntity<PlantModel> getInfoForPlantById(@PathVariable("plantId") int plantId) {
        logger.info("Request made for info for plant with Id : " + plantId);
        PlantModel plant = plantsService.getById(plantId);
        return new ResponseEntity<PlantModel>(plant, HttpStatus.OK);
    }

    @GetMapping("allPlants")
    public ResponseEntity<List<PlantModel>> getInfoForAllPlants() {
        logger.info("Request made for info for all plants");
        List<PlantModel> plantsList = plantsService.getAll();
        return new ResponseEntity<List<PlantModel>>(plantsList, HttpStatus.OK);
    }

    @GetMapping("allPlantsInfo")
    public ResponseEntity<List<PlantsSearchDtoInfo>> getSearchInfoForAllPlants() {
        List<PlantsSearchDtoInfo> plantsList = plantsService.getSearchInfo();
        return new ResponseEntity<List<PlantsSearchDtoInfo>>(plantsList, HttpStatus.OK);
    }

    @PutMapping("additionalInfo/{plantId}")
    public ResponseEntity<String> addInfoForPlant(@PathVariable("plantId") int plantId,
                                                  @RequestBody AdditionalPlantInfo additionalPlantInfo) {
        plantsInfoService.addAdditionalInfoToPlant(plantId, additionalPlantInfo);
        return new ResponseEntity<String>("Successfully added more info", HttpStatus.OK);
    }

    @PutMapping("listOfAdditionalInfo/{plantId}")
    public ResponseEntity<String> addListOfInfoForPlant(@PathVariable("plantId") int plantId,
                                                  @RequestBody List<AdditionalPlantInfo> additionalPlantInfoList) {
        plantsInfoService.addListOfAdditionalInfoToPlant(plantId, additionalPlantInfoList);
        return new ResponseEntity<String>("Successfully added more info", HttpStatus.OK);
    }

    @PutMapping("plantProblem/{plantId}")
    public ResponseEntity<String> addProblemInfoForPlant(@PathVariable("plantId") int plantId,
                                                             @RequestBody PotentialPlantProblems plantProblem) {
        logger.info("Adding problemInfo for plant : " + plantId);
        plantsInfoService.addPotentialProblemsInfoToPlant(plantId, plantProblem);
        return new ResponseEntity<String>("Successfully added info about potential problem for plant", HttpStatus.OK);
    }

    @PutMapping("allPlantProblems/{plantId}")
    public ResponseEntity<String> addAllProblemInfoForPlant(@PathVariable("plantId") int plantId,
                                                         @RequestBody List<PotentialPlantProblems> plantProblems) {
        plantsInfoService.addListOfPotentialProblemsInfoToPlant(plantId, plantProblems);
        return new ResponseEntity<String>("Successfully added info about potential problem for plant", HttpStatus.OK);
    }
}
