package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.*;
import com.seabury.web.vo.alfresco.AlfrescoPropertiesVO;
import com.seabury.web.vo.dose.project.ReturnParam;
import com.seabury.web.vo.dose.report.XML_Report;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ScenarioController {

    @Autowired
    CommonService commonService;

    @Autowired
    ScenarioService scenarioService;

    @Autowired
    VRDoseService vrDoseService;

    @Autowired
    AlfrescoService alfrescoService;

    @Autowired
    VRDoseReportService vrDoseReportService;

    @Autowired
    private AlfrescoPropertiesVO alfrescoPropertiesVO;

    @RequestMapping(value={"/scenarioList"}, method = RequestMethod.GET)
    public ModelAndView scenarioList(ModelAndView mav){
        mav.setViewName("view/sub/scenario/scenarioList");
        return mav;
    }

    @RequestMapping(value={"/scenarioList"}, method = RequestMethod.POST)
    public void scenario(HttpServletRequest request, HttpServletResponse response){
        // 인증 성공 후 돌아가야 할 페이지로 리다이렉션 한다.
        try {
            commonService.sendRedirect(request, response, "scenarioList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getScenarioList"}, method = RequestMethod.POST)
    public @ResponseBody ArrayList<ArrayMap<String, Object>> getScenarioList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) {
        return vrDoseService.getAllScenario("");
    }

    @RequestMapping(value = {"/getScenarios"}, method = RequestMethod.GET)
    public @ResponseBody ArrayList<ArrayMap<String, Object>> getScenarios(@RequestParam(required = false) String projectId) {
        return vrDoseService.getScenarios(projectId);
    }

    @RequestMapping(value = {"/scenarioDetail"}, method = RequestMethod.GET)
    public ModelAndView scenarioDetail(ModelAndView mav, @RequestParam(required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        if (StringUtils.isEmpty(id)){
            mav.addObject("editMode", false);
            mav.addObject("date", LocalDate.now());
            mav.addObject("lastModified", LocalDate.now());
            mav.addObject("startTime", LocalDate.now());
            mav.addObject("endTime", LocalDate.now());
            mav.setViewName("view/sub/scenario/scenarioDetail");
        } else {
            mav.addObject("editMode", true);
            mav.addAllObjects(vrDoseService.getScenario(id));
            mav.setViewName("view/sub/scenario/scenarioDetail");
        }
        return mav;
    }

    @RequestMapping(value = {"/scenarioDetail"}, method = RequestMethod.POST)
    public void scenarioDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "scenarioDetail");
        } catch (Exception e) {;
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/scenarioDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> scenarioDetailProperties(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "id", required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
            result.put("date", LocalDate.now());
            result.put("lastModified", LocalDate.now());
            result.put("startTime", LocalDate.now());
            result.put("endTime", LocalDate.now());
        } else {
            result = vrDoseService.getScenario(id);
            result.put("editMode", true);

            ArrayMap<String, Object> report = vrDoseReportService.getReport(id);
            if (!report.isEmpty()){
                JSONObject reportJson = XML.toJSONObject(report.get("reportXml").toString());

                Map<String, Object> reportMap = reportJson.toMap();
                result.put("report", reportMap);

                ArrayList<ArrayMap<String, Object>> list = (ArrayList<ArrayMap<String, Object>>)report.get("dosimeterInfo");
                JSONArray array = new JSONArray(list);

                String dosimeterInfo = array.toString().replace("\"", "'");
                result.put("dosimeterInfo", dosimeterInfo);
            }
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/scenarioCompare"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> scenarioCompare(@RequestParam(value = "projectId", required = false) String projectId, @RequestParam(value = "id", required = false) String id) {

        // id가 null 이면 생성, null 이 아니면 수정
        Map<String, Object> result;
        if (StringUtils.isEmpty(id)){
            result = new HashMap<>();
            result.put("editMode", false);
            result.put("date", LocalDate.now());
            result.put("lastModified", LocalDate.now());
            result.put("startTime", LocalDate.now());
            result.put("endTime", LocalDate.now());
        } else {
            result = vrDoseService.getScenario(id);
            result.put("editMode", true);

            ArrayMap<String, Object> report = vrDoseReportService.getReport(id);
            if (!report.isEmpty()){
                JSONObject reportJson = XML.toJSONObject(report.get("reportXml").toString());

                Map<String, Object> reportMap = reportJson.toMap();

                Map<String, Object> castingReportMap = (Map<String, Object>)reportMap.get("Report");
                if (!castingReportMap.isEmpty()){
                    Map<String, Object> scenarioSummaryMap = (Map<String, Object>)castingReportMap.get("scenarioSummary");

                    result.put("scenarioSummary_duration", scenarioSummaryMap.get("duration"));
                    result.put("scenarioSummary_workTime", scenarioSummaryMap.get("workTime"));
                    result.put("scenarioSummary_maxDose", scenarioSummaryMap.get("maxDose"));
                    result.put("scenarioSummary_collectiveDose", scenarioSummaryMap.get("collectiveDose"));
                }

                result.put("report", reportMap);
            }
        }
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", result);
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/scenarioCompare"}, method = RequestMethod.POST)
    public void scenarioCompare(HttpServletRequest request, HttpServletResponse response) {
        try {
            commonService.sendRedirect(request, response, "scenarioCompare");
        } catch (Exception e) {;
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/scenarioDetailDocuments"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> scenarioDetailDocuments(@RequestParam(value = "id", required = false) String id) {

        ReturnParam rp = new ReturnParam();
        try {
        // ReturnParam 작성
        Map<String, Object> scenarioDocuments = alfrescoService.getScenarioDocuments(id, 1);
            if (!scenarioDocuments.isEmpty()){
                rp.put("Documents", scenarioDocuments.get("list"));
            }
        rp.setSuccess("");
        } catch (Exception e) {
        rp.setFail("");
        }
        return rp;
    }

    @RequestMapping(value={"/scenarioInsert"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> scenarioInsert(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.insertScenario(requestMap));

        return rp;
    }

    @RequestMapping(value={"/scenarioUpdate"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> scenarioUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.updateScenario(requestMap));

        return rp;
    }

    @RequestMapping(value={"/scenarioDelete"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> scenarioDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody HashMap<String,Object> requestMap){
        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.setSuccess("");
        rp.put("result", vrDoseService.deleteScenario(requestMap));

        return rp;
    }
}
