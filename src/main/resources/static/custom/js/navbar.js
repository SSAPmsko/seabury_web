function SubMenuClick(_target){
    $("#page-body").empty();
    $("#page-body").load(_target, function (responseTxt, statusTxt, xhr) {
        if (statusTxt == "success") {
            console.log("External content loaded successfully!");
        }
        if (statusTxt == "error") {
            alert('현재 페이지는 준비중 입니다.');
            console.log("Error: " + xhr.status + ": " + xhr.statusText);
        }
   });
}