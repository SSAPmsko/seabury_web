package com.seabury.web.service;

import com.seabury.web.entity.dose.RadiologicalConditionEntity;

import java.util.List;

public interface RadiologicalConditionService {
    public List<RadiologicalConditionEntity> getRadiologicalConditionList(RadiologicalConditionEntity RadiologicalConditionEntity);

    public int insertRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
    public int updateRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
    public int deleteRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity);
}
