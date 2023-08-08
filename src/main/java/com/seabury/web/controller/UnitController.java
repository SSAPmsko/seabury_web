package com.seabury.web.controller;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.SiteEntity;
import com.seabury.web.entity.StructureEntity;
import com.seabury.web.entity.UnitEntity;
import com.seabury.web.service.*;
import com.seabury.web.vo.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    public ModelAndView unitDetail(ModelAndView mav, @RequestParam(value = "id", required = false) Integer id){
        UnitEntity whereUnit = new UnitEntity();
        whereUnit.setID(id);
        List<UnitEntity> Unit1list = unitService.getUnitList(whereUnit);

        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setObject_ID(id);
        whereStructure.setType("Unit");
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);
        mav.addObject("editMode", true);

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

    @RequestMapping(value={"/unitInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> unitInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) UnitEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", unitService.insertUnit(message));

        return rp;
    }

    @RequestMapping(value={"/unitUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> unitUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) UnitEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", unitService.updateUnit(message));

        return rp;
    }

    @RequestMapping(value={"/unitDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> unitDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) UnitEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", unitService.deleteUnit(message));

        return rp;
    }
}
