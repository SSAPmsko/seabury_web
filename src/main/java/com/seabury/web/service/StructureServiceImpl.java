package com.seabury.web.service;

import com.seabury.web.entity.dose.SourceEntity;
import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.mapper.dose.StructureMapper;
import com.seabury.web.vo.dose.StructureVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    @Qualifier(value = "sqlSession")
    private SqlSession sqlSession;

    final String STRUCTUREMAPPER_PATH = "com.seabury.web.mapper.dose.StructureMapper";

    @Autowired
    StructureMapper structureMapper;

    @Override
    public List<StructureEntity> getStructureList(StructureEntity structureEntity) {
        return structureMapper.getStructureList(structureEntity);
    }

    @Override
    public int insertStructure(StructureEntity structureEntity) {/*
        return sqlSession.insert(STRUCTUREMAPPER_PATH + ".insertStructure", structureEntity);*/
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
            structureVO.setParentID(structureEntity.getParentID());
            structureVO.setParentType(structureEntity.getParentType());
            structureVO.setType(structureEntity.getType());
            structureVO.setObjectID(structureEntity.getObjectID());
        }
        return structureVO;
    }

    public StructureEntity ConvertVOToEntity(StructureVO structureVO) {
        StructureEntity structureEntity = new StructureEntity();

        if (structureVO != null){
            structureEntity.setName(structureVO.getName());
            structureEntity.setDescription(structureVO.getDescription());
            structureEntity.setParentID(structureVO.getParentID());
            structureEntity.setParentType(structureVO.getParentType());
            structureEntity.setType(structureVO.getType());
            structureEntity.setObjectID(structureVO.getObjectID());

        }
        return structureEntity;
    }
}

