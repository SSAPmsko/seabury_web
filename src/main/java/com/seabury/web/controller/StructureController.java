package com.seabury.web.controller;

import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.StructureService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StructureController {

    @Autowired
    StructureService StructureService;
    @RequestMapping(value={"/structure"}, method = RequestMethod.GET)
    public ModelAndView example(ModelAndView mav){
        mav.setViewName("sub/structure/structure");
        return mav;
    }
}
