package com.seabury.web.mapper.integrated;

import com.seabury.web.entity.integrated.WorkFlowEntity;
import com.seabury.web.entity.integrated.WorkerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkFlowMapper {
    List<WorkFlowEntity> getWorkFlowList(WorkFlowEntity workFlowEntity);

    int insertWorkFlow(WorkFlowEntity workFlowEntity);
    int updateWorkFlow(WorkFlowEntity workFlowEntity);
    int deleteWorkFlow(WorkFlowEntity workFlowEntity);
}
