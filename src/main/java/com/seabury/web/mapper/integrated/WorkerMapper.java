package com.seabury.web.mapper.integrated;

import com.seabury.web.entity.integrated.WorkerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkerMapper {
    List<WorkerEntity> getWorkerList(WorkerEntity workerEntity);

    int insertWorker(WorkerEntity workerEntity);
    int updateWorker(WorkerEntity workerEntity);
    int deleteWorker(WorkerEntity workerEntity);
}
