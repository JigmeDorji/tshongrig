/**
 * Created by Bikash Rai on 5/23/2021.
 */
receipt = (function () {

    function baseURL() {
        return 'receipt/'
    }

    function saveReceiptVoucherDetails() {
        $('.receiptForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: baseURL() + 'saveReceiptVoucherDetails',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                type: "success",
                                title: res.text,
                            }, function () {
                                window.location.reload();
                            });
                        } else {
                            swal({
                                type: "warning",
                                title: res.text,
                            });
                        }
                    }
                })
            }
        })
    }

    let listOfAdvanceLedger;

    function getAllLedgerUnderAdvanceReceived() {
        $.ajax({
            url: baseURL() + 'getAllLedgerUnderAdvanceReceived',
            type: 'GET',
            success: function (res) {
                listOfAdvanceLedger = res;

            }
        });
    }

    let newArray;

    function onChangeForType() {
        $('#receivedFor').on('change', function () {
            let isCash = $("#isCash");
            $('#description').val('');

            isCash.val('');
            $('#bankLedgerId').val('');
            $('#capitalLedgerName').val('');
            isCash.prop('disabled', false);
            $('#bankDetails').attr('hidden', true);

            if (parseInt($(this).val()) === 1) {
                newArray = listOfAdvanceLedger.filter(function (element) {
                    return element.id !== 'new';
                });
            }
            if (parseInt($(this).val()) === 2) {
                newArray = listOfAdvanceLedger.filter(function (element) {
                    return element.id !== 'new';
                });

                newArray.unshift({
                    id: "new",
                    text: "Add New"
                });
                getLoanLedgerList();
            }
            if (parseInt($(this).val()) === 1 || parseInt($(this).val()) === 2
                || parseInt($(this).val()) === 4 || parseInt($(this).val()) === 5) {

                $('.capitalContent').attr('hidden')
                $('.generalContent').removeClass('hidden')

                if (parseInt($(this).val()) === 1 || parseInt($(this).val()) === 2) {
                    $('#description').devbridgeAutocomplete({
                        lookup: $.map(newArray, function (value) {
                            return {data: value.id, value: value.text}
                        }), onSelect: function (suggestion) {
                            if (suggestion.data === "new") {
                                $('#loadAccountDetailModal').modal('show');
                                $('#description').val("");
                                getLoanDetails();
                                getLoanLedgerList()
                            } else {
                                $('#description').val(suggestion.value);
                            }
                        }
                    })
                }

                if (parseInt($(this).val()) === 4 || parseInt($(this).val()) === 5) {

                    isCash.val(2);
                    isCash.prop('disabled', true);
                    $('#bankDetails').attr('hidden', false);

                    $.ajax({
                        url: baseURL() + 'getAllLedgerUnderAccountType',
                        type: 'GET',
                        data: {selectionType: $(this).val()},
                        success: function (res) {
                            $('#description').devbridgeAutocomplete({
                                lookup: $.map(res, function (value) {
                                    return {data: value.id, value: value.text}
                                }), onSelect: function (suggestion) {

                                    $('#description').val(suggestion.value);
                                    $('#ledgerId').val(suggestion.data);
                                }
                            })
                        }
                    });
                }

            }

            if (parseInt($(this).val()) === 3) {
                $('.capitalContent').removeAttr('hidden');
                $('.generalContent').addClass('hidden')
            }
        })
    }

    function getLoanLedgerList() {
        $.ajax({
            url: baseURL() + 'getLoanLedgerList',
            type: 'GET',
            success: function (res) {

                newArray.unshift({
                    id: "new",
                    text: "Add New"
                })
                newArray = res;

                $('#description').devbridgeAutocomplete({
                    lookup: $.map(newArray, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        if (suggestion.data === "new") {
                            $('#loadAccountDetailModal').modal('show');
                            $('#description').val("");
                        } else {
                            $('#description').val(suggestion.value);
                        }
                    }
                })
            }
        });
    }

    function receivedInOnChange() {
        $('#isCash').on('change', function () {
            if (parseInt($(this).val()) === 1) {
                $('#bankDetails').attr('hidden', true);
            } else if (parseInt($(this).val()) === 2) {
                $('#bankDetails').attr('hidden', false);
            } else {
                $('#bankDetails').attr('hidden', true);
            }
        })
    }

    function saveLoanDetail() {
        $('#saveBtn').on('click', function () {
            $('.loanDetailForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: baseURL() + 'saveLoanDetail',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    type: "success",
                                    title: res.text,
                                }, function () {
                                    window.location.reload();
                                });
                            } else {
                                swal({
                                    type: "warning",
                                    title: res.text,
                                });
                            }
                        }
                    })
                }
            })
        })
    }

    function getLoanDetails() {
        spms.ajax(baseURL() + 'getLoanDetails',
            'GET', {}
            , function (res) {

                $('#loanDetailGrid').dataTable().fnDestroy();

                let columnDef = [
                    {data: 'loanId', class: 'hidden loanId'},
                    {data: 'loanLedgerName', class: 'loanLedgerName'},
                    {data: 'loanAccNo', class: 'loanAccNo'},
                    {data: 'bank', class: 'bank'},
                    {data: 'branch', class: 'branch'},
                    {data: 'monthlyEmi', class: 'monthlyEmi'},
                    {
                        data: 'action', render: function () {
                            return '<input type="button" class="btn btn-primary btn-xs" style="width: 70px" value="Edit" id="edit">'
                        }
                    },
                ];

                $('#loanDetailGrid').DataTable({
                    data: res,
                    columns: columnDef,
                    searching: false,
                    paging: false,
                    info: false
                });
            })
    }

    function onClickEditButton() {
        $('#loanDetailGrid').on('click', '#edit', function () {
            let selectedRow = $(this).closest('tr');
            $('#loanId').val(selectedRow.find('.loanId').text())
            $('#loanLedgerName').val(selectedRow.find('.loanLedgerName').text())
            $('#loanAccNo').val(selectedRow.find('.loanAccNo').text())
            $('#bank').val(selectedRow.find('.bank').text())
            $('#branch').val(selectedRow.find('.branch').text())
            $('#monthlyEmi').val(selectedRow.find('.monthlyEmi').text());
            $('#loanLedgerName').attr('readonly', true);
        })
    }

    return {
        saveReceiptVoucherDetails: saveReceiptVoucherDetails,
        getAllLedgerUnderAdvanceReceived: getAllLedgerUnderAdvanceReceived,
        receivedInOnChange: receivedInOnChange,
        onChangeForType: onChangeForType,
        saveLoanDetail: saveLoanDetail,
        onClickEditButton: onClickEditButton
    }
})
();
$(document).ready(function () {
    $('#loanDetailGrid').css('width', '100%');
    receipt.saveReceiptVoucherDetails();
    receipt.getAllLedgerUnderAdvanceReceived();
    receipt.receivedInOnChange();
    receipt.onChangeForType();
    receipt.saveLoanDetail();
    receipt.onClickEditButton();
});
