package com.seabury.web.vo.dose.project;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShieldVO {
    Integer ID;
    Integer ProjectID;
    Integer ScenarioID;
    String Shield;
    String Material;
}