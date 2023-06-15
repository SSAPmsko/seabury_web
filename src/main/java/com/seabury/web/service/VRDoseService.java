package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.VRDose_ProjectEntity;

import java.util.ArrayList;

public interface VRDoseService {
    ArrayMap<String, Object> getProject(String id);

    ArrayList<ArrayMap<String, Object>> getProjects(String roomId);
}
