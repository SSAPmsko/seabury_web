var rootName = "source";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // DataGrid Data load
    loadData();

    // DataGrid Double Click Event
     $("#dg_source").on("dblclick", "table", function(e) {
        dg_sourceModifyExecute();
    });
});

function dg_sourceCreateExecute(){
    location.href = rootName + "Detail";
}

function dg_sourceDeleteExecute(){
    if ($("#dg_source").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dg_source").data("kendoGrid").getSelectedData()[0].id;
            return true;
        } else {
            return false;
        }
    }
}

function dg_sourceModifyExecute(){
    if ($("#dg_source").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dg_source").data("kendoGrid").getSelectedData()[0].id;
        var scenarioId = $("#dg_source").data("kendoGrid").getSelectedData()[0].scenarioId;
        var sourceName = $("#dg_source").data("kendoGrid").getSelectedData()[0].name;
        //location.href = rootName + "Detail?" + "scenarioId=" + scenarioId + "&" + "id=" + id;

        $.ajax({
            url : "/sourceDetailProperties?scenarioId=" + scenarioId + "&" + "id=" + id,
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('sourceDetail_' + id, sourceName, 'source/sourceDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}

function loadData() {
    $("#dg_source").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            /*{ field: "projectId" },*/
            /*{ field: "scenarioId" },*/
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
                        url : "/getSourceList",
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
                        /*projectId: { type: "string" },*/
                        /*scenarioId: { type: "string" },*/
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