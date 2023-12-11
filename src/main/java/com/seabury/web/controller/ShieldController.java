package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.dose.project.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ShieldController {

    @Autowired
    CommonService commonService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/shieldList"}, method = RequestMethod.GET)
    public ModelAndView shieldList(ModelAndView mav){
        mav.setViewName("view/sub/shield/shieldList");
        return mav;
    }

    @RequestMapping(value={"/shieldList"}, method = RequestMethod.POST)
    public void shieldList(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "shieldList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getShieldList"}, method = RequestMethod.GET)
    public @ResponseBody ArrayList<ArrayMap<String, Object>> getShieldList(@RequestParam(required = false) String scenarioId) {
        return vrDoseService.getShields(scenarioId);
    }

    @RequestMapping(value = {"/getAllShieldList"}, method = RequestMethod.GET)
    public @ResponseBody  ArrayList<ArrayMap<String, Object>> getAllShieldList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getAllShields("");
    }

    @RequestMapping(value = {"/shieldDetail"}, method = RequestMethod.GET)
    public ModelAndView shieldDetail(ModelAndView mav, @RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(scenarioId) && StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/shield/shieldDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getShield(scenarioId, id));
            mav.setViewName("view/sub/shield/shieldDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/shieldDetail"}, method = RequestMethod.POST)
    public void shieldDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "shieldDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/shieldDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> shieldDetailProperties(@RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
        } else {
            result = vrDoseService.getShield(scenarioId, id);
            result.put("editMode", true);
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }
}

