package com.seabury.web.controller;

import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.StructureService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StructureController {
    @Autowired
    CommonService commonService;

    @Autowired
    StructureService StructureService;

    @RequestMapping(value={"/structure"}, method = RequestMethod.GET)
    public ModelAndView structure(ModelAndView mav){
        mav.setViewName("sub/structure/structure");
        return mav;
    }

    @RequestMapping(value={"/structure"}, method = RequestMethod.POST)
    public void structure(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "structure");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
