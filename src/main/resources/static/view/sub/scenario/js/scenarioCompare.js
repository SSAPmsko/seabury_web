var rootName = "scenario";

var sample_colors = ["#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac"];

$(document).ready(function(){
    onLoadedScenarioCompare();
});

function onLoadedScenarioCompare(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    // 최초 로드시 scenarioId 추출, unique ID 생성
    var formName = "scenarioCompareForm";
    var scenarioId = $("#txt_scenarioId").val();
    var uniqueId;
    if (scenarioId !== "" && scenarioId !== "${id}" && scenarioId !== undefined) {
        uniqueId = "_" + formName + "_" + scenarioId;
    } else if (scenarioId === "") {
        // create 인 경우
        uniqueId = "_" + formName + "_newItem";
    }
    // form 의 Id를 Unique 한 Id 변경, 데이터 로드 및 이벤트 처리가 여러번 발생 되지 않도록 제어
    var isFirst = replaceFormId('scenarioCompareForm', uniqueId);

    if (isFirst === true){

        chart_DurationLoadData(uniqueId);
        chart_WorkTimeLoadData(uniqueId);
        chart_MaxIndividualDoseLoadData(uniqueId);
        chart_CollectiveDoseLoadData(uniqueId);

        createCheckBoxGroup(uniqueId);
    }
}

function createCheckBoxGroup(uniqueId){
    let dataSource;
    $.ajax({
        url: location.origin + "/getScenarioList",
        type: 'POST',
        async: false,
        processData: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            dataSource = result;
        },
        error: function (result) {
            dataSource = result;
        }
    });

    if (dataSource !== undefined){
        let items =[];

        dataSource.forEach(data => {
            // Remove Current Scenario Id
            // 24.01.02, 샘플 데이터 없음. 현재는 테스트 위한 주석
            //if (data.id != $("#txt_scenarioId" + uniqueId).val()){
                let item = {
                    label: data.name,
                    value: data.id,
                    data: data,
                };
                items.push(item);
            //}
        });

        // create CheckBoxGroup from ul HTML element
        let checkBoxGroup = $("#list_scenarioCompare" + uniqueId).kendoCheckBoxGroup({
            items: items,
            layout: "vertical",
            change: function (e) {
                if (e.target.length > 0){
                    let selectedScenarioId = e.target[0].value;

                    const status = e.target.is(":checked");
                    if (status){
                        $.ajax({
                            url: location.origin + "/scenarioCompare" + "?id=" + selectedScenarioId,
                            type: 'GET',
                            async: false,
                            processData: false,
                            dataType: "json",
                            contentType: "application/json;charset=UTF-8",
                            success: function (result) {
                                chart_DurationAddData(uniqueId, selectedScenarioId, result.result.name, result.result.scenarioSummary_duration / 360000);
                                chart_WorkTimeAddData(uniqueId, selectedScenarioId, result.result.name, result.result.scenarioSummary_workTime);
                                chart_MaxIndividualDoseAddData(uniqueId, selectedScenarioId, result.result.name, result.result.scenarioSummary_maxDose);
                                chart_CollectiveDoseAddData(uniqueId, selectedScenarioId, result.result.name, result.result.scenarioSummary_collectiveDose);

                            },
                            error: function (result) {
                                alert("정상 처리에 실패 하였습니다.");
                            }
                        });
                    } else{
                        chart_DurationRemoveData(uniqueId, selectedScenarioId);
                        chart_WorkTimeRemoveData(uniqueId, selectedScenarioId);
                        chart_MaxIndividualDoseRemoveData(uniqueId, selectedScenarioId);
                        chart_CollectiveDoseRemoveData(uniqueId, selectedScenarioId);

                    }
                }
            }
        }).data("kendoCheckBoxGroup");

       /* Select All
        $("#selectAllCheckbox").change(function () {
            if (this.checked) {
                checkBoxGroup.checkAll(true)
            } else {
                checkBoxGroup.checkAll(false)
            }
        });
        */
    }
}

function chart_DurationLoadData(uniqueId){
    const dataArray = [];
    const datasets = [];
    const data= {
        id : $("#txt_scenarioId" + uniqueId).val(),
        xValue : $("#txt_scenarioName" + uniqueId).val(),
        yValue : $("#txt_scenarioDuration" + uniqueId).val() / 360000,
    };
    dataArray.push(data);

    const dataset = {
        data: dataArray,
        backgroundColor: sample_colors[0],
        fill: true,
    };
    datasets.push(dataset);

    // Graphs
    var div_chart = document.getElementById("chart_scenarioDuration" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            datasets: datasets,
        },
        options: {
            parsing: {
                xAxisKey: 'xValue',
                yAxisKey: 'yValue',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Duration'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                },
            }
        }
    });
}

function chart_DurationAddData(uniqueId, id, xValue, yValue){
    let chart = Chart.getChart("chart_scenarioDuration" + uniqueId);

    if (chart !== undefined){
        const dataArray = [];
        const data= {
            id : id,
            xValue : xValue,
            yValue : yValue,
        };
        dataArray.push(data);

        const dataset = {
            data: dataArray,
            backgroundColor: sample_colors[chart.data.datasets.length],
            fill: true,
        };

        chart.data.datasets.push(dataset);

        chart.update();	//차트 업데이트
    }
}

function chart_DurationRemoveData(uniqueId, id){
    let chart = Chart.getChart("chart_scenarioDuration" + uniqueId);

    // 라벨 삭제
    // chart.data.labels.splice(-1,1);//라벨 삭제
    let idx;
    for (let i = 0; i < chart.data.datasets.length; i++){
        let dataset =  chart.data.datasets[i];
        if (dataset.data[0].id === id){
            idx = i;
            break;
        }
    }

    if (idx !== 0){
        chart.data.datasets.splice(idx, 1);
    }
    chart.update();	//차트 업데이트
}

function chart_WorkTimeLoadData(uniqueId){
    const dataArray = [];
    const datasets = [];
    const data= {
        id : $("#txt_scenarioId" + uniqueId).val(),
        xValue : $("#txt_scenarioName" + uniqueId).val(),
        yValue : $("#txt_scenarioWorkTime" + uniqueId).val(),
    };
    dataArray.push(data);

    const dataset = {
        data: dataArray,
        backgroundColor: sample_colors[0],
        fill: true,
    };
    datasets.push(dataset);

    // Graphs
    var div_chart = document.getElementById("chart_scenarioWorkTime" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            datasets: datasets,
        },
        options: {
            parsing: {
                xAxisKey: 'xValue',
                yAxisKey: 'yValue',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Work (man/hrs)'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                },
            }
        }
    });
}

function chart_WorkTimeAddData(uniqueId, id, xValue, yValue){
    let chart = Chart.getChart("chart_scenarioWorkTime" + uniqueId);

    if (chart !== undefined){
        const dataArray = [];
        const data= {
            id : id,
            xValue : xValue,
            yValue : yValue,
        };
        dataArray.push(data);

        const dataset = {
            data: dataArray,
            backgroundColor: sample_colors[chart.data.datasets.length],
            fill: true,
        };

        chart.data.datasets.push(dataset);

        chart.update();	//차트 업데이트
    }
}

function chart_WorkTimeRemoveData(uniqueId, id){
    let chart = Chart.getChart("chart_scenarioWorkTime" + uniqueId);

    // 라벨 삭제
    // chart.data.labels.splice(-1,1);//라벨 삭제
    let idx;
    for (let i = 0; i < chart.data.datasets.length; i++){
        let dataset =  chart.data.datasets[i];
        if (dataset.data[0].id === id){
            idx = i;
            break;
        }
    }

    if (idx !== 0){
        chart.data.datasets.splice(idx, 1);
    }
    chart.update();	//차트 업데이트
}

function chart_MaxIndividualDoseLoadData(uniqueId){
    const dataArray = [];
    const datasets = [];
    const data= {
        id : $("#txt_scenarioId" + uniqueId).val(),
        xValue : $("#txt_scenarioName" + uniqueId).val(),
        yValue : $("#txt_scenarioMaxDose" + uniqueId).val(),
    };
    dataArray.push(data);

    const dataset = {
        data: dataArray,
        backgroundColor: sample_colors[0],
        fill: true,
    };
    datasets.push(dataset);

    // Graphs
    var div_chart = document.getElementById("chart_scenarioMaxIndividualDose" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            datasets: datasets,
        },
        options: {
            parsing: {
                xAxisKey: 'xValue',
                yAxisKey: 'yValue',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Max Individual Dose (mSV)'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                },
            }
        }
    });
}

function chart_MaxIndividualDoseAddData(uniqueId, id, xValue, yValue){
    let chart = Chart.getChart("chart_scenarioMaxIndividualDose" + uniqueId);

    if (chart !== undefined){
        const dataArray = [];
        const data= {
            id : id,
            xValue : xValue,
            yValue : yValue,
        };
        dataArray.push(data);

        const dataset = {
            data: dataArray,
            backgroundColor: sample_colors[chart.data.datasets.length],
            fill: true,
        };

        chart.data.datasets.push(dataset);

        chart.update();	//차트 업데이트
    }
}

function chart_MaxIndividualDoseRemoveData(uniqueId, id){
    let chart = Chart.getChart("chart_scenarioMaxIndividualDose" + uniqueId);

    // 라벨 삭제
    // chart.data.labels.splice(-1,1);//라벨 삭제
    let idx;
    for (let i = 0; i < chart.data.datasets.length; i++){
        let dataset =  chart.data.datasets[i];
        if (dataset.data[0].id === id){
            idx = i;
            break;
        }
    }

    if (idx !== 0){
        chart.data.datasets.splice(idx, 1);
    }
    chart.update();	//차트 업데이트
}

function chart_CollectiveDoseLoadData(uniqueId){
    const dataArray = [];
    const datasets = [];
    const data= {
        id : $("#txt_scenarioId" + uniqueId).val(),
        xValue : $("#txt_scenarioName" + uniqueId).val(),
        yValue : $("#txt_scenarioCollectiveDose" + uniqueId).val(),
    };
    dataArray.push(data);

    const dataset = {
        data: dataArray,
        backgroundColor: sample_colors[0],
        fill: true,
    };
    datasets.push(dataset);

    // Graphs
    var div_chart = document.getElementById("chart_scenarioCollectiveDose" + uniqueId);
    var chart = new Chart(div_chart, {
        type: 'bar',
        data: {
            datasets: datasets,
        },
        options: {
            parsing: {
                xAxisKey: 'xValue',
                yAxisKey: 'yValue',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Collective Dose (man - mSv)'
                },
                legend: {
                    display: false,
                    position: 'bottom',
                },
            }
        }
    });
}

function chart_CollectiveDoseAddData(uniqueId, id, xValue, yValue){
    let chart = Chart.getChart("chart_scenarioCollectiveDose" + uniqueId);

    if (chart !== undefined){
        const dataArray = [];
        const data= {
            id : id,
            xValue : xValue,
            yValue : yValue,
        };
        dataArray.push(data);

        const dataset = {
            data: dataArray,
            backgroundColor: sample_colors[chart.data.datasets.length],
            fill: true,
        };

        chart.data.datasets.push(dataset);

        chart.update();	//차트 업데이트
    }
}

function chart_CollectiveDoseRemoveData(uniqueId, id){
    let chart = Chart.getChart("chart_scenarioCollectiveDose" + uniqueId);

    // 라벨 삭제
    // chart.data.labels.splice(-1,1);//라벨 삭제
    let idx;
    for (let i = 0; i < chart.data.datasets.length; i++){
        let dataset =  chart.data.datasets[i];
        if (dataset.data[0].id === id){
            idx = i;
            break;
        }
    }

    if (idx !== 0){
        chart.data.datasets.splice(idx, 1);
    }
    chart.update();	//차트 업데이트
}

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}