package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.service.WorkersService;
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
public class WorkersController {

    @Autowired
    CommonService commonService;

    @Autowired
    WorkersService workersService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/workerList"}, method = RequestMethod.GET)
    public ModelAndView workerList(ModelAndView mav){
        mav.setViewName("view/sub/worker/workerList");
        return mav;
    }

    @RequestMapping(value = {"/getWorkerList"}, method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> getWorkerList(@RequestParam(required = false) String scenarioId) {
        return vrDoseService.getWorkers(scenarioId);
    }

    @RequestMapping(value = {"/getAllWorkerList"}, method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> getAllWorkerList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getAllWorkers("");
    }

    @RequestMapping(value = {"/workerDetail"}, method = RequestMethod.GET)
    public ModelAndView workerDetail(ModelAndView mav, @RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(scenarioId) && StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/worker/workerDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getWorker(scenarioId, id));
            mav.setViewName("view/sub/worker/workerDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/workerDetail"}, method = RequestMethod.POST)
    public void workerDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "workerDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/workerDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> workerDetailProperties(@RequestParam(required = false) String scenarioId, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
        } else {
            result = vrDoseService.getWorker(scenarioId, id);
            result.put("editMode", true);
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }
}
