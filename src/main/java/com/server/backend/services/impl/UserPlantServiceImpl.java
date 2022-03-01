package com.server.backend.services.impl;

import com.server.backend.models.UserInfo;
import com.server.backend.models.UserPlant;
import com.server.backend.repos.UserPlantRepo;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserPlantService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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
    public void addPlantToUserOwnCollection(UserPlant userPlant) {
        userPlantRepo.save(userPlant);
    }

    @Override
    public UserPlant getById(int userPlantId) {
        return findPlantById(userPlantId);
    }

    @Override
    public void addPlantToUserAssignedCollection(int userOwnerid, int plantId, int  userAssignedId) {
        UserPlant userPlant = findUserPlantByUserOwnerIdAndPlantId(userOwnerid, plantId);
        UserInfo user = userInfoService.getById(userAssignedId);
        userPlant.setUserAssigned(user);
        userPlantRepo.save(userPlant);
    }

    @Override
    public void removePlantFromUserOwnCollection(int userId, int plantId) {
        UserPlant userPlant = findUserPlantByUserOwnerIdAndPlantId(userId, plantId);
        userPlantRepo.delete(userPlant);
    }

    @Override
    public void removePlantFromUserAssignedCollection(int userId, int plantId) {
       UserPlant userPlant = findUserPlantByAssignedUserIdAndPlantId(userId, plantId);
       userPlant.setUserAssigned(null);
       userPlantRepo.save(userPlant);
    }

    private UserPlant findPlantById(int id) {
        if (userPlantRepo.findById(id).isPresent()) {
            return userPlantRepo.findById(id).get();
        } else {
            throw new EntityNotFoundException("No plant with that ID exists in DB");
        }
    }

    private UserPlant findUserPlantByUserOwnerIdAndPlantId(int userId, int plantId) {
        UserPlant userPlant = userPlantRepo.findByUserIdAndPlantId(userId, plantId);
        if (userPlant == null) {
            throw new EntityNotFoundException("Plant for this user can't be found");
        }
        return userPlant;
    }

    private UserPlant findUserPlantByAssignedUserIdAndPlantId(int userId, int plantId) {
        UserPlant userPlant = userPlantRepo.findByAssignerUserIdAndPlantId(userId, plantId);
        if (userPlant == null) {
            throw new EntityNotFoundException("Plant for this user can't be found");
        }
        return userPlant;
    }
}