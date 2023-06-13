package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.ProjectEntity;
import com.seabury.web.service.ProjectService;
import com.seabury.web.service.VRDoseService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/project"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){

        // Sample Database CRUD
        /*
        ProjectEntity sample = new ProjectEntity();
        sample.setName("Sample");
        if (projectService.insertProject(sample) == 1) {
            sample.setDescription("Modify");
            projectService.updateProject(sample);

            var qq = projectService.getProjectList(new ProjectEntity());

            projectService.deleteProject(sample);
        }
        */
        ArrayMap<String, Object> projectData =  vrDoseService.getProject("projects", "1");

        if (projectData.size() > 0 ) {
            mav.addAllObjects(projectData);
        }

        mav.setViewName("sub/project/project");
        return mav;
    }
}
