var config = {
    settings:{
        hasHeaders: true,
        constrainDragToContainer: true,
        reorderEnabled: true,
        selectionEnabled: false,
        popoutWholeStack: false,
        blockedPopoutsThrowError: true,
        closePopoutsOnUnload: true,
        showPopoutIcon: true,
        showMaximiseIcon: true,
        showCloseIcon: true
    },
    content: [{
        type: 'row',
        isClosable: false,
        content: [
            {
                title: 'Structure',
                type:'component',
                width: 35,
                componentName: 'goldenLayout',
                componentState: { text: 'root', htmlStr: getHtmlTemplate("/templates/view/sub/structure/structureList.html")}
            },
            {
                title: 'main',
                type:'component',
                componentName: 'goldenLayout',
                componentState: { text: "", htmlStr: getHtmlTemplate("/templates/view/main/index.html")}
            }]
    }]
};

var myLayout = new window.GoldenLayout( config, $('#layoutContainer') );

myLayout.registerComponent( 'goldenLayout', function( container, state ){

    container.getElement().html( state.htmlStr );
});

myLayout.init();

$(window).resize(function () {
    onResize();
});
$(window).load(function () {
    onResize();
});

function onResize(){
    width = $('#page-body').width();
    height = $('#page-body').height();
    myLayout.updateSize(width, height - 10);
}