package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class XML_ScenarioDetails {
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedCollectiveDoseChart;
    @JacksonXmlProperty(isAttribute = true)
    public String workersDoseRateChart;
    @JacksonXmlProperty(isAttribute = true)
    public String workersAccumulatedDoseChart;
    @JacksonXmlProperty(isAttribute = true)
    public String maxIndividualDoseRateChart;
    @JacksonXmlProperty(isAttribute = true)
    public String minIndividualDoseRateChart;
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedCollectiveDosePerStepChart;

    //public XML_WorkersInfo workersInfo;
    public XML_Worker[] workersInfo;
    //public XML_EquipmentInfo equipmentInfo;
    public XML_Equipment[] equipmentInfo;
    //public XML_WorkPlanInfo workPlanInfo;
    public XML_WorkStepInfo[] workPlanInfo;
    public XML_RadiologicalConditionOnStart radiologicalConditionOnStart;
    public XML_RadiologicalConditionOnEnd radiologicalConditionOnEnd;
}
