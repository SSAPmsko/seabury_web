package com.seabury.web.service;

import com.seabury.web.entity.projectEntity;

import java.util.List;

public interface projectService {
    public List<projectEntity> getProjectList(projectEntity projectEntity);

    public int insertProject(projectEntity projectEntity);
    public int updateProject(projectEntity projectEntity);
    public int deleteProject(projectEntity projectEntity);
}
