package com.seabury.web.vo.dose;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkersVO {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Name;
    String Role;
    String Protection;
    Integer PersonnelID;
}