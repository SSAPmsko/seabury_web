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
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/scenarioDetailProperties",
        method : "GET",
        type : "json",
        async : false,
        contentType : "application/json",
        success : function(result) {
            addDockItem('scenarioDetail_' + 'newItem', 'scenarioDetail_' + 'newItem', 'scenario/scenarioDetail', result);
        },
        error : function(result) {
            alert("정상 처리에 실패 하였습니다.");
        }
    }).done(function(fragment){

    });
}

function dg_scenarioCompareExecute(){
    if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0) {
        var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
        var projectId = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].projectId;
        var scenarioName = "Compare_" + $("#dg_scenario").data("kendoGrid").getSelectedData()[0].name;

        $.ajax({
            url : "/scenarioCompare?projectId=" + projectId + "&id=" + id,
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('scenarioCompare_' + id, scenarioName, 'scenario/scenarioCompare', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    } else{

    }
}

function dg_scenarioDeleteExecute(){
    if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0) {
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
        var projectId = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].projectId;
        var scenarioName = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].name;

        //location.href = rootName + "Detail?" + "id=" + id;

        $.ajax({
            url : "/scenarioDetailProperties?projectId=" + projectId + "&id=" + id,
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('scenarioDetail_' + id, scenarioName, 'scenario/scenarioDetail', result);
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
            { field: "id" },
            { field: "projectId" },
            { field: "name" },
            { field: "status"},
            { field: "description" },
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
                        id: { type: "number" },
                        projectId: { type: "number" },
                        name: { type: "string" },
                        status: { type: "string" },
                        description: { type: "string" },
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

function dg_scenarioReloadExecute(){
    $('#dg_scenario').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_scenario').data('kendoGrid').refresh(); <!--  refresh current UI -->
}

function popupScenarioCompare(){
    var wnd = $("#popup_compare_scenario");
    var dialog = wnd.kendoWindow({
        anchor: $("#btn_compare"),
        visible: false,
        modal: true,
        resizable: false,
        draggable: false,
        width: "400px",
        height: "300px",
        title: "Compare Scenario",
        open: function(e) {
            $.ajax({
                url : location.origin + "/getScenarioList",
                method : "POST",
                type : "json",
                async : false,
                contentType : "application/json",
                success : function(result) {

                },
                error : function(result) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            }).done(function(fragment){

            });
        }
    }).data("kendoWindow").center().open();
}