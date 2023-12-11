package com.seabury.web.vo.alfresco;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ALF_People {
    @Key
    public String id; // required
    @Key
    public String firstName; // required
    @Key
    public String lastName;
    //    @Key
//    public String displayName; // alfresco 5.2.1
    @Key
    public String description;
    @Key
    public String avatarId;
    @Key
    public String email; // required
    @Key
    public String skypeId;
    @Key
    public String googleId;
    @Key
    public String instantMessageId;
    @Key
    public String jobTitle;
    @Key
    public String location;
    @Key
    public String company; // JSONObject response Error
    @Key
    public String mobile;
    @Key
    public String telephone;
    @Key
    public String statusUpdatedAt;
    //    @Key
//    public String userStatus; // alfresco 5.2.1
    @Key
    public Boolean enabled;
    @Key
    public Boolean emailNotificationsEnabled;
    @Key
    public List<String> aspectNames;
    @Key
    public JSONObject properties;
//    @Key
//    public JSONObject capabilities;

    // Custom Field
    public String password;
    /*
    public String passwordConfirm;
    public String oldPassword;
    public Map<String, Object> permissions;
*/

    // isu Field
/*
    public String teamNm;
    public String teamCd;
    public String partNm;
    public String partCd;
    public String NAME;
    public String empNo;
    public String JIKWEE_NM;
    public String JIKWEE_CD;
    public String JOB_NM;
    public String MAIL_ADDR;
    public String plantCd;
    public String plantNm;
    public String tenantId;
    public String USE_LANG;
    public String USE_THEME;
    public String USE_ALARM;
    public String LOGO_REAL_PATH;
    public String FCON_REAL_PATH;
    public String TENANT_KEY;
*/
}
