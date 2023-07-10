package com.seabury.web.service;

import com.seabury.web.entity.ProjectEntity;

import java.util.List;

public interface ProjectService {
    public List<ProjectEntity> getProjectList(ProjectEntity projectEntity);

    public ProjectEntity selectProject (Integer ID);
    public int insertProject(ProjectEntity projectEntity);
    public int updateProject(ProjectEntity projectEntity);
    public int deleteProject(ProjectEntity projectEntity);
}
