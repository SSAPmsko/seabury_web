
$(document).ready(function(){

    scenarioloadData();
    startfunction();

});
function startfunction(){
            var rootName = "scenario";
// 클릭한 위치 active 적용
           $("#" + rootName).addClass('active');

           // DataGrid Double Click Event
            $("#dataGrid").on("", "table", function(e) {
            if(rootName != "equipment"){
               dataGridModifyExecute();
            }
           });

           // CheckButton Selected Event
           //$("#dataGrid tbody").on("click", ".k-checkbox", onSelected);


           function dataGridCreateExecute(){
               location.href = rootName + "Detail";
           }

           function dataGridDeleteExecute(){
               if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
                   if(confirm("해당 아이템을 삭제 하시겠습니까?")){
                       var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
                       //location.href = "write_del_ok.jsp?num=1";
                       return true;
                   } else {
                       return false;
                   }
               }
           }

           function dataGridModifyExecute(){
               if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
                   var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
                                   location.href = rootName + "Detail?" + "id=" + id;
                   /*
                   $.ajax({
                       url : "/projectDetail",
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

}

function scenarioloadData() {
               $("#dataGrid").kendoGrid({
                   columns: [
                       /*{ selectable: true, headerTemplate: '<input type="checkbox" style="visibility:collapse;" />'},*/
                       { field: "id" },
                       { field: "projectId" },
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
                                   url : "/getScenarioList",
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
                   sortable: false,
                   resizable: true,
                   pageable: true
               });
           }

function workpackloadData() {
    var formData = {};
fo
               $("#dataGrid").kendoGrid({
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
                   sortable: false,
                   resizable: true,
                   pageable: true
               });
           }

function projectloadData() {
               $("#dataGrid").kendoGrid({
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
                       //serverPaging: true,
                       //serverFiltering: true,
                       //serverSorting: true
                   },
                   selectable: "row",
                   scrollable: false,
                   filterable: true,
                   sortable: false,
                   resizable: true,
                   pageable: true
               });
           }

function equipmentloadData() {
               $("#dataGrid").kendoGrid({
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

//탭 클릭 이벤트
(() => {

$('#myTab').on('click', "a", function(e) {
/*if($('#dataGrid').data('kendoGrid').columns != null)
{

}*/
           init();
           var rootName = $(this).attr("value");
           // 클릭한 위치 active 적용
           $("#" + rootName).addClass('active');

           // DataGrid Double Click Event
            $("#dataGrid").on("dblclick", "table", function(e) {
            if(rootName != "equipment"){
               dataGridModifyExecute();
            }
           });

           // CheckButton Selected Event
           //$("#dataGrid tbody").on("click", ".k-checkbox", onSelected);


           function dataGridCreateExecute(){
               location.href = rootName + "Detail";
           }

           function dataGridDeleteExecute(){
               if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
                   if(confirm("해당 아이템을 삭제 하시겠습니까?")){
                       var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
                       //location.href = "write_del_ok.jsp?num=1";
                       return true;
                   } else {
                       return false;
                   }
               }
           }

           function dataGridModifyExecute(){
               if ($("#dataGrid").data("kendoGrid").getSelectedData().length > 0){
                   var id = $("#dataGrid").data("kendoGrid").getSelectedData()[0].id;
                   location.href = rootName + "Detail?" + "id=" + id;
                   /*
                   $.ajax({
                       url : "/projectDetail",
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

           function init(){
              $('#dataGrid').data().kendoGrid.destroy();
              $('#dataGrid').empty();
           }



           switch(rootName) {
              case "scenario":
                  scenarioloadData();
                break;
              case "workpack":
                  workpackloadData();
                break;
              case "project":
                  projectloadData();
                break;
              case "equipment":
                  equipmentloadData();
                break;
            default:
                break;
           }

    })
})()

