package com.seabury.web.mapper.dose;

import com.seabury.web.entity.dose.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
mybatis 최신 버전에서는 @Mapper 사용해야함
기존에는 @Repository
 */

@Mapper
public interface ProjectMapper {
    List<ProjectEntity> getProjectList(ProjectEntity projectEntity);

    ProjectEntity selectProject(Integer ID);

    int insertProject(ProjectEntity projectEntity);
    int updateProject(ProjectEntity projectEntity);
    int deleteProject(ProjectEntity projectEntity);
}
