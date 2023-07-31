package com.seabury.web.controller;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.PlantService;
import com.seabury.web.service.StructureService;
import com.seabury.web.vo.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CreateController {

    @Autowired
    CommonService commonService;

    @Autowired
    StructureService structureService;

    @RequestMapping(value={"/createDetail"}, method = RequestMethod.GET)
    public ModelAndView createDetail(ModelAndView mav ){
        StructureEntity whereStructure = new StructureEntity();
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);
        mav.addObject("Structurelist", Structurelist);

        mav.setViewName("view/sub/create/createDetail");

        return mav;
    }


}
