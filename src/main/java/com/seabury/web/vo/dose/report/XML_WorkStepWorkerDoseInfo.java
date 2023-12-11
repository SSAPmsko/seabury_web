package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_WorkStepWorkerDoseInfo {
    @JacksonXmlProperty(isAttribute = true)
    public String workerName;
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedDose;
    @JacksonXmlProperty(isAttribute = true)
    public String maxDoseRate;
    @JacksonXmlProperty(isAttribute = true)
    public String accumulatedDoseTrend;
}
