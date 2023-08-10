package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class VRDose_ProjectEntity {
    BigDecimal id;
    Boolean defaultProject;
    BigDecimal date;
    String name;
    String description;
    BigDecimal startDate;
    BigDecimal endDate;
    String createdBy;
    String justification;
    String room;
    /*Object properties;*/
}