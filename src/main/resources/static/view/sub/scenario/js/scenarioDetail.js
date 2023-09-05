$(document).ready(function(){

    startfunction();
    sourceloadData();
});

function startfunction(){
        var rootName = "scenario";
        // 클릭한 위치 active 적용
        //$("#" + rootName).addClass('active');

        // DataGrid Double Click Event
        $("#dg_scenario").on("dblclick ", "table", function(e) {
           if(rootName != "equipment"){
           dg_scenarioModifyExecute();
        }
        });

        function dg_scenarioCreateExecute(){
            location.href = rootName + "Detail";
        }

        function dg_scenarioDeleteExecute(){
                       if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
                           if(confirm("해당 아이템을 삭제 하시겠습니까?")){
                               var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
                               //location.href = "write_del_ok.jsp?num=1";
                               return true;
                           } else {
                               return false;
                           }
                       }
                   }

        function dg_scenarioModifyExecute(){
        if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
                var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
                var scenarioId = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].scenarioId;
                location.href = rootName + "Detail?" + "scenarioId=" + scenarioId + "&" + "id=" + id;
            }
        }

        document.getElementById("scenariohtml").style.display = "";
        document.getElementById("dg_scenario").style.display = "none";



        var editMode = $("#txt_editMode").val();

        if (editMode == 'true') {
            $('#btn_delete').removeClass("visually-hidden")
        } else if (editMode == 'false') {
            $('#btn_delete').addClass("visually-hidden")
        }
}

$('#myTab').on('click', "a", function(e) {

   init();
   var rootName = $(this).attr("value");
   $("#" + rootName).addClass('active');

      // DataGrid Double Click Event
       $("#dg_scenario").on("dblclick ", "table", function(e) {
       if(rootName != "equipment"){
          dg_scenarioModifyExecute();
       }
      });

      // CheckButton Selected Event
      //$("#dg_scenario tbody").on("click", ".k-checkbox", onSelected);


      function dg_scenarioCreateExecute(){
          location.href = rootName + "Detail";
      }

      function dg_scenarioDeleteExecute(){
          if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
              if(confirm("해당 아이템을 삭제 하시겠습니까?")){
                  var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
                  //location.href = "write_del_ok.jsp?num=1";
                  return true;
              } else {
                  return false;
              }
          }
      }

      function dg_scenarioModifyExecute(){
          if ($("#dg_scenario").data("kendoGrid").getSelectedData().length > 0){
              var id = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].id;
              var scenarioId = $("#dg_scenario").data("kendoGrid").getSelectedData()[0].scenarioId;
              location.href = rootName + "Detail?" + "scenarioId=" + scenarioId + "&" + "id=" + id;
          }
      }


      function init(){
         $('#dg_scenario').data().kendoGrid.destroy();
         $('#dg_scenario').empty();
      }



      switch(rootName) {
         case "workpack":
             workpackloadData();
           break;
         case "source":
             sourceloadData();
           break;
         case "equipment":
             equipmentloadData();
           break;
         case "shield":
             shieldloadData();
           break;
         case "worker":
             workerloadData();
           break;

       default:
           break;
      }


     if(rootName == "scenario"){
       document.getElementById("scenariohtml").style.display = "";
       document.getElementById("dg_scenario").style.display = "none";
     }
     else{
      document.getElementById("scenariohtml").style.display = "none";
      document.getElementById("dg_scenario").style.display = "";
     }
});

function GridSaveExecute(){

    var url;
    var formData = {};
    formData.name = $('#txt_name').val();
    formData.date = $('#dt_date').val();
    formData.lastModified = $('#dt_lastModified').val();
    formData.description = $('#txt_description').val();
    formData.startTime = $('#dt_startTime').val();
    formData.endTime = $('#dt_endTime').val();
    formData.createdBy = $('#txt_createdBy').val();
    formData.modifiedBy = $('#txt_modifiedBy').val();
    formData.status = $('#txt_status').val();

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode == 'true'){
        url = "/scenarioUpdate";
        formData.id = $('#txt_id').val();
    }
    // Insert
    else {
        url = "/scenarioInsert";
    }

    $.ajax({
        url : url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            location.href = "scenarioDetail?" + "id=" + data.result.id;
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function GridDeleteExecute(){
    if(confirm("해당 아이템을 삭제 하시겠습니까?")){

     var formData = {};
        formData.id = $('#txt_id').val();

        $.ajax({
            url : "/scenarioDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                location.href = "scenarioList";
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}

function workpackloadData() {

    $("#dg_scenario").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
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
                        url : "/getWorkpackList",
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
                        projectId: { type: "string" },
                        scenarioId: { type: "string" },
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

function equipmentloadData() {

               $("#dg_scenario").kendoGrid({
                   columns: [
                       /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
                       { field: "id" },
                       { field: "projectId" },
                       { field: "scenarioId" },
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
                                   url : "/getEquipmentList",
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
                                   projectId: { type: "string" },
                                   scenarioId: { type: "string" },
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
                   sortable: false,
                   resizable: true,
                   pageable: true
               });
           }

function sourceloadData() {

    $("#dg_scenario").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
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
                        url : "/getSourceList",
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
                        projectId: { type: "string" },
                        scenarioId: { type: "string" },
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

function shieldloadData() {

    $("#dg_scenario").kendoGrid({
        columns: [
            /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
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
                        url : "/getShieldList",
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
                        projectId: { type: "string" },
                        scenarioId: { type: "string" },
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

function workerloadData() {

    $("#dg_scenario").kendoGrid({
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