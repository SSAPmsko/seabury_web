package com.seabury.web.service;

import com.seabury.web.entity.UnitEntity;

import java.util.List;

public interface UnitService {
    public List<UnitEntity> getUnitList(UnitEntity UnitEntity);

    public int insertUnit(UnitEntity UnitEntity);
    public int updateUnit(UnitEntity UnitEntity);
    public int deleteUnit(UnitEntity UnitEntity);
}
