package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_ReportHeader {
    @JacksonXmlProperty(isAttribute = true)
    public String reportType;
    @JacksonXmlProperty(isAttribute = true)
    public String project;
    @JacksonXmlProperty(isAttribute = true)
    public String scenario;
    @JacksonXmlProperty(isAttribute = true)
    public String scenarioStatus;
    @JacksonXmlProperty(isAttribute = true)
    public String projectStartDate;
    @JacksonXmlProperty(isAttribute = true)
    public String projectEndDate;
    @JacksonXmlProperty(isAttribute = true)
    public String producedBy;
    @JacksonXmlProperty(isAttribute = true)
    public String jobID;
    @JacksonXmlProperty(isAttribute = true)
    public String documentID;
    @JacksonXmlProperty(isAttribute = true)
    public String calculatorInfo;
}
