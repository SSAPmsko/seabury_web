package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.VRDose_ProjectEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VRDoseService {
    ArrayMap getProject(String id);
    ArrayList getProjects(String roomId);
    ArrayMap insertProject(HashMap<String,Object> map);
    ArrayMap updateProject(HashMap<String,Object> map);
    Boolean deleteProject(HashMap<String,Object> map);

    List<Map<String, Object>> getAllScenario(String roomId);
    List<Map<String, Object>> getScenarios(String projectId);
    ArrayMap getScenario(String id);
    ArrayMap insertScenario(HashMap<String,Object> map);
    ArrayMap updateScenario(HashMap<String,Object> map);
    Boolean deleteScenario(HashMap<String,Object> map);

    List<Map<String, Object>> getEquipments(String scenarioId);
    List<Map<String, Object>> getWorkers(String scenarioId);
    List<Map<String, Object>> getSources(String scenarioId);
    List<Map<String, Object>> getShields(String scenarioId);
    List<Map<String, Object>> getWorkSteps(String scenarioId);

    List<Map<String, Object>> getAllEquipments(String roomId);
    List<Map<String, Object>> getAllWorkers(String roomId);
    List<Map<String, Object>> getAllSources(String roomId);
    List<Map<String, Object>> getAllShields(String roomId);
    List<Map<String, Object>> getAllWorkSteps(String roomId);

    Map<String, Object>  getEquipment(String scenarioId, String id);
    Map<String, Object>  getWorker(String scenarioId, String id);
    Map<String, Object>  getSource(String scenarioId, String id);
    Map<String, Object>  getShield(String scenarioId, String id);
    Map<String, Object>  getWorkStep(String scenarioId, String id);
}
