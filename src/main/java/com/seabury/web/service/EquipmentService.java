package com.seabury.web.service;

import com.seabury.web.entity.dose.EquipmentEntity;

import java.util.List;

public interface EquipmentService {
    public List<EquipmentEntity> getEquipmentList(EquipmentEntity equipmentEntity);

    public int insertEquipment(EquipmentEntity equipmentEntity);
    public int updateEquipment(EquipmentEntity equipmentEntity);
    public int deleteEquipment(EquipmentEntity equipmentEntity);
}
