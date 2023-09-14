package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class SiteEntity {
    Integer ID;
    Byte[] Image;
    String Operator;
    String ReactorType;
    String ReactorSupplier;
    Date ConstructionBegan;
    Date CommissionDate;
    Date DecommissionDate;
    Float ThermalCapacity;
    String Status;
    String Name;
}
