package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_ProjectSummary {
    @JacksonXmlProperty(isAttribute = true)
    public String name;
    @JacksonXmlProperty(isAttribute = true)
    public String description;
    @JacksonXmlProperty(isAttribute = true)
    public String justification;
    @JacksonXmlProperty(isAttribute = true)
    public String doseLimit;
}
