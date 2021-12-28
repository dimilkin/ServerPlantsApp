package com.server.backend.services.impl;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PlantModel;
import com.server.backend.models.PotentialPlantProblems;
import com.server.backend.repos.AdditionalPlantInfoRepo;
import com.server.backend.repos.PotentialPlantProblemsRepo;
import com.server.backend.services.PlantsInfoService;
import com.server.backend.services.PlantsService;
import org.springframework.stereotype.Service;

@Service
public class PlantsInfoServiceImpl implements PlantsInfoService {

    AdditionalPlantInfoRepo additionalPlantInfoRepo;
    PotentialPlantProblemsRepo potentialPlantProblemsRepo;
    PlantsService plantsService;

    public PlantsInfoServiceImpl(AdditionalPlantInfoRepo additionalPlantInfoRepo,
                                 PotentialPlantProblemsRepo potentialPlantProblemsRepo,
                                 PlantsService plantsService) {
        this.additionalPlantInfoRepo = additionalPlantInfoRepo;
        this.potentialPlantProblemsRepo = potentialPlantProblemsRepo;
        this.plantsService = plantsService;
    }

    @Override
    public String addAdditionalInfoToPlant(int plantId, AdditionalPlantInfo additionalPlantInfo) {
        PlantModel plantModel = plantsService.getById(plantId);
        additionalPlantInfo.setPlantId(plantModel);
        additionalPlantInfoRepo.save(additionalPlantInfo);
        return "Success!";
    }

    @Override
    public String updateAdditionalInfoOfPlant(int plantId, AdditionalPlantInfo additionalPlantInfo) {
        return null;
    }

    @Override
    public String addPotentialProblemsInfoToPlant(int plantId, PotentialPlantProblems potentialPlantProblems) {
        PlantModel plantModel = plantsService.getById(plantId);
        potentialPlantProblems.setPlantId(plantModel);
        potentialPlantProblemsRepo.save(potentialPlantProblems);
        return "Success!";
    }

    @Override
    public String updatePotentialProblemsInfoToPlant(int plantId, PotentialPlantProblems potentialPlantProblems) {
        return null;
    }
}
