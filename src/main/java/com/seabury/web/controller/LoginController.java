package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    CommonService commonService;

    @RequestMapping(value={"/loginDo"}, method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response){

        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "main");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav){
        mav.setViewName("view/login/login");
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        session.invalidate();

        try {
            commonService.sendRedirect(request, response, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
