var detailid;
var idurl;
var url;
var formData = {};
var RoomformData = {};
var structype;
var strucData = {};
var createtype = $('#txt_createtype').val();

$(document).ready(function () {
    // 클릭한 위치 active 적용
    $("#create").addClass('active');
    $("#btn_create").on("click", function(e) {
            InsertPost();
    });
});


/*$("#type_picker").change(function () {
    $('#parent_picker option').remove();
    var type = $("#type_picker option:checked").val();

    pickerLoadData(type);

    switch (type) {
        case "Site":
            $("#parent_picker").attr("disabled", true);
            break;
        case "Plant":
            $("#parent_picker").attr("disabled", false);
            break;
        case "Unit":
            $("#parent_picker").attr("disabled", false);
            break;
        default:
            break;
    }
});*/

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

/*function pickerLoadData(type) {


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
    //structure 리스트
    $.ajax({
        type: 'POST',
        url: "/getStructureList",
        data: JSON.stringify(formData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        error: function (request, status, error) {
            alert(request.status)
        },
        success: function (data) {
            data.forEach(item => {
                var parentSelect = $('#parent_picker');

                parentSelect.append(new Option(item.name, item.objectID, item.type, true));
            });
        }
    });
}*/
function RoomInsertPost() {
    RoomformData.name =$('#txt_name').val();


}


function InsertPost() {

    formData.name =$('#txt_name').val();
    formData.operator = $('#txt_operator').val();
    formData.status = $('#txt_status').val();
    formData.reactorType = $('#txt_reactortype').val();
    formData.reactorSupplier = $('#txt_reactorsupplier').val();
    formData.constructionBegan = $('#dt_constructionbegan').val();
    formData.commissionDate = $('#dt_commissiondate').val();
    formData.decommissionDate = $('#dt_decommissiondate').val();
    formData.thermalCapacity = $('#txt_thermalcapacity').val();//적용안됌
    // Update
    if (createtype == "Site") {
        url = "/siteInsert";

    } else if (createtype == "Unit") {
        url = "/unitInsert";

    } else if (createtype == "Plant") {
        url = "/plantInsert";
    }


    $.ajax({
        url: url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {

            GetId()

            // 리스트 리로드
            if (createtype == "Site") {
                dg_siteReloadExecute();
            } else if (createtype == "Unit") {
                dg_plantReloadExecute();

            } else if (createtype == "Plant") {
                dg_unitReloadExecute();
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}
function GetId() {
    if (createtype == "Site") {
        idurl = "/getSiteListId";
    } else if (createtype == "Unit") {
        idurl = "/getUnitListId";
    } else if (createtype == "Plant") {
        idurl = "/getPlantListId";
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
function StructureInsert() {
    if (createtype == "Site") {
    } else if (createtype == "Plant") {
        structype = "Site";
    } else if (createtype == "Unit") {
        structype = "Plant";
    }


    strucData.type = createtype;
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
            alert("생성 완료 되었습니다.")
            // 해당 패널 닫기
            var DetailContainer = myLayout.root.getItemsById('createDetail_newItem');
            if (DetailContainer !== undefined) {
                DetailContainer[0].close();
            }
        },
        error: function (data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}
