package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.EquipmentService;
import com.seabury.web.service.VRDoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class EquipmentController {

    @Autowired
    CommonService commonService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/equipmentList"}, method = RequestMethod.GET)
    public ModelAndView equipmentList(ModelAndView mav){
        mav.setViewName("view/sub/equipment/equipmentList");
        return mav;
    }

    @RequestMapping(value={"/equipmentList"}, method = RequestMethod.POST)
    public void equipmentList(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "equipmentList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getEquipmentList"}, method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getEquipmentList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        /*vrDoseService.getAllEquipments(message.get("scenarioId").toString());
        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setType(message.get("type").toString());
        List<StructureEntity> qq = structureService.getStructureList(whereStructure);*/
        return vrDoseService.getAllEquipments("");

    }

    @RequestMapping(value = {"/equipmentDetail"}, method = RequestMethod.GET)
    public ModelAndView equipmentDetail(ModelAndView mav, @RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(scenarioId) && StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/equipment/equipmentDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getEquipment(scenarioId, id));
            mav.setViewName("view/sub/equipment/equipmentDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/equipmentDetail"}, method = RequestMethod.POST)
    public void equipmentDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "equipmentDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

