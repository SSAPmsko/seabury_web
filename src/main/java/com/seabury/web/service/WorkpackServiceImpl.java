package com.seabury.web.service;

import com.seabury.web.entity.WorkpackEntity;
import com.seabury.web.mapper.WorkpackMapper;
import com.seabury.web.vo.WorkpackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkpackServiceImpl implements WorkpackService {

    @Autowired
    WorkpackMapper workpackMapper;

    @Override
    public List<WorkpackEntity> getWorkpackList(WorkpackEntity workpackEntity) {
        return workpackMapper.getWorkpackList(workpackEntity);
    }

    @Override
    public int insertWorkpack(WorkpackEntity workpackEntity) { return workpackMapper.updateWorkpack(workpackEntity);
    }

    @Override
    public int updateWorkpack(WorkpackEntity workpackEntity) {
        return workpackMapper.updateWorkpack(workpackEntity);
    }

    @Override
    public int deleteWorkpack(WorkpackEntity workpackEntity) {
        return workpackMapper.deleteWorkpack(workpackEntity);
    }

    public WorkpackVO ConvertEntityToVO(WorkpackEntity workpackEntity) {
        WorkpackVO workpackVO = new WorkpackVO();

        if (workpackEntity != null) {
            workpackVO.setID(workpackEntity.getID());
            workpackVO.setImage(workpackEntity.getImage());
            workpackVO.setOperator(workpackEntity.getOperator());
            workpackVO.setReactorType(workpackEntity.getReactorType());
            workpackVO.setReactorSupplier(workpackEntity.getReactorSupplier());
            workpackVO.setConstructionBegan(workpackEntity.getConstructionBegan());
            workpackVO.setCommissionDate(workpackEntity.getCommissionDate());
            workpackVO.setDecommissionDate(workpackEntity.getDecommissionDate());
            workpackVO.setStatus(workpackEntity.getStatus());
        }
        return workpackVO;
    }

    public WorkpackEntity ConvertVOToEntity(WorkpackVO workpackVO) {
        WorkpackEntity workpackEntity = new WorkpackEntity();

        if (workpackVO != null) {
            workpackEntity.setID(workpackVO.getID());
            workpackEntity.setImage(workpackVO.getImage());
            workpackEntity.setOperator(workpackVO.getOperator());
            workpackEntity.setReactorType(workpackVO.getReactorType());
            workpackEntity.setReactorSupplier(workpackVO.getReactorSupplier());
            workpackEntity.setConstructionBegan(workpackVO.getConstructionBegan());
            workpackEntity.setCommissionDate(workpackVO.getCommissionDate());
            workpackEntity.setDecommissionDate(workpackVO.getDecommissionDate());
            workpackEntity.setStatus(workpackVO.getStatus());
        }
        return workpackEntity;
    }
}
