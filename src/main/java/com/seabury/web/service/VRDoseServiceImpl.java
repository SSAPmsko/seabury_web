package com.seabury.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.DateTime;
import com.seabury.web.entity.VRDose_ProjectEntity;
import com.seabury.web.entity.VRDose_PropertiesEntity;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class VRDoseServiceImpl implements VRDoseService {
    @Autowired
    private VRDose_PropertiesEntity vrDose_propertiesEntity;

    private HttpRequestFactory requestFactory;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Override
    public ArrayMap getProject(String id) {

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/projectdb/projects/" + id);

        ArrayMap result = new ArrayMap();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());
            convertLongToDate(result, "date");
            convertLongToDate(result, "startDate");
            convertLongToDate(result, "endDate");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList getProjects(String roomId) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects");
        if (!StringUtils.isEmpty(roomId)){
            gUrl.set("roomId", roomId);
        }

        ArrayList result = new ArrayList();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            for (Object item : result = request.execute().parseAs(result.getClass())) {
                if (item instanceof ArrayMap){
                    ArrayMap<String, Object> castingItem = CastingObjectToString(item);
                    try {
                        Float.parseFloat(castingItem.get("doseLimit").toString());
                    } catch (Exception e) {
                        castingItem.put("doseLimit", "0.0");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap insertProject(HashMap<String,Object> map) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects");

        ArrayMap result = new ArrayMap();

        try {
            /* Sample Data
            {
                "defaultProject": true,
                "date": "2023-06-29T02:18:35.608Z",
                "name": "My Project",
                "description": "string",
                "startDate": "2023-06-29T02:18:35.608Z",
                "endDate": "2023-06-29T02:18:35.608Z",
                "createdBy": "string",
                "justification": "string",
                "doseLimit": 0,
                "room": "Rooms/ground",
                "properties": "propertyName:propertyValue"
            }
            */

            convertDateToISO8601(map, "date");
            convertDateToISO8601(map, "startDate");
            convertDateToISO8601(map, "endDate");
            //convertString2Float(map, "doseLimit"); // String Convert Float
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
    public ArrayMap updateProject(HashMap<String,Object> map) {

        ArrayMap result = new ArrayMap();

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects/" + id);

            try {
                convertDateToISO8601(map, "date");
                convertDateToISO8601(map, "startDate");
                convertDateToISO8601(map, "endDate");
                //convertString2Float(map, "doseLimit"); // String Convert Float
                map.remove("editMode"); // View 에서 생성/수정을 확인하기 위한 필드이므로 삭제

                try {
                    Float.parseFloat(map.get("doseLimit").toString());
                } catch (Exception e) {
                    map.put("doseLimit", "0.0");
                }

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
    public ArrayList getAllScenario(String projectId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata");
        if (!StringUtils.isEmpty(projectId)){
            gUrl.set("projectId", projectId);
        }

        ArrayList result = new ArrayList();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            for (Object item : result = request.execute().parseAs(result.getClass())) {
                CastingObjectToString(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap getScenario(String id) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + id);

        ArrayMap result = new ArrayMap();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayMap insertScenario(HashMap<String,Object> map){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata");

        ArrayMap result = new ArrayMap();

        try {
            convertDateToISO8601(map, "date");
            convertDateToISO8601(map, "startDate");
            convertDateToISO8601(map, "endDate");

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
    public ArrayMap updateScenario(HashMap<String,Object> map) {

        ArrayMap result = new ArrayMap();

        Object obj = map.get("id");
        if (!StringUtils.isEmpty(obj)) {
            String id = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + id);

            try {
                convertDateToISO8601(map, "date");
                convertDateToISO8601(map, "startDate");
                convertDateToISO8601(map, "endDate");
                //convertString2Float(map, "doseLimit"); // String Convert Float
                map.remove("editMode"); // View 에서 생성/수정을 확인하기 위한 필드이므로 삭제

                try {
                    Float.parseFloat(map.get("doseLimit").toString());
                } catch (Exception e) {
                    map.put("doseLimit", "0.0");
                }

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
    public List<ArrayMap<String, Object>> getEquipments(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        List<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;
                result = datalist.stream().filter(n -> n.get("type").equals("OBJECT")).collect(Collectors.toList());
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        CastingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ArrayMap<String, Object>> getWorkers(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        List<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;
                result = datalist.stream().filter(n -> n.get("type").equals("MANIKIN")).collect(Collectors.toList());
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        CastingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ArrayMap<String, Object>> getSources(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        List<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;
                result = datalist.stream().filter(n -> n.get("type").equals("SOURCE")).collect(Collectors.toList());
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        CastingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ArrayMap<String, Object>> getShields(String scenarioId){
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId);

        List<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            ArrayMap scenarioData = request.execute().parseAs(ArrayMap.class);

            Object participantInfo = scenarioData.get("participantInfo");
            if(participantInfo instanceof ArrayList){
                List<ArrayMap<String, Object>> datalist = (List<ArrayMap<String, Object>>)participantInfo;
                result = datalist.stream().filter(n -> n.get("type").equals("SHIELD")).collect(Collectors.toList());
                result.forEach((item -> item.forEach((k, v) -> {
                    if (v.getClass().getSimpleName().equals("Object")){
                        CastingObjectToString(item);
                    }
                })));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ArrayMap<String, Object>> getWorkSteps(String scenarioId){

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/scenariodata/" + scenarioId + "/workplan");

        List<ArrayMap<String, Object>> result = new ArrayList<>();

        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            InputStream inputStream = request.execute().getContent();

            String xmlString = getXmlString(inputStream);
            Object elementWorkPlan =  xmlToJson(xmlString);

            if (elementWorkPlan != null){
                LinkedHashMap castingWorkPlan = (LinkedHashMap)elementWorkPlan;
                LinkedHashMap castingWorkPlanData = (LinkedHashMap)castingWorkPlan.get("WorkPlan");
                LinkedHashMap castingSteps = (LinkedHashMap)castingWorkPlanData.get("steps");
                result = (ArrayList)castingSteps.get("WorkStep");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void convertLongToDate(ArrayMap map, String key){
        map.put(key, new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(map.get(key).toString()))));
    }

    private void convertLongToDatetime(ArrayMap map, String key){
        map.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(map.get(key).toString()))));
    }

    private void convertDateToISO8601(HashMap<String,Object> map, String key){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get(key).toString());
            map.put(key, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(date));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void convertString2Float(HashMap<String,Object> map, String key){
        Object value = map.get(key);
        if (!StringUtils.isEmpty(value)){
            map.put(key, Float.parseFloat(map.get(key).toString()));// String Convert Float
        } else {
            map.put(key, 0.0);// String Convert Float
        }
    }

    private String getJsonString(HashMap<String,Object> map) {
        JSONObject json = new JSONObject();
        map.forEach((key, value) -> {
            json.put(key.toString(), value.toString());
        });

        return json.toString();
    }

    private ArrayMap<String, Object> CastingObjectToString(Object item){
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
