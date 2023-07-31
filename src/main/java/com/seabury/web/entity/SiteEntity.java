package com.seabury.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SiteEntity {
    Integer ID;
    Byte[] Image;
    String Operator;
    String ReactorType;
    String ReactorSupplier;
    Timestamp ConstructionBegan;
    Timestamp CommissionDate;
    Timestamp DecommissionDate;
    Float ThermalCapacity;
    String Status;
}
