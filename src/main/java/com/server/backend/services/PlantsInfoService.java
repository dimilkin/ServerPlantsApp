package com.server.backend.services;

import com.server.backend.models.AdditionalPlantInfo;
import com.server.backend.models.PotentialPlantProblems;

public interface PlantsInfoService {

    String addAdditionalInfoToPlant(int plantId, AdditionalPlantInfo additionalPlantInfo);

    String updateAdditionalInfoOfPlant(int plantId, AdditionalPlantInfo additionalPlantInfo);

    String addPotentialProblemsInfoToPlant(int plantId, PotentialPlantProblems potentialPlantProblems);

    String updatePotentialProblemsInfoToPlant(int plantId, PotentialPlantProblems potentialPlantProblems);
}
