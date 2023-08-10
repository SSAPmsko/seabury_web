package com.seabury.web.controller;

import com.seabury.web.entity.dose.PlantEntity;
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
    public ModelAndView plantDetail(ModelAndView mav ,@RequestParam(value = "id", required = false) Integer id){

        PlantEntity wherePlant = new PlantEntity();
        wherePlant.setID(id);
        List<PlantEntity> Platn1list = plantService.getPlantList(wherePlant);

        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setObject_ID(id);
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
