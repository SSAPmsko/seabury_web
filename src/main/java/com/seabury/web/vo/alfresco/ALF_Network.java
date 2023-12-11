package com.seabury.web.vo.alfresco;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ALF_Network {
    @Key
    public String id;

    @Key
    public boolean homeNetwork;

    //@Key
    //DateTime createdAt;

    @Key
    public boolean paidNetwork;

    @Key
    public boolean isEnabled;

    @Key
    public String subscriptionLevel;
}

