var rootName = "Plant";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#site").addClass('active');
    plantLoadData();

    plantLoad();

    function onChange() {
        $("#plant_picker").data("kendoGrid").dataSource.read();
    }
});
function plantLoad(){
    var formName = "plantPropertyForm";
    var siteId = $("#txt_siteId").val();
    var uniqueId;
    uniqueId = "_" + formName + "_" + "1";
    var isFirst = replaceFormId('plantPropertyForm', uniqueId);

    if (isFirst === true){
        replaceTabFormId('plantTabForm', uniqueId);
    }


    // DataGrid Data load
    dg_siteLoadData(uniqueId);
    dg_plantLoadData(uniqueId);
    dg_unitLoadData(uniqueId);
    dg_roomLoadData(uniqueId);


    // DataGrid Double Click Event
    $("#dg_plant_site" + uniqueId).on("dblclick", "table", function(e) {
        dg_siteModifyExecute(uniqueId);
    });
    $("#dg_plant_plant" + uniqueId).on("dblclick", "table", function(e) {
        dg_plantModifyExecute(uniqueId);
    });
    $("#dg_plant_unit" + uniqueId).on("dblclick", "table", function(e) {
        dg_unitModifyExecute(uniqueId);
    });
    $("#dg_plant_room" + uniqueId).on("dblclick", "table", function(e) {
        dg_roomModifyExecute(uniqueId);
    });

    // CheckButton Selected Event
    //$("#dg_site tbody").on("click", ".k-checkbox", onSelected);
}
function replaceTabFormId(formName, uniqueId) {
    var form = document.getElementById(formName);

    if (form !== undefined){
        var formList = $("[form=" + formName + "]");

        for(var i= 0; i <formList.length; i++){
            formList[i].id = formList[i].id + uniqueId;

            switch (formList [i].tagName){
                case "A":
                    formList[i].attributes['data-bs-target'].value = formList[i].attributes['data-bs-target'].value + uniqueId;
                    formList[i].attributes['aria-controls'].value = formList[i].attributes['aria-controls'].value + uniqueId;
                    break;
                case "DIV":
                    formList[i].attributes['aria-labelledby'].value = formList[i].attributes['aria-labelledby'].value + uniqueId;
                    break;
                default:
                    break;
            }
        }

        form.id = formName + uniqueId;

        return true;
    } else{
        return false;
    }
}

function replaceTabFormNewId(formName, srcId, distId) {
    var form = document.getElementById(formName + srcId);

    if (form !== undefined){
        var formList = $("[form="+ formName + srcId+ "]");

        for(var i= 0; i < formList.length; i++){
            formList[i].id = formList[i].id.replace(srcId, distId);
            formList[i].attributes['form'].value = formList[i].attributes['form'].value.replace(srcId, distId);
        }

        form.id = form.id.replace(srcId, distId);
    } else{
    }
}

$("#plant_picker").change(function () {
    var type = $("#plant_picker option:checked").val();

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


function dg_plantCreateExecute(){
    //location.href = rootName + "Detail";
    var createtype = $("a.nav-link.active").text();
    if (createtype != "Room")
    {
        $.ajax({
            url : "/createDetailProperties",
            method : "GET",
            data:  {"type" : createtype},
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
    else {
        $.ajax({
            url : "/createDetailProperties",
            method : "GET",
            data:  {"type" : "Room"},
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('roomcreateDetail_' + 'newItem', 'roomcreateDetail_' + 'newItem', 'create/roomcreateDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}
function dg_siteModifyExecute(uniqueId){
    if ($("#dg_plant_site" + uniqueId).data("kendoGrid").getSelectedData().length > 0){
        var siteId = $("#dg_plant_site" + uniqueId).data("kendoGrid").getSelectedData()[0].id;
        var siteName = $("#dg_plant_site" + uniqueId).data("kendoGrid").getSelectedData()[0].name;

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
    }
}
function dg_plantModifyExecute(uniqueId){
    if ($("#dg_plant_plant" + uniqueId).data("kendoGrid").getSelectedData().length > 0){
        var plantId = $("#dg_plant_plant" + uniqueId).data("kendoGrid").getSelectedData()[0].id;
        var plantName = $("#dg_plant_plant" + uniqueId).data("kendoGrid").getSelectedData()[0].name;

        $.ajax({
            url : "/plantDetailProperties",
            data : {"id" : plantId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('plantDetail_' + plantId, plantName, 'plant/plantDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}
function dg_unitModifyExecute(uniqueId){
    if ($("#dg_plant_unit" + uniqueId).data("kendoGrid").getSelectedData().length > 0){
        var unitId = $("#dg_plant_unit" + uniqueId).data("kendoGrid").getSelectedData()[0].id;
        var unitName = $("#dg_plant_unit" + uniqueId).data("kendoGrid").getSelectedData()[0].name;

        $.ajax({
            url : "/unitDetailProperties",
            data : {"id" : unitId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('unitDetail_' + unitId, unitName, 'unit/unitDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}
function dg_roomModifyExecute(uniqueId){
    if ($("#dg_plant_room" + uniqueId).data("kendoGrid").getSelectedData().length > 0){
        var roomId = $("#dg_plant_room" + uniqueId).data("kendoGrid").getSelectedData()[0].id;
        var roomName = $("#dg_plant_room" + uniqueId).data("kendoGrid").getSelectedData()[0].name;

        $.ajax({
            url : "/roomDetailProperties",
            data : {"id" : roomId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('roomDetail_' + roomId, roomName, 'room/roomDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}

function dg_siteLoadData(uniqueId) {
    $("#dg_plant_site" + uniqueId).kendoGrid({
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
                            const tempList = result.sort((a,b) => {
                                if(a.name > b.name) return 1;
                                if(a.name < b.name) return -1;
                                return 0;
                            });
                            console.log(tempList)
                            options.success(tempList);
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
function dg_plantLoadData(uniqueId) {
    $("#dg_plant_plant" + uniqueId).kendoGrid({
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
                        url : "/getPlantDetailList",
                        type: 'POST',
                        async: false,
                        processData: false,
                        dataType: "json",
                        contentType: "application/json;charset=UTF-8",
                        success: function(result) {
                            const tempList = result.sort((a,b) => {
                                if(a.name > b.name) return 1;
                                if(a.name < b.name) return -1;
                                return 0;
                            });
                            console.log(tempList)
                            options.success(tempList);
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
function dg_unitLoadData(uniqueId) {
    $("#dg_plant_unit" + uniqueId).kendoGrid({
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
                            const tempList = result.sort((a,b) => {
                                if(a.name > b.name) return 1;
                                if(a.name < b.name) return -1;
                                return 0;
                            });
                            console.log(tempList)
                            options.success(tempList);
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
function dg_roomLoadData(uniqueId) {
    $("#dg_plant_room" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "operator" },
            { field: "description" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getRoomDetailList",
                        type: 'POST',
                        async: false,
                        processData: false,
                        dataType: "json",
                        contentType: "application/json;charset=UTF-8",
                        success: function(result) {
                            const tempList = result.sort((a,b) => {
                                if(a.name > b.name) return 1;
                                if(a.name < b.name) return -1;
                                return 0;
                            });
                            console.log(tempList)
                            options.success(tempList);
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
function plantLoadData() {
    $("#dg_plant_plant").kendoGrid({
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
                        url : "/getPlantDetailList",
                        type: 'POST',
                        async: false,
                        processData: false,
                        dataType: "json",
                        contentType: "application/json;charset=UTF-8",
                        success: function(result) {
                            const tempList = result.sort((a,b) => {
                                if(a.name > b.name) return 1;
                                if(a.name < b.name) return -1;
                                return 0;
                            });
                            console.log(tempList)
                            options.success(tempList);
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


function dg_plantReloadExecute(){
    $('#dg_plant_site').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_plant_site').data('kendoGrid').refresh(); <!--  refresh current UI -->
    $('#dg_plant_plant').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_plant_plant').data('kendoGrid').refresh(); <!--  refresh current UI -->
    $('#dg_plant_unit').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_plant_unit').data('kendoGrid').refresh(); <!--  refresh current UI -->
    $('#dg_plant_room').data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_plant_room').data('kendoGrid').refresh(); <!--  refresh current UI -->
}

