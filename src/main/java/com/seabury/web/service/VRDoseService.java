package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.VRDose_ProjectEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface VRDoseService {
    ArrayMap getProject(String id);

    ArrayList getProjects(String roomId);

    ArrayMap insertProject(HashMap<String,Object> map);

    ArrayMap updateProject(HashMap<String,Object> map);

    Boolean deleteProject(HashMap<String,Object> map);

    ArrayList getAllScenario(String projectId);

    ArrayMap getScenario(String id);

    List<ArrayMap<String, Object>> getEquipments(String scenarioId);

    List<ArrayMap<String, Object>> getWorkers(String scenarioId);

    List<ArrayMap<String, Object>> getSources(String scenarioId);

    List<ArrayMap<String, Object>> getShields(String scenarioId);

    ArrayMap insertScenario(HashMap<String,Object> map);

    ArrayMap updateScenario(HashMap<String,Object> map);

    Boolean deleteScenario(HashMap<String,Object> map);

    List<ArrayMap<String, Object>> getWorkSteps(String scenarioId);
}
