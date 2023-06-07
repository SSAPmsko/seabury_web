package com.seabury.web.controller;

import com.seabury.web.entity.projectEntity;
import com.seabury.web.service.projectService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {

    @Autowired
    projectService projectService;
    @RequestMapping(value={"/project"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        projectEntity sample = new projectEntity();
        sample.setName("Sample");
        int insertID = projectService.insertProject(sample);
        sample.setDescription("Modify");
        projectService.updateProject(sample);

        var qq = projectService.getProjectList(null);

        projectService.deleteProject(sample);



        mav.setViewName("sub/project");
        return mav;
    }
}
