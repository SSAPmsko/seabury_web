package com.seabury.web.mapper;

import com.seabury.web.entity.ProjectEntity;
import com.seabury.web.entity.ScenarioEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface ScenarioMapper {
    List<ScenarioEntity> getScenarioList(ScenarioEntity scenarioEntity);

    int insertScenario(ScenarioEntity scenarioEntity);
    int updateScenario(ScenarioEntity scenarioEntity);
    int deleteScenario(ScenarioEntity scenarioEntity);
}
