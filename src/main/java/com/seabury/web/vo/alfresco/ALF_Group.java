package com.seabury.web.vo.alfresco;

import com.google.api.client.util.Key;

public class ALF_Group {
    @Key
    public String id;

    @Key
    public String displayName;

    @Key
    public Boolean isRoot;

    // not supported in the current version.
//    @Key
//    public JSONObject parentIds;

    // not supported in the current version.
//    @Key
//    public JSONObject zones;

}
