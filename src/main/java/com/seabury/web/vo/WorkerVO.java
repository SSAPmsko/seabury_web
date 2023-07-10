package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WorkerVO {
    Integer ID;
    Integer Project_ID;
    Integer Scenario_ID;
    String Name;
    String Role;
    String Protection;
    Integer Personnel_ID;
}