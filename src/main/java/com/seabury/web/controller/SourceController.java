package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.EquipmentService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.dose.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SourceController {

    @Autowired
    CommonService commonService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/sourceList"}, method = RequestMethod.GET)
    public ModelAndView sourceList(ModelAndView mav){
        mav.setViewName("view/sub/source/sourceList");
        return mav;
    }

    @RequestMapping(value={"/sourceList"}, method = RequestMethod.POST)
    public void equipmentList(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "sourceList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getSourceList"}, method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getSourceList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getAllSources("");
    }

    @RequestMapping(value = {"/sourceDetail"}, method = RequestMethod.GET)
    public ModelAndView sourceDetail(ModelAndView mav, @RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(scenarioId) && StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/source/sourceDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getSource(scenarioId, id));
            mav.setViewName("view/sub/source/sourceDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/sourceDetail"}, method = RequestMethod.POST)
    public void sourceDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "sourceDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/sourceDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> sourceDetailProperties(@RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
        } else {
            result = vrDoseService.getSource(scenarioId, id);
            result.put("editMode", true);
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }
}

