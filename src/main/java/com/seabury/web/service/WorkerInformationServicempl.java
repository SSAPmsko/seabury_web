package com.seabury.web.service;

import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.entity.WorkerInformationEntity;
import com.seabury.web.mapper.ScenarioMapper;
import com.seabury.web.mapper.WorkerInformationMapper;
import com.seabury.web.vo.PlantVO;
import com.seabury.web.vo.WorkerInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerInformationServicempl implements WorkerInformationService {

    @Autowired
    WorkerInformationMapper WorkerInformationMapper;

    @Override
    public List<WorkerInformationEntity> getWorkerInformationList(WorkerInformationEntity WorkerInformationEntity) {
        return WorkerInformationMapper.getWorkerInformationList(WorkerInformationEntity);
    }

    @Override
    public int insertWorkerInformation(WorkerInformationEntity WorkerInformationEntity) { return WorkerInformationMapper.updateWorkerInformation(WorkerInformationEntity);
    }

    @Override
    public int updateWorkerInformation(WorkerInformationEntity WorkerInformationEntity) {
        return WorkerInformationMapper.updateWorkerInformation(WorkerInformationEntity);
    }

    @Override
    public int deleteWorkerInformation(WorkerInformationEntity WorkerInformationEntity) {
        return WorkerInformationMapper.deleteWorkerInformation(WorkerInformationEntity);
    }

    public WorkerInformationVO ConvertEntityToVO(WorkerInformationEntity WorkerInformationEntity) {
        WorkerInformationVO WorkerInformationVO = new WorkerInformationVO();

        if (WorkerInformationEntity != null) {
            WorkerInformationVO.setID(WorkerInformationEntity.getID());
            WorkerInformationVO.setImage(WorkerInformationEntity.getImage());
            WorkerInformationVO.setOperator(WorkerInformationEntity.getOperator());
            WorkerInformationVO.setReactorType(WorkerInformationEntity.getReactorType());
            WorkerInformationVO.setReactorSupplier(WorkerInformationEntity.getReactorSupplier());
            WorkerInformationVO.setConstructionBegan(WorkerInformationEntity.getConstructionBegan());
            WorkerInformationVO.setCommissionDate(WorkerInformationEntity.getCommissionDate());
            WorkerInformationVO.setDecommissionDate(WorkerInformationEntity.getDecommissionDate());
            WorkerInformationVO.setStatus(WorkerInformationEntity.getStatus());
        }
        return WorkerInformationVO;
    }

    public WorkerInformationEntity ConvertVOToEntity(WorkerInformationVO WorkerInformationVO) {
        WorkerInformationEntity WorkerInformationEntity = new WorkerInformationEntity();

        if (WorkerInformationVO != null) {
            WorkerInformationEntity.setID(WorkerInformationVO.getID());
            WorkerInformationEntity.setImage(WorkerInformationVO.getImage());
            WorkerInformationEntity.setOperator(WorkerInformationVO.getOperator());
            WorkerInformationEntity.setReactorType(WorkerInformationVO.getReactorType());
            WorkerInformationEntity.setReactorSupplier(WorkerInformationVO.getReactorSupplier());
            WorkerInformationEntity.setConstructionBegan(WorkerInformationVO.getConstructionBegan());
            WorkerInformationEntity.setCommissionDate(WorkerInformationVO.getCommissionDate());
            WorkerInformationEntity.setDecommissionDate(WorkerInformationVO.getDecommissionDate());
            WorkerInformationEntity.setStatus(WorkerInformationVO.getStatus());
        }
        return WorkerInformationEntity;
    }
}
