var rootName = "plant";
var parentformPlant = {};
parentformPlant.type = "Site";
var IDformPlant = {};
IDformPlant.type = "Plant";
var strucPlantID;
var PlantTypeform = {};
PlantTypeform.type = "Plant";

$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#plant").addClass('active');
    onLoadedPlant();
    timestamp();

    $("#parent_picker").attr("disabled", true);
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
        LoadPlantParentId(uniqueId);
        LoadPlantParent(uniqueId);
        LoadPlantID(uniqueId)


        // button event
        $("#btn_plant_save" + uniqueId).on("click", function(e) {
            dg_plantSaveExecute(uniqueId);
        });

        $("#btn_plant_delete" + uniqueId).on("click", function(e) {
            dg_plantDeleteExecute(uniqueId);
        });
    }
}

function LoadPlantID(uniqueId) {

    var plantIntId = $('#txt_plantId'+ uniqueId).val();

    IDformPlant.objectID =  parseInt(plantIntId);

    $.ajax({
        type: 'POST',
        url: "/getStructureID",
        data: JSON.stringify(IDformPlant),
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
function LoadPlantParentId(uniqueId)
{
    var plantIntId = $('#txt_plantId'+ uniqueId).val();

    PlantTypeform.objectID =  parseInt(plantIntId);

    $.ajax({
        type: 'POST',
        url: "/getStructureParentID",
        data: JSON.stringify(PlantTypeform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
            alert("error")
        },
        success: function (data) {
            data.forEach(item => {
                strucpicker = item.parentID;
            });

        }
    }).done(function (fragment) {

    });

}

function LoadPlantParent(uniqueId) {
//structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureType",
        data: JSON.stringify(parentformPlant),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
        },
        success: function (data) {
            var parentSelect = $('#plant_parent' + uniqueId);
            const tempList = data.sort((a,b) => {
                if(a.name > b.name) return 1;
                if(a.name < b.name) return -1;
                return 0;
            });
            tempList.forEach(item => {
                var parentid = $("#txt_parentId"+ uniqueId).val();
                if (strucpicker == item.objectID)
                {
                    parentSelect.append(new Option(item.name, item.objectID, true, true));
                }
                else{
                    parentSelect.append(new Option(item.name, item.objectID, false, false));
                }

            });
        }
    });
}
function dg_plantSaveExecute(uniqueId){

    var structureform = {};
    structureform.id = strucPlantID;
    structureform.name = $('#txt_name' + uniqueId).val();
    structureform.objectID = $('#txt_plantId' + uniqueId).val();
    structureform.parentID = $("#plant_parent"+ uniqueId).val();
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
    // Update
        formData.id = $('#txt_plantId' + uniqueId).val();

    $.ajax({
        url : "/structureUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(structureform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            $.ajax({
                url : "/plantUpdate",
                type: 'POST',
                async: false,
                data: JSON.stringify(formData),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {
                    //location.href = "plantDetail?" + "id=" + data.result.id;
                    alert('저장이 완료 되었습니다.');
                    loadTreeData();
                    // 리스트 리로드
                    dg_plantReloadExecute();
                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });

}

function dg_plantDeleteExecute(uniqueId){
    var structureform = {};
    structureform.id = strucPlantID;


    var formData = {};
    formData.id = $('#txt_plantId' + uniqueId).val();
    $.ajax({
        url : "/structureDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(structureform),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
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

