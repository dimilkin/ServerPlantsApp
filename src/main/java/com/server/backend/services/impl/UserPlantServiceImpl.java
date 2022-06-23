package com.server.backend.services.impl;

import com.server.backend.models.UserInfo;
import com.server.backend.models.UserPlant;
import com.server.backend.repos.UserPlantRepo;
import com.server.backend.services.PlantsService;
import com.server.backend.services.UserInfoService;
import com.server.backend.services.UserPlantService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
    public UserPlant addPlantToUserOwnCollection(UserPlant userPlant) {
        return userPlantRepo.save(userPlant);
    }

    @Override
    public UserPlant updateUserPlant(UserPlant userPlant) {
        return userPlantRepo.save(userPlant);
    }

    @Override
    public UserPlant getById(int userPlantId) {
        return findPlantById(userPlantId);
    }

    @Override
    public void addPlantToUserAssignedCollection(int userPlantid, int userAssignedId) {
        UserPlant userPlant = findUserPlantByUserOwnerIdAndPlantId(userPlantid);
        UserInfo user = userInfoService.getById(userAssignedId);
        userPlant.setUserAssigned(user);
        userPlantRepo.save(userPlant);
    }

    @Override
    public void removePlantFromUserOwnCollection(int userPlantId) {
        UserPlant userPlant = findUserPlantByUserOwnerIdAndPlantId(userPlantId);
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
            String errorMessage = String.format("No userPlant with ID: %s exists in DB", id);
            throw new EntityNotFoundException(errorMessage);
        }
    }

    private UserPlant findUserPlantByUserOwnerIdAndPlantId(int userPlantId) {

        UserPlant userPlant = userPlantRepo.getById(userPlantId);
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
