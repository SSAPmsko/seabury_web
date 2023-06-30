package com.seabury.web.service;

import com.seabury.web.entity.EquipmentInformationEntity;
import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.ScenarioEntity;

import java.util.List;

public interface EquipmentInformationService {
    public List<EquipmentInformationEntity> getEquipmentInformationList(EquipmentInformationEntity EquipmentInformationEntity);

    public int insertEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity);
    public int updateEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity);
    public int deleteEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity);
}
