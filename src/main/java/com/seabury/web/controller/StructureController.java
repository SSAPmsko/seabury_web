package com.seabury.web.controller;

import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.StructureService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class StructureController {
    @Autowired
    CommonService commonService;


    @Autowired
    StructureService structureService;
    @Autowired
    StructureService StructureService;

    @RequestMapping(value={"/structure"}, method = RequestMethod.GET)
    public ModelAndView structure(ModelAndView mav){
        mav.setViewName("view/sub/structure/structure");
        return mav;
    }

    @RequestMapping(value={"/structure"}, method = RequestMethod.POST)
    public void structure(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "structure");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = {"/getStructureList"}, method = RequestMethod.POST)
//    public @ResponseBody List<StructureEntity> getStructureList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) StructureEntity message) {
//        StructureEntity whereStructure = new StructureEntity();
//
//        List<StructureEntity> qq = structureService.getStructureList(whereStructure);
//
//        return qq;
//    }
}
