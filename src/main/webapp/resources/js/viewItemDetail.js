/**
 * Created by jigmePc on 8/25/2019.
 */

viewItemDetail = (function () {
    //to load the existing list of users
    function getItemDetail() {
        let itemCode = $('#itemCode').val();
        let asOnDate = $('#asOnDate').val();
        if (itemCode !== '') {
            $.ajax({
                url: 'viewItemDetail/getItemDetail',
                type: 'GET',
                data: {itemCode: itemCode, asOnDate: asOnDate},
                success: function (res) {
                    var columnDef = [
                        {
                            data: 'index',
                            render: function (index) {
                                return index + 1;
                            }
                        },
                        {data: 'purchaseId', class: 'hidden'},
                        {data: 'voucherNo', class: 'voucherNo hidden'},
                        {
                            data: 'purchaseDate',
                            render: function (data) {
                                return formatAsDate(data);
                            }
                        },
                        {
                            data: 'particular', class: 'particular',
                            render: function (data, type, row) {
                                return row.voucherNo === 0 ? "Opening" : parseInt($('#businessType').val()) === 4 && row.particular==="Sale" ? "Issue" : row.particular;
                            }
                        },
                        {data: 'qty', class: 'qty'},
                        {data: 'sellingPrice'},
                        {data: 'balance'},
                        {
                            data: 'action',
                            render: function (detail, type, row) {
                                var purchaseId = row.purchaseId;
                                var particular = row.particular;
                                var purchaseDate = row.purchaseDate;
                                var voucherNo = row.voucherNo;
                                if (particular === 'Purchase' || particular === 'Opening Balance') {
                                    if ($('#hasEditRole').val().toString() === 'true') {
                                        if (parseInt(voucherNo) !== 0) {
                                            return '<a href=' + "viewItemDetail" + '/navigateToPurchasePage?purchaseId=' +
                                                encodeURIComponent(purchaseId) + "&purchaseDate=" + purchaseDate + "&voucherNo=" + voucherNo + '>' +
                                                '<input type=button" class="btn btn-primary btn-xs" value="Edit"></a>'
                                        } else {
                                            return '<a href=' + "viewItemDetail" + '/navigateToPurchaseOpeningPage?purchaseId=' +
                                                encodeURIComponent(purchaseId) + "&purchaseDate=" + purchaseDate + "&voucherNo=" + voucherNo + '>' +
                                                '<input type=button" class="btn btn-primary btn-xs" value="Edit"></a>'
                                        }
                                    } else {
                                        return '';
                                    }

                                } else {
                                    return '';
                                }
                            }
                        },
                        {
                            data: 'action',class: 'hidden',
                            render: function (detail, type, row) {
                                let particular = row.particular;
                                if (particular === 'Purchase') {
                                    if ($('#hasDeleteRole').val().toString() === 'true') {
                                        return ''// '<input type="button" class="btn btn-danger btn-xs" id="deleteBtn" class="" value="Delete">'
                                    } else {
                                        return '';
                                    }
                                } else {
                                    return '';
                                }
                            }
                        }

                    ];

                    var order = [[1, 'asc']];
                    var t = $('#viewItemDetailTable').DataTable({
                        data: res,
                        columns: columnDef
                    });
                    t.on('order.dt search.dt', function () {
                        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1;
                        });
                    }).draw()

                }

            });
        } else {
            window.location.href = spms.getUrl() + 'viewItem';
        }

        //})
    }


    function on_Click_ViewBtn() {
        $('#btnView').on('click', function () {
            $('#selectedItemViewTable').dataTable().fnDestroy();
            if ($('#itemCategoryId').val() == 0 && $('#fromDate').val() == "" && $('#toDate').val() == "") {
                $('#fromDate').focus();
                errorMsg('Please select the date range.');
            } else if ($('#itemCategoryId').val() == '') {
                $('#itemCategoryId').focus();
                errorMsg('Please select item type.')
            } else {
                $('#selectedItemView').attr('hidden', false);
                $('#allItemView').attr('hidden', true);
                var itemCategoryId = $('#itemCategoryId').val();
                var fromDate = $('#fromDate').val();
                var toDate = $('#toDate').val();
                $.ajax({
                    url: 'viewItem/getSpecificItem',
                    type: 'GET',
                    data: {itemCategoryId: itemCategoryId, strFromDate: fromDate, strToDate: toDate},
                    success: function (res) {
                        //$('#selectedItemViewTable').dataTable().fnClearTable();
                        var columnDef = [
                            {
                                data: 'index',
                                render: function (index) {
                                    return index + 1;
                                }
                            },
                            {data: 'itemCategoryId', class: 'hidden'},
                            {data: 'partNumber'},
                            {data: 'partDescription'},
                            {data: 'locationId'},
                            {data: 'qty'},
                            {data: 'pricePerQty'},
                            {
                                data: 'action',
                                render: function (data) {
                                    return '<input type="button" class="btn btn-primary btn-xs" id="editBtn" class="" value="Edit">'
                                }
                            },
                        ];
                        var order = [[1, 'asc']];
                        var t = $('#selectedItemViewTable').DataTable({
                            data: res,
                            columns: columnDef
                        });
                        t.on('order.dt search.dt', function () {
                            t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                                cell.innerHTML = i + 1;
                            });
                        }).draw();

                        /*   for (var i in res) {
                         selectedItemViewTable.fnAddData([
                         '<input type="hidden" id="itemCategoryId" class="form-control" readonly="true" value="' + res[i].itemCategoryId + '"><input type="text" id="indexS" class="form-control" readonly="true" value="' + (parseInt(i) + 1) + '">',
                         '<input type="text"  id="itemName" class="form-control" readonly="true" value="' + res[i].partNumber + '">',
                         '<input type="text"  id="itemName" class="form-control" readonly="true" value="' + res[i].partDescription + '">',
                         '<input type="text"  id="itemName" class="form-control" readonly="true" value="' + res[i].locationId + '">',
                         '<input type="text" id="qty" class="form-control" readonly="true" value="' + res[i].qty + '">',
                         '<input type="text" id="pricePerQty" class="form-control" readonly="true" value="' + res[i].pricePerQty + '">',
                         '<input type="button" id="editBtn"  value="Edit">'
                         ]);
                         }*/
                    }
                });
            }
        })
    }

    function onClickDelete() {
        $('#viewItemDetailTable tbody').on('click', 'tr #deleteBtn', function () {
            let selectedRow = $(this).closest('tr');
            let voucherNo = selectedRow.find(".voucherNo").text();
            let itemCode = $("#itemCode").val();
            let qty = selectedRow.find(".qty").text();
            if (checkBalQtyIsNegative(qty)) {
                errorMsg("Negative Balance.")
                return false;
            }

            swal({
                title: "Confirmation",
                text: "Are you sure that you want to proceed ?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#dd6b55",
                confirmButtonText: "Confirm",
                cancelButtonText: "Cancel",
                closeOnConfirm: false,
                closeOnCancel: false
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: 'viewItemDetail/deletePurchaseRelatedVoucher',
                        type: 'GET',
                        data: {voucherNo: voucherNo, itemCode: itemCode, qty: qty},
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    type: "success",
                                    title: res.text,
                                }, function () {
                                    window.location.reload();
                                });
                            } else {
                                errorMsg(res.text)
                            }
                        }
                    });
                } else {
                    swal("Cancelled", "You have cancelled the operation.", "warning");
                }
            });
        })
    }

    function onClickBack() {
        $('#backBtn').on('click', function () {
            window.location.href = spms.getUrl() + 'viewItem';
        })
    }


    function checkBalQtyIsNegative(currentPurchaseDelQty) {
        let totalPurchaseQty = 0;
        let totalSaleQty = 0;
        $('#viewItemDetailTable tbody').find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            let particular = selectedRow.find('.particular').text();
            if (particular === 'Purchase' || particular === 'Opening') {
                totalPurchaseQty = totalPurchaseQty + parseFloat(selectedRow.find('.qty').text());
            }
            if (particular === 'Sale') {
                totalSaleQty = totalSaleQty + parseFloat(selectedRow.find('.qty').text());
            }
        });

        totalPurchaseQty = totalPurchaseQty - currentPurchaseDelQty;

        if ((totalPurchaseQty - totalSaleQty) < 0) {
            return true;
        } else
            return false;
    }

    return {
        getItemDetail: getItemDetail,
        onClickBack: onClickBack,
        onClickDelete: onClickDelete
    }
})();

$(document).ready(function () {
    viewItemDetail.getItemDetail();
    viewItemDetail.onClickBack();
    viewItemDetail.onClickDelete();
});
