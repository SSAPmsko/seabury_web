$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#plant").addClass('active');

    // DataGrid Data load
    loadData();

    // DataGrid Double Click Event
     $("#dataGrid").on("dblclick ", "table", function(e) {
        dataGridModifyExecute();
    });

    // CheckButton Selected Event
    //$("#dataGrid tbody").on("click", ".k-checkbox", onSelected);
});

function dataGridCreateExecute(){
    location.href = "plantDetail";
}

function dataGridDeleteExecute(){
    if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var plantId = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
            //location.href = "write_del_ok.jsp?num=1";
            return true;
        } else {
            return false;
        }
    }
}

function dataGridModifyExecute(){
    if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
        var plantId = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
        location.href = "plantDetail?" + "id=" + plantId;
        /*
        $.ajax({
            url : "/plantDetail",
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
        */
    }
}

/*
function onSelected(e) {
    var grid = $("#dataGrid").data("kendoGrid");
    var row = $(e.target).closest("tr");

    if(row.hasClass("k-selected")){
        setTimeout(function(e) {
            var grid = $("#dataGrid").data("kendoGrid");
            grid.clearSelection();
        })
    } else {
        grid.clearSelection();
    };
};

var gridElement = $("#dataGrid");

function resizeGrid() {
    gridElement.data("kendoGrid").resize();
}

$(window).resize(function(){
    resizeGrid();
});
*/

function loadData() {
    $("#dataGrid").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            /*{ field: "defaultplant" },*/
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
                        url : "/getPlantList",
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
                        /*defaultplant: { type: "string" },*/
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

/*
function SubMenuClick(_target){
    $("#page-body").empty();

    $("#page-body").load(_target, function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success") {
            console.log("External content loaded successfully!");
        }
        if (statusTxt == "error") {
            console.log("Error: " + xhr.status + ": " + xhr.statusText);
        }
    });
}
*/
