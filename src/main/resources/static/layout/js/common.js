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