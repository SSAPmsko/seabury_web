package com.seabury.web.service;

import com.seabury.web.entity.dose.StructureEntity;

import java.util.List;

public interface StructureService {
    public List<StructureEntity> getStructureList(StructureEntity structureEntity);

    public int insertStructure(StructureEntity structureEntity);
    public int updateStructure(StructureEntity structureEntity);
    public int deleteStructure(StructureEntity structureEntity);
}
