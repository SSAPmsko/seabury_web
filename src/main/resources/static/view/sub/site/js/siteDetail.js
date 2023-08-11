$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#site").addClass('active');
/*$("#parent_picker").attr("disabled", true);*/

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

/*$("#type_picker").change(function(){
                // 변경된 값으로 비교 후 alert 표출
                if($("#type_picker option:checked").text() == "Site"){
                    $("#parent_picker").attr("disabled", true);
                } else if($("#type_picker option:checked").text() == "Plant"){

                    $("#parent_picker ").attr("disabled", false);

                } else if($("#type_picker option:checked").text() == "Unit"){

                    $("#parent_picker").attr("disabled", false);
                }
 });*/
function historyBack(){
    //window.history.back();
    location.href = "siteList";
}

function loadData() {

//structure 리스트

$.ajax({
            type: 'GET',
            url: "/getStructureList",
            dataType: "json",
            error: function(request, status, error) {
                alert(request.status)},
            success: function(data) {
                data.forEach(item => {
                	var node = {
                	id:item.id, type : item.type, name : item.name ,objectid : item.objectID, parenttype : item.parentType ,parentid : item.parentID
                	};
                	/*var $typeSelect = $('#type_picker');
                	var $parentSelect = $('#parent_picker');
                	$typeSelect.append(new Option(node.type ,node.object_id , true , true));
                	$parentSelect.append(new Option(node.parent_type ,node.parent_id , true , true));*/
                });

            }})
/*//site
    $.ajax({
                            url : "/getSiteList",
                            type: 'POST',
                            async: false,
                            processData: false,
                            dataType: "json",
                            contentType: "application/json;charset=UTF-8",
                            success: function(result) {
                              options.success(result);
                            },
                            error: function(result) {
                              options.error(result);
                            }
                        });*/
}


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
//    formData.type = $('#txt_type').val();
//    formData.parent = $('#txt_parent').val();
//    formData.name = $('#txt_name').val();
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

    url = "/siteUpdate";
    formData.id = $('#txt_id').val();


    $.ajax({
        url : url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            location.href = "siteDetail?" + "id=" + formData.id;
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
            url : "/siteDelete",
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