var rootName = "source";

$(document).ready(function(){
    // 클릭한 위치 active 적용
    //$("#" + rootName).addClass('active');

    var editMode = $("#txt_editMode").val();
});

function historyBack(){
    //window.history.back();
    location.href = rootName +"List";
}