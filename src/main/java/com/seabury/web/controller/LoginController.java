package com.seabury.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    @RequestMapping(value={"/loginDo"}, method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        String endPointUrl = "main";
        if(endPointUrl!= null && endPointUrl.indexOf("://") < 0){
            String urlPrefix = request.getRequestURL().toString();

            urlPrefix = urlPrefix.substring(0,urlPrefix.indexOf(request.getContextPath())+request.getContextPath().length());

            if(urlPrefix.startsWith("http://") && !urlPrefix.startsWith("http://localhost")){
                urlPrefix = urlPrefix.replaceAll("http://","https://");
            }
            System.out.println("urlPrefix:"+urlPrefix);

            endPointUrl = urlPrefix + endPointUrl;
        }
        response.sendRedirect(endPointUrl);
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("login/login");
        return mav;
    }
}
