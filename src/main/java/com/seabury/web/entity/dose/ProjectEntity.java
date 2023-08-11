package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProjectEntity {
    Integer ID;
    String Name;
    String Description;
    String Creator;
    String Location;
    Timestamp StartDate;
    Timestamp EndDate;
    Float DoseLimit;
    String Status;
    Byte[] Image;
    String ReportType;
    Integer JobID;
    Integer DocumentID;
    String ProducedBy;
    Double RadiationCalculator;
    String Justification;
    Integer StructureID;
}
