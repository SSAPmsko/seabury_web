var rootName = "project";

$(document).ready(function(){

    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // DataGrid Data load
    dg_projectLoadData();

    // DataGrid Double Click Event
     $("#dg_project").on("dblclick", "table", function(e) {
        dg_projectModifyExecute();
    });
});

function dg_projectCreateExecute(){
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/projectDetailProperties",
        method : "GET",
        type : "json",
        async : false,
        contentType : "application/json",
        success : function(result) {
            addDockItem('projectDetail_' + 'newItem', 'projectDetail_' + 'newItem', 'project/projectDetail', result);
        },
        error : function(result) {
            alert("정상 처리에 실패 하였습니다.");
        }
    }).done(function(fragment){

    });
}

function dg_projectDeleteExecute(){
    if ($("#dg_project").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dg_project").data("kendoGrid").getSelectedData()[0].id;
            //location.href = "write_del_ok.jsp?num=1";
            return true;
        } else {
            return false;
        }
    }
}

function dg_projectModifyExecute(){
    if ($("#dg_project").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dg_project").data("kendoGrid").getSelectedData()[0].id;

        //location.href = rootName + "Detail?" + "id=" + id;

        $.ajax({
            url : "/projectDetailProperties?id=" + id,
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('projectDetail_' + id, 'projectDetail_' + id, 'project/projectDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}

function dg_projectLoadData(page) {
    $("#dg_project").kendoGrid({
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
                        id: { type: "number" },
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
            //serverPaging: true,
            //serverFiltering: true,
            //serverSorting: true
        },
        selectable: "row",
        scrollable: false,
        filterable: true,
        sortable: true,
        resizable: true,
        pageable: true
    });
}

function dg_projectReloadExecute(){
    $('#dg_project').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_project').data('kendoGrid').refresh(); <!--  refresh current UI -->
}