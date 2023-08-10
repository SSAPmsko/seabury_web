package com.seabury.web.mapper.dose;

import com.seabury.web.entity.dose.RadiologicalConditionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface RadiologicalConditionMapper {
    List<RadiologicalConditionEntity> getRadiologicalConditionList(RadiologicalConditionEntity radiologicalconditionEntity);

    int insertRadiologicalCondition(RadiologicalConditionEntity radiologicalconditionEntity);
    int updateRadiologicalCondition(RadiologicalConditionEntity radiologicalconditionEntity);
    int deleteRadiologicalCondition(RadiologicalConditionEntity radiologicalconditionEntity);
}
