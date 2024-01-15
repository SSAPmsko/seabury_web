var rootName = "unit";
var parentformUnit = {};
parentformUnit.type = "Plant";
var IDformUnit = {};
IDformUnit.type = "Unit";
var strucUnitID;
$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#unit").addClass('active');
    onLoadedUnit();
    timestamp();

    $("#parent_picker").attr("disabled", true);

});

function onLoadedUnit(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var unitId = $("#txt_unitId").val();
    var uniqueId;
    if (unitId !== "" && unitId !== "${id}" && unitId !== undefined) {
        uniqueId = "_" + unitId;
    } else if (unitId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('unitPropertyForm', uniqueId);

    if (isFirst === true){
        LoadUnitParent(uniqueId);
        LoadUnitID(uniqueId)


        // button event
        $("#btn_unit_save" + uniqueId).on("click", function(e) {
            dg_unitSaveExecute(uniqueId);
        })

        $("#btn_unit_delete" + uniqueId).on("click", function(e) {
            dg_unitDeleteExecute(uniqueId);
        });
    }
}
function LoadUnitID(uniqueId) {

    var unitIntId = $('#txt_unitId'+ uniqueId).val();

    IDformUnit.objectID =  parseInt(unitIntId);

    $.ajax({
        type: 'POST',
        url: "/getStructureID",
        data: JSON.stringify(IDformUnit),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            data.forEach(item => {
                strucUnitID = item.id;
            });
        }
    }).done(function (fragment) {

    });
}
function LoadUnitParent(uniqueId) {
//structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureType",
        data: JSON.stringify(parentformUnit),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
        },
        success: function (data) {
            var parentSelect = $('#unit_parent' + uniqueId);
            data.forEach(item => {
                var parentid = $("#txt_parentId"+ uniqueId).val();

                if (parentid == item.objectID)
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

function dg_unitSaveExecute(uniqueId){
    var structureform = {};
    structureform.id = strucUnitID;
    structureform.name = $('#txt_name' + uniqueId).val();
    structureform.objectID = $('#txt_unitId' + uniqueId).val();
    structureform.parentID = $("#unit_parent"+ uniqueId).val();
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
    formData.id = $('#txt_unitId' + uniqueId).val();


    $.ajax({
        url: "/structureUpdate",
        type: 'POST',
        async: false,
        data: JSON.stringify(structureform),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            $.ajax({
                url : "/unitUpdate",
                type: 'POST',
                async: false,
                data: JSON.stringify(formData),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {
                    //location.href = "unitDetail?" + "id=" + data.result.id;
                    alert('저장이 완료 되었습니다.');

                    // 리스트 리로드
                    dg_unitReloadExecute();

                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });
        }
    });
}

function dg_unitDeleteExecute(uniqueId){
    var structureform = {};
    structureform.id = strucUnitID;

    var formData = {};
    formData.id = $('#txt_unitId' + uniqueId).val();


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
                url : "/unitDelete",
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
                    dg_unitReloadExecute();
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
    location.href = "unitList";
}

function dataGridSaveExecute() {

    var strucData = {};

    strucData.id = $('#txt_strucid').val();
    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();


    var url;
    var formData = {};

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
    url = "/unitUpdate";
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

