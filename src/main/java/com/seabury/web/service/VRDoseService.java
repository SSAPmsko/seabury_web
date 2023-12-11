package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VRDoseService {
    ArrayMap<String, Object> getProject(String id);
    ArrayList<ArrayMap<String, Object>> getProjects(String roomId);
    ArrayMap<String, Object> insertProject(HashMap<String,Object> map);
    ArrayMap<String, Object> updateProject(HashMap<String,Object> map);
    Boolean deleteProject(HashMap<String,Object> map);

    ArrayList<ArrayMap<String, Object>> getAllScenario(String roomId);
    ArrayList<ArrayMap<String, Object>> getScenarios(String projectId);
    ArrayMap<String, Object> getScenario(String id);
    ArrayMap<String, Object> insertScenario(HashMap<String,Object> map);
    ArrayMap<String, Object> updateScenario(HashMap<String,Object> map);
    Boolean deleteScenario(HashMap<String,Object> map);

    ArrayList<ArrayMap<String, Object>> getEquipments(String scenarioId);
    ArrayList<ArrayMap<String, Object>> getWorkers(String scenarioId);
    ArrayList<ArrayMap<String, Object>> getSources(String scenarioId);
    ArrayList<ArrayMap<String, Object>> getShields(String scenarioId);
    ArrayList<ArrayMap<String, Object>> getWorkSteps(String scenarioId);

    ArrayList<ArrayMap<String, Object>> getAllEquipments(String roomId);
    ArrayList<ArrayMap<String, Object>> getAllWorkers(String roomId);
    ArrayList<ArrayMap<String, Object>> getAllSources(String roomId);
    ArrayList<ArrayMap<String, Object>> getAllShields(String roomId);
    ArrayList<ArrayMap<String, Object>> getAllWorkSteps(String roomId);

    ArrayMap<String, Object>  getEquipment(String scenarioId, String id);
    ArrayMap<String, Object>  getWorker(String scenarioId, String id);
    ArrayMap<String, Object>  getSource(String scenarioId, String id);
    ArrayMap<String, Object>  getShield(String scenarioId, String id);
    ArrayMap<String, Object>  getWorkStep(String scenarioId, String id);
}
