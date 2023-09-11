(() => {
  'use strict'

  document.querySelector('#navbarSideCollapse').addEventListener('click', () => {
    document.querySelector('.offcanvas-collapse').classList.toggle('open')
  })
})()

function addDockItem(id, title, path, properties){

    // Navigation Bar 가 상태가 Open 이면, 닫음
    if ($('#navbars').hasClass('open') == true) {
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
            alert(properties.result);

            for (const [k, v] of Object.entries(properties.result)){
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
        if (myLayout.root.contentItems[0].contentItems.length > 0){
            myLayout.root.contentItems[0].contentItems[0].addChild( newItemConfig );
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
