saleDetail = (function () {
    function getSaleList() {
        displaySaleDetail($('#fromDate').val(), $('#toDate').val());
    }

    function displaySaleDetail(fromDate, toDate) {

        if (fromDate !== '' && toDate !== '') {
            $.ajax({
                url: 'saleDetail/getSaleList',
                type: 'GET',
                data: {fromDate: fromDate, toDate: toDate},
                success: function (res) {
                    let i = 1;
                    let columnDef = [
                        {data: 'saleRecordId', class: 'saleRecordId hidden'},
                        {data: 'voucherNo', class: 'voucherNo hidden'},
                        {
                            data: 'rowNo',
                            render: function () {
                                return i++;
                            }
                        }, {
                            data: 'saleDate',
                            render: function (data) {
                                return formatAsDate(data)
                            }
                        },

                        {data: 'receiptMemoNo', class: 'receiptMemoNo'},
                        {data: 'invoiceNo', class: 'invoiceNo'},
                        {data: 'totalAmount', class: 'totalAmount'},
                        {
                            data: 'action',
                            render: function (detail, type, row) {
                                let deleteBtn = '';
                                let editBtn = '';
                                let hasDeleteRole = $('#hasDeleteRole').val();
                                let hasEditRole = $('#hasEditRole').val();

                                if (hasDeleteRole.toString() === 'true') {
                                    deleteBtn = '<button  type="button" class="btn btn-danger btn-xs" id="btnDelete"><i class="fa fa-trash"></i> Delete</button>';
                                }
                                if (hasEditRole.toString() === 'true') {
                                    editBtn = '<button type="button" class="btn btn-primary btn-xs" id="btnSaleEdit"><i class="fa fa-edit"></i> Edit</button></a>';
                                }

                                return '<a href=' + "saleDetail" + '/edit?receiptMemoNo=' + encodeURIComponent(row.receiptMemoNo) + '&voucherNo=' + encodeURIComponent(row.voucherNo) + '> ' +
                                    editBtn +
                                    '<a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#itemSaleDetailModal"><button  type="button" class="btn btn-info btn-xs" id="btnView"><i class="fa fa-info-circle"></i> View</button></a>' +
                                    deleteBtn
                            }
                        },
                    ];

                    spms.populateTableData($('#saleDetailGrid'), columnDef, res,)
                }
            });
        }
    }

    function deleteSaleRelatedVoucher() {
        $('#saleDetailGrid tbody').on('click', 'tr #btnDelete', function () {
            $('#saleDetailGrid tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');
            selectedRow.addClass('selected');
            confirmMessage("Confirm Deletion ?", function (e) {
                if (e) {
                    $.ajax({
                        url: 'saleDetail/deleteSaleRelatedVoucher',
                        type: 'GET',
                        data: {
                            receiptMemoNo: selectedRow.find('.receiptMemoNo').text(),
                            voucherNo: selectedRow.find('.voucherNo').text()
                        },
                        success: function (res) {
                            if (res.status === 1) {
                                successMsg(res.text);
                                getSaleList();
                            } else {
                                errorMsg(res.text)
                            }
                        }
                    });
                }
            })
        });
    }


    function getSaleDetailView() {
        $('#saleDetailGrid tbody').on('click', 'tr #btnView', function () {
            $('#saleDetailGrid tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');
            selectedRow.addClass('selected');
            $('#itemSaleDetailModal').modal('show');

            $.ajax({
                url: 'saleDetail/getSaleDetailView',
                type: 'GET',
                data: {voucherNo: selectedRow.find('.voucherNo').text()},
                success: function (res) {
                    let i = 1;
                    let columnDef = [
                        {
                            data: 'rowNo',
                            render: function () {
                                return i++;
                            }
                        },
                        {data: 'itemCode'},
                        {data: 'itemName', class: 'itemName'},
                        {data: 'sellingPrice', class: 'sellingPrice'},
                        {data: 'qty', class: 'qty'},
                        {data: 'totalAmount', class: 'totalAmount'},
                    ];
                    spms.populateTableData($('#saleDetailViewGrid'), columnDef, res.saleItemListDTO);
                    $('#saleDetailViewGrid').css('width', '100%');

                }
            });
        });
    }

    function onchangeDateRange() {
        $('#fromDate').on('change', function () {
            displaySaleDetail($(this).val(), $('#toDate').val())
        })

        $('#toDate').on('change', function () {
            displaySaleDetail($('#fromDate').val(), $(this).val());
        })
    }

    return {
        getSaleList: getSaleList,
        getSaleDetailView: getSaleDetailView,
        deleteSaleRelatedVoucher: deleteSaleRelatedVoucher,
        onchangeDateRange: onchangeDateRange
    }
})();

$(document).ready(function () {
    saleDetail.getSaleList();
    saleDetail.getSaleDetailView();
    saleDetail.deleteSaleRelatedVoucher();
    saleDetail.onchangeDateRange();

});