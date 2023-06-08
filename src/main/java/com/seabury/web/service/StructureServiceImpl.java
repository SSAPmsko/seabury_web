package com.seabury.web.service;

import com.seabury.web.entity.StructureEntity;
import com.seabury.web.mapper.StructureMapper;
import com.seabury.web.vo.StructureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    StructureMapper StructureMapper;

    @Override
    public List<StructureEntity> getStructureList(StructureEntity StructureEntity) {
        return StructureMapper.getStructureList(StructureEntity);
    }

    @Override
    public int insertStructure(StructureEntity StructureEntity) {
        return StructureMapper.insertStructure(StructureEntity);
    }

    @Override
    public int updateStructure(StructureEntity StructureEntity) {
        return StructureMapper.updateStructure(StructureEntity);
    }

    @Override
    public int deleteStructure(StructureEntity StructureEntity) {
        return StructureMapper.deleteStructure(StructureEntity);
    }

    public StructureVO ConvertEntityToVO(StructureEntity StructureEntity) {
        StructureVO StructureVO = new StructureVO();

        if (StructureEntity != null) {
            StructureVO.setName(StructureEntity.getName());
            StructureVO.setDescription(StructureEntity.getDescription());
            StructureVO.setParent_ID(StructureEntity.getParent_ID());
            StructureVO.setType(StructureEntity.getType());
            StructureVO.setObject_ID(StructureEntity.getObject_ID());
        }
        return StructureVO;
    }

    public StructureEntity ConvertVOToEntity(StructureVO StructureVO) {
        StructureEntity StructureEntity = new StructureEntity();

        if (StructureVO != null){
            StructureEntity.setName(StructureVO.getName());
            StructureEntity.setDescription(StructureVO.getDescription());
            StructureEntity.setParent_ID(StructureVO.getParent_ID());
            StructureEntity.setType(StructureVO.getType());
            StructureEntity.setObject_ID(StructureVO.getObject_ID());

        }
        return StructureEntity;
    }
}

