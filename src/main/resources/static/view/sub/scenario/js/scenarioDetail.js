var rootName = "scenario";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#" + rootName).addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}

function dataGridSaveExecute(){

    var url;
    var formData = {};
    formData.name = $('#txt_name').val();
    formData.date = $('#dt_date').val();
    formData.lastModified = $('#dt_lastModified').val();
    formData.description = $('#txt_description').val();
    formData.startTime = $('#dt_startTime').val();
    formData.endTime = $('#dt_endTime').val();
    formData.createdBy = $('#txt_createdBy').val();
    formData.modifiedBy = $('#txt_modifiedBy').val();
    formData.status = $('#txt_status').val();

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode == 'true'){
        url = "/scenarioUpdate";
        formData.id = $('#txt_id').val();
    }
    // Insert
    else {
        url = "/scenarioInsert";
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
            location.href = "scenarioDetail?" + "id=" + data.result.id;
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
            url : "/scenarioDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                location.href = "scenarioList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}