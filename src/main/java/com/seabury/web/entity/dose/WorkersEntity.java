package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkersEntity {
    Integer ID;
    Integer Project_ID;
    Integer Scenario_ID;
    String Name;
    String Role;
    String Protection;
    Integer Personnel_ID;
}