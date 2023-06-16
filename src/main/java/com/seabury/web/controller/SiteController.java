package com.seabury.web.controller;

import com.seabury.web.entity.SiteEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.SiteService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SiteController {
    @Autowired
    CommonService commonService;

    @Autowired
    SiteService siteService;

    @RequestMapping(value={"/site"}, method = RequestMethod.GET)
    public ModelAndView site(ModelAndView mav){
        mav.setViewName("view/sub/site/site");
        return mav;
    }

    @RequestMapping(value={"/site"}, method = RequestMethod.POST)
    public void site(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "site");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
