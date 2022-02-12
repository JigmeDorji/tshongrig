/* ------------------------------------------------------------------------------
 *
 *  # Datatables data sources
 *
 *  Demo JS code for datatable_data_sources.html page
 *
 * ---------------------------------------------------------------------------- */


// Setup module
// ------------------------------

let DatatableDataSources = function() {
    
    let _componentDatatableDataSources = function() {
        if (!$().DataTable) {
            console.warn('Warning - datatables.min.js is not loaded.');
            return;
        }

        // Setting datatable defaults
        $.extend( $.fn.dataTable.defaults, {
            autoWidth: false,
            dom: '<"datatable-header"fl><"datatable-scroll"t><"datatable-footer"ip>',
            language: {
                search: '<span>Filter:</span> _INPUT_',
                searchPlaceholder: 'Type to filter...',
                lengthMenu: '<span>Show:</span> _MENU_',
                paginate: { 'first': 'First', 'last': 'Last', 'next': $('html').attr('dir') == 'rtl' ? '&larr;' : '&rarr;', 'previous': $('html').attr('dir') == 'rtl' ? '&rarr;' : '&larr;' }
            }
        });

        // Apply custom style to select
        $.extend( $.fn.dataTableExt.oStdClasses, {
            "sLengthSelect": "custom-select"
        });

        // Location alert
        $('.datatable-generated tbody').on('click', 'a', function () {
            let data = table.row($(this).parents('tr')).data();
            alert(data[0] +"'s location is: "+ data[ 2 ]);
        });
    };

    return {
        init: function() {
            _componentDatatableDataSources();
        }
    }
}();


// Initialize module
// ------------------------------

document.addEventListener('DOMContentLoaded', function() {
    DatatableDataSources.init();
});
