package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SourceEntity {
    Integer ID;
    Integer Project_ID;
    Integer Scenario_ID;
    String Shield;
    String Material;
}
