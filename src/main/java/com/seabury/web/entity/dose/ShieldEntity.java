package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShieldEntity {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Shield;
    String Material;
}
