package com.seabury.web.service;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.mapper.PlantMapper;
import com.seabury.web.vo.PlantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    PlantMapper PlantMapper;

    @Override
    public List<PlantEntity> getPlantList(PlantEntity PlantEntity) {
        return PlantMapper.getPlantList(PlantEntity);
    }

    @Override
    public int insertPlant(PlantEntity PlantEntity) {
        return PlantMapper.insertPlant(PlantEntity);
    }

    @Override
    public int updatePlant(PlantEntity PlantEntity) {
        return PlantMapper.updatePlant(PlantEntity);
    }

    @Override
    public int deletePlant(PlantEntity PlantEntity) {
        return PlantMapper.deletePlant(PlantEntity);
    }

    public PlantVO ConvertEntityToVO(PlantEntity PlantEntity) {
        PlantVO PlantVO = new PlantVO();

        if (PlantEntity != null) {
            PlantVO.setID(PlantEntity.getID());
            PlantVO.setImage(PlantEntity.getImage());
            PlantVO.setOperator(PlantEntity.getOperator());
            PlantVO.setReactorType(PlantEntity.getReactorType());
            PlantVO.setReactorSupplier(PlantEntity.getReactorSupplier());
            PlantVO.setConstructionBegan(PlantEntity.getConstructionBegan());
            PlantVO.setCommissionDate(PlantEntity.getCommissionDate());
            PlantVO.setDecommissionDate(PlantEntity.getDecommissionDate());
            PlantVO.setStatus(PlantEntity.getStatus());
        }
        return PlantVO;
    }

    public PlantEntity ConvertVOToEntity(PlantVO PlantVO) {
        PlantEntity PlantEntity = new PlantEntity();

        if (PlantVO != null){
            PlantEntity.setID(PlantVO.getID());
            PlantEntity.setImage(PlantVO.getImage());
            PlantEntity.setOperator(PlantVO.getOperator());
            PlantEntity.setReactorType(PlantVO.getReactorType());
            PlantEntity.setReactorSupplier(PlantVO.getReactorSupplier());
            PlantEntity.setConstructionBegan(PlantVO.getConstructionBegan());
            PlantEntity.setCommissionDate(PlantVO.getCommissionDate());
            PlantEntity.setDecommissionDate(PlantVO.getDecommissionDate());
            PlantEntity.setStatus(PlantVO.getStatus());
        }
        return PlantEntity;
    }
}
