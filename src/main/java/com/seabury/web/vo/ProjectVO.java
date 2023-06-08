package com.seabury.web.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProjectVO {
    Integer ID;
    String Name;
    String Description;
    String Creator;
    String Location;
    Timestamp Start_date;
    Timestamp End_date;
    Float Dose_limit;
    String Status;
    Byte[] Image;
    String ReportType;
    Integer Job_ID;
    Integer Document_ID;
    String ProducedBy;
    Double RadiationCalculator;
    String Justification;
    Integer Structure_ID;
}
