package com.seabury.web.controller;

import com.seabury.web.entity.SiteEntity;
import com.seabury.web.entity.StructureEntity;
import com.seabury.web.entity.UnitEntity;
import com.seabury.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UnitController {
    @Autowired
    CommonService commonService;
    VRDoseService vrDoseService;

    @Autowired
    StructureService structureService;
    @Autowired
    UnitService unitService;

    @RequestMapping(value={"/unitDetail"}, method = RequestMethod.GET)
    public ModelAndView unitDetail(ModelAndView mav){
        UnitEntity whereUnit = new UnitEntity();
        whereUnit.setID(1);
        List<UnitEntity> Unit1list = unitService.getUnitList(whereUnit);

        StructureEntity whereStructure = new StructureEntity();
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);


        mav.addObject("Structurelist", Structurelist);
        mav.addObject("Unit1list", Unit1list);

        mav.setViewName("view/sub/unit/unitDetail");
        return mav;
    }

    @RequestMapping(value={"/unitDetail"}, method = RequestMethod.POST)
    public void unitDetail(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "unitDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getUnitList"}, method = RequestMethod.POST)
    public @ResponseBody List<UnitEntity> getUnitList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) UnitEntity message) {
        UnitEntity whereUnit = new UnitEntity();
        whereUnit.setID(1);
        List<UnitEntity> qq = unitService.getUnitList(whereUnit);

        return qq;
    }
}
