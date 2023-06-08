package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;

import java.util.List;

public interface PlantService {
    public List<PlantEntity> getPlantList(PlantEntity PlantEntity);

    public int insertPlant(PlantEntity PlantEntity);
    public int updatePlant(PlantEntity PlantEntity);
    public int deletePlant(PlantEntity PlantEntity);
}
