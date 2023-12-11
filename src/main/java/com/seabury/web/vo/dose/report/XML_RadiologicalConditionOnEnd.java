package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_RadiologicalConditionOnEnd {
    @JacksonXmlProperty(isAttribute = true)
    public String doseMap;

    @JacksonXmlElementWrapper(useWrapping = false)
    public XML_Source[] Source;
    @JacksonXmlElementWrapper(useWrapping = false)
    public XML_Shield[] Shield;
}
