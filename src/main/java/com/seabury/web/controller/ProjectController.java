package com.seabury.web.controller;

import com.seabury.web.service.CommonService;
import com.seabury.web.service.ProjectService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.dose.ReturnParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {
    @Autowired
    CommonService commonService;

    @Autowired
    VRDoseService vrDoseService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = {"/projectList"}, method = RequestMethod.GET)
    public ModelAndView projectList(ModelAndView mav) {
        mav.setViewName("view/sub/project/projectList");
        return mav;
    }


    @RequestMapping(value = {"/projectList"}, method = RequestMethod.POST)
    public void projectList(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "projectList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/projectDetail"}, method = RequestMethod.GET)
    public ModelAndView projectDetail(ModelAndView mav, @RequestParam(value = "id", required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.addObject("date", LocalDate.now());
            mav.addObject("startDate", LocalDate.now());
            mav.addObject("endDate", LocalDate.now());
            mav.addObject("doseLimit", 0.0);
            mav.setViewName("view/sub/project/projectDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getProject(id));
            mav.setViewName("view/sub/project/projectDetail");
        }
        return mav;
    }

    @RequestMapping(value={"/projectInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> projectInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.insertProject(requestMap));

        return rp;
    }

    @RequestMapping(value={"/projectUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> projectUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.updateProject(requestMap));

        return rp;
    }

    @RequestMapping(value={"/projectDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> projectDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.deleteProject(requestMap));

        return rp;
    }

    @RequestMapping(value = {"/projectDetail"}, method = RequestMethod.POST)
    public void projectDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "projectDetail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getProjectList"}, method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getProjectList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getProjects("");
    }
}
