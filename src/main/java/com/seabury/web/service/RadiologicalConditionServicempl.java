package com.seabury.web.service;

import com.seabury.web.entity.RadiologicalConditionEntity;
import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.mapper.RadiologicalConditionMapper;
import com.seabury.web.vo.RadiologicalConditionVO;
import com.seabury.web.vo.ScenarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadiologicalConditionServicempl implements RadiologicalConditionService {

    @Autowired
    RadiologicalConditionMapper RadiologicalConditionMapper;

    @Override
    public List<RadiologicalConditionEntity> getRadiologicalConditionList(RadiologicalConditionEntity RadiologicalConditionEntity) {
        return RadiologicalConditionMapper.getRadiologicalConditionList(RadiologicalConditionEntity);
    }

    @Override
    public int insertRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity) { return RadiologicalConditionMapper.insertRadiologicalCondition(RadiologicalConditionEntity);
    }

    @Override
    public int updateRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity) {
        return RadiologicalConditionMapper.updateRadiologicalCondition(RadiologicalConditionEntity);
    }

    @Override
    public int deleteRadiologicalCondition(RadiologicalConditionEntity RadiologicalConditionEntity) {
        return RadiologicalConditionMapper.deleteRadiologicalCondition(RadiologicalConditionEntity);
    }

    public RadiologicalConditionVO ConvertEntityToVO(RadiologicalConditionEntity RadiologicalConditionEntity) {
        RadiologicalConditionVO RadiologicalConditionVO = new RadiologicalConditionVO();

        if (RadiologicalConditionEntity != null) {
            RadiologicalConditionVO.setID(RadiologicalConditionEntity.getID());
            RadiologicalConditionVO.setImage(RadiologicalConditionEntity.getImage());
            RadiologicalConditionVO.setOperator(RadiologicalConditionEntity.getOperator());
            RadiologicalConditionVO.setReactorType(RadiologicalConditionEntity.getReactorType());
            RadiologicalConditionVO.setReactorSupplier(RadiologicalConditionEntity.getReactorSupplier());
            RadiologicalConditionVO.setConstructionBegan(RadiologicalConditionEntity.getConstructionBegan());
            RadiologicalConditionVO.setCommissionDate(RadiologicalConditionEntity.getCommissionDate());
            RadiologicalConditionVO.setDecommissionDate(RadiologicalConditionEntity.getDecommissionDate());
            RadiologicalConditionVO.setStatus(RadiologicalConditionEntity.getStatus());
        }
        return RadiologicalConditionVO;
    }

    public RadiologicalConditionEntity ConvertVOToEntity(RadiologicalConditionVO RadiologicalConditionVO) {
        RadiologicalConditionEntity RadiologicalConditionEntity = new RadiologicalConditionEntity();

        if (RadiologicalConditionVO != null){
            RadiologicalConditionEntity.setID(RadiologicalConditionVO.getID());
            RadiologicalConditionEntity.setImage(RadiologicalConditionVO.getImage());
            RadiologicalConditionEntity.setOperator(RadiologicalConditionVO.getOperator());
            RadiologicalConditionEntity.setReactorType(RadiologicalConditionVO.getReactorType());
            RadiologicalConditionEntity.setReactorSupplier(RadiologicalConditionVO.getReactorSupplier());
            RadiologicalConditionEntity.setConstructionBegan(RadiologicalConditionVO.getConstructionBegan());
            RadiologicalConditionEntity.setCommissionDate(RadiologicalConditionVO.getCommissionDate());
            RadiologicalConditionEntity.setDecommissionDate(RadiologicalConditionVO.getDecommissionDate());
            RadiologicalConditionEntity.setStatus(RadiologicalConditionVO.getStatus());
        }
        return RadiologicalConditionEntity;
    }
}
