package com.seabury.web.service;

import com.seabury.web.entity.dose.SourceEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {

    @Override
    public List<SourceEntity> getSourceList(SourceEntity sourceEntity) {
        return null;
    }

    @Override
    public int insertSource(SourceEntity sourceEntity) {
        return 0;
    }

    @Override
    public int updateSource(SourceEntity sourceEntity) {
        return 0;
    }

    @Override
    public int deleteSource(SourceEntity sourceEntity) {
        return 0;
    }
}
