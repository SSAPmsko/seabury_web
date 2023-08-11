package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EquipmentEntity {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Name;
    String Description;
}