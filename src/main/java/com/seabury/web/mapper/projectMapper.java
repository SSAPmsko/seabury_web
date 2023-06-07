package com.seabury.web.mapper;

import com.seabury.web.entity.projectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface projectMapper {
    List<projectEntity> getProjectList(projectEntity projectEntity);

    int insertProject(projectEntity projectEntity);
    int updateProject(projectEntity projectEntity);
    int deleteProject(projectEntity projectEntity);
}
