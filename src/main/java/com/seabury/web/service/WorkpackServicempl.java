package com.seabury.web.service;

import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.entity.WorkpackEntity;
import com.seabury.web.mapper.ScenarioMapper;
import com.seabury.web.mapper.WorkpackMapper;
import com.seabury.web.vo.PlantVO;
import com.seabury.web.vo.WorkpackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkpackServicempl implements WorkpackService {

    @Autowired
    WorkpackMapper WorkpackMapper;

    @Override
    public List<WorkpackEntity> getWorkpackList(WorkpackEntity WorkpackEntity) {
        return WorkpackMapper.getWorkpackList(WorkpackEntity);
    }

    @Override
    public int insertWorkpack(WorkpackEntity WorkpackEntity) { return WorkpackMapper.updateWorkpack(WorkpackEntity);
    }

    @Override
    public int updateWorkpack(WorkpackEntity WorkpackEntity) {
        return WorkpackMapper.updateWorkpack(WorkpackEntity);
    }

    @Override
    public int deleteWorkpack(WorkpackEntity WorkpackEntity) {
        return WorkpackMapper.deleteWorkpack(WorkpackEntity);
    }

    public WorkpackVO ConvertEntityToVO(WorkpackEntity WorkpackEntity) {
        WorkpackVO WorkpackVO = new WorkpackVO();

        if (WorkpackEntity != null) {
            WorkpackVO.setID(WorkpackEntity.getID());
            WorkpackVO.setImage(WorkpackEntity.getImage());
            WorkpackVO.setOperator(WorkpackEntity.getOperator());
            WorkpackVO.setReactorType(WorkpackEntity.getReactorType());
            WorkpackVO.setReactorSupplier(WorkpackEntity.getReactorSupplier());
            WorkpackVO.setConstructionBegan(WorkpackEntity.getConstructionBegan());
            WorkpackVO.setCommissionDate(WorkpackEntity.getCommissionDate());
            WorkpackVO.setDecommissionDate(WorkpackEntity.getDecommissionDate());
            WorkpackVO.setStatus(WorkpackEntity.getStatus());
        }
        return WorkpackVO;
    }

    public WorkpackEntity ConvertVOToEntity(WorkpackVO WorkpackVO) {
        WorkpackEntity WorkpackEntity = new WorkpackEntity();

        if (WorkpackVO != null) {
            WorkpackEntity.setID(WorkpackVO.getID());
            WorkpackEntity.setImage(WorkpackVO.getImage());
            WorkpackEntity.setOperator(WorkpackVO.getOperator());
            WorkpackEntity.setReactorType(WorkpackVO.getReactorType());
            WorkpackEntity.setReactorSupplier(WorkpackVO.getReactorSupplier());
            WorkpackEntity.setConstructionBegan(WorkpackVO.getConstructionBegan());
            WorkpackEntity.setCommissionDate(WorkpackVO.getCommissionDate());
            WorkpackEntity.setDecommissionDate(WorkpackVO.getDecommissionDate());
            WorkpackEntity.setStatus(WorkpackVO.getStatus());
        }
        return WorkpackEntity;
    }
}
