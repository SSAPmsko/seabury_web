package com.seabury.web.service;

import com.seabury.web.entity.EquipmentInformationEntity;
import com.seabury.web.entity.ScenarioEntity;
import com.seabury.web.mapper.EquipmentInformationMapper;
import com.seabury.web.vo.EquipmentInformationVO;
import com.seabury.web.vo.ScenarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentInformationServicempl implements EquipmentInformationService {

    @Autowired
    EquipmentInformationMapper EquipmentInformationMapper;

    @Override
    public List<EquipmentInformationEntity> getEquipmentInformationList(EquipmentInformationEntity EquipmentInformationEntity) {
        return EquipmentInformationMapper.getEquipmentInformationList(EquipmentInformationEntity);
    }

    @Override
    public int insertEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity) { return EquipmentInformationMapper.insertEquipmentInformation(EquipmentInformationEntity);
    }

    @Override
    public int updateEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity) {
        return EquipmentInformationMapper.updateEquipmentInformation(EquipmentInformationEntity);
    }

    @Override
    public int deleteEquipmentInformation(EquipmentInformationEntity EquipmentInformationEntity) {
        return EquipmentInformationMapper.deleteEquipmentInformation(EquipmentInformationEntity);
    }

    public EquipmentInformationVO ConvertEntityToVO(EquipmentInformationEntity EquipmentInformationEntity) {
        EquipmentInformationVO EquipmentInformationVO = new EquipmentInformationVO();

        if (EquipmentInformationEntity != null) {
            EquipmentInformationVO.setID(EquipmentInformationEntity.getID());
            EquipmentInformationVO.setImage(EquipmentInformationEntity.getImage());
            EquipmentInformationVO.setOperator(EquipmentInformationEntity.getOperator());
            EquipmentInformationVO.setReactorType(EquipmentInformationEntity.getReactorType());
            EquipmentInformationVO.setReactorSupplier(EquipmentInformationEntity.getReactorSupplier());
            EquipmentInformationVO.setConstructionBegan(EquipmentInformationEntity.getConstructionBegan());
            EquipmentInformationVO.setCommissionDate(EquipmentInformationEntity.getCommissionDate());
            EquipmentInformationVO.setDecommissionDate(EquipmentInformationEntity.getDecommissionDate());
            EquipmentInformationVO.setStatus(EquipmentInformationEntity.getStatus());
        }
        return EquipmentInformationVO;
    }

    public EquipmentInformationEntity ConvertVOToEntity(EquipmentInformationVO EquipmentInformationVO) {
        EquipmentInformationEntity EquipmentInformationEntity = new EquipmentInformationEntity();

        if (EquipmentInformationVO != null){
            EquipmentInformationEntity.setID(EquipmentInformationVO.getID());
            EquipmentInformationEntity.setImage(EquipmentInformationVO.getImage());
            EquipmentInformationEntity.setOperator(EquipmentInformationVO.getOperator());
            EquipmentInformationEntity.setReactorType(EquipmentInformationVO.getReactorType());
            EquipmentInformationEntity.setReactorSupplier(EquipmentInformationVO.getReactorSupplier());
            EquipmentInformationEntity.setConstructionBegan(EquipmentInformationVO.getConstructionBegan());
            EquipmentInformationEntity.setCommissionDate(EquipmentInformationVO.getCommissionDate());
            EquipmentInformationEntity.setDecommissionDate(EquipmentInformationVO.getDecommissionDate());
            EquipmentInformationEntity.setStatus(EquipmentInformationVO.getStatus());
        }
        return EquipmentInformationEntity;
    }
}
