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
    UnitMapper unitMapper;

    @Override
    public List<UnitEntity> getUnitList(UnitEntity unitEntity) {
        return unitMapper.getUnitList(unitEntity);
    }

    @Override
    public int insertUnit(UnitEntity unitEntity) {
        return unitMapper.insertUnit(unitEntity);
    }

    @Override
    public int updateUnit(UnitEntity unitEntity) {
        return unitMapper.updateUnit(unitEntity);
    }

    @Override
    public int deleteUnit(UnitEntity unitEntity) {
        return unitMapper.deleteUnit(unitEntity);
    }

    public UnitVO ConvertEntityToVO(UnitEntity unitEntity) {
        UnitVO unitVO = new UnitVO();

        if (unitEntity != null) {
            unitVO.setID(unitEntity.getID());
            unitVO.setImage(unitEntity.getImage());
            unitVO.setOperator(unitEntity.getOperator());
            unitVO.setReactorType(unitEntity.getReactorType());
            unitVO.setReactorSupplier(unitEntity.getReactorSupplier());
            unitVO.setConstructionBegan(unitEntity.getConstructionBegan());
            unitVO.setCommissionDate(unitEntity.getCommissionDate());
            unitVO.setDecommissionDate(unitEntity.getDecommissionDate());
            unitVO.setStatus(unitEntity.getStatus());
        }
        return unitVO;
    }

    public UnitEntity ConvertVOToEntity(UnitVO unitVO) {
        UnitEntity unitEntity = new UnitEntity();

        if (unitVO != null){
            unitEntity.setID(unitVO.getID());
            unitEntity.setImage(unitVO.getImage());
            unitEntity.setOperator(unitVO.getOperator());
            unitEntity.setReactorType(unitVO.getReactorType());
            unitEntity.setReactorSupplier(unitVO.getReactorSupplier());
            unitEntity.setConstructionBegan(unitVO.getConstructionBegan());
            unitEntity.setCommissionDate(unitVO.getCommissionDate());
            unitEntity.setDecommissionDate(unitVO.getDecommissionDate());
            unitEntity.setStatus(unitVO.getStatus());
        }
        return unitEntity;
    }
}
