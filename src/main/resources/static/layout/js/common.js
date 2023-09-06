// html 템플릿 반환
function getHtmlTemplate(url){
    var html = "";
    $.ajax({
        url: url,
        async: false,
        processData: false,
        success: function(data){
            html = data;
        }
    });

    return html;
}

function formToJsonObject(formName){
    var formList = $("[form="+formName+"]");

    var obj = {};
    for(var i=0;i<formList.length;i++){
        obj[formList[i].id] = formList[i].value;
    }

    return obj;
}