var detailid;
var idurl;
var url;
var formData = {};
var structype;
var strucData = {};

$(document).ready(function () {
    // 클릭한 위치 active 적용
    $("#create").addClass('active');
    startpicker();

    $("#type_picker").on("click", function(e) {
        $('#parent_picker option').remove();
        var type = $("#type_picker option:checked").val();

        pickerLoadData(type);
    });
    $("#btn_structure_create").on("click", function(e) {
        InsertPost();
    });
});
function startpicker(){
    $('#parent_picker option').remove();
    var type = "Site";

    pickerLoadData(type);
}

function pickerLoadData(type) {


    switch (type) {
        case "Site":
            formData.type = null;
            break;
        case "Plant":
            formData.type = "Site";
            break;
        case "Unit":
            formData.type = "Plant";
            break;
        default:
            break;
    }
    if (formData.type != null)
    {
        $.ajax({
            type: 'POST',
            url: "/getStructureList",
            data: JSON.stringify(formData),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            error: function (request, status, error) {

            },
            success: function (data) {
                data.forEach(item => {
                    var parentSelect = $('#parent_picker');

                    parentSelect.append(new Option(item.name, item.objectID, item.type, true));
                });

            }
        });


    }
    //structure 리스트
    $.ajax({
        type: 'POST',
        url: "/get"+ type +"DetailList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {

        },
        success: function (data) {
            $('#name_picker option').remove();
            data.forEach(item => {

                var nameSelect = $('#name_picker');
                nameSelect.append(new Option(item.name, item.id, item.type, true));
            });
        }
    });
}

function InsertPost() {

    formData.name = $('#txt_name').val();
    formData.description = $('#txt_description').val();
    formData.type = $('#type_picker').val();
    switch (formData.type)
    {
        case "Site":
            formData.parentType = null;
            break;
        case "Plant":
            formData.parentType = "Site";
            break;
        case "Unit":
            formData.parentType = "Plant";
            break;
        default:
            break;
    }


    formData.objectID = $('#name_picker').val();
    formData.parentID = $('#parent_picker').val();


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



