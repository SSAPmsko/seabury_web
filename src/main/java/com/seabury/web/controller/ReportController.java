package com.seabury.web.controller;

import com.google.api.client.util.ArrayMap;
import com.seabury.web.service.*;
import com.seabury.web.vo.alfresco.AlfrescoPropertiesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class ReportController {
    @Autowired
    CommonService commonService;

    @Autowired
    VRDoseReportService vrDoseReportService;

    @RequestMapping(value = {"/getReport"}, method = RequestMethod.GET)
    public @ResponseBody ArrayMap<String, Object> getReport(@RequestParam(required = false) String scenarioId) {
        return vrDoseReportService.getReport(scenarioId);
    }
}
