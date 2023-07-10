package com.seabury.web.service;

import com.seabury.web.entity.EquipmentEntity;
import com.seabury.web.mapper.EquipmentMapper;
import com.seabury.web.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentMapper equipmentMapper;

    @Override
    public List<EquipmentEntity> getEquipmentList(EquipmentEntity equipmentEntity) {
        return equipmentMapper.getEquipmentList(equipmentEntity);
    }

    @Override
    public int insertEquipment(EquipmentEntity equipmentEntity) {
        return equipmentMapper.insertEquipment(equipmentEntity);
    }

    @Override
    public int updateEquipment(EquipmentEntity equipmentEntity) {
        return equipmentMapper.updateEquipment(equipmentEntity);
    }

    @Override
    public int deleteEquipment(EquipmentEntity equipmentEntity) {
        return equipmentMapper.deleteEquipment(equipmentEntity);
    }

    public EquipmentVO ConvertEntityToVO(EquipmentEntity equipmentEntity) {
        EquipmentVO equipmentVO = new EquipmentVO();

        if (equipmentEntity != null) {
            equipmentVO.setID(equipmentEntity.getID());
            equipmentVO.setProject_ID(equipmentEntity.getProject_ID());
            equipmentVO.setScenario_ID(equipmentEntity.getScenario_ID());
            equipmentVO.setName(equipmentEntity.getName());
            equipmentVO.setDescription(equipmentEntity.getDescription());
        }
        return equipmentVO;
    }

    public EquipmentEntity ConvertVOToEntity(EquipmentVO equipmentVO) {
        EquipmentEntity equipmentEntity = new EquipmentEntity();

        if (equipmentVO != null){
            equipmentEntity.setID(equipmentVO.getID());
            equipmentEntity.setProject_ID(equipmentVO.getProject_ID());
            equipmentEntity.setScenario_ID(equipmentVO.getScenario_ID());
            equipmentEntity.setName(equipmentVO.getName());
            equipmentEntity.setDescription(equipmentVO.getDescription());
        }
        return equipmentEntity;
    }
}
