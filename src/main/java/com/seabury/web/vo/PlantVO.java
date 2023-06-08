package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PlantVO {
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