package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;


public interface VRDoseReportService {
    ArrayMap<String, Object> getReport(String id);
    ArrayList<ArrayMap<String, Object>> getReports();
    //ArrayMap<String, Object> insertReport(HashMap<String,Object> map);
    //ArrayMap<String, Object> updateReport(HashMap<String,Object> map);
    //Boolean deleteReport(HashMap<String,Object> map);
}
