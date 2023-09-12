$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#unit").addClass('active');
$("#parent_picker").attr("disabled", true);
    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

$("#type_picker").change(function(){
                // 변경된 값으로 비교 후 alert 표출
                if($("#type_picker option:checked").val() == "Site"){
                    $("#parent_picker").attr("disabled", true);
                } else if($("#type_picker option:checked").val() == "Plant"){

                    $("#parent_picker").attr("disabled", false);

                } else if($("#type_picker option:checked").val() == "Unit"){

                    $("#parent_picker").attr("disabled", false);
                }
 });

function historyBack(){
    //window.history.back();
    location.href = "unitList";
}
/*function loadData() {
    var resultList = new Array();

    $.ajax({
        type: 'GET',
        url: "/getStructureList",
        dataType: "json",
        async: false,
        error: function(request, status, error) {

        },
        success: function(data) {
            var tempList = new Array();
            data.forEach(item => {
                var node = { tags : [item.objectID, item.parentID], text : item.name , href : typeToHref(item.type), type : item.type };
                tempList.push(node);
            });

            // Sorting Node - Site
            tempList.filter(n => n.type == 'Site').forEach(item => {
                resultList.push(item);
            });
            // Sorting Node - Plant
            tempList.filter(n => n.type == 'Plant').forEach(item => {
                var parentNode = resultList.find(n => n.tags[0] == item.tags[1]);

                if (parentNode != null) {
                    if (parentNode.nodes == null) {
                        parentNode.nodes = new Array();
                    }
                    parentNode.nodes.push(item);
                }
            });
            // Sorting Node - Unit
            tempList.filter(n => n.type == 'Unit').forEach(item => {
                resultList.forEach(parentItem => {
                    if (parentItem.nodes != null) {
                        var parentNode = parentItem.nodes.find(m => m.type == 'Plant' && m.tags[0] == item.tags[1]);

                        if (parentNode != null) {
                            if (parentNode.nodes == null) {
                                parentNode.nodes = new Array();
                            }
                            parentNode.nodes.push(item);
                        }
                    }
                });
            });
            // Sorting Node - Project (추후 구현)
        }
    });

    return JSON.stringify(resultList);
}*/


function dataGridSaveExecute(){

var strucData = {};

    strucData.id = $('#txt_strucid').val();
    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();
    $.ajax({
            url : "/structureUpdate",
            type: 'POST',
            async: false,
            data: JSON.stringify(strucData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {

            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });

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

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode == 'true'){
        url = "/unitUpdate";
        formData.id = $('#txt_id').val();
    }
    // Insert
    else {
        url = "/unitInsert";
    }


    $.ajax({
        url : url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            location.href = "unitDetail?" + "id=" + formData.id;
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dataGridDeleteExecute(){
    if(confirm("해당 아이템을 삭제 하시겠습니까?")){

    var strucData = {};

        strucData.id = $('#txt_strucid').val();
        $.ajax({
                url : "/structureDelete",
                type: 'POST',
                async: false,
                data: JSON.stringify(strucData),
                processData: false,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success : function(data) {

                },
                error : function(data) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            });

     var formData = {};
        formData.id = $('#txt_id').val();

        $.ajax({
            url : "/unitDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                location.href = "plantList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}