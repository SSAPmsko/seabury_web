(() => {
  'use strict'

  document.querySelector('#navbarSideCollapse').addEventListener('click', () => {
    document.querySelector('.offcanvas-collapse').classList.toggle('open')
  })
})()

function addDockItem(id, title, path, properties){

    // Navigation Bar 가 상태가 Open 이면, 닫음
    if ($('#navbars').hasClass('open') === true) {
        $('#navbars').removeClass('open');
    }

    var findDockItem = myLayout.root.getItemsById(id);

    // 해당 아이템이 있으면,
    if (findDockItem.length > 0) {
        var stackPanel = findDockItem[0].parent;

        stackPanel.setActiveContentItem(findDockItem[0]);
    } else {

        var htmlStr = getHtmlTemplate("/templates/view/sub/" + path + ".html");

        if (properties !== undefined){
            htmlStr = htmlStr.replace(/th:value/g,'value');


            for (const [k, v] of Object.entries(properties.result)){
                if (k === "report"){
                    htmlStr = customReport(k, v, htmlStr);
                }
                htmlStr = htmlStr.replace('${' + k + '}' ,v);
            }
            // Empty Value Init
            htmlStr = htmlStr.replace(/\${(.*)}/g ,'');
        }

        var newItemConfig = {
            id : id,
            title: title,
            type: 'component',
            componentName: 'goldenLayout',
            componentState: { text: "", htmlStr: htmlStr }
        };

        // dock panel 이 1개 이상일떄,
        var dockPanelCount = myLayout.root.contentItems[0].contentItems.length;
        if(dockPanelCount === 1){
        }

        if (dockPanelCount > 1){
            //alert(dockPanelCount);
            // 기존 코드 첫번째 dockPanel에 넣음
            //myLayout.root.contentItems[0].contentItems[0].addChild( newItemConfig );
            myLayout.root.contentItems[0].contentItems[dockPanelCount - 1].addChild( newItemConfig );
        } else {
            myLayout.root.contentItems[0].addChild( newItemConfig );
        }
    }
}

function replaceFormId(formName, uniqueId) {
    var form = document.getElementById(formName);

    if (form !== undefined){
        var formList = $("[form=" + formName + "]");

        for(var i= 0; i <formList.length; i++){
            formList[i].id = formList[i].id + uniqueId;
            formList[i].attributes['form'].value = formName + uniqueId;
        }

        form.id = formName + uniqueId;

        return true;
    } else{
        return false;
    }
}

function replaceFormNewId(formName, srcId, distId) {
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

function customReport(k, v, htmlStr){
    for (const [k2, v2] of Object.entries(v.Report)){

        switch (k2) {
            case "reportHeader":
            case "projectSummary":
            case "scenarioSummary":
                for (const [k3, v3] of Object.entries(v2)){
                    htmlStr = htmlStr.replace('${' +  k + "." + k2 + "." + k3 + '}', v3);
                }
                break;
            case "scenarioDetails":
                for (const [k3, v3] of Object.entries(v2)){
                    htmlStr = htmlStr.replace('${' +  k + "." + k2 + "." + k3 + '}', JSON.stringify(v3).replaceAll("\"", "'"));
                }
            break;
            default:
                break;

        }
    }
    return htmlStr;
}

/*
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
}*/
