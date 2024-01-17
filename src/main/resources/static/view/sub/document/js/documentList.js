

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#plant").addClass('active');

    // DataGrid Data load
    dg_documentLoadData();

    // DataGrid Double Click Event
    $("#dg_document").on("dblclick", "table", function(e) {
        dg_documentModifyExecute();
    });

    // CheckButton Selected Event
    //$("#dg_plant tbody").on("click", ".k-checkbox", onSelected);
});



function dg_documentLoadData() {
    $("#dg_document").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { hidden:true, field: "objectId" },
            { field: "name" },
            { field: "scenarioName" },
            { field: "createdBy" },
            {
                field: "createDate",
                format : "{0: yyyy-MM-dd HH:mm:ss}"
            }
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getDocumentList",
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
                        objectId: { type: "string" },
                        name: { type: "string" },
                        scenarioName: { type: "string" },
                        createdBy: { type: "string" },
                        createDate: { type: "date" }
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

function dg_documentModifyExecute(){
    if ($("#dg_document").data("kendoGrid").getSelectedData().length > 0){

        var docUid = $("#dg_document").data("kendoGrid").getSelectedData()[0].uid;
        var dateitem = $("#dg_document").data("kendoGrid").dataSource.getByUid(docUid);

        var docName = dateitem.name;

        $.ajax({
            url : "/documentDetailProperties",
            data : {"id" : dateitem.objectId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('documentDetail_' + docName, docName, 'document/documentDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}