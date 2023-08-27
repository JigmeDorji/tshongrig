/**
 * Created by SonamPC on 12-Dec-16.
 */

let isGeneralTrader = generalTrader().isGeneralTrader("openingBalanceInventory");

// alert(isGeneralTrader)
viewItem = (function () {


    /* var viewItemTable = $('#viewItemTable').dataTable({
     "paging": false,
     "searching": false,
     "info": false

     });*/
    /* var selectedItemViewTable = $('#selectedItemViewTable').dataTable({
     "paging": false,
     "searching": false,
     "info": false

     });*/
    //to load the existing list of users
    let totalAmount = 0;

    function onAsOnDateChange() {
        $('#asOnDate').on('change', function () {
            let asOnDate = $(this).val();
            getItemAvailable(asOnDate);
        })
    }


    // function getItemAvailable(asOnDate) {
    //     if (typeof asOnDate == 'undefined') {
    //         asOnDate = $('#asOnDate').val();
    //     }
    //     if (asOnDate !== '') {
    //         $.ajax({
    //             url: 'closingStock/getItemAvailable',
    //             type: 'GET',
    //             data: {asOnDate: asOnDate},
    //             success: function (res) {
    //                 $('#itemTable').dataTable().fnDestroy();
    //                 let totalAmt = 0;
    //                 var columnDef = [
    //                     {data: 'purchaseId', class: 'hidden'},
    //                     {data: 'purchaseId', class: 'hidden'},
    //                     {data: 'serialNo', class: 'commonFields'},
    //                     {data: 'itemCode'},
    //                     {data: 'itemName'},
    //                     {data: 'locationId'},
    //                     {data: 'qtyBig'},
    //                     {data: 'unitName'},
    //                     {
    //                         data: 'costPrice', class: 'commonFields',
    //                     },
    //                     {
    //                         data: 'closingStockAmount', class: '',
    //
    //                     },
    //
    //                 ];
    //
    //                 $('#itemTable').DataTable({
    //                     'iDisplayLength': [100],
    //                     data: res,
    //                     bSort: false,
    //                     columns: columnDef,
    //                     "footerCallback": function (row, data, start, end, display) {
    //                         var api = this.api(), data;
    //
    //                         // Remove the formatting to get integer data for summation
    //                         let intVal = function (i) {
    //                             return typeof i === 'string' ?
    //                                 i.replace(/[\$,]/g, '') * 1 :
    //                                 typeof i === 'number' ?
    //                                     i : 0;
    //                         };
    //                         // Total over all pages
    //                         totalAmount = api.column(7).data().reduce(function (a, b) {
    //                             return intVal(a) + intVal(b);
    //                         }, 0);
    //
    //
    //                     },
    //                     'rowCallback': function (row, data) {
    //                         totalAmount = totalAmount + parseInt(spms.removeCommaSeparation($(row).children(':nth-child(7)').text()));
    //                         // totalAmount = res;
    //                         let totalStockAmount = 0;
    //                         for (let i = 0; i < res.length; i++) {
    //                             totalStockAmount += res[i].qtyBig * res[i].costPrice;
    //                         }
    //                         alert(spms.formatAmount(totalStockAmount.toFixed(3)))
    //                     },
    //                 });
    //                 $('#closingBal').text(spms.formatAmount(totalAmount.toFixed(2)));
    //             }, complete: function () {
    //                 _showHideCol();
    //             }
    //
    //         });
    //     }
    // }
    function getItemAvailable(asOnDate) {
        if (typeof asOnDate == 'undefined') {
            asOnDate = $('#asOnDate').val();
        }
        if (asOnDate !== '') {
            $.ajax({
                url: 'closingStock/getItemAvailable',
                type: 'GET',
                data: {asOnDate: asOnDate},
                success: function (res) {
                    $('#itemTable').dataTable().fnDestroy();
                    var totalAmount = 0;
                    let totalStockAmount = 0;
                    var columnDef = [
                        {data: 'purchaseId', class: 'hidden'},
                        {data: 'purchaseId', class: 'hidden'},
                        {data: 'serialNo', class: 'commonFields'},
                        {data: 'itemCode'},
                        {data: 'itemName'},
                        {data: 'locationId'},
                        {data: 'qtyBig'},
                        {data: 'unitName'},
                        {data: 'costPrice', class: 'commonFields'},
                        {data: 'closingStockAmount', class: ''},
                    ];

                    $('#itemTable').DataTable({
                        'iDisplayLength': [100],
                        data: res,
                        bSort: false,
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

                            // Calculate and display the customized data

                            for (let i = 0; i < res.length; i++) {
                                totalStockAmount += res[i].qtyBig * res[i].costPrice;
                            }
                            $(api.column(9).footer()).html(spms.formatAmount(totalStockAmount.toFixed(3)));
                        },
                        'rowCallback': function (row, data) {
                            totalAmount += parseInt(spms.removeCommaSeparation($(row).children(':nth-child(7)').text()));
                        },
                    });

                    $('#totalClosingStock').text((spms.formatAmount(totalStockAmount.toFixed(3))));
                },
                complete: function () {
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

    $('#viewItemTable').remove('attr', 'style');

    return {
        getItemAvailable: getItemAvailable,
        onAsOnDateChange: onAsOnDateChange
    }
})();

$(document).ready(function () {
    viewItem.getItemAvailable();
    viewItem.onAsOnDateChange();

});
