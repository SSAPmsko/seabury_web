package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XML_WorkStepInfo {
    @JacksonXmlProperty(isAttribute = true)
    public String name;
    @JacksonXmlProperty(isAttribute = true)
    public String startTime;
    @JacksonXmlProperty(isAttribute = true)
    public String duration;
    @JacksonXmlProperty(isAttribute = true)
    public String explanation;
    @JacksonXmlProperty(isAttribute = true)
    public String riskEvaluation;
    @JacksonXmlProperty(isAttribute = true)
    public String precautions;
    @JacksonXmlProperty(isAttribute = true)
    public String description;
    @JacksonXmlProperty(isAttribute = true)
    public String numberOfWorkers;
    @JacksonXmlProperty(isAttribute = true)
    public String workers;
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedCollectiveDose;
    @JacksonXmlProperty(isAttribute = true)
    public String maxIndividualDoseRate;
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedDosePerWorkerChart;

    @JacksonXmlProperty(localName = "WorkStepWorkerDoseInfo")
    public XML_WorkStepWorkerDoseInfo WorkStepWorkerDoseInfo;
}
