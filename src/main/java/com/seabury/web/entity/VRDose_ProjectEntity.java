package com.seabury.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    Object properties;
}
