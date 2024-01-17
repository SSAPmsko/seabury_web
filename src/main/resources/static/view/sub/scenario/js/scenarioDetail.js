var rootName = "scenario";

var sample_colors = ["#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac"];

var dosimeterInfo;
var workersInfo;
var equipmentInfo;
var workPlanInfo;
var radiologicalConditionOnStart;
var radiologicalConditionOnEnd;

$(document).ready(function(){
    onLoadedScenario();
});

function onLoadedScenario(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 scenarioId 추출, unique ID 생성
    var formName = "scenarioPropertyForm";
    var scenarioId = $("#txt_scenarioId").val();
    var uniqueId;
    if (scenarioId !== "" && scenarioId !== "${id}" && scenarioId !== undefined) {
        uniqueId = "_" + formName + "_" + scenarioId;
    } else if (scenarioId === "") {
        // create 인 경우
        uniqueId = "_" + formName + "_newItem";
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

        var dosimeterInfoStr = $("#txt_dosimeterInfo" + uniqueId).val();
        dosimeterInfo = JSON.parse(dosimeterInfoStr.replace(/'/g, '"'));

        var workersInfoStr = $("#txt_workersInfo" + uniqueId).val();
        workersInfo = JSON.parse(workersInfoStr.replace(/'/g, '"'));

        var equipmentInfoStr = $("#txt_equipmentInfo" + uniqueId).val();
        equipmentInfo = JSON.parse(equipmentInfoStr.replace(/'/g, '"'));

        var workPlanInfoStr = $("#txt_workPlanInfo" + uniqueId).val();
        workPlanInfo = JSON.parse(workPlanInfoStr.replace(/'/g, '"'));

        var radiologicalConditionOnStartStr = $("#txt_radiologicalConditionOnStart" + uniqueId).val();
        radiologicalConditionOnStart = JSON.parse(radiologicalConditionOnStartStr.replace(/'/g, '"'));

        var radiologicalConditionOnEndStr = $("#txt_radiologicalConditionOnEnd" + uniqueId).val();
        radiologicalConditionOnEnd = JSON.parse(radiologicalConditionOnEndStr.replace(/'/g, '"'));

        // Scenario Detail Data load
        dg_scenario_workpackLoadData(uniqueId);
        dg_scenario_equipmentLoadData(uniqueId);
        dg_scenario_workerLoadData(uniqueId);
        dg_scenario_start_sourceLoadData(uniqueId);
        dg_scenario_start_shieldLoadData(uniqueId);
        dg_scenario_end_sourceLoadData(uniqueId);
        dg_scenario_end_shieldLoadData(uniqueId);
        //dg_scenario_reportLoadData(uniqueId);
        dg_scenario_fileListLoadData(uniqueId);

        chart_accumulated_collective_doseLoadData(uniqueId);
        chart_workers_dose_rateLoadData(uniqueId);
        chart_workers_accumulated_doseLoadData(uniqueId);
        chart_maximum_individual_dose_rateLoadData(uniqueId);
        chart_minimum_individual_dose_rateLoadData(uniqueId);
        chart_accumulated_collective_dose_per_stepLoadData(uniqueId);

        // button event
        $("#btn_scenario_save" + uniqueId).on("click", function(e) {
            dg_scenarioSaveExecute(uniqueId);
        });

        $("#btn_scenario_delete" + uniqueId).on("click", function(e) {
            dg_scenarioDeleteExecute(uniqueId);
        });
        $("#btn_planner_open" + uniqueId).on("click", function(e) {
            dg_pullscreenplanner(uniqueId);
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
                var formName = "scenarioPropertyForm";
                var oldId = "_" + formName + "_newItem";

                $("#txt_editMode" + oldId).val(true);
                $("#btn_scenario_save" + oldId).off("click");
                $("#btn_scenario_delete" + oldId).off("click");
                $("#btn_scenario_delete" + oldId).removeClass("visually-hidden");

                var scenarioId = data.result.id;
                newScenarioContainer[0].setTitle('scenarioDetail_' + scenarioId);
                newScenarioContainer[0].config.id = 'scenarioDetail_' + scenarioId;

                var newId = "_" + formName + "_" + scenarioId;

                $("#txt_scenarioId" + oldId).val(scenarioId);

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
function dg_pullscreenplanner(uniqueId){
    addDockItem('' + uniqueId, "플래너 스크린", 'planner/plannerindex');
}

function dg_scenario_workpackLoadData(uniqueId) {
    $("#dg_scenario_workpack" + uniqueId).kendoGrid({
        columns: [
            { field: "name" },
            { field: "startTime" },
            { field: "duration" },
            { field: "workers"},
        ],
        dataSource: {
            data: workPlanInfo.WorkStepInfo,
            /*transport: {
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
            },*/
            schema: {
                model: {
                    fields: {
                        name: { type: "string" },
                        startTime: { type: "string" },
                        duration: { type: "string"},
                        workers: {type: "string"},
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
        pageable: false
    });
}

function dg_scenario_equipmentLoadData(uniqueId) {
    $("#dg_scenario_equipment" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "description" },
        ],
        dataSource: {
            data: equipmentInfo.Equipment,
            /*transport: {
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
            },*/
            schema: {
                model: {
                    fields: {
                        id: { type: "string" },
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
        pageable: false
    });
}

function dg_scenario_start_sourceLoadData(uniqueId) {
    $("#dg_scenario_start_source" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
        ],
        dataSource: {
            data:radiologicalConditionOnStart.Source,
            /*transport: {
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
            },*/
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
        pageable: false
    });
}

function dg_scenario_start_shieldLoadData(uniqueId) {
    $("#dg_scenario_start_shield" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "material" },
        ],
        dataSource: {
            data:radiologicalConditionOnStart.Shield,
            /*transport: {
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
            },*/
            schema: {
                model: {
                    fields: {
                        id: { type: "string" },
                        name: { type: "string" },
                        material: { type: "string" },
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
        pageable: false
    });
}

function dg_scenario_end_sourceLoadData(uniqueId) {
    $("#dg_scenario_end_source" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
        ],
        dataSource: {
            data:radiologicalConditionOnEnd.Source,
            /*transport: {
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
            },*/
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
        pageable: false
    });
}

function dg_scenario_end_shieldLoadData(uniqueId) {
    $("#dg_scenario_end_shield" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "material" },
        ],
        dataSource: {
            data:radiologicalConditionOnEnd.Shield,
            /*transport: {
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
            },*/
            schema: {
                model: {
                    fields: {
                        id: { type: "string" },
                        name: { type: "string" },
                        material: { type: "string" },
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
        pageable: false
    });
}

function dg_scenario_workerLoadData(uniqueId) {
    $("#dg_scenario_worker" + uniqueId).kendoGrid({
        columns: [
            { field: "id" },
            { field: "name" },
            { field: "protection"},
        ],
        dataSource: {
            data: workersInfo.Worker,
            /*transport: {
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
            },*/
            schema: {
                model: {
                    fields: {
                        id: { type: "string" },
                        name: { type: "string" },
                        protection: { type: "string" },
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
        pageable: false
    });
}

function dg_scenario_reportLoadData(uniqueId){
    $.ajax({
        url : "/getReport?scenarioId=" + $('#txt_scenarioId' + uniqueId).val(),
        type: 'GET',
        async: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function(result) {
            alert(JSON.stringify(result));
        },
        error: function(result) {

        }
    });
}

function dg_scenario_fileListLoadData(uniqueId){
    $("#dg_scenario_fileList" + uniqueId).kendoGrid({
        columns: [
/*            { field: "thumbnailUrl", title: "Thumbnail",
                template: "<img></img>",
            },*/
           /* {
                field: "objectId", title: "ObjectId",
                template: "<div class='product-photo' style='background-image: url(../../../static/img/alfresco/doclib.png); background-repeat: no-repeat; background-position: center center; height: 80px; width: 100%;'></div>"
            },*/
            { field: "name" },
            /*{ field: "description" },
            {
                filed: "download", title: "Download",
                template: "<div> " +
                    "<button class='k-button k-button-md k-rounded-md k-button-solid k-button-solid-base ms-' type='button' form='scenarioPropertyForm' style='height: 30px;' id='btn_scenario_download' onclick='alert(\"download\")'>" +
                    "<span class='k-button-icon k-icon k-i-paperclip-alt'></span>" +
                    "<span class='k-button-text'>Download</span></button></div>",
            },*/
            { field: "createdBy" },
            { field: "createDate" },
        ],
        dataSource: {
            transport: {
                read: function(options){
                    $.ajax({
                        url : "/scenarioDetailDocuments?id=" + $('#txt_scenarioId' + uniqueId).val(),
                        type: 'GET',
                        async: false,
                        dataType: "json",
                        contentType: "application/json;charset=UTF-8",
                        success: function(result) {
                            console.log(result);
                            options.success(result.Documents);
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
                        /*thumbnailUrl:{ type: "string"},*/
                        /*objectId: { type: "string" },*/
                        name: { type: "string" },
                        createdBy: { type: "string" },
                        createDate: { type: "string" },
                    }
                }
            },
            pageSize: 5,
        },
        selectable: "row",
        scrollable: false,
        filterable: true,
        sortable: true,
        resizable: true,
        pageable: false
    });
}

function chart_accumulated_collective_doseLoadData(uniqueId){
    const labels = [];
    const datasets = [];

    if (dosimeterInfo !== undefined && dosimeterInfo.length > 0){
        const item = dosimeterInfo[0];

        let dataArray = [];
        let totalSumData = 0.0;
        for (let i in item.times) {
            if (i == 0){
                for (let j in item.times) {
                    labels.push(item.times[j]);
                }
            }

            let sumData = 0.0;
            for (let j in dosimeterInfo){
                const data = dosimeterInfo[j].doseRates[i];
                sumData += data;
            }
            totalSumData += sumData;
            dataArray.push(totalSumData);
        }

        const dataset = {
            label: "Collective accumulated dose",
            data: dataArray,
            borderColor: sample_colors[i],
            fill: false,
            borderWidth: 1,
            pointRadius: 0,
            pointHitRadius:10,
        };
        datasets.push(dataset);
    }

    // Graphs
    var div_chart = document.getElementById("chart_accumulated_collective_dose" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'line',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Accumulated collective dose'
                },
                legend: {
                    display: true,
                    position: 'bottom',
                },
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv',
                    },
                    ticks: {
                        autoSkip: true,
                        maxRotation : 0,
                    }
                },
                x: {
                    type: "time",
                    distribution: 'series',
                    time: {
                        unit: 'second',
                        displayFormats: {
                            second: 'HH:mm:ss'
                        }
                    },
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            },
            responsive: true,
            maintainAspectRatio: true,
        }
    })
}

function chart_workers_dose_rateLoadData(uniqueId){
    const labels = [];
    const datasets = [];

    if (dosimeterInfo !== undefined){
        for (let i in dosimeterInfo){
            const item = dosimeterInfo[i];
            if (i == 0){
                for (let j in item.times) {
                    labels.push(item.times[j]);
                }
            }

            const dataset = {
                label: item.dosimeterName,
                data: item.doseRates,
                borderColor: sample_colors[i],
                fill: false,
                borderWidth: 1,
                pointRadius: 0,
                pointHitRadius:10,
            };
            datasets.push(dataset);
        }
    }

    // Graphs
    var div_chart = document.getElementById("chart_workers_dose_rate" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'line',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Workers dose-rate'
                },
                legend: {
                    display: true,
                    position: 'bottom',
                }
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv/h',
                    },
                    ticks: {
                        autoSkip: true,
                        maxRotation : 0,
                    }
                },
                x: {
                    type: "time",
                    distribution: 'series',
                    time: {
                        unit: 'second',
                        displayFormats: {
                            second: 'HH:mm:ss'
                        }
                    },
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            }
        }
    });
}

function chart_workers_accumulated_doseLoadData(uniqueId){
    const labels = [];
    const datasets = [];

    if (dosimeterInfo !== undefined){
        for (let i in dosimeterInfo){
            const item = dosimeterInfo[i];
            if (i == 0){
                for (let j in item.times) {
                    // labels.push(moment.unix(item.times[j]));
                    labels.push(item.times[j]);
                }
            }
            const dataArray = [];
            let sumData = 0.0;
            let prevTime = item.times[0];
            let prevValue = item.doseRates[0];
            for (let j in item.doseRates){
                if (j>0) {
                    const data = (prevValue + item.doseRates[j]) * (item.times[j] - prevTime) / 2;
                    sumData += data;
                    dataArray.push(sumData);
                    //
                    prevTime = item.times[j];
                    prevValue = item.doseRates[j];
                } else {
                    dataArray.push(0.0);
                }
            }

            const dataset = {
                label: item.dosimeterName,
                data: dataArray,
                borderColor: sample_colors[i],
                fill: false,
                borderWidth: 1,
                pointRadius: 0,
                pointHitRadius:10,
            };
            datasets.push(dataset);
        }
    }

    // Graphs
    var div_chart = document.getElementById("chart_workers_accumulated_dose" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'line',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Workers accumulated dose'
                },
                legend: {
                    display: true,
                    position: 'bottom',
                }
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv',
                    },
                    ticks: {
                        autoSkip: true,
                        maxRotation : 0,
                    }
                },
                x: {
                    type: "time",
                    time: {
                        unit: 'second',
                        displayFormats: {
                            second: 'HH:mm:ss'
                        }
                    },
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            }
        }
    });
}

function chart_maximum_individual_dose_rateLoadData(uniqueId){
    let labels = [];
    const dataArray = [];
    const datasets = [];
    if (dosimeterInfo !== undefined){
        for (let i in dosimeterInfo){
            const item = dosimeterInfo[i];

            labels.push(item.dosimeterName);
            dataArray.push(Math.max(...item.doseRates));
        }

        const dataset = {
            //label: item.dosimeterName,
            data: dataArray,
            backgroundColor: sample_colors[0],
            fill: true,
        };
        datasets.push(dataset);
    }

    // Graphs
    var div_chart = document.getElementById("chart_maximum_individual_dose_rate" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Maximum individual dose-rate'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                }
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv/h',
                    },
                    ticks: {
                        autoSkip: false,
                        maxRotation: 0,
                        beginAtZero: true,
                    }
                },
                x: {
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            }
        }
    });
}

function chart_minimum_individual_dose_rateLoadData(uniqueId){
    let labels = [];
    const dataArray = [];
    const datasets = [];
    if (dosimeterInfo !== undefined){
        for (let i in dosimeterInfo){
            const item = dosimeterInfo[i];

            labels.push(item.dosimeterName);
            dataArray.push(Math.min(...item.doseRates));
        }

        const dataset = {
            //label: item.dosimeterName,
            data: dataArray,
            backgroundColor: sample_colors[0],
            fill: true,
        };
        datasets.push(dataset);
    }

    // Graphs
    var div_chart = document.getElementById("chart_minimum_individual_dose_rate" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Minimum individual dose-rate'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                }
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv/h',
                    },
                    ticks: {
                        autoSkip: false,
                        maxRotation: 0,
                        beginAtZero: true,
                    }
                },
                x: {
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            }
        }
    });
}

function chart_accumulated_collective_dose_per_stepLoadData(uniqueId){
    let labels = [];
    const dataArray = [];
    const datasets = [];
    if (workPlanInfo !== undefined && workPlanInfo.WorkStepInfo !== undefined ){
        for (let i in workPlanInfo.WorkStepInfo){
            const item = workPlanInfo.WorkStepInfo[i];

            labels.push(item.name);
            dataArray.push(item.accumulatedCollectiveDose.replace(" mSv", ""));
        }

        const dataset = {
            //label: item.dosimeterName,
            data: dataArray,
            backgroundColor: sample_colors[0],
            fill: true,
        };
        datasets.push(dataset);
    }

    // Graphs
    var div_chart = document.getElementById("chart_accumulated_collective_dose_per_step" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets,
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Accumulated collective dose per step'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                }
            },
            scales: {
                y: {
                    scaleLabel: {
                        display: true,
                        labelString : 'mSv/h',
                    },
                    ticks: {
                        autoSkip: false,
                        maxRotation: 0,
                        beginAtZero: true,
                    }
                },
                x: {
                    ticks: {
                        autoSkipPadding: 10,
                        maxRotation: 0,
                    }
                }
            }
        }
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

function dg_scenario_fileListModifyExecute(uniqueId){
    if ($("#dg_scenario_fileList" + uniqueId).data("kendoGrid").getSelectedData().length > 0){

        var docUid = $("#dg_document").data("kendoGrid").getSelectedData()[0].uid;
        var dateitem = $("#dg_document").data("kendoGrid").dataSource.getByUid(docUid);

        var docName = dateitem.name;

        $.ajax({
            url : "/documentDetailProperties",
            data : {"id" : dateitem.objectId},
            method : "GET",
            type : "json",
            async : false,
            contentType : "application/json",
            success : function(result) {
                addDockItem('documentDetail_' + docName, docName, 'document/documentDetail', result);
            },
            error : function(result) {
                alert("정상 처리에 실패 하였습니다.");
            }
        }).done(function(fragment){

        });
    }
}

