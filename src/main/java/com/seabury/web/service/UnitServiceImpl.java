package com.seabury.web.service;

import com.seabury.web.entity.UnitEntity;
import com.seabury.web.mapper.UnitMapper;
import com.seabury.web.vo.UnitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitMapper UnitMapper;

    @Override
    public List<UnitEntity> getUnitList(UnitEntity UnitEntity) {
        return UnitMapper.getUnitList(UnitEntity);
    }

    @Override
    public int insertUnit(UnitEntity UnitEntity) {
        return UnitMapper.insertUnit(UnitEntity);
    }

    @Override
    public int updateUnit(UnitEntity UnitEntity) {
        return UnitMapper.updateUnit(UnitEntity);
    }

    @Override
    public int deleteUnit(UnitEntity UnitEntity) {
        return UnitMapper.deleteUnit(UnitEntity);
    }

    public UnitVO ConvertEntityToVO(UnitEntity UnitEntity) {
        UnitVO UnitVO = new UnitVO();

        if (UnitEntity != null) {
            UnitVO.setID(UnitEntity.getID());
            UnitVO.setImage(UnitEntity.getImage());
            UnitVO.setOperator(UnitEntity.getOperator());
            UnitVO.setReactorType(UnitEntity.getReactorType());
            UnitVO.setReactorSupplier(UnitEntity.getReactorSupplier());
            UnitVO.setConstructionBegan(UnitEntity.getConstructionBegan());
            UnitVO.setCommissionDate(UnitEntity.getCommissionDate());
            UnitVO.setDecommissionDate(UnitEntity.getDecommissionDate());
            UnitVO.setStatus(UnitEntity.getStatus());
        }
        return UnitVO;
    }

    public UnitEntity ConvertVOToEntity(UnitVO UnitVO) {
        UnitEntity UnitEntity = new UnitEntity();

        if (UnitVO != null){
            UnitEntity.setID(UnitVO.getID());
            UnitEntity.setImage(UnitVO.getImage());
            UnitEntity.setOperator(UnitVO.getOperator());
            UnitEntity.setReactorType(UnitVO.getReactorType());
            UnitEntity.setReactorSupplier(UnitVO.getReactorSupplier());
            UnitEntity.setConstructionBegan(UnitVO.getConstructionBegan());
            UnitEntity.setCommissionDate(UnitVO.getCommissionDate());
            UnitEntity.setDecommissionDate(UnitVO.getDecommissionDate());
            UnitEntity.setStatus(UnitVO.getStatus());
        }
        return UnitEntity;
    }
}
