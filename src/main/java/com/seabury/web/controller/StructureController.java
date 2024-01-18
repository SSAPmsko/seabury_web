package com.seabury.web.controller;

import com.seabury.web.entity.dose.StructureEntity;
import com.seabury.web.service.CommonService;
import com.seabury.web.service.StructureService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.dose.project.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class StructureController {
    @Autowired
    CommonService commonService;

    @Autowired
    StructureService structureService;

    @Autowired
    VRDoseService vrDoseService;

    @RequestMapping(value={"/structureList"}, method = RequestMethod.GET)
    public ModelAndView structureList(ModelAndView mav){
        mav.setViewName("view/sub/structure/structureList");
        return mav;
    }

    @RequestMapping(value = {"/getStructureList"}, method = RequestMethod.GET)
    public @ResponseBody List<StructureEntity> getStructureList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) StructureEntity message) {
        StructureEntity whereStructure = new StructureEntity();

        List<StructureEntity> qq = structureService.getStructureList(whereStructure);



        return qq;
    }

    @RequestMapping(value = {"/structureDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> structureDetailProperties(@RequestParam(value = "id", required = false) Integer id) {
        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setID(id);

        List<StructureEntity> result;
        result = structureService.getStructureList(whereStructure);

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result.get(0));
        rp.setSuccess("");


        return rp;
    }

    @RequestMapping(value = {"/getStructureType"}, method = RequestMethod.POST)
    public @ResponseBody List<StructureEntity> getStructureTypeFilter(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setType(message.get("type").toString());
        List<StructureEntity> qq = structureService.getStructureList(whereStructure);
        return qq;
    }

    @RequestMapping(value = {"/getStructureID"}, method = RequestMethod.POST)
    public @ResponseBody List<StructureEntity> getStructureIDFilter(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setType(message.get("type").toString());
        whereStructure.setObjectID((Integer) message.get("objectID"));
        List<StructureEntity> qq = structureService.getStructureList(whereStructure);
        return qq;
    }

    @RequestMapping(value = {"/getStructureParentID"}, method = RequestMethod.POST)
    public @ResponseBody List<StructureEntity> getStructureParentIDFilter(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = true) Map<String, Object> message) {
        StructureEntity whereStructure = new StructureEntity();
        whereStructure.setType(message.get("type").toString());
        whereStructure.setObjectID((Integer) message.get("objectID"));
        List<StructureEntity> qq = structureService.getStructureList(whereStructure);
        return qq;
    }



    @RequestMapping(value={"/structureList"}, method = RequestMethod.POST)
    public void structureList(HttpServletRequest request, HttpServletResponse response){

        try {
            commonService.sendRedirect(request, response, "structureList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/structureDetail"}, method = RequestMethod.GET)
    public ModelAndView structureDetail(ModelAndView mav, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.setViewName("view/sub/structure/structureDetail");
        } else {
            mav.addObject("editMode", true);
            // mav.addAllObjects((ArrayMap)vrDoseService.getWorkStep(scenarioId, id));
            mav.setViewName("view/sub/structure/structureDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/structureDetail"}, method = RequestMethod.POST)
    public void structureDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "structureDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value={"/structureInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> structureInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody StructureEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", structureService.insertStructure(message));

        return rp;
    }

    @RequestMapping(value={"/structureUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> structureUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody StructureEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", structureService.updateStructure(message));

        return rp;
    }

    @RequestMapping(value={"/structureDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> structureDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) StructureEntity message){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", structureService.deleteStructure(message));


        return rp;
    }
}
