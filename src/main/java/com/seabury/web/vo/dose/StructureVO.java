package com.seabury.web.vo.dose;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StructureVO {
    Integer ID;
    String Name;
    String Description;
    Integer ParentID;
    String ParentType;
    String Type;
    Integer ObjectID;
}
