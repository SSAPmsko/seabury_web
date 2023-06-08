package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StructureVO {
    String Name;
    String Description;
    Integer Parent_ID;
    String Type;
    Integer Object_ID;
}
