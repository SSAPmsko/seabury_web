package com.seabury.web.service;

import com.seabury.web.entity.StructureEntity;

import java.util.List;

public interface StructureService {
    public List<StructureEntity> getStructureList(StructureEntity StructureEntity);

    public int insertStructure(StructureEntity StructureEntity);
    public int updateStructure(StructureEntity StructureEntity);
    public int deleteStructure(StructureEntity StructureEntity);
}
