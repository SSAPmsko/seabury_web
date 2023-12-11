var rootName = "Room";

$(document).ready(function(){
    // DataGrid Data load
    dg_roomLoadData();

    // DataGrid Double Click Event
    $("#dg_room").on("dblclick", "table", function(e) {
        dg_roomModifyExecute();
    });
});


function dg_roomCreateExecute(){
    //location.href = rootName + "Detail";

    $.ajax({
        url : "/createDetailProperties",
        method : "GET",
        data:  {"type" : "Room"},
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


function dg_roomModifyExecute(){
    if ($("#dg_room").data("kendoGrid").getSelectedData().length > 0){
        var roomId = $("#dg_room").data("kendoGrid").getSelectedData()[0].id;
        var roomName = $("#dg_room").data("kendoGrid").getSelectedData()[0].name;


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


function dg_roomLoadData() {
    $("#dg_room").kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
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

