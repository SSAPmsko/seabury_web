package com.seabury.web.controller;

import com.seabury.web.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UnitController {

    @Autowired
    UnitService unitService;
    @RequestMapping(value={"/unit"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("sub/unit");
        return mav;
    }
}
