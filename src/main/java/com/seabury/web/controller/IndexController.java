package com.seabury.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    
    @GetMapping(value="/example")
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("example/index");
        return mav;
    }
    
    @GetMapping(value="/main")
    public ModelAndView main(ModelAndView mav){
        mav.setViewName("main/index");
        return mav;
    }
    
    
	/* kyc 0612 - Add Reference */
    
    @GetMapping(value="/radiationsource/nd_60_103_inh")
    public ModelAndView radiationsource_nd_60_103_inh(ModelAndView mav){
        mav.setViewName("sub/radiationsource/nd_60_103_inh");
        return mav;
    }
    
    @GetMapping(value="/radiationsource/nd_60_103_ing")
    public ModelAndView radiationsource_nd_60_103_ing(ModelAndView mav){
        mav.setViewName("sub/radiationsource/nd_60_103_ing");
        return mav;
    }
    
    @GetMapping(value="/radiationsource/nd_103_ext")
    public ModelAndView radiationsource_nd_103_ext(ModelAndView mav){
        mav.setViewName("sub/radiationsource/nd_103_ext");
        return mav;
    }
    
    @GetMapping(value="/radiationsource/nd_60_ext")
    public ModelAndView radiationsource_nd_60_ext(ModelAndView mav){
        mav.setViewName("sub/radiationsource/nd_60_ext");
        return mav;
    }
    
    @GetMapping(value="/radiationsource/nd_103_w")
    public ModelAndView radiationsource_nd_103_w(ModelAndView mav){
        mav.setViewName("sub/radiationsource/nd_103_w");
        return mav;
    }
}
