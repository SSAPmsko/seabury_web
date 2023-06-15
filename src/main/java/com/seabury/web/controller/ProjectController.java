package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.ProjectEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.ProjectService;
import com.seabury.web.service.VRDoseService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ProjectController {
    @Autowired
    CommonService commonService;

    @Autowired
    VRDoseService vrDoseService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value={"/project"}, method = RequestMethod.GET)
    public ModelAndView project(ModelAndView mav){

        ArrayMap<String, Object> projectData =  vrDoseService.getProject("1");

        if (projectData.size() > 0 ) {
            mav.addAllObjects(projectData);
        }


        ArrayList<ArrayMap<String, Object>> projectsData = vrDoseService.getProjects("");

        if (projectsData.size() > 0 ) {
            mav.addObject(projectsData);
        }

        mav.setViewName("sub/project/project");
        return mav;
    }

    @RequestMapping(value={"/project"}, method = RequestMethod.POST)
    public void project(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
