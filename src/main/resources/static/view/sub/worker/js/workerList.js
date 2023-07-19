var rootName = "worker";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#" + rootName).addClass('active');

    // DataGrid Data load
    loadData();

    // DataGrid Double Click Event
     $("#dataGrid").on("dblclick ", "table", function(e) {
        dataGridModifyExecute();
    });
});

function dataGridCreateExecute(){
    location.href = rootName + "Detail";
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
        var scenarioId = $("#dataGrid").data("kendoGrid").getSelectedData()[0].scenarioId;
        location.href = rootName + "Detail?" + "scenarioId=" + scenarioId + "&" + "id=" + id;
    }
}

function loadData() {
    $("#dataGrid").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            /*{ field: "defaultProject" },*/
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
                        url : "/getProjectList",
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
                        /*defaultProject: { type: "string" },*/
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