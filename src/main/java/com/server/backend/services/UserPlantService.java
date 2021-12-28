package com.server.backend.services;

import com.server.backend.models.UserPlant;

public interface UserPlantService {

    void addPlantToUserOwnCollection(UserPlant userPlant);

    void addPlantToUserAssignedCollection(int userOwnerid, int plantIdi, int  userAssignedId);

    void removePlantFromUserOwnCollection(int userId, int plantId);

    void removePlantFromUserAssignedCollection(int userId, int plantId);

    UserPlant getById(int userPlantId);
}
