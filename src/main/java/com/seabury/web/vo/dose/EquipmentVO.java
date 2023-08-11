package com.seabury.web.vo.dose;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EquipmentVO {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Name;
    String Description;
}