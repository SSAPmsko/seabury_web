package com.seabury.web.service;

import com.seabury.web.entity.WorkerEntity;
import com.seabury.web.mapper.WorkerMapper;
import com.seabury.web.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerMapper workerMapper;

    @Override
    public List<WorkerEntity> getWorkerList(WorkerEntity workerEntity) {
        return workerMapper.getWorkerList(workerEntity);
    }

    @Override
    public int insertWorker(WorkerEntity workerEntity) {
        return workerMapper.updateWorker(workerEntity);
    }

    @Override
    public int updateWorker(WorkerEntity workerEntity) {
        return workerMapper.updateWorker(workerEntity);
    }

    @Override
    public int deleteWorker(WorkerEntity workerEntity) {
        return workerMapper.deleteWorker(workerEntity);
    }

    public WorkerVO ConvertEntityToVO(WorkerEntity WorkerEntity) {
        WorkerVO WorkerVO = new WorkerVO();

        if (WorkerEntity != null) {
            WorkerVO.setID(WorkerEntity.getID());
            WorkerVO.setProject_ID(WorkerEntity.getProject_ID());
            WorkerVO.setScenario_ID(WorkerEntity.getScenario_ID());
            WorkerVO.setName(WorkerEntity.getName());
            WorkerVO.setRole(WorkerEntity.getRole());
            WorkerVO.setProtection(WorkerEntity.getProtection());
            WorkerVO.setPersonnel_ID(WorkerEntity.getPersonnel_ID());
        }
        return WorkerVO;
    }

    public WorkerEntity ConvertVOToEntity(WorkerVO WorkerVO) {
        WorkerEntity WorkerEntity = new WorkerEntity();

        if (WorkerVO != null) {
            WorkerEntity.setID(WorkerVO.getID());
            WorkerEntity.setProject_ID(WorkerVO.getProject_ID());
            WorkerEntity.setScenario_ID(WorkerVO.getScenario_ID());
            WorkerEntity.setName(WorkerVO.getName());
            WorkerEntity.setRole(WorkerVO.getRole());
            WorkerEntity.setProtection(WorkerVO.getProtection());
            WorkerEntity.setPersonnel_ID(WorkerVO.getPersonnel_ID());
        }
        return WorkerEntity;
    }
}
