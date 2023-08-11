package com.seabury.web.entity.integrated;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WorkFlowEntity {
	Integer ID;
	String Description;
	Integer AnalysisItem_ID;
	Integer Worker_ID;
	Integer Posture_ID;
	Timestamp StartTime;
	Timestamp EndTime;
}
