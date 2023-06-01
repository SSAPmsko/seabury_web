package com.seabury.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(value={"/example"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("example/index");
        return mav;
    }

    @RequestMapping(value={"/main"}, method = RequestMethod.GET)
    public ModelAndView main(ModelAndView mav){
        mav.setViewName("main/index");
        return mav;
    }
}
