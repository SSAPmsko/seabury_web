package com.seabury.web.mapper.dose;

import com.seabury.web.entity.dose.WorkersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface WorkersMapper {
    List<WorkersEntity> getWorkersList(WorkersEntity workersEntity);

    int insertWorkers(WorkersEntity workersEntity);
    int updateWorkers(WorkersEntity workersEntity);
    int deleteWorkers(WorkersEntity workersEntity);
}
