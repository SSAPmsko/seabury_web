package com.seabury.web.service;

import com.seabury.web.entity.dose.WorkersEntity;
import com.seabury.web.mapper.dose.WorkersMapper;
import com.seabury.web.vo.dose.WorkersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkersServiceImpl implements WorkersService {

    @Autowired
    WorkersMapper workersMapper;

    @Override
    public List<WorkersEntity> getWorkersList(WorkersEntity workersEntity) {
        return workersMapper.getWorkersList(workersEntity);
    }

    @Override
    public int insertWorkers(WorkersEntity workersEntity) {
        return workersMapper.updateWorkers(workersEntity);
    }

    @Override
    public int updateWorkers(WorkersEntity workersEntity) {
        return workersMapper.updateWorkers(workersEntity);
    }

    @Override
    public int deleteWorkers(WorkersEntity workersEntity) {
        return workersMapper.deleteWorkers(workersEntity);
    }

    public WorkersVO ConvertEntityToVO(WorkersEntity workersEntity) {
        WorkersVO workersVO = new WorkersVO();

        if (workersEntity != null) {
            workersVO.setID(workersEntity.getID());
            workersVO.setProject_ID(workersEntity.getProject_ID());
            workersVO.setScenario_ID(workersEntity.getScenario_ID());
            workersVO.setName(workersEntity.getName());
            workersVO.setRole(workersEntity.getRole());
            workersVO.setProtection(workersEntity.getProtection());
            workersVO.setPersonnel_ID(workersEntity.getPersonnel_ID());
        }
        return workersVO;
    }

    public WorkersEntity ConvertVOToEntity(WorkersVO workersVO) {
        WorkersEntity workersEntity = new WorkersEntity();

        if (workersVO != null) {
            workersEntity.setID(workersVO.getID());
            workersEntity.setProject_ID(workersVO.getProject_ID());
            workersEntity.setScenario_ID(workersVO.getScenario_ID());
            workersEntity.setName(workersVO.getName());
            workersEntity.setRole(workersVO.getRole());
            workersEntity.setProtection(workersVO.getProtection());
            workersEntity.setPersonnel_ID(workersVO.getPersonnel_ID());
        }
        return workersEntity;
    }
}
