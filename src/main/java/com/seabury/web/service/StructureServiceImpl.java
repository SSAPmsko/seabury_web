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
    StructureMapper structureMapper;

    @Override
    public List<StructureEntity> getStructureList(StructureEntity structureEntity) {
        return structureMapper.getStructureList(structureEntity);
    }

    @Override
    public int insertStructure(StructureEntity structureEntity) {
        return structureMapper.insertStructure(structureEntity);
    }

    @Override
    public int updateStructure(StructureEntity structureEntity) {
        return structureMapper.updateStructure(structureEntity);
    }

    @Override
    public int deleteStructure(StructureEntity structureEntity) {
        return structureMapper.deleteStructure(structureEntity);
    }

    public StructureVO ConvertEntityToVO(StructureEntity structureEntity) {
        StructureVO structureVO = new StructureVO();

        if (structureEntity != null) {
            structureVO.setName(structureEntity.getName());
            structureVO.setDescription(structureEntity.getDescription());
            structureVO.setParent_ID(structureEntity.getParent_ID());
            structureVO.setType(structureEntity.getType());
            structureVO.setObject_ID(structureEntity.getObject_ID());
        }
        return structureVO;
    }

    public StructureEntity ConvertVOToEntity(StructureVO structureVO) {
        StructureEntity structureEntity = new StructureEntity();

        if (structureVO != null){
            structureEntity.setName(structureVO.getName());
            structureEntity.setDescription(structureVO.getDescription());
            structureEntity.setParent_ID(structureVO.getParent_ID());
            structureEntity.setType(structureVO.getType());
            structureEntity.setObject_ID(structureVO.getObject_ID());

        }
        return structureEntity;
    }
}

