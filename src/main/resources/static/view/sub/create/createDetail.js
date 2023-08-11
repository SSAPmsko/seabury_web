$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#create").addClass('active');

loadData();
});


$("#type_picker").change(function(){

                if($("#type_picker option:checked").val() == "Site"){
                    $("#parent_picker").attr("disabled", true);
                } else if($("#type_picker option:checked").val() == "Plant"){
                    $("#parent_picker").attr("disabled", false);

                } else if($("#type_picker option:checked").val() == "Unit"){
                    $("#parent_picker").attr("disabled", false);

                }
 });

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
                var $parentSelect = $('#parent_picker');
                	var node = {
                	type : item.type, name : item.name ,objectid : item.objectID, parenttype : item.parentType ,parentid : item.parentID
                	};
                    $parentSelect.append(new Option(node.name ,node.objectid , node.type , true));

                });
            }})
}

function dataGridSaveExecute(){
var detailid;
var url;
    var formData = {};
    formData.id = $('#txt_id').val();
    formData.type = $('#type_picker').val();
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
    if (formData.type == "Site")
    {
        url = "/siteInsert";

    }else if(formData.type == "Unit")
    {
        url = "/unitInsert";

    }else if(formData.type == "Plant")
    {
        url = "/plantInsert";
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
         var result = data.Result;
         detailid = data.result.id;
         alert("중지");
        /*if (formData.type == "Site")
            {
                location.href = "plantList";

            }else if(formData.type == "Unit")
            {
                location.href = "plantList";

            }else if(formData.type == "Plant")
            {
                location.href = "plantList";
            }*/

        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });

    var structype;

    if($("#type_picker option:checked").val() == "Site"){

    } else if($("#type_picker option:checked").val() == "Plant"){
         structype = "Site";
    } else if($("#type_picker option:checked").val() == "Unit"){
         structype = "Plant";
    }

    var strucData = {};

    strucData.type = $('#type_picker option:checked').text();
    strucData.parenttype = structype;
    strucData.name = $('#txt_name').val();
    strucData.description = $('#txt_description').val();
    strucData.objectid = detailid;
    strucData.parentid = $('#parent_picker').val();
    $.ajax({
            url : "/structureInsert",
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


}

