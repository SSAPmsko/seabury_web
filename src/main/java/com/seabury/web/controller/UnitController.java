package com.seabury.web.controller;

import com.seabury.web.entity.SiteEntity;
import com.seabury.web.entity.UnitEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.UnitService;
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
public class UnitController {
    @Autowired
    CommonService commonService;

    @Autowired
    UnitService unitService;

    @RequestMapping(value={"/unit"}, method = RequestMethod.GET)
    public ModelAndView unit(ModelAndView mav){
        mav.setViewName("view/sub/unit/unit");
        return mav;
    }

    @RequestMapping(value={"/unit"}, method = RequestMethod.POST)
    public void unit(HttpServletRequest request, HttpServletResponse response){
        try {
            commonService.sendRedirect(request, response, "unit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getUnitList"}, method = RequestMethod.POST)
    public @ResponseBody List<UnitEntity> getUnitList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) UnitEntity message) {
        UnitEntity whereUnit = new UnitEntity();
        whereUnit.setID(1);
        List<UnitEntity> qq = unitService.getUnitList(whereUnit);

        return qq;
    }
}
