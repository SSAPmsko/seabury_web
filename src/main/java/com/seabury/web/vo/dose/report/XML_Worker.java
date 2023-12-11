package com.seabury.web.vo.dose.report;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XML_Worker {
    @JacksonXmlProperty(isAttribute = true)
    public String id;
    @JacksonXmlProperty(isAttribute = true)
    public String name;
    @JacksonXmlProperty(isAttribute = true)
    public String role;
    @JacksonXmlProperty(isAttribute = true)
    public String protection;
    @JacksonXmlProperty(isAttribute = true)
    public String personnelId;
}
