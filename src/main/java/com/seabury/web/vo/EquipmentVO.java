package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EquipmentVO {
    Integer ID;
    Integer Project_ID;
    Integer Scenario_ID;
    String Name;
    String Description;
}