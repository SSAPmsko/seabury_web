package com.seabury.web.controller;

import com.seabury.web.entity.PlantEntity;
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
import java.util.List;

@Controller
public class PlantController {
    @Autowired
    CommonService commonService;

    @Autowired
    PlantService plantService;


    @RequestMapping(value={"/plantDetail"}, method = RequestMethod.GET)
    public ModelAndView plant(ModelAndView mav){

        /// SAMPLE ///
        /*
        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(2);
        List<PlantEntity> qq = plantService.getPlantList(wherePlant);
        */

        mav.setViewName("view/sub/plant/plantDetail");
        return mav;
    }

    @RequestMapping(value={"/plantDetail"}, method = RequestMethod.POST)
    public void plant(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "plantDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
