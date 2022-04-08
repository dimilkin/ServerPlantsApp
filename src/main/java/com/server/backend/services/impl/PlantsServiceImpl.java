package com.server.backend.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import com.server.backend.dto.PlantInfoDto;
import com.server.backend.dto.PlantsSearchDtoInfo;
import com.server.backend.mappers.PlantInfoMapper;
import com.server.backend.models.PlantModel;
import com.server.backend.repos.PlantsRepo;
import com.server.backend.services.PlantsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantsServiceImpl implements PlantsService {

    private PlantsRepo plantsRepo;
    private List<PlantsSearchDtoInfo> plantsSearchDtoInfos;
    public List<PlantModel> allPlantsInfoFromDb;

    public PlantsServiceImpl(PlantsRepo plantsRepo) {
        this.plantsRepo = plantsRepo;
    }

    @Override
    public List<PlantModel> getAll() {
        if (allPlantsInfoFromDb == null){
            allPlantsInfoFromDb = new ArrayList<>();
        }
        allPlantsInfoFromDb.clear();
        allPlantsInfoFromDb.addAll(plantsRepo.findAll());
        return allPlantsInfoFromDb;
    }

    @Override
    public List<PlantsSearchDtoInfo> getSearchInfo(){
        if (plantsSearchDtoInfos == null || plantsSearchDtoInfos.isEmpty()){
            plantsSearchDtoInfos = new ArrayList<>();
            plantsSearchDtoInfos.addAll(getSearchInfoFromDb());
        }
        return plantsSearchDtoInfos;
    }

    private List<PlantsSearchDtoInfo> getSearchInfoFromDb() {
        return getAll().stream()
                .map(plantModel -> new PlantsSearchDtoInfo(plantModel.getId(), plantModel.getCommonName(), plantModel.getScientificName()))
                .collect(Collectors.toList());
    }

    @Override
    public void insertAllToDb(List<PlantModel> plants) {
        plants.forEach(plant -> plantsRepo.save(plant));
    }

    @Override
    public void create(PlantModel plant) {
        plantsRepo.save(plant);
    }

    @Override
    public void update(PlantInfoDto plant, int plantId) {
        PlantModel plantModel = PlantInfoMapper.mapPlantDtoToPlantModel(plant);
        plantModel.setId(plantId);
        plantsRepo.save(plantModel);
    }

    @Override
    public void delete(int id) {
        PlantModel plant = findPlantById(id);
        plantsRepo.delete(plant);
    }

    @Override
    public PlantModel getById(int id) {
        return findPlantById(id);
    }

    private PlantModel findPlantById(int id) {
        return getAll().stream()
                .filter(model -> model.getId() == id)
                .findFirst().orElse(getPlantInfoFromDb(id));
    }

    private PlantModel getPlantInfoFromDb(int id){
        if (plantsRepo.findById(id).isPresent()) {
            return plantsRepo.findById(id).get();
        } else {
            throw new EntityNotFoundException("No plant with that ID exists in DB");
        }
    }
}
