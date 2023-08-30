var rootName = "project";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
        // DataGrid Data load
        loadData();

        // DataGrid Double Click Event
         $("#dataGrid").on("dblclick ", "table", function(e) {
            dataGridModifyExecute();
        });

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
        url = "/projectUpdate";
        formData.id = $('#txt_id').val();
    }
    // Insert
    else {
        url = "/projectInsert";
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
            location.href = "projectDetail?" + "id=" + data.result.id;
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
            url : "/projectDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                location.href = "projectList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}



function dataGridCreateExecute(){
    location.href = "scenario" + "Detail";
}

function dataGridDeleteExecute(){
    if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
            return true;
        } else {
            return false;
        }
    }
}

function dataGridModifyExecute(){
    if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
        location.href = "scenario" + "Detail?" + "id=" + id;
    }
}

function loadData() {
    $("#dataGrid").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "projectId" },
            /*{ field: "date" },*/
            { field: "name" },
            { field: "description" },
            /*{ field: "startDate" },
            { field: "endDate" },
            { field: "createdBy" },
            { field: "justification" },
            { field: "doseLimit" },
            { field: "room" },*/
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getScenarioList",
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
            },
            schema: {
                model: {
                    fields: {
                        id: { type: "string" },
                        projectId: { type: "string" },
                        /*date: { type: "string" },*/
                        name: { type: "string" },
                        description: { type: "string" },
                        /*startDate: { type: "string" },
                        endDate: { type: "string" },
                        createdBy: { type: "string" },
                        justification: { type: "string" },
                        doseLimit: { type: "string" },
                        room: { type: "string" },*/
                    }
                }
            },
            pageSize: 10,
        },
        selectable: "row",
        scrollable: false,
        filterable: true,
        sortable: true,
        resizable: true,
        pageable: true
    });
}