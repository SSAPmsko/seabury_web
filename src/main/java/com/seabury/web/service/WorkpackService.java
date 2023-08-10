package com.seabury.web.service;

import com.seabury.web.entity.dose.WorkpackEntity;

import java.util.List;

public interface WorkpackService {
    public List<WorkpackEntity> getWorkpackList(WorkpackEntity workpackEntity);

    public int insertWorkpack(WorkpackEntity workpackEntity);
    public int updateWorkpack(WorkpackEntity workpackEntity);
    public int deleteWorkpack(WorkpackEntity workpackEntity);
}
