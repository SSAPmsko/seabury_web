package com.seabury.web.service;

import com.seabury.web.entity.SourceEntity;

import java.util.List;

public interface SourceService {
    public List<SourceEntity> getSourceList(SourceEntity sourceEntity);

    public int insertSource(SourceEntity sourceEntity);
    public int updateSource(SourceEntity sourceEntity);
    public int deleteSource(SourceEntity sourceEntity);
}
