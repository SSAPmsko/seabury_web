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

    Boolean  deleteProject(HashMap<String,Object> map);
}
