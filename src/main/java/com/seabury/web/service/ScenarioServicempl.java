package com.seabury.web.service;

import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.mapper.ScenarioMapper;
import com.seabury.web.vo.PlantVO;
import com.seabury.web.vo.ScenarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioServicempl implements ScenarioService {

    @Autowired
    ScenarioMapper ScenarioMapper;

    @Override
    public List<ScenarioEntity> getScenarioList(ScenarioEntity ScenarioEntity) {
        return ScenarioMapper.getScenarioList(ScenarioEntity);
    }

    @Override
    public int insertScenario(ScenarioEntity ScenarioEntity) {
        return ScenarioMapper.insertScenario(ScenarioEntity);
    }

    @Override
    public int updateScenario(ScenarioEntity ScenarioEntity) {
        return ScenarioMapper.updateScenario(ScenarioEntity);
    }

    @Override
    public int deleteScenario(ScenarioEntity ScenarioEntity) {
        return ScenarioMapper.deleteScenario(ScenarioEntity);
    }

    public ScenarioVO ConvertEntityToVO(ScenarioEntity ScenarioEntity) {
        ScenarioVO ScenarioVO = new ScenarioVO();

        if (ScenarioEntity != null) {
            ScenarioVO.setID(ScenarioEntity.getID());
            ScenarioVO.setImage(ScenarioEntity.getImage());
            ScenarioVO.setOperator(ScenarioEntity.getOperator());
            ScenarioVO.setReactorType(ScenarioEntity.getReactorType());
            ScenarioVO.setReactorSupplier(ScenarioEntity.getReactorSupplier());
            ScenarioVO.setConstructionBegan(ScenarioEntity.getConstructionBegan());
            ScenarioVO.setCommissionDate(ScenarioEntity.getCommissionDate());
            ScenarioVO.setDecommissionDate(ScenarioEntity.getDecommissionDate());
            ScenarioVO.setStatus(ScenarioEntity.getStatus());
        }
        return ScenarioVO;
    }

    public ScenarioEntity ConvertVOToEntity(ScenarioVO ScenarioVO) {
        ScenarioEntity ScenarioEntity = new ScenarioEntity();

        if (ScenarioVO != null){
            ScenarioEntity.setID(ScenarioVO.getID());
            ScenarioEntity.setImage(ScenarioVO.getImage());
            ScenarioEntity.setOperator(ScenarioVO.getOperator());
            ScenarioEntity.setReactorType(ScenarioVO.getReactorType());
            ScenarioEntity.setReactorSupplier(ScenarioVO.getReactorSupplier());
            ScenarioEntity.setConstructionBegan(ScenarioVO.getConstructionBegan());
            ScenarioEntity.setCommissionDate(ScenarioVO.getCommissionDate());
            ScenarioEntity.setDecommissionDate(ScenarioVO.getDecommissionDate());
            ScenarioEntity.setStatus(ScenarioVO.getStatus());
        }
        return ScenarioEntity;
    }
}
