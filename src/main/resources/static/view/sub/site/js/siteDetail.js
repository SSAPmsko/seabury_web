var rootName = "site";

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
        var editMode = $("#txt_editMode" + uniqueId).val();
        if (editMode === 'true') {
            $('#btn_site_delete' + uniqueId).removeClass("visually-hidden");
        } else {
            $('#btn_site_delete' + uniqueId).addClass("visually-hidden");
        }


        // button event
        $("#btn_site_save" + uniqueId).on("click", function(e) {
            dg_siteSaveExecute(uniqueId);
        });

        $("#btn_site_delete" + uniqueId).on("click", function(e) {
            dg_siteDeleteExecute(uniqueId);
        });
    }
}

function dg_siteSaveExecute(uniqueId){
    var url;
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

        url = "/siteUpdate";

    $.ajax({
        url : url,
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

            // 저장된 데이터가 신규 인 경우
            var newSiteContainer = myLayout.root.getItemsById('siteDetail_newItem');

            if (newSiteContainer !== undefined) {
                var oldId = "_newItem";

                $('#btn_site_save' + oldId).off("click");
                $('#btn_site_delete' + oldId).off("click");
                $('#btn_site_delete' + oldId).removeClass("visually-hidden");

                var siteId = data.result.id;
                newSiteContainer[0].setTitle('siteDetail_' + siteId);
                newSiteContainer[0].config.id = 'siteDetail_' + siteId;

                var newId = "_" + siteId;

                $('#txt_siteId' + oldId).val(siteId);

                replaceFormNewId('sitePropertyForm', oldId, newId);

                // button event
                $("#btn_site_save" + newId).on("click", function(e) {
                    dg_siteSaveExecute(newId);
                });

                $("#btn_site_delete" + newId).on("click", function(e) {
                    dg_siteDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_siteDeleteExecute(uniqueId){
    var formData = {};
    formData.id = $('#txt_siteId' + uniqueId).val();

    $.ajax({
        url : "/siteDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "siteList";
            alert('삭제가 완료 되었습니다.');

            // 해당 패널 닫기
            var siteDetailContainer = myLayout.root.getItemsById('siteDetail_' + $('#txt_siteId' + uniqueId).val());
            if (siteDetailContainer !== undefined) {
                siteDetailContainer[0].close();
            }

            // 리스트 리로드
            dg_siteReloadExecute();
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
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

function dg_siteReloadExecute(){
    $('#dg_site').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_site').data('kendoGrid').refresh(); <!--  refresh current UI -->
}