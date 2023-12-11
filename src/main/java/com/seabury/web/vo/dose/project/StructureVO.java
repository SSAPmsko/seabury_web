package com.seabury.web.vo.dose.project;


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
