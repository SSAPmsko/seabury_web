package com.seabury.web.vo.alfresco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ALF_File {
    @Key
    public String name;
    @Key
    public Date createdAt;
    @Key
    public Date modifiedAt;
    @Key
    public JSONObject createdByUser;
    @Key
    public JSONObject modifiedByUser;
    @Key
    public Boolean isFolder;
    @Key
    public Boolean isFile;
    @Key
    public String guid;
    @Key
    public String createdBy;
    @Key
    public String modifiedBy;
    @Key
    public String mimeType;
    @Key
    public Integer sizeInBytes;
    @Key
    public JSONObject content;
    @Key
    public String parentId;
    @Key
    public String id;
}
