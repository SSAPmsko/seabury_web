var detailid;
var formData = {};

$(document).ready(function () {
    // 클릭한 위치 active 적용
    $("#create").addClass('active');
    $("#btn_create").on("click", function(e) {
        InsertPost();
    });
});

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


function InsertPost() {

    formData.name =$('#txt_name').val();
    formData.operator = $('#txt_operator').val();
    formData.description = $('#txt_description').val();


    $.ajax({
        url: "/roomInsert",
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {

            alert('저장이 완료 되었습니다.');
            GetId()


        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}
function GetId() {
    $.ajax({
        type: 'GET',
        url: "/getRoomList",
        dataType: "json",
        error: function (request, status, error) {
            alert(request.status)
        },
        success: function (data) {
            detailid = data;
            StructureRoom();
        }
    });

}
function StructureRoom() {
    var strucData = {};

    strucData.type = "Room";
    strucData.parentType = "Unit";

    strucData.name = $('#txt_name').val();
    strucData.operator = $('#txt_operator').val();
    strucData.description = $('#txt_description').val();
    strucData.objectID = detailid;


    $.ajax({
        url: "/structureInsert",
        type: 'POST',
        async: false,
        data: JSON.stringify(strucData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            StructureProject();

        },
        error: function (data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });

}
function StructureProject() {
    var strucData = {};


    strucData.type = "Project";
    strucData.parentType = "Room";
    strucData.parentID = detailid;

    $.ajax({
        url: "/structureInsert",
        type: 'POST',
        async: false,
        data: JSON.stringify(strucData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            // 리스트 리로드
            dg_roomReloadExecute();

            // 해당 패널 닫기
            var DetailContainer = myLayout.root.getItemsById('roomcreateDetail_newItem');
            if (DetailContainer !== undefined) {
                DetailContainer[0].close();
            }
        },
        error: function (data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}


