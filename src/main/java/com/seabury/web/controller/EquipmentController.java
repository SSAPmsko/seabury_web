package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EquipmentController {

    @Autowired
    CommonService commonService;

    @Autowired
    EquipmentService equipmentService;

    @RequestMapping(value={"/equipment"}, method = RequestMethod.GET)
    public ModelAndView equipment(ModelAndView mav){
        mav.setViewName("view/sub/equipment/equipment");
        return mav;
    }

    @RequestMapping(value={"/equipment"}, method = RequestMethod.POST)
    public void equipment(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "equipment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

