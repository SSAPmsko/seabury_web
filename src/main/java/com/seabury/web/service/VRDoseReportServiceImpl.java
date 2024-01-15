package com.seabury.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.dose.VRDose_PropertiesEntity;
import com.seabury.web.vo.dose.report.*;
import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class VRDoseReportServiceImpl implements VRDoseReportService{
    @Autowired
    private VRDose_PropertiesEntity vrDose_propertiesEntity;

    private HttpRequestFactory requestFactory;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Override
    public ArrayMap<String, Object> getReport(String id) {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/reportdb/reports?scenarioDataId=" + id);

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());

            if (!result.isEmpty()) {
                result.forEach(item ->{
                    castingObjectToString(item);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !result.isEmpty() ? result.get(result.size() - 1) : new ArrayMap<>();
    }

    @Override
    public ArrayList<ArrayMap<String, Object>> getReports() {
        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/reportdb/reports");

        ArrayList<ArrayMap<String, Object>> result = new ArrayList<>();
        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            result = request.execute().parseAs(result.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*convertDosimeterInfo(result);*/

        return result;
    }

    @Override
    public XML_Report DeserializeReport (String xmlString) {
        ObjectMapper xmlMapper = new XmlMapper();
        XML_Report result = new XML_Report();
        try {
            result = xmlMapper.readValue(xmlString, XML_Report.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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
}
