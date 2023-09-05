package com.seabury.web.controller;

import com.seabury.web.entity.integrated.ND_60_103_INH_Entity;
import com.seabury.web.entity.integrated.WorkFlowEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.IntegratedDoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IntegratedDoseController {

    @Autowired
    CommonService commonService;

    IntegratedDoseService integratedDoseService;

    @GetMapping(value="/WorkFlow")
    public ModelAndView WorkFlow(ModelAndView mav){
        mav.setViewName("view/sub/integrated/WorkFlow");
        return mav;
    }

    @PostMapping(value="/getWorkFlowList")
    public @ResponseBody List<WorkFlowEntity> getWorkFlowList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return integratedDoseService.getWorkFlowList(new WorkFlowEntity());
    }
}
