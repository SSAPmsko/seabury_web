$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#plant").addClass('active');

    loadData();

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function historyBack(){
    //window.history.back();
    location.href = "plantList";
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
                	$typeSelect.append(new Option(node.type ,node.object_id , true , true));
                	$parentSelect.append(new Option(node.parent_type ,node.parent_id , true , true));
                });

            }})

//plant 리스트
    $.ajax({
                            url : "/getPlantList",
                            type: 'POST',
                            async: false,
                            processData: false,
                            dataType: "json",
                            contentType: "application/json;charset=UTF-8",
                            error: function(data) {
                                            alert(data)},
                            success: function(data) {
                            alert(data.operator)
                            data.forEach(item => {
                            var node = { id : item.id, text : item.operator };
                            $('#txt_operator1').val(node.text);
                            });


                        }
                        });
}

function dataGridSaveExecute(){

    var url;
    var formData = {};
    //formData.type = $('#txt_type1').val();
      //  formData.parent = $('#txt_parent1').val();
        //formData.name = $('#txt_name1').val();
        formData.description = $('#txt_description1').val();
        formData.operator = $('#txt_operator1').val();
        formData.status = $('#txt_status1').val();
        formData.reactortype = $('#txt_reactortype1').val();
        formData.reactorsupplier = $('#txt_reactorsupplier1').val();
        formData.constructionbegan = $('#dt_constructionbegan1').val();
        formData.commissiondate = $('#dt_commissiondate1').val();
        formData.decommissiondate = $('#dt_decommissiondate1').val();
        formData.thermalcapacity = $('#txt_thermalcapacity1').val();

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode == 'true'){
        url = "/plantUpdate";
        formData.id = $('#txt_id').val();
    }
    // Insert
    else {
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
            location.href = "plantDetail?" + "id=" + data.result.id;
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
            url : "/plantDelete",
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