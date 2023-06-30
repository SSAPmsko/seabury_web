package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.WorkerInformationEntity;
import com.seabury.web.entity.WorkpackEntity;

import java.util.List;

public interface WorkerInformationService {
    public List<WorkerInformationEntity> getWorkerInformationList(WorkerInformationEntity WorkerInformationEntity);

    public int insertWorkerInformation(WorkerInformationEntity WorkerInformationEntity);
    public int updateWorkerInformation(WorkerInformationEntity WorkerInformationEntity);
    public int deleteWorkerInformation(WorkerInformationEntity WorkerInformationEntity);
}
