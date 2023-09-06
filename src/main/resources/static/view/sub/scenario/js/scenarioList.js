var rootName = "scenario";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // DataGrid Data load
    dg_scenarioLoadData();

    // DataGrid Double Click Event
     $("#dg_scenario").on("dblclick", "table", function(e) {
        dg_scenarioModifyExecute();
    });
});

function dg_scenarioCreateExecute(){
    location.href = rootName + "Detail";
}

function dg_scenarioDeleteExecute(){
    if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
            return true;
        } else {
            return false;
        }
    }
}

function dg_scenarioModifyExecute(){
    if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;

        //location.href = rootName + "Detail?" + "id=" + id;

        $.ajax({
            url : "/scenarioDetailProperties?id=" + id,
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('scenarioDetail_' + id, 'scenarioDetail_' + id, 'scenario/scenarioDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}

function dg_scenarioLoadData() {
    $("#dg_scenario").kendoGrid({
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