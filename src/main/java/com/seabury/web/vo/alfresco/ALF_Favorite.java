package com.seabury.web.vo.alfresco;

import com.google.api.client.util.Key;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;

public class ALF_Favorite {

    @Key
    public String targetGuid;

    @Key
    public JSONObject target;
}
