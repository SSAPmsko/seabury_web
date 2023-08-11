package com.seabury.web.service;

import com.seabury.web.entity.integrated.ND_60_103_INH_Entity;
import com.seabury.web.entity.integrated.WorkFlowEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IntegratedDoseService {
    public List<WorkFlowEntity> getWorkFlowList(WorkFlowEntity workFlowEntity);
}
