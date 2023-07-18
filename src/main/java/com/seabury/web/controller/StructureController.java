package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.StructureService;
import com.seabury.web.service.VRDoseService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StructureController {
    @Autowired
    CommonService commonService;

    @Autowired
    StructureService StructureService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/structureList"}, method = RequestMethod.GET)
    public ModelAndView structureList(ModelAndView mav){
        mav.setViewName("view/sub/structure/structureList");
        return mav;
    }

    @RequestMapping(value={"/structureList"}, method = RequestMethod.POST)
    public void structureList(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "structureList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/structureDetail"}, method = RequestMethod.GET)
    public ModelAndView structureDetail(ModelAndView mav, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/structure/structureDetail");
        } else {
            mav.addObject("editMode", true);
            // mav.addAllObjects((ArrayMap)vrDoseService.getWorkStep(scenarioId, id));
            mav.setViewName("view/sub/structure/structureDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/structureDetail"}, method = RequestMethod.POST)
    public void structureDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "structureDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
