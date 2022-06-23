package com.server.backend.services;

import com.server.backend.models.UserPlant;

public interface UserPlantService {

    UserPlant addPlantToUserOwnCollection(UserPlant userPlant);

    UserPlant updateUserPlant(UserPlant userPlant);

    void addPlantToUserAssignedCollection(int userPlantId, int  userAssignedId);

    void removePlantFromUserOwnCollection(int userPlantId);

    void removePlantFromUserAssignedCollection(int userId, int plantId);

    UserPlant getById(int userPlantId);
}
