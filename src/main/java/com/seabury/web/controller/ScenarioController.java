package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.ScenarioService;
import com.seabury.web.service.VRDoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ScenarioController {

    @Autowired
    CommonService commonService;

    @Autowired
    ScenarioService scenarioService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/scenario"}, method = RequestMethod.GET)
    public ModelAndView scenario(ModelAndView mav){
        // ---------------------------- Sample ------------------------------------------
        ArrayList list = vrDoseService.getAllScenario("4");
        for (Object map : list){
            if (map instanceof ArrayMap){
                ArrayMap<String, Object> item = (ArrayMap<String, Object>)map;
                ArrayMap dataList = vrDoseService.getScenario(item.get("id").toString());
                List<ArrayMap<String, Object>> equipments = vrDoseService.getEquipments(item.get("id").toString());
                List<ArrayMap<String, Object>> workers = vrDoseService.getWorkers(item.get("id").toString());
                List<ArrayMap<String, Object>> sources = vrDoseService.getSources(item.get("id").toString());
                List<ArrayMap<String, Object>> shields = vrDoseService.getShields(item.get("id").toString());
                List<ArrayMap<String, Object>> workSteps = vrDoseService.getWorkSteps(item.get("id").toString());
            }
        }
        // ---------------------------- Sample ------------------------------------------

        mav.setViewName("view/sub/scenario/scenario");
        return mav;
    }

    @RequestMapping(value={"/scenario"}, method = RequestMethod.POST)
    public void scenario(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "scenario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
