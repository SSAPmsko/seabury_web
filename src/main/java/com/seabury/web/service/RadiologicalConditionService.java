package com.seabury.web.service;

import com.seabury.web.entity.RadiologicalConditionEntity;
import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.ScenarioEntity;

import java.util.List;

public interface RadiologicalConditionService {
    public List<RadiologicalConditionEntity> getRadiologicalConditionList(RadiologicalConditionEntity RadiologicalConditionEntity);

    public int insertRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
    public int updateRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
    public int deleteRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
}
