package com.seabury.web.service;

import com.seabury.web.entity.dose.PlantEntity;

import java.util.List;

public interface PlantService {
    public List<PlantEntity> getPlantList(PlantEntity plantEntity);

    public int insertPlant(PlantEntity plantEntity);
    public int updatePlant(PlantEntity plantEntity);
    public int deletePlant(PlantEntity plantEntity);
}
