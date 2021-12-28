package com.server.backend.mappers;

import com.server.backend.dto.PlantInfoDto;
import com.server.backend.models.PlantModel;

public class PlantInfoMapper {

    public static PlantModel mapPlantDtoToPlantModel(PlantInfoDto info){
        PlantModel plant = new PlantModel();
        plant.setOrigin(info.getOrigin());
        plant.setAirHumidity(info.getAirHumidity());
        plant.setCommonName(info.getCommonName());
        plant.setLight(info.getLight());
        plant.setMaxGrowth(info.getMaxGrowth());
        plant.setPoisonousForPets(info.getPoisonousForPets());
        plant.setPropagation(info.getPropagation());
        plant.setRePotting(info.getRePotting());
        plant.setScientificName(info.getScientificName());
        plant.setSoil(info.getSoil());
        plant.setTemperature(info.getTemperature());
        plant.setWatering(info.getWatering());
        plant.setWhereItGrowsBest(info.getWhereItGrowsBest());
        return plant;
    }
}
