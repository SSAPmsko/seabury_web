package com.seabury.web.service;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.VRDose_ProjectEntity;

public interface VRDoseService {
    ArrayMap<String, Object> getProject(String property, String id);

}
