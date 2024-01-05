var rootName = "site";
var IDformSite = {};
IDformSite.type = "Site";
var strucSiteID;

$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#site").addClass('active');
    onLoadedSite();
    timestamp();

    $("#parent_picker").attr("disabled", true);

});

function onLoadedSite(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var siteId = $("#txt_siteId").val();
    var uniqueId;
    if (siteId !== "" && siteId !== "${id}" && siteId !== undefined) {
        uniqueId = "_" + siteId;
    } else if (siteId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('sitePropertyForm', uniqueId);

    if (isFirst === true){
        LoadPlantID(uniqueId)

        // button event
        $("#btn_site_save" + uniqueId).on("click", function(e) {
            dg_siteSaveExecute(uniqueId);
        });

        $("#btn_site_delete" + uniqueId).on("click", function(e) {
            dg_siteDeleteExecute(uniqueId);
        });
    }
}

function LoadSiteID(uniqueId) {

    var siteIntId = $('#txt_siteId'+ uniqueId).val();

    IDformSite.objectID =  parseInt(siteIntId);

    $.ajax({
        type: 'POST',
        url: "/getStructureID",
        data: JSON.stringify(IDformSite),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            data.forEach(item => {
                strucPlantID = item.id;
            });
        }
    }).done(function (fragment) {

    });
}

function dg_siteSaveExecute(uniqueId){
    var structureform = {};
    structureform.id = strucSiteID;
    structureform.name = $('#txt_name' + uniqueId).val();
    structureform.objectID = $('#txt_siteId' + uniqueId).val();
    structureform.description = $('#txt_description' + uniqueId).val();

    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.operator = $('#txt_operator' + uniqueId).val();
    formData.status = $('#txt_status' + uniqueId).val();
    formData.reactorType = $('#txt_reactortype' + uniqueId).val();
    formData.reactorSupplier = $('#txt_reactorsupplier' + uniqueId).val();
    formData.constructionBegan = $('#dt_constructionbegan' + uniqueId).val();
    formData.commissionDate = $('#dt_commissiondate' + uniqueId).val();
    formData.decommissionDate = $('#dt_decommissiondate' + uniqueId).val();
    formData.thermalCapacity = $('#txt_thermalcapacity' + uniqueId).val();
    formData.id = $('#txt_siteId' + uniqueId).val();

    $.ajax({
        url: "/structureUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(structureform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
           $.ajax({
               url : "/siteUpdate",
               type: 'POST',
               async: false,
               data: JSON.stringify(formData),
              dataType: "json",
              contentType: "application/json;charset=UTF-8",
               success : function(data) {
                 //location.href = "siteDetail?" + "id=" + data.result.id;
                 alert('저장이 완료 되었습니다.');

                    // 리스트 리로드
                    dg_siteReloadExecute();


               },
               error : function(data) {
                  alert("정상 처리에 실패 하였습니다.");
              }
            });
        }
    });
}

function dg_siteDeleteExecute(uniqueId){
    var structureform = {};
    structureform.id = strucSiteID;

    var formData = {};
    formData.id = $('#txt_siteId' + uniqueId).val();


    $.ajax({
        url: "/structureDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(structureform),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            $.ajax({
                url : "/siteDelete",
                type: 'DELETE',
                async: false,
                data: JSON.stringify(formData),
                processData: false,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {
                    //location.href = "unitList";
                    alert('삭제가 완료 되었습니다.');

                    // 해당 패널 닫기
                    var unitDetailContainer = myLayout.root.getItemsById('unitDetail_' + $('#txt_unitId' + uniqueId).val());
                    if (unitDetailContainer !== undefined) {
                        unitDetailContainer[0].close();
                    }

                    // 리스트 리로드
                    dg_siteReloadExecute();
                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });
        }
    });
}

$("#type_picker").change(function () {
    // 변경된 값으로 비교 후 alert 표출
    if ($("#type_picker option:checked").val() == "Site") {
        $("#parent_picker").attr("disabled", true);
    } else if ($("#type_picker option:checked").val() == "Site") {

        $("#parent_picker").attr("disabled", false);

    } else if ($("#type_picker option:checked").val() == "Unit") {

        $("#parent_picker").attr("disabled", false);
    }
});

function timestamp() {
    var today = new Date();
    today.setHours(today.getHours() + 9);
    return today.toISOString().replace('T', ' ').substring(0, 19);
}

function historyBack() {
    //window.history.back();
    location.href = "siteList";
}

function dataGridSaveExecute() {

    var strucData = {};

    strucData.id = $('#txt_strucid').val();
    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();


    var url;
    var formData = {};
    //formData.type = $('#txt_type1').val();
    //  formData.parent = $('#txt_parent1').val();
    //formData.name = $('#txt_name1').val();
    formData.description = $('#txt_description').val();
    formData.operator = $('#txt_operator').val();
    formData.status = $('#txt_status').val();
    formData.reactorType = $('#txt_reactortype').val();
    formData.reactorSupplier = $('#txt_reactorsupplier').val();
    formData.constructionBegan = $('#dt_constructionbegan').val();
    formData.commissionDate = $('#dt_commissiondate').val();
    formData.decommissionDate = $('#dt_decommissiondate').val();
    formData.thermalCapacity = $('#txt_thermalcapacity').val();//적용안됌
    formData.name = $('#dt_decommissiondate').val();
    url = "/siteUpdate";
    formData.id = $('#txt_id').val();


}

function dataGridDeleteExecute() {
    if (confirm("해당 아이템을 삭제 하시겠습니까?")) {

        var strucData = {};

        strucData.id = $('#txt_strucid').val();


        var formData = {};
        formData.id = $('#txt_id').val();


    }
}

