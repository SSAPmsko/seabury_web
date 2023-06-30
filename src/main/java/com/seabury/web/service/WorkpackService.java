package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.entity.WorkpackEntity;

import java.util.List;

public interface WorkpackService {
    public List<WorkpackEntity> getWorkpackList(WorkpackEntity WorkpackEntity);

    public int insertWorkpack(WorkpackEntity WorkpackEntity);
    public int updateWorkpack(WorkpackEntity WorkpackEntity);
    public int deleteWorkpack(WorkpackEntity WorkpackEntity);
}
