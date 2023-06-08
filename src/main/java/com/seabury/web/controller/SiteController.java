package com.seabury.web.controller;

import com.seabury.web.entity.SiteEntity;
import com.seabury.web.service.SiteService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteController {

    @Autowired
    SiteService siteService;
    @RequestMapping(value={"/site"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("sub/site");
        return mav;
    }
}
