package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class XML_Report {
    @JacksonXmlProperty(isAttribute = true)
    public String language;

    public XML_ReportHeader reportHeader;
    public XML_ProjectSummary projectSummary;
    public XML_ScenarioSummary scenarioSummary;
    public XML_ScenarioDetails scenarioDetails;
    public XML_ScenarioAnnotations scenarioAnnotations;
}
