package com.seabury.web.controller;

import com.seabury.web.entity.PlantEntity;
import com.seabury.web.entity.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.PlantService;
import com.seabury.web.service.StructureService;
import com.seabury.web.vo.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PlantController {
    @Autowired
    CommonService commonService;


    @Autowired
    PlantService plantService;

    @Autowired
    StructureService structureService;

    @RequestMapping(value={"/plantDetail"}, method = RequestMethod.GET)
    public ModelAndView plantDetail(ModelAndView mav ){
        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(2);
        List<PlantEntity> Platn1list = plantService.getPlantList(wherePlant);

        StructureEntity whereStructure = new StructureEntity();
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);

        mav.setViewName("view/sub/plant/plantDetail");
        mav.addObject("Structurelist", Structurelist);
        mav.addObject("Platn1list", Platn1list);

        return mav;
    }




    @RequestMapping(value = {"/getStructureList"}, method = RequestMethod.GET)
    public @ResponseBody List<StructureEntity> getStructureList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) StructureEntity message) {
        StructureEntity whereStructure = new StructureEntity();

        List<StructureEntity> qq = structureService.getStructureList(whereStructure);


        return qq;
    }

    @RequestMapping(value = {"/getPlantList"}, method = RequestMethod.POST)
    public @ResponseBody List<PlantEntity> getPlantList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) PlantEntity message) {
        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(2);
        List<PlantEntity> qq = plantService.getPlantList(wherePlant);


        return qq;
    }

    @RequestMapping(value={"/plantInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> plantInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) PlantEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", plantService.insertPlant(message));

        return rp;
    }

    @RequestMapping(value={"/plantUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> plantUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) PlantEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", plantService.updatePlant(message));

        return rp;
    }

    @RequestMapping(value={"/plantDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> plantDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) PlantEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", plantService.insertPlant(message));

        return rp;
    }
}
