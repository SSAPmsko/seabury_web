package com.seabury.web.vo.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;
import org.json.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ALF_Document {
    @Key
    public String createdAt;
    @Key
    public Boolean isFolder;
    @Key
    public Boolean isFile;
    @Key
    public String createdByUser;
    @Key
    public String modifiedAt;
    @Key
    public JSONObject modifiedByUser;
    @Key
    public String name;
    @Key
    public String id;
    @Key
    public String nodeType;
    @Key
    public JSONObject content;
    @Key
    public String parentId;
    @Key
    public ALF_Search search;

}
