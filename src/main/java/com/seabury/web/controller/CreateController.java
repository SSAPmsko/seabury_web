package com.seabury.web.controller;

import com.seabury.web.entity.dose.PlantEntity;
import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.StructureService;
import com.seabury.web.vo.dose.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
        mav.addObject("constructionBegan", LocalDate.now());
        mav.addObject("commissionDate", LocalDate.now());
        mav.addObject("decommissionDate", LocalDate.now());
        mav.addObject("thermalCapacity", 0.0);
        mav.setViewName("view/sub/create/createDetail");

        return mav;
    }

    @RequestMapping(value = {"/createDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> plantDetailProperties(@RequestParam(value = "id", required = false) Integer id) {

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");

        return rp;
    }
}
