var detailid;
var idurl;
var url;
var pickerformData = {};
var structype;
var strucData = {};

$(document).ready(function () {
    // 클릭한 위치 active 적용
    $("#create").addClass('active');

    onLoadedStruc();

    /*$("#type_picker option:checked").val() = rp.value.type*/

    /*if ($("#type_picker option:checked").val() == "Site") {
        $("#parent_picker").attr("disabled", true);
    }*/

});

$("#txt_type").change(function () {
    var type = $('#txt_type option:checked').val();
    alert("f");

    switch (type) {
        case "Site":
            pickerformData.type = null;
            pickerLoadData(uniqueId);
            break;
        case "Plant":
            pickerformData.type = "Site";
            pickerLoadData(uniqueId);
            break;
        case "Unit":
            pickerformData.type = "Plant";
            pickerLoadData(uniqueId);
            break;
        case "Room":
            pickerformData.type = "Unit";
            pickerLoadData(uniqueId);
            break;
        default:
            break;
    }
});


function onLoadedStruc(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var strucId = $("#txt_strucId").val();
    var uniqueId;


    if (strucId !== "" && strucId !== "${id}" && strucId !== undefined) {
        uniqueId = "_" + strucId;
    } else if (strucId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('strucPropertyForm', uniqueId);

    if (isFirst === true){
        // button event
        $("#btn_struc_save" + uniqueId).on("click", function(e) {
            dg_strucSaveExecute(uniqueId);
        });

        $("#btn_struc_delete" + uniqueId).on("click", function(e) {
            dg_strucDeleteExecute(uniqueId);
        });
        pickerLoadData(uniqueId);
    }
}

function loadData() {
    //structure 리스트
    $.ajax({
        type: 'GET',
        url: "/getStructureList",
        dataType: "json",
        error: function (request, status, error) {
            alert(request.status)
        },
        success: function (data) {
            data.forEach(item => {
                var $parentSelect = $('#parent_picker');
                var node = {
                    type: item.type,
                    name: item.name,
                    objectid: item.objectID,
                    parenttype: item.parentType,
                    parentid: item.parentID
                };
                $parentSelect.append(new Option(node.name, node.objectid, node.type, true));
            });
        }
    });
}

function pickerLoadData(uniqueId) {

    //structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureList",
        data: JSON.stringify(pickerformData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
            alert("실패");
        },
        success: function (data) {
            alert("성공");

            var parentSelect = $('#parent_picker' + uniqueId);
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
    $.ajax({
        type: 'POST',
        url: "/get"+ type +"DetailList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            data.forEach(item => {
                var nameSelect = $('#name_picker' + uniqueId);
                var objectid = $("#txt_objectId"+ uniqueId).val();

                if (objectid == item.id)
                {
                    nameSelect.append(new Option(item.name, item.id, true, true));
                }
                else{
                    nameSelect.append(new Option(item.name, item.id, false, false));
                }
            });
        }
    });
}




function dg_strucSaveExecute(uniqueId) {
    if ($("#type_picker option:checked" + uniqueId).val() == "Site") {
    } else if ($("#type_picker option:checked" + uniqueId).val() == "Plant") {
        structype = "Site";
    } else if ($("#type_picker option:checked" + uniqueId).val() == "Unit") {
        structype = "Plant";
    }


    strucData.type = $('#type_picker option:checked' + uniqueId).text();
    strucData.parentType = structype;

    strucData.name = $('#txt_name' + uniqueId).val();
    strucData.description = $('#txt_description' + uniqueId).val();
    strucData.objectID = $('#name_picker' + uniqueId).val();
    strucData.parentID = $('#parent_picker' + uniqueId).val();
    $.ajax({
        url: "/structureInsert",
        type: 'POST',
        async: false,
        data: JSON.stringify(strucData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            alert('저장이 완료 되었습니다.');

            // 리스트 리로드
            dg_strucReloadExecute();

            // 저장된 데이터가 신규 인 경우
            var newStrucContainer = myLayout.root.getItemsById('strucDetail_newItem');

            if (newStrucContainer !== undefined) {
                var oldId = "_newItem";

                $('#txt_editMode' + oldId).val(true);
                $('#btn_struc_save' + oldId).off("click");
                $('#btn_struc_delete' + oldId).off("click");
                $('#btn_struc_delete' + oldId).removeClass("visually-hidden");

                var strucId = data.result.id;
                newStrucContainer[0].setTitle('strucDetail_' + strucId);
                newStrucContainer[0].config.id = 'strucDetail_' + strucId;

                var newId = "_" + strucId;

                $('#txt_strucId' + oldId).val(strucId);

                replaceFormNewId('strucPropertyForm', oldId, newId);

                // button event
                $("#btn_struc_save" + newId).on("click", function(e) {
                    dg_strucSaveExecute(newId);
                });

                $("#btn_struc_delete" + newId).on("click", function(e) {
                    dg_strucDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_strucDeleteExecute(uniqueId){
    var formData = {};
    formData.id = $('#txt_strucd' + uniqueId).val();

    $.ajax({
        url : "/structureDelete",
        type: 'DELETE',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "strucList";
            alert('삭제가 완료 되었습니다.');

            // 해당 패널 닫기
            var strucDetailContainer = myLayout.root.getItemsById('strucDetail_' + $('#txt_strucId' + uniqueId).val());
            if (strucDetailContainer !== undefined) {
                strucDetailContainer[0].close();
            }

            // 리스트 리로드
            dg_strucReloadExecute();
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_strucReloadExecute(){
    $('#dg_structure').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_structure').data('kendoGrid').refresh(); <!--  refresh current UI -->
}
