$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#site").addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function historyBack(){
    //window.history.back();
    location.href = "siteList";
}

function dataGridSaveExecute(){

    var url;
    var formData = {};
    formData.type = $('#txt_type').val();
    formData.parent = $('#txt_parent').val();
    formData.name = $('#txt_name').val();
    formData.description = $('#txt_description').val();
    formData.operator = $('#txt_operator').val();
    formData.status = $('#txt_status').val();
    formData.reactortype = $('#txt_reactortype').val();
    formData.reactorsupplier = $('#txt_reactorsupplier').val();
    formData.constructionbegan = $('#dt_constructionbegan').val();
    formData.commissiondate = $('#dt_commissiondate').val();
    formData.decommissiondate = $('#dt_decommissiondate').val();
    formData.thermalcapacity = $('#txt_thermalcapacity').val();

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode == 'true'){
        url = "/siteUpdate";
        formData.id = $('#txt_id').val();
    }    // Insert
    else {
        url = "/siteInsert";
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
            location.href = "siteDetail?" + "id=" + data.result.id;
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
            url : "/siteDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                location.href = "siteList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}