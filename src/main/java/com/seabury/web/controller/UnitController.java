package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UnitController {
    @Autowired
    CommonService commonService;

    @Autowired
    UnitService unitService;

    @RequestMapping(value={"/unit"}, method = RequestMethod.GET)
    public ModelAndView unit(ModelAndView mav){
        mav.setViewName("sub/unit/unit");
        return mav;
    }

    @RequestMapping(value={"/unit"}, method = RequestMethod.POST)
    public void unit(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "unit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
