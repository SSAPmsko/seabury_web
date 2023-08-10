package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.service.WorkersService;
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

    @RequestMapping(value={"/workerList"}, method = RequestMethod.POST)
    public void workerList(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "workerList");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
