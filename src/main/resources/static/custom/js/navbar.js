function SubMenuClick(_target){
    $("#page-body").empty();
    $("#page-body").load(_target, function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success") {
            alert(_target);
            console.log("External content loaded successfully!");
        }
        if (statusTxt == "error") {
            alert('현재 페이지는 준비중 입니다.');
            console.log("Error: " + xhr.status + ": " + xhr.statusText);
        }
   });
}

$(document).ready(function(){
  initNavbar();
});

function initNavbar(){
    // actvie 활성화
    //$(".nav-item > .active").css("color", "white");

    $('.nav-link').click(function () {
        // .nav-link 클릭시 이전의 active 값 해제 후,
        //$(".nav-item > .active").css("color", "#007bff");
        $('.nav-link').removeClass('active');

        // 클릭한 위치 active 적용
        $(this).addClass('active');
        //$(".nav-item > .active").css("color", "white");
    });
}