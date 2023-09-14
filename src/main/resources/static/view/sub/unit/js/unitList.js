var rootName = "Unit";
$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#unit").addClass('active');

    // DataGrid Data load
    dg_unitLoadData();

    // DataGrid Double Click Event
    $("#dg_unit").on("dblclick", "table", function(e) {
        dg_unitModifyExecute();
    });

    // CheckButton Selected Event
    //$("#dg_unit tbody").on("click", ".k-checkbox", onSelected);
});

$("#unit_picker").change(function () {
    var type = $("#unit_picker option:checked").val();

    switch (type) {
        case "Site":
            addDockItem('site', 'siteList', 'site/siteList')
            break;
        case "Plant":
            addDockItem('plant', 'plantList', 'plant/plantList')

            break;
        case "Unit":
            addDockItem('unit', 'unitList', 'unit/unitList')

            break;
        default:
            break;
    }
});


function dg_unitCreateExecute(){
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/createDetailProperties",
        method : "GET",
        data:  {"type" : "Unit"},
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

function dg_unitModifyExecute(){
    if ($("#dg_unit").data("kendoGrid").getSelectedData().length > 0){
        var unitId = $("#dg_unit").data("kendoGrid").getSelectedData()[0].id;


        $.ajax({
            url : "/unitDetailProperties",
            data : {"id" : unitId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                alert(unitId)
                addDockItem('unitDetail_' + unitId, 'unitDetail_' + unitId, 'unit/unitDetail', result);
                alert("성공")
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
        /*
        $.ajax({
            url : "/unitDetail",
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

