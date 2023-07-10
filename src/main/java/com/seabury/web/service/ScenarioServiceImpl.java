package com.seabury.web.service;

import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.mapper.ScenarioMapper;
import com.seabury.web.vo.ScenarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    @Autowired
    ScenarioMapper scenarioMapper;

    @Override
    public List<ScenarioEntity> getScenarioList(ScenarioEntity scenarioEntity) {
        return scenarioMapper.getScenarioList(scenarioEntity);
    }

    @Override
    public int insertScenario(ScenarioEntity scenarioEntity) {
        return scenarioMapper.insertScenario(scenarioEntity);
    }

    @Override
    public int updateScenario(ScenarioEntity scenarioEntity) {
        return scenarioMapper.updateScenario(scenarioEntity);
    }

    @Override
    public int deleteScenario(ScenarioEntity scenarioEntity) {
        return scenarioMapper.deleteScenario(scenarioEntity);
    }

    public ScenarioVO ConvertEntityToVO(ScenarioEntity scenarioEntity) {
        ScenarioVO scenarioVO = new ScenarioVO();
        //
        if (scenarioEntity != null) {
            scenarioVO.setID(scenarioEntity.getID());
            scenarioVO.setImage(scenarioEntity.getImage());
            scenarioVO.setOperator(scenarioEntity.getOperator());
            scenarioVO.setReactorType(scenarioEntity.getReactorType());
            scenarioVO.setReactorSupplier(scenarioEntity.getReactorSupplier());
            scenarioVO.setConstructionBegan(scenarioEntity.getConstructionBegan());
            scenarioVO.setCommissionDate(scenarioEntity.getCommissionDate());
            scenarioVO.setDecommissionDate(scenarioEntity.getDecommissionDate());
            scenarioVO.setStatus(scenarioEntity.getStatus());
        }
        return scenarioVO;
    }

    public ScenarioEntity ConvertVOToEntity(ScenarioVO scenarioVO) {
        ScenarioEntity scenarioEntity = new ScenarioEntity();

        if (scenarioVO != null){
            scenarioEntity.setID(scenarioVO.getID());
            scenarioEntity.setImage(scenarioVO.getImage());
            scenarioEntity.setOperator(scenarioVO.getOperator());
            scenarioEntity.setReactorType(scenarioVO.getReactorType());
            scenarioEntity.setReactorSupplier(scenarioVO.getReactorSupplier());
            scenarioEntity.setConstructionBegan(scenarioVO.getConstructionBegan());
            scenarioEntity.setCommissionDate(scenarioVO.getCommissionDate());
            scenarioEntity.setDecommissionDate(scenarioVO.getDecommissionDate());
            scenarioEntity.setStatus(scenarioVO.getStatus());
        }
        return scenarioEntity;
    }
}
