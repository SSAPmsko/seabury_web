package com.seabury.web.vo.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ALF_HighlightList {

    @Key
    public String field;

    @Key
    public List<String> snippets;
}
