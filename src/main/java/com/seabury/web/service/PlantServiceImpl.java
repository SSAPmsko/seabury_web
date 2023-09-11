package com.seabury.web.service;

import com.seabury.web.entity.dose.PlantEntity;
import com.seabury.web.mapper.dose.PlantMapper;
import com.seabury.web.vo.dose.PlantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    PlantMapper plantMapper;


    @Override
    public List<PlantEntity> getPlantList(PlantEntity plantEntity) { return plantMapper.getPlantList(plantEntity); }

    @Override
    public int insertPlant(PlantEntity plantEntity) {
        return plantMapper.insertPlant(plantEntity);
    }

    @Override
    public int updatePlant(PlantEntity plantEntity) {
        return plantMapper.updatePlant(plantEntity);
    }

    @Override
    public int deletePlant(PlantEntity plantEntity) {
        return plantMapper.deletePlant(plantEntity);
    }

    public PlantVO ConvertEntityToVO(PlantEntity plantEntity) {
        PlantVO plantVO = new PlantVO();

        if (plantEntity != null) {
            plantVO.setID(plantEntity.getID());
            plantVO.setImage(plantEntity.getImage());
            plantVO.setOperator(plantEntity.getOperator());
            plantVO.setReactorType(plantEntity.getReactorType());
            plantVO.setReactorSupplier(plantEntity.getReactorSupplier());
            plantVO.setConstructionBegan(plantEntity.getConstructionBegan());
            plantVO.setCommissionDate(plantEntity.getCommissionDate());
            plantVO.setDecommissionDate(plantEntity.getDecommissionDate());
            plantVO.setStatus(plantEntity.getStatus());
            plantVO.setStatus(plantEntity.getName());
        }
        return plantVO;
    }

    public PlantEntity ConvertVOToEntity(PlantVO plantVO) {
        PlantEntity plantEntity = new PlantEntity();

        if (plantVO != null){
            plantEntity.setID(plantVO.getID());
            plantEntity.setImage(plantVO.getImage());
            plantEntity.setOperator(plantVO.getOperator());
            plantEntity.setReactorType(plantVO.getReactorType());
            plantEntity.setReactorSupplier(plantVO.getReactorSupplier());
            plantEntity.setConstructionBegan(plantVO.getConstructionBegan());
            plantEntity.setCommissionDate(plantVO.getCommissionDate());
            plantEntity.setDecommissionDate(plantVO.getDecommissionDate());
            plantEntity.setStatus(plantVO.getStatus());
            plantEntity.setStatus(plantVO.getName());

        }
        return plantEntity;
    }
}
