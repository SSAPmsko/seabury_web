package com.seabury.web.service;

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
            structureVO.setParent_Type(structureEntity.getParent_Type());
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
            structureEntity.setParent_Type(structureVO.getParent_Type());
            structureEntity.setType(structureVO.getType());
            structureEntity.setObject_ID(structureVO.getObject_ID());

        }
        return structureEntity;
    }
}

