package com.server.backend.services.impl;

import com.server.backend.models.PlantModel;
import com.server.backend.models.UserInfo;
import com.server.backend.repos.PlantsRepo;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantsServiceImpl implements PlantsService {

    private PlantsRepo plantsRepo;
    private UserInfoService userInfoService;

    public PlantsServiceImpl(PlantsRepo plantsRepo, UserInfoService userInfoService) {
        this.plantsRepo = plantsRepo;
        this.userInfoService = userInfoService;
    }

    @Override
    public List<PlantModel> getAll() {
        return plantsRepo.findAll();
    }

    @Override
    public void insertAllToDb(List<PlantModel> plants) {
        plants.forEach( plant -> plantsRepo.save(plant));
    }

    @Override
    public void create(PlantModel plant) {
        plantsRepo.save(plant);
    }

    @Override
    public void update(PlantModel plant) {
        plantsRepo.save(plant);
    }

    @Override
    public void delete(int id) {
        PlantModel plant = plantsRepo.findById(id).get();
        plantsRepo.delete(plant);
    }

    @Override
    public PlantModel getById(int id) {
        return plantsRepo.findById(id).get();
    }

    @Override
    public void addPlantToUserCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        PlantModel plant = plantsRepo.findById(plantId).get();
        user.addPlantToOwnCollection(plant);
        userInfoService.update(user);
    }

    @Override
    public void removePlantFromUserCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        PlantModel plant = plantsRepo.findById(plantId).get();
        user.removePlantFromOwnCollection(plant);
        userInfoService.update(user);
    }
}
