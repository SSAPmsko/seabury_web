package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_ScenarioSummary {
    @JacksonXmlProperty(isAttribute = true)
    public String scenarioID;
    @JacksonXmlProperty(isAttribute = true)
    public String name;
    @JacksonXmlProperty(isAttribute = true)
    public String scenarioStatus;
    @JacksonXmlProperty(isAttribute = true)
    public String location;
    @JacksonXmlProperty(isAttribute = true)
    public String startTime;
    @JacksonXmlProperty(isAttribute = true)
    public String duration;
    @JacksonXmlProperty(isAttribute = true)
    public String riskValue;
    @JacksonXmlProperty(isAttribute = true)
    public String description;
    @JacksonXmlProperty(isAttribute = true)
    public String procedure;
    @JacksonXmlProperty(isAttribute = true)
    public String workTime;
    @JacksonXmlProperty(isAttribute = true)
    public String maxDose;
    @JacksonXmlProperty(isAttribute = true)
    public String collectiveDose;
}
