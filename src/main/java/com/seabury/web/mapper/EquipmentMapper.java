package com.seabury.web.mapper;

import com.seabury.web.entity.EquipmentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface EquipmentMapper {
    List<EquipmentEntity> getEquipmentList(EquipmentEntity equipmentEntity);

    int insertEquipment(EquipmentEntity equipmentEntity);
    int updateEquipment(EquipmentEntity equipmentEntity);
    int deleteEquipment(EquipmentEntity equipmentEntity);
}
