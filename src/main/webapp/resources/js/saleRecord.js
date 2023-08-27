/**
 * Created by SonamPC on 12-Dec-16.
 */

saleRecord = (function () {
    //To save the data
    function generateReport() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'saleRecord/generateReport',
                    type: 'POST',
                    data: $(form).serializeArray()
                });
            }
        });

    }

    return {
        // generateReport:generateReport
        //

    }
})
();

$(document).ready(function () {
    //saleRecord.generateReport();
    // alert("sd")
});

$.ajax({
    url: 'saleRecord/getTodaySalesReport',
    type: 'GET',
    success: function (res) {
        console.log(res);
        $('#database').DataTable().destroy(); // Destroy the existing DataTable instance

        var totalAmount = 0;
        var columnDef = [
            {data: 'serialNumber', className: 'commonFields'},
            {data: 'itemCode'},
            {data: 'itemName'},
            {data: 'sumQty'},
            {data: 'sellingPrice'},
            {data: 'amount', className: 'commonFields'}
        ];

        $('#database').DataTable({
            data: res,
            columns: columnDef,
            columnDefs: [
                {targets: [0, 5], orderable: false} // Disable sorting for column 0 (SI.NO) and 5 (Amount)
            ],
            footerCallback: function (row, data, start, end, display) {
                var api = this.api();

                // Remove the formatting to get integer data for summation
                var intVal = function (i) {
                    return typeof i === 'string' ? i.replace(/[\$,]/g, '') * 1 : typeof i === 'number' ? i : 0;
                };

                // Total over all pages
                totalAmount = api.column(5, {page: 'all'}).data().reduce(function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0);

                $('#inventorySaleTotalAmount').text(totalAmount.toFixed(2));

                const totalAmtInvoiceSale = parseInt($('#totalAmtInvoiceSale').text());
                if (isNaN(totalAmtInvoiceSale)) {
                    errorMsg("Invalid totalAmtInvoiceSale");
                } else {
                    const totalSaleAmount = totalAmtInvoiceSale + totalAmount;
                    $('#totalSale').text(totalSaleAmount.toFixed(2));
                }
            }
        });
    },
    complete: function () {
        // Perform any additional actions after the data has been loaded and the DataTable is initialized
    }
});


// $.ajax({
//     url: 'saleRecord/getTodaySalesReport',
//     type: 'GET',
//     success: function (res) {
//         console.log(res);
//         $('#database').DataTable().destroy(); // Destroy the existing DataTable instance
//
//         var totalAmount = 0;
//         var columnDef = [
//             { data: 'serialNumber', className: 'commonFields' },
//             { data: 'itemCode' },
//             { data: 'itemName' },
//             { data: 'sumQty' },
//             { data: 'sellingPrice' },
//             { data: 'amount', className: 'commonFields' }
//         ];
//
//         $('#database').DataTable({
//             data: res,
//             columns: columnDef,
//             columnDefs: [
//                 { targets: [0, 5], orderable: false } // Disable sorting for column 0 (SI.NO) and 5 (Amount)
//             ],
//             footerCallback: function (row, data, start, end, display) {
//                 var api = this.api();
//
//                 // Remove the formatting to get integer data for summation
//                 var intVal = function (i) {
//                     return typeof i === 'string' ?
//                         i.replace(/[\$,]/g, '') * 1 :
//                         typeof i === 'number' ?
//                             i : 0;
//                 };
//
//                 // Total over all pages
//                 totalAmount = api.column(5, { page: 'all' }).data().reduce(function (a, b) {
//                     return intVal(a) + intVal(b);
//                 }, 0);
//                 // Update the Total amount in the footer
//                 // $(api.column(5).footer()).html('<strong>Inventory Sale:</strong> ' + totalAmount);
//                 //
//                 // // Update other footer rows with external source values
//                 // $(row).find('td:eq(1)').html('${totalAmtInvoiceSale}');
//                 // $(row).find('td:eq(3)').html('12');
//                 // $(row).find('td:eq(5)').html('12');
//                 // $(row).find('td:eq(7)').html('12');
//                 // $(row).find('td:eq(9)').html('12');
//                 // $(row).find('td:eq(11)').html('12');
//                 // $(row).find('td:eq(13)').html('12');
//             }
//         });
//     },
//     complete: function () {
//         // Perform any additional actions after the data has been loaded and the DataTable is initialized
//     }
// });
