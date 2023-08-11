package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StructureEntity {
    Integer ID;
    String Name;
    String Description;
    Integer ParentID;
    String Type;
    String ParentType;
    Integer ObjectID;
}
