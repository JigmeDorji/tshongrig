/**
 * Created by SonamPC on 12-Dec-16.
 */

viewItemsForRawMaterial = (function () {

    //to load the existing list of users
    let totalAmount = 0;

    function onAsOnDateChange() {
        $('#asOnDate').on('change', function () {
            let asOnDate = $(this).val();
            getItemAvailable(asOnDate);
        })
    }


    function getItemAvailable(asOnDate) {
        if (typeof asOnDate == 'undefined') {
            asOnDate = $('#asOnDate').val();
        }
        if (asOnDate !== '') {
            $.ajax({
                url: 'viewItemsForRawMaterial/getItemAvailable',
                type: 'GET',
                data: {asOnDate: asOnDate},
                success: function (res) {
                    $('#itemTable').dataTable().fnDestroy();
                    let totalAmt = 0;
                    var columnDef = [
                        {data: 'purchaseId', class: 'hidden'},
                        {data: 'partNo', class: 'commonFields'},
                        {data: 'itemCode'},
                        {data: 'itemName'},
                        {data: 'locationId'},
                        {data: 'qtyBig'},
                        {data: 'unitName'},
                        // {
                        //     data: 'costPrice', class: "right-align",
                        //     render: function (data) {
                        //         return spms.formatAmount(data.toFixed(2));
                        //     }
                        // },
                        {
                            data: 'sellingPrice', class: 'commonFields',
                            // render: function (data, type, row) {
                            //     let amount = row.amount;
                            //     if (row.qtyBig === 0) {
                            //         amount = -1 * amount;
                            //     }
                            //     return spms.formatAmount(amount.toFixed(2));
                            // }
                        },
                        {
                            data: 'action', class: '',
                            render: function (detail, type, row) {
                                let itemCode = row.itemCode;
                                let asOnDate = $('#asOnDate').val();
                                return '<a href=' + "viewItemsForRawMaterial" + '/navigateToDetail?itemCode=' +
                                    encodeURIComponent(itemCode) + "&asOnDate=" + asOnDate + '>' +
                                    '<input type=button" class="btn btn-sm btn-primary td-center" style="width: 70px" value="View"></a>'
                            }
                        },
                    ];

                    $('#itemTable').DataTable({
                        'iDisplayLength': [100],
                        data: res,
                        bSort:false,
                        columns: columnDef,
                        "footerCallback": function (row, data, start, end, display) {
                            var api = this.api(), data;

                            // Remove the formatting to get integer data for summation
                            let intVal = function (i) {
                                return typeof i === 'string' ?
                                    i.replace(/[\$,]/g, '') * 1 :
                                    typeof i === 'number' ?
                                        i : 0;
                            };
                            // Total over all pages
                            totalAmount = api.column(7).data().reduce(function (a, b) {
                                return intVal(a) + intVal(b);
                            }, 0);

                        },
                        'rowCallback': function (row, data) {
                            totalAmount = totalAmount + parseInt(spms.removeCommaSeparation($(row).children(':nth-child(7)').text()));
                        },
                        /*dom: 'Bfrtip',
                        buttons: [{
                            extend: 'pdf',
                            download: 'open',
                            pageSize: 'B5',
                            orientation: 'portrait',
                            exportOptions: {
                                columns: "thead th:not(.noExport)"
                            }
                        }, {
                            extend: 'excelHtml5',
                            download: 'open',
                            exportOptions: {
                                columns: "thead th:not(.noExport)"
                            }
                        }, {
                            extend: 'print',
                            exportOptions: {
                                columns: "thead th:not(.noExport)"
                            }
                        }],*/
                    });
                    $('#closingBal').text(spms.formatAmount(totalAmount.toFixed(2)));
                }, complete: function () {
                    _showHideCol();
                }

            });
        }
    }

    function _showHideCol() {
        if ($('#businessTypeId').val() == 4) {
            $('.commonFields').attr('hidden', true)
        } else {
            $('.commonFields').attr('hidden', false)
        }
    }

    $('#viewItemsForRawMaterialTable').remove('attr', 'style');

    return {
        getItemAvailable: getItemAvailable,
        onAsOnDateChange: onAsOnDateChange
    }
})();

$(document).ready(function () {
    viewItemsForRawMaterial.getItemAvailable();
    viewItemsForRawMaterial.onAsOnDateChange();

});


function onAsOnDateChange() {
    $('#asOnDate').on('change', function () {
        let asOnDate = $(this).val();
        getItemAvailable(asOnDate);
    })
}

onAsOnDateChange()

function getItemAvailable(asOnDate) {
    if (typeof asOnDate == 'undefined') {
        asOnDate = $('#asOnDate').val();
    }
    if (asOnDate !== '') {
        $.ajax({
            url: 'viewItemsForRawMaterial/getItemAvailable',
            type: 'GET',
            data: {asOnDate: asOnDate},
            success: function (res) {
                $('#itemTable').dataTable().fnDestroy();
                let totalAmt = 0;
                var columnDef = [
                    {data: 'purchaseId', class: 'hidden'},
                    // {data: 'partNo', class: 'commonFields'},
                    {data: 'rawMaterialParticularName'},
                    {data: 'rawMaterialParticularQty'},
                    {data: 'rawMaterialParticularUnit'},
                    {data: 'rawMaterialParticularPrice'},
                    {data: 'rawMaterialParticularLocation'},
                    {data: 'rawMaterialParticularLocation'},
                    {data: 'storageModifier'},


                    {
                        data: 'action', class: '',
                        render: function (detail, type, row) {
                            let itemCode = row.itemCode;
                            let asOnDate = $('#asOnDate').val();
                            return '<a href=' + "viewItem" + '/navigateToDetail?itemCode=' +
                                encodeURIComponent(itemCode) + "&asOnDate=" + asOnDate + '>' +
                                '<input type=button" class="btn btn-sm btn-primary td-center" style="width: 70px" value="View"></a>'
                        }
                    },
                ];

                $('#itemTable').DataTable({
                    'iDisplayLength': [100],
                    data: res,
                    bSort:false,
                    columns: columnDef,
                    "footerCallback": function (row, data, start, end, display) {
                        var api = this.api(), data;

                        // Remove the formatting to get integer data for summation
                        let intVal = function (i) {
                            return typeof i === 'string' ?
                                i.replace(/[\$,]/g, '') * 1 :
                                typeof i === 'number' ?
                                    i : 0;
                        };
                        // Total over all pages
                        totalAmount = api.column(7).data().reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    },
                    'rowCallback': function (row, data) {
                        totalAmount = totalAmount + parseInt(spms.removeCommaSeparation($(row).children(':nth-child(7)').text()));
                    },
                    /*dom: 'Bfrtip',
                    buttons: [{
                        extend: 'pdf',
                        download: 'open',
                        pageSize: 'B5',
                        orientation: 'portrait',
                        exportOptions: {
                            columns: "thead th:not(.noExport)"
                        }
                    }, {
                        extend: 'excelHtml5',
                        download: 'open',
                        exportOptions: {
                            columns: "thead th:not(.noExport)"
                        }
                    }, {
                        extend: 'print',
                        exportOptions: {
                            columns: "thead th:not(.noExport)"
                        }
                    }],*/
                });
                $('#closingBal').text(spms.formatAmount(totalAmount.toFixed(2)));
            }, complete: function () {
                _showHideCol();
            }

        });
    }
}

