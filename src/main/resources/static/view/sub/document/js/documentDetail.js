
$(document).ready(function () {
    //Tag ID 변경
    onLoadedDocument();
});

function onLoadedDocument(){
    //최초 로드시 DocumentId 추출, unique ID 생성
    var formName = "documentPropertyForm";
    var documentId;

    if($("#txt_documentId").val().indexOf(';')) {
        documentId = $("#txt_documentId").val().split(';')[0];
    } else {
        documentId = $("#txt_documentId").val();
    }

    var uniqueId;

    if (documentId !== "" && documentId !== "${objectId}" && documentId !== undefined) {
        uniqueId = "_" + formName + "_" + documentId;
    } else if (documentId === "") {
        // create 인 경우
        uniqueId = "_" + formName + "_newItem";
    }

    var isFirst = replaceFormId('documentPropertyForm', uniqueId);

    if(isFirst)
    {
        //Create Date Binding
        var cusdate = new Date($("#txt_CreateDate"+uniqueId).val());

        $("#createDate"+uniqueId).val(ConvertToDateFormat(cusdate));

        $("#btn_scenario"+uniqueId).on("click", function(e) {
            onScenarioClick(uniqueId)
        });

        //File Download Blob
        $("#btn_downloadBlob"+uniqueId).on("click", function(e) {
            onDocumentDownloadBlob(uniqueId)
        });


        var filecontent = $("#txt_renditionContent"+uniqueId).val();
        if(filecontent != null){
            var blob = new Blob([filecontent],{type: "text/plain;charset=utf-8"});
            //PDF Viewer
            var reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onload =  function(e){
                $("#pdfViewer"+uniqueId).kendoPDFViewer({
                    pdfjsProcessing: {
                        file: {
                            data : filecontent
                        }
                    },
                    error: function (e) {
                        e._defaultPrevented=true;
                        e.message="지원하지 않는 파일 형식입니다.";
                    },
                    width: "100%",
                    height: 400
                }).getKendoPDFViewer();
            };
        }

    }
}

function onDocumentDownloadBlob(uniqueId){
    var filecontent = $("#txt_fileContent"+uniqueId).val();
    var blobUrl = new Blob([filecontent],{type: "text/plain;charset=utf-8"});
    var fileName = $("#txt_name"+uniqueId).val();

    kendo.saveAs({
        dataURI: blobUrl,
        proxyURL: "/path/to/proxy",
        fileName: fileName
    });
}
function  onScenarioClick(uniqueId){
    var id = $("#txt_scenarioId"+uniqueId).val();
    var projectId = $("#txt_scenarioId"+uniqueId).val();
    var scenarioName = $("#txt_scenarioName"+uniqueId).val();
    //Scenario Name 필요
    $.ajax({
        url : "/scenarioDetailProperties?projectId=" + projectId + "&id=" + id,
        method : "GET",
        type : "json",
        async : false,
        contentType : "application/json",
        success : function(result) {
            addDockItem('scenarioDetail_' + id, scenarioName, 'scenario/scenarioDetail', result);
        },
        error : function(result) {
            alert("정상 처리에 실패 하였습니다.");
        }
    }).done(function(fragment){

    });
}

function  ConvertToDateFormat(date){
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;
    hour = hour >= 10 ? hour : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    second = second >= 10 ? second : '0' + second;

    return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}