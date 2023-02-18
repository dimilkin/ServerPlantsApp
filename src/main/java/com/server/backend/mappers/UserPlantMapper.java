package com.server.backend.mappers;

import com.server.backend.dto.UserPlantDto;
import com.server.backend.models.UserPlant;

public class UserPlantMapper {
    public static UserPlantDto mapUserPlantToDto (UserPlant userPlant){
        UserPlantDto plantDto = new UserPlantDto();
        plantDto.setId(userPlant.getId());
        plantDto.setPlantModelId(userPlant.getPlant().getId());
        plantDto.setProvidedName(userPlant.getProvidedName());
        plantDto.setWaterPeriod(userPlant.getWaterPeriod());
        return plantDto;
    }
    // empty commit
}
