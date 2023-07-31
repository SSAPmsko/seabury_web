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
                	type : item.type, name : item.name ,object_id : item.object_ID, parent_type : item.parent_Type ,parent_id : item.parent_ID
                	};
                	var $typeSelect = $('#type_picker');
                	var $parentSelect = $('#parent_picker');
                	$typeSelect.append(new Option(node.type ,node.type , true , true));
                	$parentSelect.append(new Option(node.parent_type ,node.parent_type , true , true));
                });

            }})

/*//unit
    $.ajax({
                            url : "/getUnitList",
                            type: 'POST',
                            async: false,
                            processData: false,
                            dataType: "json",
                            contentType: "application/json;charset=UTF-8",
                            success: function(result) {

                            },
                            error: function(result) {
                              options.error(result);
                            }
                        });*/
}

function dataGridSaveExecute(){

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
            formData.ThermalCapacity = $('#txt_thermalcapacity').val();//적용안됌

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