package com.server.backend.services.impl;

import com.server.backend.models.UserInfo;
import com.server.backend.models.UserPlant;
import com.server.backend.repos.UserPlantRepo;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserPlantService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class UserPlantServiceImpl implements UserPlantService {

    UserInfoService userInfoService;
    PlantsService plantsService;
    UserPlantRepo userPlantRepo;

    public UserPlantServiceImpl(UserInfoService userInfoService, PlantsService plantsService, UserPlantRepo userPlantRepo) {
        this.userInfoService = userInfoService;
        this.plantsService = plantsService;
        this.userPlantRepo = userPlantRepo;
    }

    @Override
    public void createUserPlant(UserPlant userPlant) {
        userPlantRepo.save(userPlant);
    }

    @Override
    public void update(UserPlant userPlant) {
        userPlantRepo.save(userPlant);
    }

    @Override
    public UserPlant getById(int userPlantId) {
        return findPlantById(userPlantId);
    }

    @Override
    public void addPlantToUserOwnCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        addPlantToUserCollection(user, plantId, user.getOwnPlants());
    }

    @Override
    public void addPlantToUserAssignedCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        addPlantToUserCollection(user, plantId, user.getAssignedPlants());
    }

    @Override
    public void removePlantFromUserOwnCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        removePlantFromUserCollection(user, plantId, user.getOwnPlants());
    }

    @Override
    public void removePlantFromUserAssignedCollection(int userId, int plantId) {
        UserInfo user = userInfoService.getById(userId);
        removePlantFromUserCollection(user, plantId, user.getAssignedPlants());
    }

    private <E extends Collection<UserPlant>> void addPlantToUserCollection(UserInfo user, int plantId, E collection) {
        UserPlant plant = findPlantById(plantId);
        user.addPlantToCollection(plant, collection);
        userInfoService.update(user);
    }

    private <E extends Collection<UserPlant>> void removePlantFromUserCollection(UserInfo user, int plantId, E e) {
        UserPlant plant = findPlantById(plantId);
        user.removePlantFromCollection(plant, e);
        userInfoService.update(user);
    }

    private UserPlant findPlantById(int id) {
        if (userPlantRepo.findById(id).isPresent()) {
            return userPlantRepo.findById(id).get();
        } else {
            throw new EntityNotFoundException("No plant with that ID exists in DB");
        }
    }
}
