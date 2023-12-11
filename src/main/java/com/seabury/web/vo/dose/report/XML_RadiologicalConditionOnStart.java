package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@Getter
@Setter
public class XML_RadiologicalConditionOnStart {
    @JacksonXmlProperty(isAttribute = true)
    public String doseMap;

    @JacksonXmlElementWrapper(useWrapping = false)
    public XML_Source[] Source;
    @JacksonXmlElementWrapper(useWrapping = false)
    public XML_Shield[] Shield;
}
