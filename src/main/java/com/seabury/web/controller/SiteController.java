package com.seabury.web.controller;

import com.seabury.web.entity.dose.SiteEntity;
import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.SiteService;
import com.seabury.web.service.StructureService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.dose.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class SiteController {
    @Autowired
    CommonService commonService;
    VRDoseService vrDoseService;

    @Autowired
    StructureService structureService;
    @Autowired
    SiteService siteService;

    @RequestMapping(value = {"/getSiteList"}, method = RequestMethod.GET)
    public @ResponseBody Integer getindexsiteList(@RequestParam(value = "id", required = false) Integer id) {
        SiteEntity whereSite = new SiteEntity();

        List<SiteEntity> qq = siteService.getSiteList(whereSite);
        whereSite = qq.get(qq.size()-1);

        return whereSite.getID();
    }

    @RequestMapping(value = {"/siteDetail"}, method = RequestMethod.GET)
    public ModelAndView siteDetail(ModelAndView mav, @RequestParam(value = "id", required = false) Integer id) {
        SiteEntity whereSite = new SiteEntity();
        whereSite.setID(id);
        List<SiteEntity> Site1list = siteService.getSiteList(whereSite);

        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setObjectID(id);
        whereStructure.setType("Site");
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);


        mav.addObject("editMode", true);
        mav.setViewName("view/sub/plant/plantDetail");
        mav.addObject("Structurelist", Structurelist);
        mav.addObject("Site1list", Site1list);

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
    public @ResponseBody Map<String, Object> siteInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) SiteEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", siteService.insertSite(message));

        return rp;
    }


    @RequestMapping(value = {"/siteUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> siteUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) SiteEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", siteService.updateSite(message));

        return rp;
    }

    @RequestMapping(value = {"/siteDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> siteDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) SiteEntity message) {
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", siteService.deleteSite(message));

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
