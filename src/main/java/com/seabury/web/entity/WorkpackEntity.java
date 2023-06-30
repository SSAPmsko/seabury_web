package com.seabury.web.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WorkpackEntity {
    Integer ID;
    String Image;
    String Operator;
    String ReactorType;
    String ReactorSupplier;
    Timestamp ConstructionBegan;
    Timestamp CommissionDate;
    Float DecommissionDate;
    String ThermalCapacity;
    Byte[] Status;
}