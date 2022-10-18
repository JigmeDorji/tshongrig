/**
 * Created by Bcass Sawa on 5/19/2019.
 */

voucherGroupList = (function () {
    let totalDrAmount = 0;
    let totalCrAmount = 0;
    let retainedEarningDr = 0;
    let retainedEarningCr = 0;
    let bookBalanceAmount = 0;
    let calculatedBankReconciliationAmount = 0;
    let selectedRow = 0;
    let voucherListTable;
    let voucherListGrid = $('#voucherListGrid');
    let rows = document.getElementById("voucherListGrid").children[1].children;
    let splitURL = location.search.split('ledgerId=')[1];
    let ledgerId = splitURL.split('&&')[0];
    let ledgerName = location.search.split('ledgerName=')[1].split('&&==')[0];
    $('#ledgerName').val(ledgerName);

    function loadVoucherDetails(fromDate, toDate) {

        fromDate = typeof fromDate === 'undefined' ? $('#fromDate').val() : fromDate;
        toDate = typeof toDate === 'undefined' ? $('#toDate').val() : toDate;

        $.ajax({
            url: 'voucherGroupList/getVoucherDetailsByLedgerId',
            type: 'GET',
            data: {
                ledgerId: ledgerId,
                fromDate: fromDate,
                toDate: toDate
            },
            success: function (res) {
                $('#totalClosingBalanceCr').val('');
                $('#totalClosingBalanceDr').val('');
                voucherListGrid.dataTable().fnClearTable();
                voucherListGrid.dataTable().fnDestroy();
                if (res.length > 0) {
                    totalDrAmount = res[0].totalDebit;
                    totalCrAmount = res[0].totalCredit;
                    let columnDef = [
                        {data: 'voucherCreatedDate', class: 'text-center'},
                        {data: 'voucherNo', class: 'voucherNo text-center'},
                        {
                            data: 'voucherTypeName', class: 'text-center',
                            render: function (data) {
                                return ledgerName === "Discount" ? "Sales" : data;
                            }
                        },
                        {data: 'voucherTypeId', class: 'hidden voucherTypeId'},
                        {
                            data: 'debitAmount', class: 'text-center',
                            render: function (data) {
                                let amount = '';
                                if (data != null) {
                                    amount = spms.formatAmount(data.toFixed(2));
                                }
                                return amount;
                            }
                        },
                        {
                            data: 'creditAmount',
                            class: 'text-center',
                            render: function (data) {
                                let amount = '';
                                if (data != null) {
                                    amount = spms.formatAmount(data.toFixed(2));
                                }
                                return amount;
                            }
                        }, {
                            data: 'voucherTypeName',
                            render: function (data, type, row) {
                                let typeName = row.voucherTypeName;
                                let btn = row.voucherTypeName === 'Purchase' || ledgerName === 'Purchase' ? '<a  href=' + "voucherGroupList" + '/navigateToPurchasePage?voucherNo=' +
                                    encodeURIComponent(row.voucherNo) + "&voucherNo=" + row.voucherNo + '>' +
                                    '<input type=button" class="btn btn-sm btn-primary btn-xs" style="width: 70px" value="View"></a>' :
                                    typeName !== "Salary Admin" && typeName !== "Salary Production" && typeName !== "GIS" && typeName !== "PF(Employee)" && typeName !== "PF(Employer)" && typeName !== "Salary Payable" && typeName !== "HC" && typeName !== "Salary TDS" && typeName !== "Material" ? '<input type="button" class="btn btn-sm btn-danger btn-xs deleteBtn" style="width: 70px" value="Delete">' : ''
                                //return '<a href=' + "voucherGroupList" + '/deleteLedgerVoucherDetails?voucherNo=' + encodeURIComponent(row.voucherNo) + "&voucherTypeId=" + row.voucherTypeId + '>' + '<input type="button" class="btn btn-danger btn-xs deleteBtn" style="width: 70px" value="Delete"></a>'
                                return btn
                            }
                        }
                    ];
                    voucherListTable = voucherListGrid.DataTable({
                        data: res,
                        info: false,
                        paging: false,
                        searching: true,
                        bSort: false,
                        'autoWidth': false,
                        columns: columnDef
                    });
                } else {
                    totalDrAmount = 0.00;
                    totalCrAmount = 0.00;
                }
            }
        }).then(function () {
            //load opening balance
            $.ajax({
                url: 'voucherGroupList/getOpeningBalance',
                type: 'GET',
                async: false,
                data: {
                    ledgerId: ledgerId,
                    fromDate: fromDate,
                    toDate: toDate
                },
                success: function (res) {
                    if (res.openingBal > 0) {
                        $('.openingBalance').val((res.openingBal).toFixed(2));
                        $('.openBalanceAmount').val(0.00.toFixed(2));
                    } else {
                        let openingAmt = res.openingBal == null ? 0 : res.openingBal;
                        $('.openingBalance').val(0.00.toFixed(2));
                        $('.openBalanceAmount').val(Math.abs((openingAmt).toFixed(2)));
                    }

                    if (res.accTypeId === 6) {//if its a capital acc type id
                        $('#retainedEarningArea').attr('hidden', false);
                        $('#retainedEarningDr').val((0).toFixed(2));
                        $('#retainedEarningCr').val((0).toFixed(2));
                        if (res.retainedEarning > 0) {
                            $('#retainedEarningCr').val((res.retainedEarning).toFixed(2));
                            totalCrAmount = totalCrAmount + res.retainedEarning;
                        } else {
                            $('#retainedEarningDr').val(Math.abs(res.retainedEarning).toFixed(2));
                            totalDrAmount = totalDrAmount + res.retainedEarning;
                        }

                        if (res.currentEarning > 0) {
                            $('#totalCr').val((res.currentEarning).toFixed(2));
                            totalCrAmount = totalCrAmount + res.currentEarning;
                        } else {
                            $('#totalDr').val(Math.abs(res.currentEarning).toFixed(2));
                            totalDrAmount = totalDrAmount + res.currentEarning;
                        }


                    } else {
                        $('#retainedEarningArea').attr('hidden', true);
                    }

                    $('#ledgerName').val(res.ledgerName);
                    $('#totalDr').val(spms.formatAmount((res.currentEarning).toFixed(2)));
                    $('#totalCr').val(spms.formatAmount((res.currentEarning).toFixed(2)));

                    // if (res.retainedEarning > 0) {
                    //     totalCrAmount=totalCrAmount+res.retainedEarning;
                    // } else {
                    //     totalDrAmount=totalDrAmount+res.retainedEarning;
                    // }


                    let netDrAmount = (totalDrAmount) ;
                    let netCrAmount = (totalCrAmount) + (res.openingBal);
                    if ((netDrAmount - (Math.abs(totalCrAmount))).toFixed(2) < 0) {
                        $('#totalClosingBalanceCr').val(spms.formatAmount(Math.abs(Math.abs(netDrAmount) - (Math.abs(netCrAmount))).toFixed(2)));
                        bookBalanceAmount = $('#totalClosingBalanceCr').val();
                    } else {
                        $('#totalClosingBalanceDr').val(spms.formatAmount(Math.abs(netDrAmount - (Math.abs(netCrAmount))).toFixed(2)));
                        bookBalanceAmount = $('#totalClosingBalanceDr').val();
                    }
                    $('#bookBalance').val(spms.removeCommaSeparation(bookBalanceAmount));
                    $('#bankReconciliationAmount').val(spms.removeCommaSeparation(bookBalanceAmount));
                }
            }).then(function () {
                if ($('#ledgerName').val() === "Salary Payable" || $('#ledgerName').val() === "Salary Production"
                    || $('#ledgerName').val() === "Salary Admin" || $('#ledgerName').val() === "HC"
                    || $('#ledgerName').val() === "Salary TDS" || $('#ledgerName').val() === "GIS"
                    || $('#ledgerName').val() === "PF(Employee)" || $('#ledgerName').val() === "PF(Employer)"

                ) {
                    voucherListGrid.find('tbody tr .deleteBtn').addClass('hidden', true);
                }
            });

            //get party detail
            $.ajax({
                url: 'saleInvoiceGeneration/getPartyDetail',
                type: 'GET',
                data: {partyName: ledgerName},
                success: function (res) {
                    if (typeof res.partyAddress !== 'undefined') {
                        $('.partyDetail').removeClass('hidden');
                        populate(res);
                    } else {
                        $('.partyDetail').addClass('hidden');
                    }
                }
            })

        }).then(function () {

            let directDeposit = $('#directDeposit');
            let directTransfer = $('#directTransfer');
            let chequeIssuedNotEncash = $('#chequeIssuedNotEncash');
            let bankReconciliationAmount = $('#bankReconciliationAmount');
            let previousMonthChequeEncash = $('#previousMonthChequeEncash');
            calculatedBankReconciliationAmount = parseInt(spms.removeCommaSeparation(bankReconciliationAmount.val()));

            chequeIssuedNotEncash.on('keyup', function () {
                let chequeIssuedNotEncash = $(this).val() === '' ? 0 : parseInt($(this).val());
                let totalSumAmountToDeduct = parseInt(directTransfer.val()) + parseInt(previousMonthChequeEncash.val());

                if (totalSumAmountToDeduct > 0) {
                    bankReconciliationAmount.val((calculatedBankReconciliationAmount + chequeIssuedNotEncash + parseInt(directDeposit.val())) - totalSumAmountToDeduct);
                } else {
                    bankReconciliationAmount.val(parseInt(calculatedBankReconciliationAmount) + chequeIssuedNotEncash);
                }
            });

            directDeposit.on('keyup', function () {
                let directDeposit = $(this).val() === '' ? 0 : parseInt($(this).val());
                let totalSumAmountToDeduct = parseInt(directTransfer.val()) + parseInt(previousMonthChequeEncash.val());
                if (totalSumAmountToDeduct > 0) {
                    bankReconciliationAmount.val((calculatedBankReconciliationAmount + directDeposit + parseInt(chequeIssuedNotEncash.val())) - totalSumAmountToDeduct);
                } else {
                    bankReconciliationAmount.val(calculatedBankReconciliationAmount + directDeposit + parseInt(chequeIssuedNotEncash.val()));
                }
            });

            directTransfer.on('keyup', function () {
                let directTransfer = $(this).val() === '' ? 0 : parseInt($(this).val());
                bankReconciliationAmount.val((calculatedBankReconciliationAmount + parseInt(directDeposit.val()) + parseInt(chequeIssuedNotEncash.val()))
                    - (directTransfer + parseInt(previousMonthChequeEncash.val())))
            });

            previousMonthChequeEncash.on('keyup', function () {
                let previousMonthChequeEncash = $(this).val() === '' ? 0 : parseInt($(this).val());
                bankReconciliationAmount.val((calculatedBankReconciliationAmount + parseInt(directDeposit.val()) + parseInt(chequeIssuedNotEncash.val()))
                    - (previousMonthChequeEncash + parseInt(directTransfer.val())))
            });
        });

        document.body.onkeydown = function (e) {
            rows[selectedRow].style.backgroundColor = "#FFFFFF";
            if (e.keyCode === 38) {
                selectedRow--;
            } else if (e.keyCode === 40) {
                selectedRow++;
            } else if (e.keyCode === 13) {
                window.location.href = spms.getUrl() + 'voucherCreation?voucherNo=' + rows[selectedRow].cells[1].innerHTML + '&&voucherTypeId=' + rows[selectedRow].cells[3].innerHTML;
            }
            if (selectedRow >= rows.length) {
                selectedRow = 0;
            } else if (selectedRow < 0) {
                selectedRow = rows.length - 1;
            }
            rows[selectedRow].style.backgroundColor = "#B0BED9";
        };
        // rows[0].style.backgroundColor = "#B0BED9";
    }

    function getVoucherDetailsBasedOnDateRage() {
        $('.dateRange').on('change', function () {
            let fromDate = $('#fromDate').val();
            let toDate = $('#toDate').val();
            if (fromDate !== '' && toDate !== '') {
                loadVoucherDetails(fromDate, toDate);
            }
        })

    }

    $('#reconciliationId').on('click', function () {
        $('#reconciliationShow').attr("hidden", false);
    });

    function saveBankReconciliation() {
        $('#btnSaveReconcilAmount').on('click', function () {
            $.ajax({
                url: 'voucherGroupList/saveBankReconciliation',
                type: 'POST',
                data: $("#voucherListForm").serializeArray(),
                success: function (res) {
                    if (res.status === 1) {
                        swal({
                            title: res.text,
                            text: "Click OK to exit",
                            type: "success"
                        }, function () {
                            window.location.reload();
                        });
                    }
                }
            })
        });
    }


    function deleteLedgerVoucherDetails() {
        $('#voucherListGrid').on('click', '.deleteBtn', function () {
            let voucherNo = $(this).closest('tr').find('.voucherNo').text();
            let voucherTypeId = $(this).closest('tr').find('.voucherTypeId').text();

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
                        url: 'voucherGroupList/deleteLedgerVoucherDetails',
                        type: 'GET',
                        data: {voucherNo: voucherNo, voucherTypeId: voucherTypeId},
                        success: function (res) {
                            if (res.status === 0) {
                                swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "warning"
                                });
                            } else {
                                if (parseInt(voucherTypeId) === 5) {
                                    $.ajax({
                                        url: 'voucherGroupList/updateSaleDetailTable',
                                        type: 'GET',
                                        async: false,
                                        data: {voucherNo: voucherNo},
                                        success: function (res) {
                                            if (res.status === 1) {
                                                swal({
                                                    title: res.text,
                                                    text: "Click OK to exit",
                                                    type: "success"
                                                }, function (isConfirm) {
                                                    if (isConfirm) {
                                                        loadVoucherDetails();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    swal({
                                        title: res.text,
                                        text: "Click OK to exit",
                                        type: "success"
                                    });
                                    loadVoucherDetails();
                                }
                            }
                        }
                    });
                } else {
                    swal("Cancelled", "You have cancelled the operation.", "warning");
                }
            });


        })
    }


    function navigateToPrevious() {
        $('#previousPage').on('click', function () {
            window.location.href = spms.getUrl() + 'ledger';
        })
    }

    function viewPurchaseDetails() {
        $('#voucherListGrid').on('click', '.viewBtn', function () {
            let voucherNo = $(this).closest('tr').find('.voucherNo').text();
            let voucherTypeId = $(this).closest('tr').find('.voucherTypeId').text();
            $.ajax({
                url: 'voucherGroupList/deleteLedgerVoucherDetails',
                type: 'GET',
                data: {voucherNo: voucherNo, voucherTypeId: voucherTypeId},
                success: function (res) {

                }
            });
        })
    }

    return {
        getVoucherDetailsBasedOnDateRage: getVoucherDetailsBasedOnDateRage,
        navigateToPrevious: navigateToPrevious,
        deleteLedgerVoucherDetails: deleteLedgerVoucherDetails,
        saveBankReconciliation: saveBankReconciliation,
        loadVoucherDetails: loadVoucherDetails
    }
})();
$(document).ready(function () {
    voucherGroupList.getVoucherDetailsBasedOnDateRage();
    voucherGroupList.navigateToPrevious();
    voucherGroupList.deleteLedgerVoucherDetails();
    voucherGroupList.saveBankReconciliation();
    voucherGroupList.loadVoucherDetails();
});