package com.seabury.web.mapper;

import com.seabury.web.entity.UnitEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface UnitMapper {
    List<UnitEntity> getUnitList(UnitEntity UnitEntity);

    int insertUnit(UnitEntity UnitEntity);
    int updateUnit(UnitEntity UnitEntity);
    int deleteUnit(UnitEntity UnitEntity);
}
