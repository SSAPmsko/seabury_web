package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.ScenarioEntity;

import java.util.List;

public interface ScenarioService {
    public List<ScenarioEntity> getScenarioList(ScenarioEntity ScenarioEntity);

    public int insertScenario(ScenarioEntity ScenarioEntity);
    public int updateScenario(ScenarioEntity ScenarioEntity);
    public int deleteScenario(ScenarioEntity ScenarioEntity);
}
