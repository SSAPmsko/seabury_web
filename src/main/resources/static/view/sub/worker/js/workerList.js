var rootName = "worker";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // DataGrid Data load
    dg_workerLoadData();

    // DataGrid Double Click Event
     $("#dg_worker").on("dblclick ", "table", function(e) {
        dg_workerModifyExecute();
    });
});

function dg_workerCreateExecute(){
    location.href = rootName + "Detail";
}

function dg_workerDeleteExecute(){
    if ($("#dg_worker").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dg_worker").data("kendoGrid").getSelectedData()[0].id;
            return true;
        } else {
            return false;
        }
    }
}

function dg_workerModifyExecute(){
    if ($("#dg_worker").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dg_worker").data("kendoGrid").getSelectedData()[0].id;
        var scenarioId = $("#dg_worker").data("kendoGrid").getSelectedData()[0].scenarioId;
        location.href = rootName + "Detail?" + "scenarioId=" + scenarioId + "&" + "id=" + id;
    }
}

function dg_workerLoadData() {
    $("#dg_worker").kendoGrid({
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