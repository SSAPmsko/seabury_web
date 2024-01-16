package com.seabury.web.vo.alfresco;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;
import org.apache.chemistry.opencmis.client.api.Document;

import java.util.Date;

@Getter
@Setter
public class ALF_DocInfoVO {
    private Document CmisDocument;
    private String Title;
    private String Name;
    private String ObjectId;

    // 23.12.06 ife custom field
    private String ProjectId;
    private String ProjectName;
    private String ScenarioId;
    private String ScenarioName;
    private String CreatedBy;
    private Date CreateDate;
    private String FileContentsB64;
    private String RenditionContentsB64;

    // Secondary Field
    private Boolean isFavorites;
    private Boolean isLock;
    private String Permission;
    private String HighLight;
    private String ThumbnailUrl;


    // isu custom field
/*
    private String No;
    private String DocClass;
    private String DocContent;
    private String EquipmentNo;
    private String MaintenancePlant;
    private String FunctionalLocation;
    private String DocTypeLv1;
    private String DocTypeLv2;
    private String DocTypeLv3;
    private String DocTypeLv4;
    private String Department;
    private String Writer;
    private Date WriterDate;
    private Double RevisionNo;
    private String DocNo;
    private String BookName;
    private Integer BookNo;
    private Date HandoverDate;
    private String ChargePart;
    private Boolean Package;
    private String DrawingNo;
    private String DrawingDescription;
    private Double DrawingSize;
    private String FileType;
    private String FilePath;
    private String PlantSection;

    // Add 21.08.23
    private String ReportTitle;
    private Date ReportDate;
    private String SheetNo;
    private String LineNo;
    private String ClassNo;
    private String DocCategory;
    private String DrawingCategory;
    private Integer DocAutoNo;
    private String DocDivision;

    // Add 21.10.29
    private String DrawingPaperSize;

    // Add 23.02.21 by kms : Alfresco 내 Type 변경이 기존에 이미 입력된 문서들이 있어 변경 불가
    private String BookNumber;
*/



}
