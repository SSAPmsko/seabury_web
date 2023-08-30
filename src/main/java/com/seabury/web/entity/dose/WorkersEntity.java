package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkersEntity {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Name;
    String Role;
    String Protection;
    Integer PersonnelID;
}