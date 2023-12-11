package com.seabury.web.vo.alfresco;

import com.google.api.client.util.Key;

import java.util.ArrayList;

public class ALF_List<T extends ALF_Entry> {
    @Key
    public ArrayList<T> entries;

    @Key
    public ALF_Pagination pagination;
}
