var rootName = "plant";

$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#plant").addClass('active');
    onLoadedPlant();
    timestamp();

    $("#parent_picker").attr("disabled", true);

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function onLoadedPlant(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var plantId = $("#txt_plantId").val();
    var uniqueId;
    if (plantId !== "" && plantId !== "${id}" && plantId !== undefined) {
        uniqueId = "_" + plantId;
    } else if (plantId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('plantPropertyForm', uniqueId);

    if (isFirst === true){
        var editMode = $("#txt_editMode" + uniqueId).val();
        if (editMode === 'true') {
            $('#btn_plant_delete' + uniqueId).removeClass("visually-hidden");
        } else {
            $('#btn_plant_delete' + uniqueId).addClass("visually-hidden");
        }


        // button event
        $("#btn_plant_save" + uniqueId).on("click", function(e) {
            dg_plantSaveExecute(uniqueId);
        });

        $("#btn_plant_delete" + uniqueId).on("click", function(e) {
            dg_plantDeleteExecute(uniqueId);
        });
    }
}

function dg_plantSaveExecute(uniqueId){
    var url;
    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.date = $('#dt_date' + uniqueId).val();
    formData.description = $('#txt_description' + uniqueId).val();
    formData.startDate = $('#dt_startDate' + uniqueId).val();
    formData.endDate = $('#dt_endDate' + uniqueId).val();
    formData.createdBy = $('#txt_createdBy' + uniqueId).val();
    formData.justification = $('#txt_justification' + uniqueId).val();
    formData.doseLimit = $('#txt_doseLimit' + uniqueId).val();
    formData.room = $('#txt_room' + uniqueId).val();

    formData.editMode = $('#txt_editMode' + uniqueId).val();
    // Update
    if (formData.editMode === 'true'){
        url = "/plantUpdate";
        formData.id = $('#txt_plantId' + uniqueId).val();
    }
    // Insert
    else {
        url = "/plantInsert";
    }

    $.ajax({
        url : url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "plantDetail?" + "id=" + data.result.id;
            alert('저장이 완료 되었습니다.');

            // 리스트 리로드
            dg_plantReloadExecute();

            // 저장된 데이터가 신규 인 경우
            var newPlantContainer = myLayout.root.getItemsById('plantDetail_newItem');

            if (newPlantContainer !== undefined) {
                var oldId = "_newItem";

                $('#txt_editMode' + oldId).val(true);
                $('#btn_plant_save' + oldId).off("click");
                $('#btn_plant_delete' + oldId).off("click");
                $('#btn_plant_delete' + oldId).removeClass("visually-hidden");

                var plantId = data.result.id;
                newPlantContainer[0].setTitle('plantDetail_' + plantId);
                newPlantContainer[0].config.id = 'plantDetail_' + plantId;

                var newId = "_" + plantId;

                $('#txt_plantId' + oldId).val(plantId);

                replaceFormNewId('plantPropertyForm', oldId, newId);

                // button event
                $("#btn_plant_save" + newId).on("click", function(e) {
                    dg_plantSaveExecute(newId);
                });

                $("#btn_plant_delete" + newId).on("click", function(e) {
                    dg_plantDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_plantDeleteExecute(uniqueId){
    var formData = {};
    formData.id = $('#txt_plantd' + uniqueId).val();

    $.ajax({
        url : "/plantDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "plantList";
            alert('삭제가 완료 되었습니다.');

            // 해당 패널 닫기
            var plantDetailContainer = myLayout.root.getItemsById('plantDetail_' + $('#txt_plantId' + uniqueId).val());
            if (plantDetailContainer !== undefined) {
                plantDetailContainer[0].close();
            }

            // 리스트 리로드
            dg_plantReloadExecute();
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
    } else if ($("#type_picker option:checked").val() == "Plant") {

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
    location.href = "plantList";
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
    formData.editMode = $('#txt_editMode').val();
    url = "/plantUpdate";
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

function dg_plantReloadExecute(){
    $('#dg_plant').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_plant').data('kendoGrid').refresh(); <!--  refresh current UI -->
}