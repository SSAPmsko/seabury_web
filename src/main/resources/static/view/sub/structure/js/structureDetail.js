var rootName = "structure";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    $("#" + rootName).addClass('active');

    var editMode = $("#txt_editMode").val();

    if (editMode == 'true') {
        $('#btn_delete').removeClass("visually-hidden")
    } else if (editMode == 'false') {
        $('#btn_delete').addClass("visually-hidden")
    }
});

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}
