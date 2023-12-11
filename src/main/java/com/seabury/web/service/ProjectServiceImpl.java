package com.seabury.web.service;

import com.seabury.web.entity.dose.ProjectEntity;
import com.seabury.web.mapper.dose.ProjectMapper;
import com.seabury.web.vo.dose.project.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<ProjectEntity> getProjectList(ProjectEntity projectEntity) {
        return projectMapper.getProjectList(projectEntity);
    }

    @Override
    public ProjectEntity selectProject(Integer ID) {
        return projectMapper.selectProject(ID);
    }

    @Override
    public int insertProject(ProjectEntity projectEntity) {
        return projectMapper.insertProject(projectEntity);
    }

    @Override
    public int updateProject(ProjectEntity projectEntity) {
        return projectMapper.updateProject(projectEntity);
    }

    @Override
    public int deleteProject(ProjectEntity projectEntity) {
        return projectMapper.deleteProject(projectEntity);
    }

    public ProjectVO ConvertEntityToVO(ProjectEntity projectEntity) {
        ProjectVO projectVO = new ProjectVO();

        if (projectEntity != null) {
            projectVO.setID(projectEntity.getID());
            projectVO.setName(projectEntity.getName());
            projectVO.setDescription(projectEntity.getDescription());
            projectVO.setCreator(projectEntity.getCreator());
            projectVO.setLocation(projectEntity.getLocation());
            projectVO.setStartDate(projectEntity.getStartDate());
            projectVO.setEndDate(projectEntity.getEndDate());
            projectVO.setDoseLimit(projectEntity.getDoseLimit());
            projectVO.setStatus(projectEntity.getStatus());
            projectVO.setImage(projectEntity.getImage());
            projectVO.setReportType(projectEntity.getReportType());
            projectVO.setJobID(projectEntity.getJobID());
            projectVO.setDocumentID(projectEntity.getDocumentID());
            projectVO.setProducedBy(projectEntity.getProducedBy());
            projectVO.setRadiationCalculator(projectEntity.getRadiationCalculator());
            projectVO.setJustification(projectEntity.getJustification());
            projectVO.setDocumentID(projectEntity.getStructureID());
        }
        return projectVO;
    }

    public ProjectEntity ConvertVOToEntity(ProjectVO projectVO) {
        ProjectEntity projectEntity = new ProjectEntity();

        if (projectVO != null){
            projectEntity.setID(projectVO.getID());
            projectEntity.setName(projectVO.getName());
            projectEntity.setDescription(projectVO.getDescription());
            projectEntity.setCreator(projectVO.getCreator());
            projectEntity.setLocation(projectVO.getLocation());
            projectEntity.setStartDate(projectVO.getStartDate());
            projectEntity.setEndDate(projectVO.getEndDate());
            projectEntity.setDoseLimit(projectVO.getDoseLimit());
            projectEntity.setStatus(projectVO.getStatus());
            projectEntity.setImage(projectVO.getImage());
            projectEntity.setReportType(projectVO.getReportType());
            projectEntity.setJobID(projectVO.getJobID());
            projectEntity.setDocumentID(projectVO.getDocumentID());
            projectEntity.setProducedBy(projectVO.getProducedBy());
            projectEntity.setRadiationCalculator(projectVO.getRadiationCalculator());
            projectEntity.setJustification(projectVO.getJustification());
            projectEntity.setStructureID(projectVO.getStructureID());
        }
        return projectEntity;
    }
}
