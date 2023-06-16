package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
    @Autowired
    CommonService commonService;

    @GetMapping(value="/example")
    public ModelAndView example(ModelAndView mav){
        // Sample Database CRUD

        /*ProjectEntity sample = new ProjectEntity();
        sample.setName("Sample");
        if (projectService.insertProject(sample) == 1) {
            sample.setDescription("Modify");
            projectService.updateProject(sample);

            var qq = projectService.getProjectList(new ProjectEntity());

            projectService.deleteProject(sample);
        }*/

        mav.setViewName("view/example/index");
        return mav;
    }
    
    @GetMapping(value="/main")
    public ModelAndView main(ModelAndView mav){
        mav.setViewName("view/main/index");
        return mav;
    }
}
