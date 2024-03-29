/**
 * Created by jigmePc on 5/4/2019.
 */


ledger = (function () {

    function save() {
        // perClickSubmissionValidatorHandler("#btnSave", "#ledgerForm", "ledger/save", (res) => {
        //     if (res.status === 1) {
        //         swal({
        //                 title: res.text,
        //                 text: "Click OK to exit",
        //                 type: "success"
        //             }, function () {
        //                 window.location.reload();
        //             }
        //         )
        //     } else {
        //         swal({
        //             title: res.text,
        //             text: "Click OK to exit",
        //             type: "warning"
        //         });
        //     }
        //
        // })
        $('.globalForm').validate({
            submitHandler: function (form) {
                $('#btnSave').prop('disabled', true); // Disable the button immediately
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
                    },
                    complete: function () {

                        $('#btnSave').prop('disabled', false); // Re-enable the button after AJAX request completes

                    }
                });
            }
        });
    }

    function onSelectAccType() {
        $('#accTypeId').on('change', function () {
            if ($('#accTypeId').val() !== '') {
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
            }
        })
    }

    function getLedgerList() {
        $.ajax({
            url: 'ledger/getLedgerList',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },
                    {data: 'ledgerId', class: 'ledgerId hidden'},
                    {data: 'ledgerName', class: 'ledgerName left-align'},
                    {data: 'accTypeId', class: 'accTypeId hidden'},
                    {
                        data: 'action',
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
                var t = $('#ledgerTable').DataTable({
                    data: res,
                    "aLengthMenu": [[10, 50, 75, -1], [25, 50, 75, "All"]],
                    pageLength: 50,
                    columns: columnDef,
                    bSort: false,
                    columnDefs: [{
                        "defaultContent": "-",
                        "targets": "_all"
                    }]
                });

                /* t.on('order.dt search.dt', function () {
                     t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                         cell.innerHTML = i + 1;
                     });
                 }).draw()*/
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

                        if (res.accTypeId >= 12 && res.accTypeId <= 17) {
                            $('#editOpeningBal').val(res.openingBal).attr('disabled', 'disabled');
                        } else {
                            $('#editOpeningBal').val(res.openingBal).removeAttr('disabled');
                        }
                        // if(res.accTypeId>=12 && res.accTypeId<=17 ){
                        //   console.log("we")
                        // }
                        // $('#editOpeningBal').val(res.openingBal).removeAttr('disabled');

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
                    $('#editBtnSave').prop('disabled', true);
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
                        },
                        complete: () => {
                            $('#editBtnSave').prop('disabled', false);
                        }
                    });
                }
            })
        });
    }

    function checkLedgerName() {
        let ledgerName = $('#ledgerName');
        ledgerName.on('blur', function () {
            isLedgerNameExists($(this).val());
        });

        ledgerName.on('keydown', function (e) {
            if (e.keyCode === 13) {
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

    $("#accTypeId").change(function () {

        let selectedValue = $(this).children("option:selected").val();
        if (selectedValue >= 12 && selectedValue <= 17) {
            $('#openingBal').attr('disabled', 'disabled');
            // alert("A")
        } else {
            $('#openingBal').removeAttr('disabled');
            // alert("B")
        }
    });

    $('#editAccTypeId').on("change", function () {
        // 12,13,14,15,16,17
        $('#editOpeningBal').removeAttr('disabled');

        // if (this.value >= 12 && this.value <= 17) {
        //     $('#editOpeningBal').removeAttr('disabled');
        // } else {
        //     $('#editOpeningBal').attr('disabled', 'disabled');
        //
        // }

    })


});

