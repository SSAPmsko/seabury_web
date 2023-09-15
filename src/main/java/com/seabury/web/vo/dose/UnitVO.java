package com.seabury.web.vo.dose;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class UnitVO {
    Integer ID;
    Byte[] Image;
    String Operator;
    String ReactorType;
    String ReactorSupplier;
    Date ConstructionBegan;
    Date CommissionDate;
    Date DecommissionDate;
    String ThermalCapacity;
    String Status;
    String Name;

}
