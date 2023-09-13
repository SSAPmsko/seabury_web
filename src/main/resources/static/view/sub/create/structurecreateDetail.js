var detailid;
var idurl;
var url;
var formData = {};
var structype;
var strucData = {};

$(document).ready(function () {
    // 클릭한 위치 active 적용
    $("#create").addClass('active');

});

function InsertPost() {

    formData.reactorSupplier = $('#txt_name').val();
    formData.constructionBegan = $('#txt_description').val();
    formData.commissionDate = $('#type_picker').val();
    formData.decommissionDate = $('#parent_picker').val();
    formData.thermalCapacity = $('#name_picker').val();//적용안됌
    // Update
        url = "/structureInsert";



    $.ajax({
        url: url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {

            alert('저장이 완료 되었습니다.');

            // 리스트 리로드
            dg_strucReloadExecute();


        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}
/*
function GetId() {
    if (formData.type == "Site") {
        idurl = "/getSiteListId";
    } else if (formData.type == "Unit") {
        idurl = "/getUnitListId";
    } else if (formData.type == "Plant") {
        idurl = "/getPlantList";
    }

    $.ajax({
        type: 'GET',
        url: idurl,
        dataType: "json",
        error: function (request, status, error) {
            alert(request.status)
        },
        success: function (data) {
            detailid = data;
            StructureInsert();
        }
    });

}
*/
/*
function StructureInsert() {
    if ($("#type_picker option:checked").val() == "Site") {
    } else if ($("#type_picker option:checked").val() == "Plant") {
        structype = "Site";
    } else if ($("#type_picker option:checked").val() == "Unit") {
        structype = "Plant";
    }


    strucData.type = $('#type_picker option:checked').text();
    strucData.parentType = structype;

    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();
    strucData.objectID = detailid;
    strucData.parentID = $('#parent_picker').val();
    $.ajax({
        url: "/structureInsert",
        type: 'POST',
        async: false,
        data: JSON.stringify(strucData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {

        },
        error: function (data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });

}*/

function dataGridSaveExecute() {
    InsertPost();
}

