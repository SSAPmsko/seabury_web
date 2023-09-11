var rootName = "scenario";

$(document).ready(function(){
    onLoadedScenario();
});

function onLoadedScenario(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 scenarioId 추출, unique ID 생성
    var scenarioId = $("#txt_scenarioId").val();
    var uniqueId;
    if (scenarioId !== "" && scenarioId !== "${id}" && scenarioId !== undefined) {
        uniqueId = "_" + scenarioId;
    } else if (scenarioId === "") {
        // create 인 경우
        uniqueId = "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('scenarioPropertyForm', uniqueId);

    if (isFirst === true){
        // Tab Control 연결 속성이 Unique 한 ID로 변경됨. 별도의 function 으로 관리
        replaceTabFormId('scenarioTabForm', uniqueId);

        var editMode = $("#txt_editMode" + uniqueId).val();
        if (editMode === 'true') {
            $('#btn_scenario_delete' + uniqueId).removeClass("visually-hidden");
        } else {
            $('#btn_scenario_delete' + uniqueId).addClass("visually-hidden");
        }

        // Scenario Detail Data load
        dg_scenario_workpackLoadData(uniqueId);
        dg_scenario_equipmentLoadData(uniqueId);
        dg_scenario_workerLoadData(uniqueId);
        dg_scenario_sourceLoadData(uniqueId);
        dg_scenario_shieldLoadData(uniqueId);

        // button event
        $("#btn_scenario_save" + uniqueId).on("click", function(e) {
            dg_scenarioSaveExecute(uniqueId);
        });

        $("#btn_scenario_delete" + uniqueId).on("click", function(e) {
            dg_scenarioDeleteExecute(uniqueId);
        });
    }
}

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}


function dg_scenarioSaveExecute(uniqueId){
    var url;
    var formData = {};
    formData.name = $('#txt_name' + uniqueId).val();
    formData.description = $('#txt_description' + uniqueId).val();
    formData.date = $('#dt_date' + uniqueId).val();
    formData.lastModified = $('#dt_lastModified' + uniqueId).val();
    formData.createdBy = $('#txt_createdBy' + uniqueId).val();
    formData.modifiedBy = $('#txt_modifiedBy' + uniqueId).val();
    formData.status = $('#txt_status' + uniqueId).val();
    formData.dt_startTime = $('#dt_startTime' + uniqueId).val();
    formData.dt_endTime = $('#dt_endTime' + uniqueId).val();

    formData.editMode = $('#txt_editMode' + uniqueId).val();
    // Update
    if (formData.editMode === 'true'){
        url = "/scenarioUpdate";
        formData.id = $('#txt_scenarioId' + uniqueId).val();
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
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success : function(data) {
            //location.href = "scenarioDetail?" + "id=" + data.result.id;
            alert('저장이 완료 되었습니다.');

            // 리스트 리로드
            dg_scenarioReloadExecute();

            // 저장된 데이터가 신규 인 경우
            var newScenarioContainer = myLayout.root.getItemsById('scenarioDetail_newItem');

            if (newScenarioContainer !== undefined) {
                var oldId = "_newItem";

                $('#txt_editMode' + oldId).val(true);
                $('#btn_scenario_save' + oldId).off("click");
                $('#btn_scenario_delete' + oldId).off("click");
                $('#btn_scenario_delete' + oldId).removeClass("visually-hidden");

                var scenarioId = data.result.id;
                newScenarioContainer[0].setTitle('scenarioDetail_' + scenarioId);
                newScenarioContainer[0].config.id = 'scenarioDetail_' + scenarioId;

                var newId = "_" + scenarioId;

                $('#txt_scenarioId' + oldId).val(scenarioId);

                replaceFormNewId('scenarioPropertyForm', oldId, newId);
                replaceTabFormNewId('scenarioTabForm', oldId, newId);

                // button event
                $("#btn_scenario_save" + newId).on("click", function(e) {
                    dg_scenarioSaveExecute(newId);
                });

                $("#btn_scenario_delete" + newId).on("click", function(e) {
                    dg_scenarioDeleteExecute(newId);
                });
            }
        },
        error : function(data) {
            alert("정상 처리에 실패 하였습니다.");
        }
    });
}

function dg_scenarioDeleteExecute(uniqueId){
    if(confirm("해당 아이템을 삭제 하시겠습니까?")){

        var formData = {};
        formData.id = $('#txt_scenarioId' + uniqueId).val();

        $.ajax({
            url : "/scenarioDelete",
            type: 'DELETE',
            async: false,
            data: JSON.stringify(formData),
            processData: false,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success : function(data) {
                //location.href = "scenarioList";
                alert('삭제가 완료 되었습니다.');

                // 해당 패널 닫기
                var scenarioDetailContainer = myLayout.root.getItemsById('scenarioDetail_' + $('#txt_scenarioId' + uniqueId).val());
                if (scenarioDetailContainer !== undefined) {
                    scenarioDetailContainer[0].close();
                }

                // 리스트 리로드
                dg_scenarioReloadExecute();
            },
            error : function(data) {
                alert("정상 처리에 실패 하였습니다.");
            }
        });
    }
}

function dg_scenario_workpackLoadData(uniqueId) {
    $("#dg_scenario_workpack" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
            { field: "name" },
            { field: "description" }
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getWorkpackList?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
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
                        id: { type: "number" },
                        projectId: { type: "number" },
                        scenarioId: { type: "number" },
                        name: { type: "string" },
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

function dg_scenario_equipmentLoadData(uniqueId) {
    $("#dg_scenario_equipment" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
            { field: "name" },
            { field: "description" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getEquipmentList?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
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
                        id: { type: "number" },
                        projectId: { type: "number" },
                        scenarioId: { type: "number" },
                        name: { type: "string" },
                        description: { type: "string" },
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

function dg_scenario_sourceLoadData(uniqueId) {
    $("#dg_scenario_source" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
            { field: "name" },
            { field: "description" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getSourceList?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
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
                        id: { type: "number" },
                        projectId: { type: "number" },
                        scenarioId: { type: "number" },
                        name: { type: "string" },
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

function dg_scenario_shieldLoadData(uniqueId) {
    $("#dg_scenario_shield" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "projectId" },
            { field: "scenarioId" },
            { field: "name" },
            { field: "description" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getShieldList?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
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
                        id: { type: "number" },
                        projectId: { type: "number" },
                        scenarioId: { type: "number" },
                        name: { type: "string" },
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

function dg_scenario_workerLoadData(uniqueId) {
    $("#dg_scenario_worker" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "description" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/getWorkerList?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
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
                        id: { type: "number" },
                        name: { type: "string" },
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