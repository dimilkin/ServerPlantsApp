package com.server.backend.services;

import com.server.backend.models.UserPlant;

public interface UserPlantService {

    void addPlantToUserOwnCollection(int userId, int plantId);

    void addPlantToUserAssignedCollection(int userId, int plantId);

    void removePlantFromUserOwnCollection(int userId, int plantId);

    void removePlantFromUserAssignedCollection(int userId, int plantId);

    void createUserPlant(UserPlant userPlant);

    void update(UserPlant userPlant);

    UserPlant getById(int userPlantId);
}
