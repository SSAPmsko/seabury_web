package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.ScenarioEntity;

import java.util.List;

public interface ScenarioService {
    public List<ScenarioEntity> getScenarioList(ScenarioEntity scenarioEntity);

    public int insertScenario(ScenarioEntity scenarioEntity);
    public int updateScenario(ScenarioEntity scenarioEntity);
    public int deleteScenario(ScenarioEntity scenarioEntity);
}
