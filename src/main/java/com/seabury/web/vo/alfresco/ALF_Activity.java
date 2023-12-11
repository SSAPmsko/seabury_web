package com.seabury.web.vo.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ALF_Activity {
    @Key
    public String postedAt;
    @Key
    public String feedPersonId;
    @Key
    public String postPersonId;
    @Key
    public Integer id;
    @Key
    public String activityType;
    @Key
    public String siteId;
    @Key
    public JSONObject activitySummary;
}
