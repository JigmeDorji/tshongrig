/**
 * Created by jigmePc on 5/4/2019.
 */


ledger = (function () {


    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                if ($('#reconciliationDate').val() === '') {
                    $('#reconciliationDate').attr('disabled', true);
                }
                $.ajax({
                    url: 'ledger/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                }
                            )
                        } else {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "warning"
                            });
                        }
                    }
                });
            }
        });
    }

    function onSelectAccType() {
        $('#accTypeId').on('change', function () {
            $.ajax({
                url: 'ledger/isBankAccType',
                type: 'GET',
                data: {accTypeId: $('#accTypeId').val()},
                success: function (res) {
                    if (res) {
                        $('.bankAccDetail').attr('hidden', false);
                        $('.bankAccDetail').attr('disabled', false);
                    } else {
                        $('.bankAccDetail').attr('hidden', true);
                        $('.bankAccDetail').attr('disabled', true);
                    }
                }
            });
        })
    }

    function getLedgerList() {
        $.ajax({
            url: 'ledger/getLedgerList',
            type: 'GET',
            success: function (res) {
                var columnDef = [
                    {
                        data: 'iterator',
                        render: function () {
                            return 1;
                        }
                    },
                    {data: 'ledgerId', class: 'ledgerId hidden'},
                    {data: 'ledgerName', class: 'ledgerName left-align'},
                    {data: 'accTypeId', class: 'accTypeId hidden'},
                    {
                        data: 'iterator',
                        render: function () {
                            let deleteBtn = '';
                            let editBtn = '';
                            let hasDeleteRole = $('#hasDeleteRole').val();
                            let hasEditRole = $('#hasEditRole').val();
                            if (hasDeleteRole.toString() === 'true') {
                                deleteBtn = '<a href="#" id="deleteBtn" class="btn btn-sm btn-danger btn-xs ml-3 d-none d-sm-inline-block deleteBtn" data-toggle="modal" data-target="#userDetailModal"><i class="fa fa-trash"></i> Delete</a>';
                            }
                            if (hasEditRole.toString() === 'true') {
                                editBtn = '<a href="#" id="editId" class="btn btn-sm btn-primary btn-xs ml-3 d-none d-sm-inline-block" data-toggle="modal" data-target="#userDetailModal"><i class="fa fa-edit"></i> Edit</a>';
                            }
                            return editBtn +
                                '&nbsp;&nbsp;<a href="#" id="viewBtn" class="btn btn-sm btn-primary viewBtn btn-xs ml-3 d-none d-sm-inline-block" data-toggle="modal" data-target="#userDetailModal"><i class="fa fa-eye"></i> View</a>' +
                                '&nbsp;&nbsp;' + deleteBtn;

                        }
                    },
                ];
                var order = [[1, 'asc']];
                var t = $('#ledgerTable').DataTable({
                    data: res,
                    "aLengthMenu": [[10, 50, 75, -1], [25, 50, 75, "All"]],
                    pageLength: 50,
                    columns: columnDef,
                    bSort:false
                });

                t.on('order.dt search.dt', function () {
                    t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                        cell.innerHTML = i + 1;
                    });
                }).draw()
            }

        });
    }

    function onUnderAccountTypeChange() {
        $('#editAccTypeId').on('change', function () {
            if ($(this).val() == 3) {
                $('.editBankAccDetail').attr('hidden', false);
                $('.editBankAccDetail').attr('disabled', false);
            } else {
                $('.editBankAccDetail').attr('hidden', true);
                $('.editBankAccDetail').attr('disabled', true);
            }
        })
    }

    function displayGridValueToField() {
        $('#ledgerTable tbody').on('click', 'tr #editId', function () {
            $('#ledgerTable tbody tr').removeClass('selected');
            var ledgerId = $(this).closest('tr')
                .addClass('selected').find("td:eq( 1 )").text();
            $.ajax({
                url: 'ledger/getLedgerDetails',
                type: 'GET',
                data: {
                    ledgerId: ledgerId
                },
                success: function (res) {

                    $('#editLedgerModal').modal('show');
                    if (res.isBankAccLedger) {
                        $('.editBankAccDetail').attr('hidden', false);
                        $('.editBankAccDetail').attr('disabled', false);
                        $('#bankId').val(res.bankId);
                        $('#editLedgerName').val(res.ledgerName);
                        $('#editAccTypeId').val(res.accTypeId).trigger('change');
                        $('#editOpeningBal').val(res.openingBal);
                        $('#editReconciliationDate').val(formatAsDate(res.reconciliationDate));
                        $('#editBankAccHolderDetail').val(res.bankAccHolderDetail);
                        $('#editAccHolderName').val(res.accHolderName);
                        $('#editAccNo').val(res.accNo);
                        $('#editBankName').val(res.bankName);
                        $('#editBranch').val(res.branch);
                    } else {
                        $('.editBankAccDetail').attr('hidden', true);
                        $('.editBankAccDetail').attr('disabled', true);
                        $('#editLedgerName').val(res.ledgerName);
                        $('#editAccTypeId').val(res.accTypeId).trigger('change');
                        $('#editOpeningBal').val(res.openingBal);
                    }
                    $('#editLedgerId').val(ledgerId);
                }
            });
        });
    }

    function updateLedgerDetails() {
        $('#editBtnSave').on('click', function () {
            if ($('#editReconciliationDate').val() === '') {
                $('#editReconciliationDate').prop('disabled', true);
            }
            $('.ledgerEditForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'ledger/updateLedgerDetails',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status == 1) {
                                swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                });
                            } else {
                                swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "warning"
                                });
                            }
                        }
                    });
                }
            })
        });
    }

    function checkLedgerName() {
        var ledgerName = $('#ledgerName');
        ledgerName.on('blur', function () {
            isLedgerNameExists($(this).val());
        });

        ledgerName.on('keydown', function (e) {
            if (e.keyCode == 13) {
                isLedgerNameExists($(this).val());
            }
        });


    }

    function isLedgerNameExists(ledgerName) {
        $.ajax({
            url: 'ledger/isLedgerNameExists',
            type: 'GET',
            data: {ledgerName: ledgerName},
            success: function (res) {
                if (res.status == 0) {
                    swal({
                        title: res.text,
                        text: "Click OK to exit",
                        type: "warning"
                    });
                    $('#ledgerName').val('');
                }
            }
        });
    }

    function redirectToLedgerDetail() {
        $('#ledgerTable').on('click', '.viewBtn', function () {
            var accTypeId = $(this).closest('tr').find('.accTypeId').text();

            var ledgerId = $(this).closest('tr').find('.ledgerId').text();
            var ledgerName = $(this).closest('tr').find('.ledgerName').text();
            window.location.href = spms.getUrl() + 'voucherGroupList?ledgerId=' + ledgerId + '&&ledgerName=' + ledgerName;

            //window.location.href = 'ledger/redirectToLedgerDetail?isParentAccount=' + false + '&&accTypeId=' + accTypeId +'&&ledgerId='+ledgerId+ '&&ledgerName='+ledgerName;
        })
    }

    function deleteLedgerDetails() {
        $('#ledgerTable').on('click', '.deleteBtn', function () {
            var ledgerId = $(this).closest('tr').find('.ledgerId').text();

            $.ajax({
                url: 'ledger/isLedgerUsed',
                type: 'POST',
                data: {ledgerId: ledgerId},
                success: function (res) {
                    if (res.status === 1) {
                        confirmMessage("Please Confirm", function (e) {
                            if (e) {
                                $.ajax({
                                    url: 'ledger/deleteLedgerDetails',
                                    type: 'POST',
                                    data: {ledgerId: ledgerId},
                                    success: function (res) {
                                        if (res.status === 0) {
                                            swal({
                                                title: res.text,
                                                text: "Click OK to exit",
                                                type: "warning"
                                            });
                                        } else {
                                            swal({
                                                title: res.text,
                                                text: "Click OK to exit",
                                                type: "success"
                                            });
                                            $('#ledgerTable').dataTable().fnDestroy();
                                            getLedgerList();
                                        }
                                    }
                                });
                            }
                        })
                    } else {
                        errorMsg(res.text);
                    }
                }
            })


        })
    }


    return {
        save: save,
        onSelectAccType: onSelectAccType,
        getLedgerList: getLedgerList,
        displayGridValueToField: displayGridValueToField,
        updateLedgerDetails: updateLedgerDetails,
        onUnderAccountTypeChange: onUnderAccountTypeChange,
        redirectToLedgerDetail: redirectToLedgerDetail,
        checkLedgerName: checkLedgerName,
        deleteLedgerDetails: deleteLedgerDetails
    }
})();
$(document).ready(function () {
    $('#accTypeId').select2();
    ledger.save();
    ledger.onSelectAccType();
    ledger.getLedgerList();
    ledger.displayGridValueToField();
    ledger.updateLedgerDetails();
    ledger.onUnderAccountTypeChange();
    ledger.redirectToLedgerDetail();
    ledger.checkLedgerName();
    ledger.deleteLedgerDetails();
});
