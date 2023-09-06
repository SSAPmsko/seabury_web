$(document).ready(function () {
    // 클릭한 위치 active 적용
    //$("#plant").addClass('active');

    // DataGrid Data load
    dg_siteLoadData();

    // DataGrid Double Click Event
    $("#dg_site").on("dblclick ", "table", function (e) {
        dg_siteModifyExecute();
    });

    // CheckButton Selected Event
    //$("#dg_plant tbody").on("click", ".k-checkbox", onSelected);
});
$("#site_picker").change(function () {
    var type = $("#site_picker option:checked").val();

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

function dg_siteCreateExecute() {
    location.href = "plantDetail";
}

function dg_siteDeleteExecute() {
    if ($("#dg_site").data("kendoGrid").getSelectedData().length > 0) {
        if (confirm("해당 아이템을 삭제 하시겠습니까?")) {
            var plantId = $("#dg_site").data("kendoGrid").getSelectedData()[0].id;
            //location.href = "write_del_ok.jsp?num=1";
            return true;
        } else {
            return false;
        }
    }
}

function dg_siteModifyExecute() {
    if ($("#dg_site").data("kendoGrid").getSelectedData().length > 0) {
        var siteId = $("#dg_site").data("kendoGrid").getSelectedData()[0].id;


        $.ajax({
            url: "/siteDetailProperties",
            data: {"id": siteId},
            method: "GET",
            type: "json",
            async: false,
            contentType: "application/json",
            success: function (result) {
                addDockItem('siteDetail_' + siteId, 'siteDetail_' + siteId, 'site/siteDetail', result);
                alert("성공")
            },
            error: function (result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function (fragment) {

        });
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

function dg_siteLoadData() {
    $("#dg_site").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            {field: "id"},
            {field: "name"},
            {field: "operator"},
            {field: "status"},
            {field: "reactorType"},
            {field: "reactorSupplier"},
            /*{ field: "constructionBegan" },
            { field: "commissionDate" },
            { field: "decommissionDate" },
            { field: "thermalCapacity" },*/
        ],
        dataSource: {
            transport: {
                read: function (options) {
                    $.ajax({
                        url: "/getSiteDetailList",
                        type: 'POST',
                        async: false,
                        processData: false,
                        dataType: "json",
                        contentType: "application/json;charset=UTF-8",
                        success: function (result) {
                            options.success(result);
                        },
                        error: function (result) {
                            options.error(result);
                        }
                    });
                }
            },
            schema: {
                model: {
                    fields: {
                        id: {type: "string"},
                        name: {type: "string"},
                        operator: {type: "string"},
                        status: {type: "string"},
                        reactorType: {type: "string"},
                        reactorSupplier: {type: "string"},
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
function addDockItem(id, title, path, properties) {

    var findDockItem = myLayout.root.getItemsById(id);

    // 해당 아이템이 있으면,
    if (findDockItem.length > 0) {

        var stackPanel = findDockItem[0].parent;

        stackPanel.setActiveContentItem(findDockItem[0]);
    } else {
        var htmlStr = getHtmlTemplate("/templates/view/sub/" + path + ".html");

        if (properties != undefined) {
            htmlStr = htmlStr.replace(/th:value/g, 'value');

            for (const [k, v] of Object.entries(properties.result)) {
                htmlStr = htmlStr.replace('${' + k + '}', v);
            }
        }

        var newItemConfig = {
            id: id,
            title: title,
            type: 'component',
            componentName: 'goldenLayout',
            componentState: {text: "", htmlStr: htmlStr}
        };

        // dock panel 이 1개 이상일떄,
        if (myLayout.root.contentItems[0].contentItems.length > 0) {
            myLayout.root.contentItems[0].contentItems[0].addChild(newItemConfig);
        } else {
            myLayout.root.contentItems[0].addChild(newItemConfig);
        }
    }
}