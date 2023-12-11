package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.service.WorkpackService;
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
public class WorkpackController {

    @Autowired
    CommonService commonService;

    @Autowired
    WorkpackService workpackService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/workpackList"}, method = RequestMethod.GET)
    public ModelAndView workpackList(ModelAndView mav){
        mav.setViewName("view/sub/workpack/workpackList");
        return mav;
    }

    @RequestMapping(value={"/workpackList"}, method = RequestMethod.POST)
    public void workpackList(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "workpackList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getWorkpackList"}, method = RequestMethod.GET)
    public @ResponseBody ArrayList<ArrayMap<String, Object>> getWorkpackList(@RequestParam(required = false) String scenarioId) {
        return vrDoseService.getWorkSteps(scenarioId);
    }

    @RequestMapping(value = {"/getAllWorkpackList"}, method = RequestMethod.GET)
    public @ResponseBody  ArrayList<ArrayMap<String, Object>> getAllWorkpackList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getAllWorkSteps("");
    }

    @RequestMapping(value = {"/workpackDetail"}, method = RequestMethod.GET)
    public ModelAndView workpackDetail(ModelAndView mav, @RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(scenarioId) && StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/workpack/workpackDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getWorkStep(scenarioId, id));
            mav.setViewName("view/sub/workpack/workpackDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/workpackDetail"}, method = RequestMethod.POST)
    public void workpackDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "workpackDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/workpackDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> workpackDetailProperties(@RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
        } else {
            result = vrDoseService.getWorkStep(scenarioId, id);
            result.put("editMode", true);
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }
}
