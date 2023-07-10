package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WorkerController {

    @Autowired
    CommonService commonService;

    @Autowired
    WorkerService workerService;

    @RequestMapping(value={"/worker"}, method = RequestMethod.GET)
    public ModelAndView worker(ModelAndView mav){
        mav.setViewName("view/sub/worker/worker");
        return mav;
    }

    @RequestMapping(value={"/worker"}, method = RequestMethod.POST)
    public void worker(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "worker");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
