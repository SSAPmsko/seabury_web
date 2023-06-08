package com.seabury.web.mapper;

import com.seabury.web.entity.StructureEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface StructureMapper {
    List<StructureEntity> getStructureList(StructureEntity StructureEntity);

    int insertStructure(StructureEntity StructureEntity);
    int updateStructure(StructureEntity StructureEntity);
    int deleteStructure(StructureEntity StructureEntity);
}
