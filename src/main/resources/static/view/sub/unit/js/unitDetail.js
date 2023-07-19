$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#unit").addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function historyBack(){
    //window.history.back();
    location.href = "unitList";
}
function loadData() {
    $.ajax({
                            url : "/getUnitList",
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
                        });
}

function dataGridSaveExecute(){

    var url;
    var formData = {};
    formData.name = $('#txt_name').val();
    formData.date = $('#dt_date').val();
    formData.description = $('#txt_description').val();
    formData.startDate = $('#dt_startDate').val();
    formData.endDate = $('#dt_endDate').val();
    formData.createdBy = $('#txt_createdBy').val();
    formData.justification = $('#txt_justification').val();
    formData.doseLimit = $('#txt_doseLimit').val();
    formData.room = $('#txt_room').val();

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
            location.href = "unitDetail?" + "id=" + data.result.id;
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
                location.href = "unitList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}