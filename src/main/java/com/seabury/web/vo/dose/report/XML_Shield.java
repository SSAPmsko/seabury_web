package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_Shield {
    @JacksonXmlProperty(isAttribute = true)
    public String id;
    @JacksonXmlProperty(isAttribute = true)
    public String name;
    @JacksonXmlProperty(isAttribute = true)
    public String material;
}
