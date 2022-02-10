/**
 * Created by Bcass Sawa on 4/9/2019.
 */

viewPrintCopy = (function () {

    var spmsJSTree = $('#spmsJSTree')

    spmsJSTree.jstree({
        "core": {
            animation: true,
            'strings': {
                'Loading ...': 'Please wait ...'
            },
            "themes": {
                "stripes": true,
                "icons": true
            },
            "data": {
                "url": function (node) {
                    return node.id === '#' ? "viewPrintCopy/getParentNodeJsTree" : "viewPrintCopy/getSpecificReportsDetailsByNodeId/" + node.id;
                },
                "data": function (node) {
                    return {"id": node.id};
                }
            }
        },
        "types": {
            "child": {
                "icon": spms.getUrl() + "/resources/jsTree/icon.jpg",
                "valid_children": ["default"]
            },
            "default": {
                "valid_children": ["default"]
            }
        },
        "plugins": ["themes", "types", "sort"]
    });

    spmsJSTree.on("changed.jstree", function (e, data) {
        if (data.selected.length) {
            if (data.instance.get_node(data.selected[0]).id != 'j1_1' && data.instance.get_node(data.selected[0]).id != 2 && data.instance.get_node(data.selected[0]).id != 3)
                window.open(spms.getUrl() + 'viewPrintCopy/displayPDFFile/' +
                    data.instance.get_node(data.selected[0]).text,
                    '_blank');
        }
    });
})();

$(document).ready(function () {
});
