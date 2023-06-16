package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PlantController {
    @Autowired
    CommonService commonService;

    @Autowired
    PlantService plantService;

    @RequestMapping(value={"/plant"}, method = RequestMethod.GET)
    public ModelAndView plant(ModelAndView mav){
        mav.setViewName("view/sub/plant/plant");
        return mav;
    }

    @RequestMapping(value={"/plant"}, method = RequestMethod.POST)
    public void plant(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "plant");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
