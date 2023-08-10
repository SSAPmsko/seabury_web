package com.seabury.web.service;

import com.seabury.web.entity.dose.UnitEntity;

import java.util.List;

public interface UnitService {
    public List<UnitEntity> getUnitList(UnitEntity unitEntity);

    public int insertUnit(UnitEntity unitEntity);
    public int updateUnit(UnitEntity unitEntity);
    public int deleteUnit(UnitEntity unitEntity);
}
