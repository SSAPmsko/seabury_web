var rootName = "project";

$(document).ready(function(){
    onLoadedProject();
});

function onLoadedProject(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 projectId 추출, unique ID 생성
    var projectId = $("#txt_projectId").val();
    var uniqueId;
    if (projectId !== "" && projectId !== "${id}" && projectId !== undefined) {
        uniqueId = "_" + projectId;
    } else if (projectId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('projectPropertyForm', uniqueId);

    if (isFirst === true){
        var editMode = $("#txt_editMode" + uniqueId).val();
        if (editMode === 'true') {
            $('#btn_project_delete' + uniqueId).removeClass("visually-hidden");
        } else {
            $('#btn_project_delete' + uniqueId).addClass("visually-hidden");
        }

        // Scenario Data load
        dg_ScenarioLoadData(uniqueId);

        /* 프로젝트에서 시나리오 정보 선택시 처리를 할것 인지?
        // dg_project_scenario Double Click Event
        $("#dg_project_scenario" + uniqueId).on("dblclick", "table", function(e) {
            dg_scenarioModifyExecute();
        });
        */

        // button event
        $("#btn_project_save" + uniqueId).on("click", function(e) {
            dg_projectSaveExecute(uniqueId);
        });

        $("#btn_project_delete" + uniqueId).on("click", function(e) {
            dg_projectDeleteExecute(uniqueId);
        });
    }
}

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}

function dg_projectSaveExecute(uniqueId){
    var url;
    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.date = $('#dt_date' + uniqueId).val();
    formData.description = $('#txt_description' + uniqueId).val();
    formData.startDate = $('#dt_startDate' + uniqueId).val();
    formData.endDate = $('#dt_endDate' + uniqueId).val();
    formData.createdBy = $('#txt_createdBy' + uniqueId).val();
    formData.justification = $('#txt_justification' + uniqueId).val();
    formData.doseLimit = $('#txt_doseLimit' + uniqueId).val();
    formData.room = $('#txt_room' + uniqueId).val();

    formData.editMode = $('#txt_editMode' + uniqueId).val();
    // Update
    if (formData.editMode === 'true'){
        url = "/projectUpdate";
        formData.id = $('#txt_projectId' + uniqueId).val();
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
                var oldId = "_newItem";

                $('#txt_editMode' + oldId).val(true);
                $('#btn_project_save' + oldId).off("click");
                $('#btn_project_delete' + oldId).off("click");
                $('#btn_project_delete' + oldId).removeClass("visually-hidden");

                var projectId = data.result.id;
                newProjectContainer[0].setTitle('projectDetail_' + projectId);
                newProjectContainer[0].config.id = 'projectDetail_' + projectId;

                var newId = "_" + projectId;

                $('#txt_projectId' + oldId).val(projectId);

                replaceFormNewId('projectPropertyForm', oldId, newId);

                // button event
                $("#btn_project_save" + newId).on("click", function(e) {
                    dg_projectSaveExecute(newId);
                });

                $("#btn_project_delete" + newId).on("click", function(e) {
                    dg_projectDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_projectDeleteExecute(uniqueId){
    alert(uniqueId);
    if(confirm("해당 아이템을 삭제 하시겠습니까?")){

     var formData = {};
        formData.id = $('#txt_projectId' + uniqueId).val();

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
                var projectDetailContainer = myLayout.root.getItemsById('projectDetail_' + $('#txt_projectId' + uniqueId).val());
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

function dg_ScenarioLoadData(uniqueId) {
    $("#dg_project_scenario" + uniqueId).kendoGrid({
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
                        url : "/getScenarios?projectId=" + $('#txt_projectId' + uniqueId).val(),
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