/* global bootstrap: false */
(() => {
  'use strict'
  const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(tooltipTriggerEl => {
    new bootstrap.Tooltip(tooltipTriggerEl)
  })
})()

$(document).ready(function(){
    // loadData
    var treeData = loadTreeData();

    if (treeData != null) {
        // Init Bootstrap TreeView
        initBootstrapTree(treeData);

        // Init Bootstrap TreeView Settings
        $('#bt_treeview_div').treeview('expandAll', { levels: 99, silent: false });


        // Find Node
        const urlParams = new URL(location.href).searchParams;
        if (urlParams.size > 0) {
            var paramId = urlParams.get('id');
           /* var paramType = urlParams.get('');*/

            var i;
            for( i = 0;  i < $('#bt_treeview_div').data('treeview').getEnabled().length; i++){
                var tagId = $('#bt_treeview_div').data('treeview').getEnabled()[i].tags[0];
                var tagType = $('#bt_treeview_div').data('treeview').getEnabled()[i].type.toLowerCase();

                if(location.pathname + "?id=" + paramId == "/"+ tagType + "Detail?id=" + tagId) {
                    var nodeId = $('#bt_treeview_div').data('treeview').getEnabled()[i].nodeId;
                    var nodeType = $('#bt_treeview_div').data('treeview').getEnabled()[i].type;

                    $('#bt_treeview_div').treeview('selectNode', [ nodeId, { silent: true } ]); // silent : ture = 노드를 선택만 하는 옵션
                }
            }
        } else {
            //$('#bt_treeview_div').treeview('selectNode', [ 0, { silent: false } ]);
        }
    }
});
function dataGridCreateExecute(){
    addDockItem('create','create', 'create/createDetail');
}

function initBootstrapTree(treeData){
    // Bootstrap Tree init
    $('#bt_treeview_div').treeview({
        data: treeData,
        //enableLinks: true,
        onNodeSelected: function (event, node) {
            // tags[0] : id
            // tags[1] : parent_id
            //alert("text:" + node.text + ", tags:" + node.tags[0]);
            $.ajax({
                url : "/"+ node.type.toLowerCase()+ "DetailProperties",
                data: {"id" : node.tags[0]},
                method : "GET",
                type : "json",
                async : false,
                contentType : "application/json",
                success : function(result) {
                    addDockItem(node.type.toLowerCase() +'Detail_' + node.tags[0], node.text, node.type+ '/'+ node.type +'Detail', result);

                },
                error : function(result) {
                    alert("정상 처리에 실패 하였습니다.");
                }
            }).done(function(fragment){

            });
        },

        onNodeUnSelected: function (event, node) {
            //alert("text:" + node.text + ", tags:" + node.tags[0]);
        },
        onNodeCollapsed: function(event, node) {
        // li node에는 slide toggle속성을 사용할수 없음. 추후 개발
        /*
        var i;
        for (i = 0; i< $('#bt_treeview_div').find("li").length; i++){
            var el = $('#bt_treeview_div').find("li")[i];
            if (el.getAttribute("data-nodeid") == node.nodeId) {
                    el.slideToggle(500, function () {
                    });
                }
            }
        */
        },
        onNodeExpanded: function (event, node) {

        }

    });

    // Search Tree
    var findExpandibleNodess = function() {
        return $('#bt_treeview_div').treeview('search', [ $('#bt_treeview_search').val(), { ignoreCase: false, exactMatch: false } ]);
    };
    var expandibleNodes = findExpandibleNodess();

    // Expand/collapse/toggle nodes
    $('#bt_treeview_search').on('keyup', function (e) {
        expandibleNodes = findExpandibleNodess();
        $('.expand-node').prop('disabled', !(expandibleNodes.length >= 1));
    });

     // Expand/collapse all
    $('#btn_expand').on('click', function (e) {
        $('#bt_treeview_div').treeview('expandAll', { levels: 99, silent: false });
    });
    $('#btn_collapse').on('click', function (e) {
        $('#bt_treeview_div').treeview('collapseAll', { silent: false });
    });
}

function loadTreeData() {
    const resultList = [];
    $.ajax({
        type: 'GET',
        url: "/getStructureList",
        dataType: "json",
        async: false,
        error: function(request, status, error) {
            alert(status);
        },
        success: function(data) {
            const tempList = [];

            data.forEach(item => {
                const node = { tags : [item.objectID, item.parentID], text : item.name , href : typeToHref(item.type), type : item.type };
                tempList.push(node);
            });

            //objectID 정렬
            /*const SortingList = tempList.sort((a,b) => (a.tags[0] - b.tags[0]));*/

            //text 정렬
            const SortingList = tempList.sort((a,b) => {
                if(a.text > b.text) return 1;
                if(a.text < b.text) return -1;
                return 0;
            });

            // Sorting Node - Site
            SortingList.filter(n => n.type === 'Site').forEach(item => {
                resultList.push(item);

            });
            // Sorting Node - Plant
            SortingList.filter(n => n.type === 'Plant').forEach(item => {
                const parentNode = resultList.find(n => n.tags[0] === item.tags[1]);

                if (parentNode !== undefined) {
                    if (parentNode.nodes === undefined) {
                        parentNode.nodes = [];
                    }
                    parentNode.nodes.push(item);
                }
            });
            // Sorting Node - Unit
            SortingList.filter(n => n.type === 'Unit').forEach(item => {
                resultList.forEach(parentItem => {
                    if (parentItem.nodes !== undefined) {
                        const parentNode = parentItem.nodes.find(m => m.type === 'Plant' && m.tags[0] === item.tags[1]);

                        if (parentNode !== undefined) {
                            if (parentNode.nodes === undefined) {
                                parentNode.nodes = [];
                            }
                            parentNode.nodes.push(item);
                        }
                    }
                });
            });
            // Sorting Node - Room
            SortingList.filter(n => n.type === 'Room').forEach(item => {
                resultList.forEach(parentItem => {
                    if (parentItem.nodes !== undefined) {
                        parentItem.nodes.forEach(plantItem => {
                            if (plantItem.nodes !== undefined) {
                                const parentNode = plantItem.nodes.find(m => m.type === 'Unit' && m.tags[0] === item.tags[1]);
                                if (parentNode != null) {
                                    if (parentNode.nodes === undefined) {
                                         parentNode.nodes = [];
                                    }
                                    parentNode.nodes.push(item);
                                }
                            }
                        });
                    }
                });
            });
            // Sorting Node - Project
            SortingList.filter(n => n.type === 'Project').forEach(item => {
                resultList.forEach(parentItem => {
                    if (parentItem.nodes !== undefined) {
                        parentItem.nodes.forEach(plantItem => {
                            if (plantItem.nodes !== undefined) {
                                plantItem.nodes.forEach(unitItem => {
                                    if (unitItem.nodes !== undefined){
                                        const parentNode = unitItem.nodes.find(m => m.type === 'Room' && m.tags[0] === item.tags[1]);
                                        if (parentNode != null) {
                                            if (parentNode.nodes === undefined) {
                                                parentNode.nodes = [];
                                            }
                                            parentNode.nodes.push(item);
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            });
        }
    });
/*

    $.ajax({
        type: 'GET',
        url: "/getRoomIDList",
        dataType: "json",
        async: false,
        error: function (request, status, error) {
            alert(status);
        },
        success: function (data) {
            roomidList = data;
        }
    });
    // Sorting Node - Project (추후 구현)
    ////////////////// 23.09.13 Demo 용도 //////////////////
    $.ajax({
        type: 'POST',
        url: "/getProjectList",
        dataType: "json",
        async: false,
        error: function (request, status, error) {
            alert(status);
        },
        success: function (data) {
            //alert(JSON.stringify(data));
            // Sorting Node - Project Sample
            var projectList = new Array();

            roomidList.forEach(item =>{
                var node = { tags: [item.projectID, item.id], text: item.name, href: typeToHref("Project"), type: "project" };
                projectList.push(node);
            })
            console.log(projectList)


            /!*setParentNode(projectList, resultList);*!/


        }
    });
    ////////////////// 23.09.13 Demo 용도 //////////////////
*/
console.log(resultList);
    return JSON.stringify(resultList);
}
function dg_sideloadExecute()
{
    loadTreeData();
}


function sampleProject(data, parentId){
    return {tags: [data.id, parentId], text: data.name, href: typeToHref("Project"), type: "project"};
}

function setParentNode (projectList, resultList){
    /*tempList.filter(n => n.type === 'Room').forEach(item => {
        resultList.forEach(parentItem => {
            if (parentItem.nodes != null) {
                parentItem.nodes.forEach(unitItem => {
                    var parentNode = unitItem.nodes.find(m => m.type === 'Unit' && m.tags[0] === item.tags[1]);
                    if (parentNode != null) {
                        if (parentNode.nodes == null) {
                            parentNode.nodes = new Array();
                        }
                    }

                })
            }
        });
    });*/
}
function dg_unitReloadExecute(){
    $('#bt_treeview_div').data(' ').dataSource.read(); <!--  first reload data source -->
    $('#bt_treeview_div').data('kendoGrid').refresh(); <!--  refresh current UI -->
}

function typeToHref(type){
    switch (type){
        case "Site":
        return "../siteDetail";

        case "Plant":
        return "../plantDetail";

        case "Unit":
        return "../unitDetail";

        case "Room":
        return  "../roomDetail";

        case "Project":
        return "../projectDetail";

        default:
        break;
    }
}

function initTree() {
    /* $('#jstree_div').jstree({
         'core' : {
         'data' : [
                 { "id" : "site_1",  "parent" : "#",       "text" : "고리원자력발전소", "state" : {"opened" : true}},
                 { "id" : "plant_1", "parent" : "site_1",  "text" : "1 & 2 발전소", "state" : {"opened" : true}},
                 { "id" : "plant_2", "parent" : "site_1",  "text" : "3 & 4 발전소", "state" : {"opened" : true}},
                 { "id" : "unit_1",  "parent" : "plant_1", "text" : "1 호기", "state" : {"opened" : true}},
                 { "id" : "unit_2",  "parent" : "plant_1", "text" : "2 호기", "state" : {"opened" : true}},
                 { "id" : "unit_3",  "parent" : "plant_2", "text" : "3 호기", "state" : {"opened" : true}},

                 { "id" : "project_1",  "parent" : "unit_1", "text" : "Demo HBWR 01", "icon":  "jstree-file", "state" : {"select" : true} },
                 { "id" : "project_2",  "parent" : "unit_1", "text" : "Demo HBWR 02", "icon" : "jstree-file" },
                 { "id" : "project_3",  "parent" : "unit_2", "text" : "Project 01", "icon" : "jstree-file" },
                 { "id" : "project_4", "parent" : "unit_3", "text" : "Project 02", "icon" : "jstree-file" },
             ]
         },
         'plugins' : ["themes", "search"],
         'search' : {
             'show_only_matches' : true,
             'show_only_matches_children' : true,
         },
         'themes' : {
             'dots' : false,
             'icons' : false,
         },
     });
 */


    /*
    $('#jstree_div').jstree({
            'core' : {
            'data' : [
            $.ajax({
                type: 'GET',
                url: "/getStructureList",
                dataType: "json",
                error: function(request, status, error) {
                   },
                success: function(data) {
                  $('#jstree_div').jstree(true).delete_node(j1_1);

                    data.forEach(item => {
                        var node = { id : item.id, text : item.name , type : item.type , lowertype : item.type.toString().toLowerCase() };
                        $('#jstree_div').jstree().create_node(item.parentID, node, "last");
                    });

                }})
              ],
              "check_callback": true
            },
            'plugins' : [ "themes","search"],
            'search' : {
                'show_only_matches' : true,
                'show_only_matches_children' : true,
            },
            'themes' : {
                'dots' : false,
                'icons' : false,
            },
        });

        // Search Event
        var to = false;
        $('#jstree_search').keyup(function () {
            if(to) {
                clearTimeout(to);
            }
        to = setTimeout(function () {
            var v = $('#jstree_search').val();
            $('#jstree_div').jstree(true).search(v);
            }, 250);
        });

        // Selected Changed Event
        $('#jstree_div').on("changed.jstree", function (e, data) {
            if (data.selected.length > 0) {
                $('#jstree_div').jstree('get_selected',true)
                var dataType = data.node.original.type;
                var temp = dataType.toString().toLowerCase();

                var dataId = data.node.original.id;

                addDockItem('temp', temp+'list', temp+'/'+temp+'Detail')

                //$('#jstree_div').jstree().get_node(data.selected, true).children('.jstree-anchor').focus();
            }
        });

        // Loaded Event
        $('#jstree_div').on('loaded.jstree', function() {
            //tree.jstree('open_all');
        });

    }*/
}