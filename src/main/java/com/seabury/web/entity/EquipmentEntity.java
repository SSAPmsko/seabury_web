package com.seabury.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EquipmentEntity {
    Integer ID;
    Integer Project_ID;
    Integer Scenario_ID;
    String Name;
    String Description;
}