package com.seabury.web.service;

import com.seabury.web.entity.WorkerEntity;

import java.util.List;

public interface WorkerService {
    public List<WorkerEntity> getWorkerList(WorkerEntity WorkerEntity);

    public int insertWorker(WorkerEntity WorkerEntity);
    public int updateWorker(WorkerEntity WorkerEntity);
    public int deleteWorker(WorkerEntity WorkerEntity);
}
