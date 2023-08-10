package com.seabury.web.service;

import com.seabury.web.entity.dose.WorkersEntity;

import java.util.List;

public interface WorkersService {
    public List<WorkersEntity> getWorkersList(WorkersEntity WorkerEntity);

    public int insertWorkers(WorkersEntity WorkerEntity);
    public int updateWorkers(WorkersEntity WorkerEntity);
    public int deleteWorkers(WorkersEntity WorkerEntity);
}
