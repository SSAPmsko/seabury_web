package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.DateTime;
import com.seabury.web.entity.VRDose_ProjectEntity;
import com.seabury.web.entity.VRDose_PropertiesEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    public ArrayMap getProject(String id) {

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/projectdb/projects/" + id);

        ArrayMap result = new ArrayMap();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());
            convertLong2Date(result, "date");
            convertLong2Date(result, "startDate");
            convertLong2Date(result, "endDate");

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
                    ArrayMap<String, Object> castingItem = (ArrayMap<String, Object>)item;
                    castingItem.remove("properties"); // <<<<<<<<<< properties 필드가 Object 형태로 반환되어, 데이터 파싱할때 문제가 발생됨. 사용하지 않는 필드여서 삭제.
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

            convertDate2ISO8601(map, "date");
            convertDate2ISO8601(map, "startDate");
            convertDate2ISO8601(map, "endDate");
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
            String projectId = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects/" + projectId);

            try {
                convertDate2ISO8601(map, "date");
                convertDate2ISO8601(map, "startDate");
                convertDate2ISO8601(map, "endDate");
                //convertString2Float(map, "doseLimit"); // String Convert Float
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
            String projectId = obj.toString();

            GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() + "/plannerdbapplication/resources/projectdb/projects/" + projectId);

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

    private void convertLong2Date(ArrayMap map, String key){
        map.put(key, new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(map.get(key).toString()))));
    }

    private void convertLong2Datetime(ArrayMap map, String key){
        map.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(map.get(key).toString()))));
    }

    private void convertDate2ISO8601(HashMap<String,Object> map, String key){
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
}
