package com.seabury.web.vo.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ALF_Search {
    @Key
    public Double score;

    @Key
    public List<ALF_HighlightList> highlight;
}
