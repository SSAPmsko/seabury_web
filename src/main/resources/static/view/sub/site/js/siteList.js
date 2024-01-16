var rootName = "Site";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#site").addClass('active');
    $(document).ready(function () {
        $("#site_picker").kendoDropDownList({
            change : onChange
        });
    });
    siteLoad();

    function onChange() {
        $("#site_picker").data("kendoGrid").dataSource.read();
    }
});
function siteLoad(){
    var formName = "sitePropertyForm";
    var siteId = $("#txt_siteId").val();
    var uniqueId;
    uniqueId = "_" + formName + "_" + "1";
    var isFirst = replaceFormId('sitePropertyForm', uniqueId);

    if (isFirst === true){
        replaceTabFormId('siteTabForm', uniqueId);
    }


    // DataGrid Data load
    dg_siteLoadData(uniqueId);
    dg_plantLoadData(uniqueId);
    dg_unitLoadData(uniqueId);
    dg_roomLoadData(uniqueId);

    // DataGrid Double Click Event
    $("#dg_site").on("dblclick", "table", function(e) {
        dg_siteModifyExecute();
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

function dg_siteModifyExecute(uniqueId){
    if ($("#dg_site" + uniqueId).data("kendoGrid").getSelectedData().length > 0){
        var siteId = $("#dg_site" + uniqueId).data("kendoGrid").getSelectedData()[0].id;
        var siteName = $("#dg_site" + uniqueId).data("kendoGrid").getSelectedData()[0].name;

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

function dg_siteLoadData(uniqueId) {
    $("#dg_site_site" + uniqueId).kendoGrid({
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
function dg_plantLoadData(uniqueId) {
    $("#dg_site_plant" + uniqueId).kendoGrid({
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

function dg_unitLoadData(uniqueId) {
    $("#dg_site_unit" + uniqueId).kendoGrid({
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

function dg_roomLoadData(uniqueId) {
    $("#dg_site_room" + uniqueId).kendoGrid({
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


function dg_siteReloadExecute(){
    $('#dg_site' +uniqueId ).data('kendoGrid').dataSource.read(); <!--  first reload data source -->
    $('#dg_site' + uniqueId).data('kendoGrid').refresh(); <!--  refresh current UI -->
}

