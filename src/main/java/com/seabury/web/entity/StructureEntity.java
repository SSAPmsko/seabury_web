package com.seabury.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StructureEntity {
    String Name;
    String Description;
    Integer Parent_ID;
    String Type;
    Integer Object_ID;
}
