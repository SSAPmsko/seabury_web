package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.VRDose_ProjectEntity;
import com.seabury.web.entity.VRDose_PropertiesEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

@Service
public class VRDoseServiceImpl implements VRDoseService {
    @Autowired
    private VRDose_PropertiesEntity vrDose_propertiesEntity;

    private HttpRequestFactory requestFactory;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Override
    public ArrayMap<String, Object> getProject(String property, String id) {

        GenericUrl gUrl = new GenericUrl(vrDose_propertiesEntity.getUrl() + ":" + vrDose_propertiesEntity.getPort() +  "/plannerdbapplication/resources/projectdb/" + property + "/" + id);

        try {
            HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

            Object result = request.execute().parseAs(Object.class);

            if (result instanceof ArrayMap){
               return (ArrayMap<String, Object>)result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayMap<String, Object>();
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
}
