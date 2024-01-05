package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomEntity {
    Integer ID;
    String Name;
    String Operator;
    String Description;
    Integer ProjectID;
    String ProjectName;
}
