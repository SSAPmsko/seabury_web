/* global bootstrap: false */
(() => {
  'use strict'
  const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(tooltipTriggerEl => {
    new bootstrap.Tooltip(tooltipTriggerEl)
  })
})()

$(document).ready(function(){
  initTree();
});

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
                	var node = { id : item.id, text : item.name , type : item.type };
                	$('#jstree_div').jstree().create_node(item.parent_ID, node, "last");
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
            location.href= temp + "Detail" ;

            //$('#jstree_div').jstree().get_node(data.selected, true).children('.jstree-anchor').focus();
        }
    });

    // Loaded Event
    $('#jstree_div').on('loaded.jstree', function() {
        //tree.jstree('open_all');
    });

}