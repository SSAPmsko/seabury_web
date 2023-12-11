package com.seabury.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.dose.VRDose_PropertiesEntity;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VRDoseServiceImpl implements VRDoseService {
    @Autowired
    private VRDose_PropertiesEntity vrDose_propertiesEntity;

    private HttpRequestFactory requestFactory;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Override
    public ArrayMap<String, Object> getProject(String id) {

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/projectdb/projects/" + id);

        ArrayMap<String, Object> result = new ArrayMap<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());
            castingObjectToString(result);
            convertLongToDate(result, "date");
            convertLongToDate(result, "startDate");
            convertLongToDate(result, "endDate");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getProjects(String roomId) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects");
        if (!StringUtils.isEmpty(roomId)){
            gUrl.set("roomId", roomId);
        }

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            if (result != null){
                for (Object item : result = request.execute().parseAs(result.getClass())) {
                    if (item instanceof ArrayMap){
                        ArrayMap<String, Object> castingItem = castingObjectToString(item);
                        convertStringToFloat(castingItem, "doseLimit");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> insertProject(HashMap<String,Object> map) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects");

        ArrayMap<String, Object> result = new ArrayMap<>();

        try {
            convertDateToISO8601(map, "date");
            convertDateToISO8601(map, "startDate");
            convertDateToISO8601(map, "endDate");
            convertStringToFloat(map, "doseLimit");

            map.remove("editMode"); // View 에서 생성/수정을 확인하기 위한 필드이므로 삭제

            String bodyStr = getJsonString(map);

            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

            result = request.execute().parseAs(result.getClass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> updateProject(HashMap<String,Object> map) {

        ArrayMap<String, Object> result = new ArrayMap<>();

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects/" + id);

            try {
                convertDateToISO8601(map, "date");
                convertDateToISO8601(map, "startDate");
                convertDateToISO8601(map, "endDate");
                convertStringToFloat(map, "doseLimit");

                map.remove("editMode"); // View 에서 생성/수정을 확인하기 위한 필드이므로 삭제

                String bodyStr = getJsonString(map);

                HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

                HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

                result = request.execute().parseAs(result.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Boolean deleteProject(HashMap<String,Object> map) {

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects/" + id);

            try {
                HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);

                request.execute();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayMap<String, Object> getScenario(String id) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + id);

        ArrayMap<String, Object> result = new ArrayMap();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            castingObjectToStringFromScenario(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getScenarios(String projectId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata");
        if (!StringUtils.isEmpty(projectId)){
            gUrl.set("projectId", projectId);
        }

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            if (result != null){
                for (ArrayMap<String, Object> item : result) {
                    castingObjectToStringFromScenario(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllScenario(String roomId){

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0) {
            for (ArrayMap project : allProjects) {
                String projectId = project.get("id").toString();
                ArrayList<ArrayMap<String, Object>> scenarios = getScenarios(projectId);
                if (scenarios != null && scenarios.size() > 0){
                    result.addAll(scenarios);
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> insertScenario(HashMap<String,Object> map){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata");

        ArrayMap<String, Object> result = new ArrayMap();

        try {
            convertDateToISO8601(map, "date");
            convertDateToISO8601(map, "lastModified");
            convertDateToISO8601(map, "startTime");
            convertDateToISO8601(map, "endTime");

            map.remove("editMode"); // View 에서 생성/수정을 확인하기 위한 필드이므로 삭제

            String bodyStr = getJsonString(map);

            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

            result = request.execute().parseAs(result.getClass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> updateScenario(HashMap<String,Object> map) {

        ArrayMap<String, Object> result = new ArrayMap();

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            ArrayMap<String, Object> updateData = getScenario(id);

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + id);

            try {
                convertDateToISO8601(map, "date");
                convertDateToISO8601(map, "lastModified");
                convertDateToISO8601(map, "startTime");
                convertDateToISO8601(map, "endTime");

                // Update Properties
                map.forEach((k, v) -> {
                    if (updateData.containsKey(k)){
                        updateData.put(k, v);
                    }
                });

                // Empty Value Convert
                castingObjectToString(updateData);

                Object participantInfo = updateData.get("participantInfo");
                if (participantInfo instanceof ArrayList){
                    if (participantInfo != null && ((ArrayList<?>) participantInfo).size() > 0){
                        List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;

                        for (ArrayMap<String, Object> _item : datalist){
                            castingObjectToString(_item);
                            convertStringToFloat(_item,"accumulatedDose");
                            convertStringToFloat(_item,"minDose");
                            convertStringToFloat(_item,"maxDose");
                            convertStringToFloat(_item,"workTime");
                        }
                    }
                }

                String bodyStr = getJsonString(updateData);

                HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

                HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

                result = request.execute().parseAs(result.getClass());

                // 데이터를 다시 취득했을시, 변환과정을 또 거쳐야 하므로 이미 변한된 데이터 재 활용
                if (result != null){
                    result = updateData;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Boolean deleteScenario(HashMap<String,Object> map) {

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + id);

            try {
                HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);

                request.execute();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getEquipments(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap<String, Object> scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                result = (ArrayList<ArrayMap<String, Object>>)participantInfo;
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        castingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getWorkers(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap<String, Object> scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                result = (ArrayList<ArrayMap<String, Object>>)participantInfo;
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        castingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getSources(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap<String, Object> scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                result = (ArrayList<ArrayMap<String, Object>>)participantInfo;
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        castingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getShields(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                result = (ArrayList<ArrayMap<String, Object>>)participantInfo;
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        castingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getWorkSteps(String scenarioId){

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId + "/workplan");

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            InputStream inputStream = request.execute().getContent();

            String xmlString = getXmlString(inputStream);
            Object elementWorkPlan =  xmlToJson(xmlString);

            if (elementWorkPlan != null){
                LinkedHashMap castingWorkPlan = (LinkedHashMap)elementWorkPlan;
                if (castingWorkPlan != null && castingWorkPlan.size() > 0){
                    LinkedHashMap<String, Object> castingWorkPlanData = (LinkedHashMap)castingWorkPlan.get("WorkPlan");

                    if (castingWorkPlanData != null && castingWorkPlanData.size() > 0){
                        LinkedHashMap stepsObject = (LinkedHashMap)castingWorkPlanData.get("steps");
                        if (!stepsObject.isEmpty()){
                            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>)stepsObject.get("WorkStep");
                            list.forEach(item -> {
                                ArrayMap<String, Object> resultItem = new ArrayMap<String, Object>();
                                item.forEach((k, v) -> {
                                    if (!k.equals("actions")){
                                        resultItem.add(k, v);
                                    }
                                });
                                result.add(resultItem);
                            });
                            //result = (ArrayList)stepsObject.get("WorkStep");// WorkStep
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllEquipments(String roomId) {

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0){
            for (ArrayMap<String, Object> project : allProjects){
                String projectId = project.get("id").toString();

                ArrayList<ArrayMap<String, Object>> allScenario = getScenarios(projectId);
                if (allScenario != null && allScenario.size() > 0){
                    for (ArrayMap<String, Object> scenario : allScenario){
                        String scenarioId = scenario.get("id").toString();

                        ArrayList<ArrayMap<String, Object>> equipments = getEquipments(scenarioId);
                        for (ArrayMap<String, Object> equipment : equipments){
                            equipment.put("projectId", projectId);
                            equipment.put("scenarioId", scenarioId);
                        }

                        result.addAll(equipments);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllWorkers(String roomId) {

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0){
            for (ArrayMap<String, Object> project : allProjects){
                String projectId = project.get("id").toString();

                ArrayList<ArrayMap<String, Object>> allScenario = getScenarios(projectId);
                if (allScenario != null && allScenario.size() > 0){
                    for (ArrayMap<String, Object> scenario : allScenario){
                        String scenarioId = scenario.get("id").toString();

                        ArrayList<ArrayMap<String, Object>> workers = getWorkers(scenarioId);
                        for (ArrayMap<String, Object> worker : workers){
                            worker.put("projectId", projectId);
                            worker.put("scenarioId", scenarioId);
                        }

                        result.addAll(workers);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllSources(String roomId) {


        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0){
            for (ArrayMap<String, Object> project : allProjects){
                String projectId = project.get("id").toString();

                ArrayList<ArrayMap<String, Object>> allScenario = getScenarios(projectId);
                if (allScenario != null && allScenario.size() > 0){
                    for (ArrayMap<String, Object> scenario : allScenario){
                        String scenarioId = scenario.get("id").toString();

                        ArrayList<ArrayMap<String, Object>> sources = getSources(scenarioId);
                        for (ArrayMap<String, Object> source : sources){
                            source.put("projectId", projectId);
                            source.put("scenarioId", scenarioId);
                        }

                        result.addAll(sources);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllShields(String roomId) {

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0){
            for (ArrayMap<String, Object> project : allProjects){
                String projectId = project.get("id").toString();

                ArrayList<ArrayMap<String, Object>> allScenario = getScenarios(projectId);
                if (allScenario != null && allScenario.size() > 0){
                    for (ArrayMap<String, Object> scenario : allScenario){
                        String scenarioId = scenario.get("id").toString();

                        ArrayList<ArrayMap<String, Object>> shields = getShields(scenarioId);
                        for (ArrayMap<String, Object> shield : shields){
                            shield.put("projectId", projectId);
                            shield.put("scenarioId", scenarioId);
                        }

                        result.addAll(shields);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getAllWorkSteps(String roomId) {

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();

        ArrayList<ArrayMap<String, Object>> allProjects =  getProjects(roomId);
        if (allProjects != null && allProjects.size() > 0){
            for (ArrayMap<String, Object> project : allProjects){
                String projectId = project.get("id").toString();

                ArrayList<ArrayMap<String, Object>> allScenario = getScenarios(projectId);
                if (allScenario != null && allScenario.size() > 0){
                    for (ArrayMap<String, Object> scenario : allScenario){
                        String scenarioId = scenario.get("id").toString();

                        ArrayList<ArrayMap<String, Object>> workSteps = getWorkSteps(scenarioId);
                        for (ArrayMap<String, Object> workStep : workSteps){
                            workStep.put("projectId", projectId);
                            workStep.put("scenarioId", scenarioId);
                        }

                        result.addAll(workSteps);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> getEquipment(String scenarioId, String id) {

        ArrayMap<String, Object>  result = new ArrayMap();

        ArrayList<ArrayMap<String, Object>> dataList = getEquipments(scenarioId);
        if (dataList.size() > 0){
            for (ArrayMap<String, Object> item : dataList){
                if (item.get("id").toString().equals(id)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> getWorker(String scenarioId, String id) {

        ArrayMap<String, Object>  result = new ArrayMap<>();

        ArrayList<ArrayMap<String, Object>> dataList = getWorkers(scenarioId);
        if (!dataList.isEmpty()){
            for (ArrayMap<String, Object> item : dataList){
                if (item.get("id").toString().equals(id)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> getSource(String scenarioId, String id) {

        ArrayMap<String, Object>  result = new ArrayMap<>();

        ArrayList<ArrayMap<String, Object>> dataList = getSources(scenarioId);
        if (!dataList.isEmpty()){
            for (ArrayMap<String, Object> item : dataList){
                if (item.get("id").toString().equals(id)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> getShield(String scenarioId, String id) {

        ArrayMap<String, Object>  result = new ArrayMap<>();

        ArrayList<ArrayMap<String, Object>> dataList = getShields(scenarioId);
        if (!dataList.isEmpty()){
            for (ArrayMap<String, Object> item : dataList){
                if (item.get("id").toString().equals(id)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public ArrayMap<String, Object> getWorkStep(String scenarioId, String id) {

        ArrayMap<String, Object> result = new ArrayMap<>();

        ArrayList<ArrayMap<String, Object>> dataList = getWorkSteps(scenarioId);
        if (!dataList.isEmpty()){
            for (ArrayMap<String, Object> item : dataList){
                if (item.get("id").toString().equals(id)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }


    private HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    request.setParser(new JsonObjectParser(new JacksonFactory()));
                    request.getHeaders().setBasicAuthentication(vrDose_propertiesEntity.getId(), vrDose_propertiesEntity.getPassword());
                }
            });
        }
        return this.requestFactory;
    }

    private void convertLongToDate(ArrayMap<String, Object> map, String key){
        if (map.get(key) != null && !StringUtils.isEmpty(map.get(key).toString())){
            map.put(key, new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(map.get(key).toString()))));
        }
    }

    private void convertLongToDatetime(ArrayMap<String, Object> map, String key){
        if (map.get(key) != null && !StringUtils.isEmpty(map.get(key).toString())){
            map.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(map.get(key).toString()))));
        }
    }

    private void convertDateToISO8601(HashMap<String,Object> map, String key){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get(key).toString());
            map.put(key, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(date));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void convertStringToFloat(Map<String,Object>map, String key){
        Object value = map.get(key);
        if (!StringUtils.isEmpty(value)){
            try {
                map.put(key, Float.parseFloat(map.get(key).toString()));// String Convert Float
            } catch (Exception e){
                map.put(key, 0.0);// String Convert Float
            }
        } else {
            map.put(key, 0.0);// String Convert Float
        }
    }

    private String getJsonString(Map<String,Object> map) {
        JSONObject json = new JSONObject();
        map.forEach((key, value) -> {
            json.put(key.toString(), value);
        });

        return json.toString();
    }

    private ArrayMap<String, Object> castingObjectToString(Object item){
        ArrayMap<String, Object> result = new ArrayMap<>();
        if (item instanceof ArrayMap){
            ArrayMap<String, Object> castingItem = (ArrayMap<String, Object>)item;
            castingItem.forEach((k, v)-> {
                if (v.getClass().getSimpleName().equals("Object")) {
                    castingItem.put(k, "");
                }
            });
            result = castingItem;
        }
        return result;
    }

    private void castingObjectToStringFromScenario(ArrayMap<String, Object> item){
        castingObjectToString(item);
        convertLongToDate(item, "date");
        convertLongToDate(item, "lastModified");
        convertLongToDatetime(item, "startTime");
        convertLongToDatetime(item, "endTime");

        Object participantInfo = item.get("participantInfo");
        if (participantInfo instanceof ArrayList){
            if (participantInfo != null && ((ArrayList<?>) participantInfo).size() > 0){
                List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;

                for (ArrayMap<String, Object> _item : datalist){
                    castingObjectToString(_item);
                    convertStringToFloat(_item,"accumulatedDose");
                    convertStringToFloat(_item,"minDose");
                    convertStringToFloat(_item,"maxDose");
                    convertStringToFloat(_item,"workTime");
                }
            }
        }

        Object projectInfo = item.get("project");
        if (projectInfo instanceof ArrayMap){
            castingObjectToString(projectInfo);
        }
    }

    private String getXmlString(InputStream inputStream) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

    private Object xmlToJson(String xml) throws Exception {
        JSONObject jsonObject = XML.toJSONObject(xml);
        ObjectMapper objectMapper = new ObjectMapper();
/*        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);*/
        return  objectMapper.readValue(jsonObject.toString(), Object.class);
    }
}

