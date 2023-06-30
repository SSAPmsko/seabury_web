package com.seabury.web.mapper;

import com.seabury.web.entity.EquipmentInformationEntity;
import com.seabury.web.entity.ProjectEntity;
import com.seabury.web.entity.WorkerInformationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface EquipmentInformationMapper {
    List<EquipmentInformationEntity> getEquipmentInformationList(EquipmentInformationEntity equipmentinformationEntity);

    int insertEquipmentInformation(EquipmentInformationEntity equipmentinformationEntity);
    int updateEquipmentInformation(EquipmentInformationEntity equipmentinformationEntity);
    int deleteEquipmentInformation(EquipmentInformationEntity equipmentinformationEntity);
}
