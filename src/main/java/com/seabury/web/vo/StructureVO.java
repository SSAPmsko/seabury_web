package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StructureVO {
    Integer ID;
    String Name;
    String Description;
    Integer Parent_ID;
    String Parent_Type;
    String Type;
    Integer Object_ID;
}
