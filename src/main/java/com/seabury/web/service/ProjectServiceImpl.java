package com.seabury.web.service;

import com.seabury.web.entity.dose.ProjectEntity;
import com.seabury.web.mapper.dose.ProjectMapper;
import com.seabury.web.vo.dose.ProjectVO;
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
            projectVO.setStart_date(projectEntity.getStart_date());
            projectVO.setEnd_date(projectEntity.getEnd_date());
            projectVO.setDose_limit(projectEntity.getDose_limit());
            projectVO.setStatus(projectEntity.getStatus());
            projectVO.setImage(projectEntity.getImage());
            projectVO.setReportType(projectEntity.getReportType());
            projectVO.setJob_ID(projectEntity.getJob_ID());
            projectVO.setDocument_ID(projectEntity.getDocument_ID());
            projectVO.setProducedBy(projectEntity.getProducedBy());
            projectVO.setRadiationCalculator(projectEntity.getRadiationCalculator());
            projectVO.setJustification(projectEntity.getJustification());
            projectVO.setStructure_ID(projectEntity.getStructure_ID());
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
            projectEntity.setStart_date(projectVO.getStart_date());
            projectEntity.setEnd_date(projectVO.getEnd_date());
            projectEntity.setDose_limit(projectVO.getDose_limit());
            projectEntity.setStatus(projectVO.getStatus());
            projectEntity.setImage(projectVO.getImage());
            projectEntity.setReportType(projectVO.getReportType());
            projectEntity.setJob_ID(projectVO.getJob_ID());
            projectEntity.setDocument_ID(projectVO.getDocument_ID());
            projectEntity.setProducedBy(projectVO.getProducedBy());
            projectEntity.setRadiationCalculator(projectVO.getRadiationCalculator());
            projectEntity.setJustification(projectVO.getJustification());
            projectEntity.setStructure_ID(projectVO.getStructure_ID());
        }
        return projectEntity;
    }
}
