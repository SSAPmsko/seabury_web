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
    $('#jstree_div').jstree({
        'core' : {
        'data' : [
                { "id" : "item1",  "parent" : "#",     "text" : "고리원자력발전소", "state" : {"opened" : true}},
                { "id" : "item2",  "parent" : "item1", "text" : "1 & 2 발전소", "state" : {"opened" : true}},
                { "id" : "item3",  "parent" : "item1", "text" : "3 & 4 발전소", "state" : {"opened" : true}},
                { "id" : "item4",  "parent" : "item2", "text" : "1 호기", "state" : {"opened" : true}},
                { "id" : "item5",  "parent" : "item2", "text" : "2 호기", "state" : {"opened" : true}},
                { "id" : "item6",  "parent" : "item3", "text" : "3 호기", "state" : {"opened" : true}},

                { "id" : "item7",  "parent" : "item4", "text" : "Demo HBWR 01", "icon":  "jstree-file", "state" : {"select" : true} },
                { "id" : "item8",  "parent" : "item4", "text" : "Demo HBWR 02", "icon" : "jstree-file" },
                { "id" : "item9",  "parent" : "item5", "text" : "Project 01", "icon" : "jstree-file" },
                { "id" : "item10", "parent" : "item6", "text" : "Project 02", "icon" : "jstree-file" },
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
            //alert(data.selected);
            //$('#jstree_div').jstree().get_node(data.selected, true).children('.jstree-anchor').focus();
        }
    });

    // Loaded Event
    $('#jstree_div').on('loaded.jstree', function() {
        //tree.jstree('open_all');
    });
}