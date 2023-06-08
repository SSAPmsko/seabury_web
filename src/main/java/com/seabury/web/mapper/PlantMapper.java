package com.seabury.web.mapper;

import com.seabury.web.entity.PlantEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface PlantMapper {
    List<PlantEntity> getPlantList(PlantEntity PlantEntity);

    int insertPlant(PlantEntity PlantEntity);
    int updatePlant(PlantEntity PlantEntity);
    int deletePlant(PlantEntity PlantEntity);
}
