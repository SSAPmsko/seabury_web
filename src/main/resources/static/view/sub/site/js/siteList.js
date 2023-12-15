var rootName = "Site";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#site").addClass('active');

    // DataGrid Data load
    dg_siteLoadData();

    // DataGrid Double Click Event
    $("#dg_site").on("dblclick", "table", function(e) {
        dg_siteModifyExecute();
    });

    // CheckButton Selected Event
    //$("#dg_site tbody").on("click", ".k-checkbox", onSelected);
});

$("#site_picker").change(function () {
    var type = $("#site_picker option:checked").val();

    switch (type) {
        case "Site":
            addDockItem('site', '사이트 목록', 'site/siteList')
            break;
        case "Plant":
            addDockItem('plant', '플랜트 목록', 'plant/plantList')

            break;
        case "Unit":
            addDockItem('unit', '유닛 목록', 'unit/unitList')

            break;
        case "Room":
            addDockItem('room', '룸 목록', 'room/roomList')

            break;
        default:
            break;
    }
});


function dg_siteCreateExecute(){
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/createDetailProperties",
        method : "GET",
        data:  {"type" : "Site"},
        type : "json",
        async : false,
        contentType : "application/json",
        success : function(result) {
            addDockItem('createDetail_' + 'newItem', 'createDetail_' + 'newItem', 'create/createDetail', result);
        },
        error : function(result) {
            alert("정상 처리에 실패 하였습니다.");
        }
    }).done(function(fragment){

    });
}

function dg_siteModifyExecute(){
    if ($("#dg_site").data("kendoGrid").getSelectedData().length > 0){
        var siteId = $("#dg_site").data("kendoGrid").getSelectedData()[0].id;
        var siteName = $("#dg_site").data("kendoGrid").getSelectedData()[0].name;

        $.ajax({
            url : "/siteDetailProperties",
            data : {"id" : siteId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('siteDetail_' + siteId, siteName, 'site/siteDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
        /*
        $.ajax({
            url : "/siteDetail",
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

function dg_siteLoadData() {
    $("#dg_site").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "name" },
            { field: "operator" },
            { field: "status" },
            { field: "reactorType" },
            { field: "reactorSupplier" },
            /*{ field: "constructionBegan" },
            { field: "commissionDate" },
            { field: "decommissionDate" },
            { field: "thermalCapacity" },*/
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getSiteDetailList",
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
                        name: { type: "string" },
                        operator: { type: "string" },
                        status: { type: "string" },
                        reactorType: { type: "string" },
                        /*reactorSupplier: { type: "string" },*/
                        /*constructionBegan: { type: "string" },
                        commissionDate: { type: "string" },
                        decommissionDate: { type: "string" },
                        thermalCapacity: { type: "string" },*/

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

function dg_siteLoadData() {
    $("#dg_site").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "name" },
            { field: "operator" },
            { field: "status" },
            { field: "reactorType" },
            { field: "reactorSupplier" },
            /*{ field: "constructionBegan" },
            { field: "commissionDate" },
            { field: "decommissionDate" },
            { field: "thermalCapacity" },*/
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getSiteDetailList",
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
                        name: { type: "string" },
                        operator: { type: "string" },
                        status: { type: "string" },
                        reactorType: { type: "string" },
                        /*reactorSupplier: { type: "string" },*/
                        /*constructionBegan: { type: "string" },
                        commissionDate: { type: "string" },
                        decommissionDate: { type: "string" },
                        thermalCapacity: { type: "string" },*/

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

function dg_unitLoadData() {
    $("#dg_unit").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "name" },
            { field: "operator" },
            { field: "status" },
            { field: "reactorType" },
            { field: "reactorSupplier" },
            /*{ field: "constructionBegan" },
            { field: "commissionDate" },
            { field: "decommissionDate" },
            { field: "thermalCapacity" },*/
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getUnitDetailList",
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
                        name: { type: "string" },
                        operator: { type: "string" },
                        status: { type: "string" },
                        reactorType: { type: "string" },
                        reactorSupplier: { type: "string" },
                        /*constructionBegan: { type: "string" },
                        commissionDate: { type: "string" },
                        decommissionDate: { type: "string" },
                        thermalCapacity: { type: "string" },*/

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

