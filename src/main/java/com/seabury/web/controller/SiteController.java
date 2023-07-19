package com.seabury.web.controller;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.SiteEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.SiteService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.ReturnParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SiteController {
    @Autowired
    CommonService commonService;
    VRDoseService vrDoseService;
    @Autowired
    SiteService siteService;


    @RequestMapping(value = {"/siteDetail"}, method = RequestMethod.GET)
    public ModelAndView siteDetail(ModelAndView mav) {
        SiteEntity whereSite = new SiteEntity();
        whereSite.setID(2);
        List<SiteEntity> qq = siteService.getSiteList(whereSite);


        mav.setViewName("view/sub/site/siteDetail");
        return mav;

    }

    @RequestMapping(value = {"/siteDetail"}, method = RequestMethod.POST)
    public void siteDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "siteDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/siteInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> siteInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String, Object> requestMap) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.insertProject(requestMap));

        return rp;
    }

    @RequestMapping(value = {"/siteUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> siteUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String, Object> requestMap) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.updateProject(requestMap));

        return rp;
    }

    @RequestMapping(value = {"/siteDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> siteDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String, Object> requestMap) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.deleteProject(requestMap));

        return rp;
    }


    @RequestMapping(value = {"/getSiteList"}, method = RequestMethod.POST)
    public @ResponseBody List<SiteEntity> getSiteList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) SiteEntity message) {
        SiteEntity whereSite = new SiteEntity();
        whereSite.setID(1);
        List<SiteEntity> qq = siteService.getSiteList(whereSite);


        return qq;
    }
}
