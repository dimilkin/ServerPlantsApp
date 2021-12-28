package com.server.backend.services.impl;

import javax.persistence.EntityNotFoundException;

import com.server.backend.dto.PlantInfoDto;
import com.server.backend.mappers.PlantInfoMapper;
import com.server.backend.models.PlantModel;
import com.server.backend.repos.PlantsRepo;
import com.server.backend.services.PlantsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlantsServiceImpl implements PlantsService {

    private PlantsRepo plantsRepo;

    public PlantsServiceImpl(PlantsRepo plantsRepo) {
        this.plantsRepo = plantsRepo;
    }

    @Override
    public List<PlantModel> getAll() {
        return plantsRepo.findAll();
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
        if (plantsRepo.findById(id).isPresent()) {
            return plantsRepo.findById(id).get();
        } else {
            throw new EntityNotFoundException("No plant with that ID exists in DB");
        }
    }
}
