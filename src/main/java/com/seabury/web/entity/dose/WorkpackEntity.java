package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WorkpackEntity {
    Integer ID;
    Byte[] Image;
    String Operator;
    String ReactorType;
    String ReactorSupplier;
    Timestamp ConstructionBegan;
    Timestamp CommissionDate;
    Timestamp DecommissionDate;
    String ThermalCapacity;
    String Status;
}