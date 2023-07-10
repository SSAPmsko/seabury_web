package com.seabury.web.mapper;

import com.seabury.web.entity.WorkerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface WorkerMapper {
    List<WorkerEntity> getWorkerList(WorkerEntity workerEntity);

    int insertWorker(WorkerEntity workerEntity);
    int updateWorker(WorkerEntity workerEntity);
    int deleteWorker(WorkerEntity workerEntity);
}
