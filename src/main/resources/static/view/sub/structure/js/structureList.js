var rootName = "structure";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // DataGrid Data load
    dg_structureLoadData();

    // DataGrid Double Click Event
    $("#dg_structure").on("dblclick ", "table", function(e) {
        dg_structureModifyExecute();
    });
});

function dg_strucCreateExecute(){
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/createDetailProperties",
        method : "GET",
        type : "json",
        async : false,
        contentType : "application/json",
        success : function(result) {
            addDockItem('createDetail_' + 'newItem', 'createDetail_' + 'newItem', 'create/createDetail');
        },
        error : function(result) {
            alert("정상 처리에 실패 하였습니다.");
        }
    }).done(function(fragment){

    });
}
function dg_structureDeleteExecute(){
    if ($("#dg_structure").data("kendoGrid").getSelectedData().length > 0){
        if(confirm("해당 아이템을 삭제 하시겠습니까?")){
            var id = $("#dg_structure").data("kendoGrid").getSelectedData()[0].id;
            return true;
        } else {
            return false;
        }
    }
}

function dg_structureModifyExecute() {
    if ($("#dg_structure").data("kendoGrid").getSelectedData().length > 0){
        var id = $("#dg_structure").data("kendoGrid").getSelectedData()[0].id;

        //location.href = rootName + "Detail?" + "id=" + id;
        alert("확인")

        $.ajax({
            url : "/structureDetailProperties",
            data : {"id" : id},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('structureDetail_' + id, 'structureDetail_' + id, 'structure/structureDetail', result);
                alert(result.name);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }

}

function dg_structureLoadData() {
    $("#dg_structure").kendoGrid({
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
                        url : "/getStructureList",
                        type: 'GET',
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
        },
        selectable: "row",
        scrollable: false,
        filterable: true,
        sortable: true,
        resizable: true,
        pageable: true
    });
}

function addDockItem(id, title, path, properties){

    var findDockItem = myLayout.root.getItemsById(id);

    // 해당 아이템이 있으면,
    if (findDockItem.length > 0) {

        var stackPanel = findDockItem[0].parent;

        stackPanel.setActiveContentItem(findDockItem[0]);
    } else {
        var htmlStr = getHtmlTemplate("/templates/view/sub/" + path + ".html");

        if (properties != undefined){
            htmlStr = htmlStr.replace(/th:value/g,'value');

            for (const [k, v] of Object.entries(properties.result)){

                htmlStr = htmlStr.replace('${' + k + '}' ,v);
            }
        }

        var newItemConfig = {
            id : id,
            title: title,
            type: 'component',
            componentName: 'goldenLayout',
            componentState: { text: "", htmlStr: htmlStr }
        };

        // dock panel 이 1개 이상일떄,
        if (myLayout.root.contentItems[0].contentItems.length > 0){
            myLayout.root.contentItems[0].contentItems[0].addChild( newItemConfig );
        } else {
            myLayout.root.contentItems[0].addChild( newItemConfig );
        }
    }
}