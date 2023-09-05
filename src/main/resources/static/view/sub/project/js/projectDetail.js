var rootName = "project";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode === 'true') {
        $('#btn_project_delete').removeClass("visually-hidden");
    } else {
        $('#btn_project_delete').addClass("visually-hidden");
    }

    var projectId = $("#txt_projectId").val();
    if (projectId !== "") {
        // Scenario Data load
        dg_ScenarioLoadData();
    }

    // dg_project Double Click Event
     $("#dg_project").on("dblclick ", "table", function(e) {
        dg_projectModifyExecute();
    });

});

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}

function dg_projectSaveExecute(){

    var url;
    var formData = {};
    formData.name = $('#txt_name').val();
    formData.date = $('#dt_date').val();
    formData.description = $('#txt_description').val();
    formData.startDate = $('#dt_startDate').val();
    formData.endDate = $('#dt_endDate').val();
    formData.createdBy = $('#txt_createdBy').val();
    formData.justification = $('#txt_justification').val();
    formData.doseLimit = $('#txt_doseLimit').val();
    formData.room = $('#txt_room').val();

    formData.editMode = $('#txt_editMode').val();
    // Update
    if (formData.editMode === 'true'){
        url = "/projectUpdate";
        formData.id = $('#txt_projectId').val();
    }
    // Insert
    else {
        url = "/projectInsert";
    }

    $.ajax({
        url : url,
        type: 'POST',
        async: false,
        data: JSON.stringify(formData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "projectDetail?" + "id=" + data.result.id;
            alert('저장이 완료 되었습니다.');

            // 리스트 리로드
            dg_projectReloadExecute();

            // 저장된 데이터가 신규 인 경우
            var newProjectContainer = myLayout.root.getItemsById('projectDetail_newItem');

            if (newProjectContainer !== undefined) {
                var projectId = data.result.id;
                newProjectContainer[0].setTitle('projectDetail_' + projectId);
                newProjectContainer[0].config.id = 'projectDetail_' + projectId;

                $('#btn_project_delete').removeClass("visually-hidden");
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_projectDeleteExecute(){
    if(confirm("해당 아이템을 삭제 하시겠습니까?")){

     var formData = {};
        formData.id = $('#txt_projectId').val();

        $.ajax({
            url : "/projectDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                //location.href = "projectList";
                alert('삭제가 완료 되었습니다.');

                // 해당 패널 닫기
                var projectDetailContainer = myLayout.root.getItemsById('projectDetail_' + $('#txt_projectId').val());
                if (projectDetailContainer !== undefined) {
                    projectDetailContainer[0].close();
                }

                // 리스트 리로드
                dg_projectReloadExecute();
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}

function dg_ScenarioLoadData() {
    $("#dg_project_scenario").kendoGrid({
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
                        url : "/getScenarios?projectId=" + $('#txt_projectId').val(),
                        type: 'GET',
                        async: false,
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
        sortable: true,
        resizable: true,
        pageable: true
    });
}