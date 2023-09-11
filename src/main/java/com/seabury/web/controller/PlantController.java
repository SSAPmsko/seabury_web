package com.seabury.web.controller;

import com.seabury.web.entity.dose.PlantEntity;
import com.seabury.web.entity.dose.SiteEntity;
import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.PlantService;
import com.seabury.web.service.StructureService;
import com.seabury.web.vo.dose.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @RequestMapping(value = {"/getPlantDetailList"}, method = RequestMethod.POST)
    public @ResponseBody List<PlantEntity> getPlantList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        PlantEntity wherePlant = new PlantEntity();
        List<PlantEntity> qq = plantService.getPlantList(wherePlant);
        return qq;
    }

    @RequestMapping(value={"/plantDetail"}, method = RequestMethod.GET)
    public ModelAndView plantDetail(ModelAndView mav ,@RequestParam(value = "id", required = false) Integer id){

        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(id);
        List<PlantEntity> Platn1list = plantService.getPlantList(wherePlant);

        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setObjectID(id);
        whereStructure.setType("Plant");
        List<StructureEntity> Structurelist = structureService.getStructureList(whereStructure);

        /*if (id == null){
            mav.addObject("editMode", false);
            mav.addObject("constructionBegan", LocalDate.now());
            mav.addObject("commissionDate", LocalDate.now());
            mav.addObject("decommissionDate", LocalDate.now());
            mav.addObject("thermalCapacity", 0.0);


            mav.setViewName("view/sub/plant/plantDetail");
        } else {*/
            mav.addObject("editMode", true);

            mav.addObject("Plant1list", Platn1list);
            mav.setViewName("view/sub/plant/plantDetail");
       /* }*/
        mav.addObject("Structurelist", Structurelist);
        return mav;
    }

    @RequestMapping(value = {"/plantDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> plantDetailProperties(@RequestParam(value = "id", required = false) Integer id) {
        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(id);
        List<PlantEntity> Plant1list = plantService.getPlantList(wherePlant);
        Plant1list.add(0, wherePlant);



        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", Plant1list.get(0));
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/getPlantList"}, method = RequestMethod.GET)
    public @ResponseBody Integer getindexplantList(@RequestParam(value = "id", required = false) Integer id) {
        PlantEntity wherePlant = new PlantEntity();

        List<PlantEntity> qq = plantService.getPlantList(wherePlant);
        wherePlant = qq.get(qq.size()-1);

        return wherePlant.getID();
    }



    @RequestMapping(value={"/plantList"}, method = RequestMethod.GET)
    public ModelAndView plantList(ModelAndView mav ){

        mav.setViewName("view/sub/plant/plantList");

        return mav;
    }
    @RequestMapping(value={"/plantInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> plantInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody PlantEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", plantService.insertPlant(message));

        return rp;
    }

    @RequestMapping(value={"/plantUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> plantUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody PlantEntity message){
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
        rp.put("result", plantService.deletePlant(message));


        return rp;
    }
}
