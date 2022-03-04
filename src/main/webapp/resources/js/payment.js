/**
 * Created by Bikash Rai on 5/15/2021.
 */

let payment = (function () {

    function baseURL() {
        return 'payment/'
    }

    function onSelectEnableDisable() {
        $('#paidForId').on('change', function () {
            let paidFor = parseInt($(this).val());
            let description = $('#description'),
                tdsType = $('#tdsType'), tdsAmount = $('#tdsAmount'),
                deductedFrom = $('#deductedFrom'), amount = $('#amount'),
                deductedAmount = $('#deductedAmount'),
                amountPaid = $('#amountPaid'), costContent = $('.costContent'), costType = $('#costType'),
                descriptionText = $('#descriptionText');

            costType.val('');
            tdsType.val('');
            description.val('');
            tdsAmount.val('');
            deductedAmount.val('');
            amountPaid.val('');
            amount.val('');
            deductedFrom.val('');
            description.attr('readonly', false);

            if (paidFor === 1) {
                descriptionText.text('Description');

                tdsType.attr('disabled', false);
                tdsAmount.attr('readonly', false);
                deductedFrom.attr('readonly', false);
                deductedAmount.attr('readonly', false);
                amountPaid.attr('readonly', false);

                costContent.removeClass('hidden');
                //load repayment ledger
                getAllLedgerUnderExpenseForCost();
            }

            if (paidFor === 2 || paidFor === 4) {
                if (paidFor === 4) {
                    fetchTDSPayableList();
                    descriptionText.text('Description');
                } else {
                    //load ledger under advance paid
                    getAllLedgerUnderAdvancePaid(2);
                    descriptionText.text('Party');
                }
                tdsType.attr('disabled', true);
                tdsAmount.attr('readonly', true);
                deductedFrom.attr('readonly', true);
                deductedAmount.attr('readonly', true);
                amountPaid.attr('readonly', true);
                costContent.addClass('hidden');

            }

            if (paidFor === 3) {

                descriptionText.text('Party');

                tdsType.attr('disabled', true);
                tdsAmount.attr('readonly', true);
                deductedFrom.attr('readonly', true);
                deductedAmount.attr('readonly', true);
                amountPaid.attr('readonly', true);
                costContent.addClass('hidden');

                //load repayment ledger
                getAllLedgerUnderPayableForRepayment();
            }
            if (paidFor === 5) {

                descriptionText.text('Description');

                tdsType.attr('disabled', false);
                tdsAmount.attr('readonly', false);
                deductedFrom.attr('readonly', false);
                deductedAmount.attr('readonly', false);
                amountPaid.attr('readonly', false);
                costContent.addClass('hidden');

                //load payable ledger
                getAllLPayableLedgerExcludingTds();
                getAllLedgerUnderAdvancePaid(5);
            }
        })
    }

    function saveAutoVoucherDetails() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                let paidForId = $('#paidForId');
                $.ajax({
                    url: baseURL() + 'save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            if (parseInt($('#paidForId').val()) === 1) {
                                window.location = spms.getUrl() + baseURL() + 'generateReport?voucherNo=' +
                                    encodeURIComponent(res.dto.voucherNo) + '&type=' + 1;
                            }
                            successMsg(res.text);
                            $('#paymentForm')[0].reset();
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

    function fetchTDSPayableList() {
        $.ajax({
            url: baseURL() + '/fetchTDSPayableList',
            type: 'GET',
            success: function (res) {
                if (res.status === 0) {
                    errorMsg(res.text);
                    $('#paidForId').val('');
                } else {
                    $('#ledgerId').val(res.text);
                    $('#description').val("TDS payable");
                    $('#description').attr("readonly", true);
                }
            }
        });
    }

    function calculateTDSAmount() {
        $('#tdsType').on('change', function () {
            let value = parseInt($(this).val());
            let tdsAmount = $('#tdsAmount');

            if ($(this).val() !== '') {
                tdsAmount.val('');
            }
            if ($('#amount').val() === '') {
                swal({
                    type: "warning",
                    title: "Please write amount.",
                });
                $("#tdsType").val('');
                return false;
            }
            //calculate TDS
            tdsCalculatedAmount(value, tdsAmount, $('#amount').val());
            totalAmountTobePaid($('#amount').val(), tdsAmount.val(), $('#deductedAmount').val());
        })
    }

    function totalAmountTobePaid(amount, tdsDeduction, advanceDeduction) {

        if (amount !== '' && tdsDeduction !== '' && advanceDeduction !== '') {
            $('#amountPaid').val(parseFloat(amount) - (parseFloat(tdsDeduction) + parseFloat(advanceDeduction)));
        } else {
            $('#amountPaid').val('')
        }
    }

//calculate total amount paid
    function calAmountToPay() {
        $('#amount').on('keyup', function () {
            totalAmountTobePaid($(this).val(), $('#tdsAmount').val(), $('#deductedAmount').val())
        });

        $('#deductedAmount').on('keyup', function () {
            totalAmountTobePaid($('#amount').val(), $('#tdsAmount').val(), $(this).val())
        })
    }

    function calTDSOnAmountChange() {
        $('#amount').on('keyup', function () {
            if ($('#tdsType').val() !== '') {
                tdsCalculatedAmount(parseInt($('#tdsType').val()), $('#tdsAmount'), $('#amount').val());
            } else {
                $('#tdsAmount').val('');
            }
        })
    }

    function calculatedTDS(percentage, amount) {
        return (percentage / 100) * amount;
    }

    function tdsCalculatedAmount(value, tdsAmount, amount) {
        if (value === 1) {
            tdsAmount.val(
                calculatedTDS(2, amount));
        }

        if (value === 2) {
            tdsAmount.val(calculatedTDS(5,
                amount))
        }
        if (value === 3) {
            tdsAmount.val(calculatedTDS(5,
                amount));
        }
        if (value === 4) {
            tdsAmount.val(calculatedTDS(3,
                amount))
        }
        if (value === 5) {
            tdsAmount.val(0)
        }
    }

    function onChangePaidIn() {
        $('#isCash').on('change', function () {
            $('#bankLedgerId').val('');
            if (parseInt($(this).val()) === 1) {
                $('#bankDetails').attr('hidden', true);
            } else if (parseInt($(this).val()) === 2) {
                $('#bankDetails').attr('hidden', false);
            } else {
                $('#bankDetails').attr('hidden', true);
            }
        })
    }

    function getAllLedgerUnderPayableForRepayment() {
        $.ajax({
            url: baseURL() + 'getLedgerUnderPayableForRepayment',
            type: 'GET',
            success: function (res) {
                $('#description').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {

                        $('#description').val(suggestion.value);
                        $('#ledgerId').val(suggestion.data);

                        loadClosingBalance(suggestion.data);
                    }
                })
            }
        });
    }

    function loadClosingBalance(ledgerId) {
        spms.ajax(baseURL() + 'getRepaymentAmount',
            'GET', {ledgerId: ledgerId}, function (res) {
                $('#amount').val(res);
            })
    }

    function getAllLPayableLedgerExcludingTds() {
        $.ajax({
            url: baseURL() + 'getAllLPayableLedgerExcludingTds',
            type: 'GET',
            success: function (res) {
                $('#description').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        $('#description').val(suggestion.value);
                        $('#ledgerId').val(suggestion.data);
                        loadClosingBalance(suggestion.data);
                    }
                })
            }
        });
    }

    function getAllLedgerUnderExpenseForCost() {
        $.ajax({
            url: baseURL() + 'getAllLedgerUnderExpenseForCost',
            type: 'GET',
            success: function (res) {
                $('#description').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {

                        $('#description').val(suggestion.value);
                        $('#ledgerId').val(suggestion.data);

                        spms.ajax(baseURL() + 'getCostTypeByLedgerId',
                            'GET', {ledgerId: suggestion.data}, function (res) {
                                if (res !== "") {
                                    $("#costType").val(res);
                                } else {
                                    $("#costType").val('');
                                }
                            })
                    }
                })
            }
        });
    }

    function getAllLedgerUnderAdvancePaid(paidForType) {
        $.ajax({
            url: baseURL() + 'getAllLedgerUnderAdvancePaid',
            type: 'GET',
            success: function (res) {
                if (paidForType === 2) {
                    $('#description').devbridgeAutocomplete({
                        minLength: 0,
                        lookup: $.map(res, function (value) {
                            return {data: value.id, value: value.text}
                        }), onSelect: function (suggestion) {
                            $('#description').val(suggestion.value);
                            $('#ledgerId').val(suggestion.data);
                        }
                    })
                } else {
                    res.unshift({
                        id: "N/A",
                        text: "Not applicable"
                    });

                    $('#deductedFrom').devbridgeAutocomplete({
                        minLength: 0,
                        lookup: $.map(res, function (value) {
                            return {data: value.id, value: value.text}
                        }), onSelect: function (suggestion) {
                            $('#deductedFrom').val(suggestion.value);
                            // $('#ledgerId').val(suggestion.data);
                            calDeductedAmount(suggestion.data);
                        }
                    })
                }
            }
        });
    }

    function calDeductedAmount(value) {
        if (value === "N/A") {
            totalAmountTobePaid($("#amount").val(), $('#tdsAmount').val(), 0)
            $('#deductedAmount').val(0);
            $('#deductedAmount').attr("readOnly", true);
        } else {
            $('#deductedAmount').attr("readOnly", false);
        }
    }

    function getAllLedgerUnderAdvanceReceived() {
        $.ajax({
            url: baseURL() + 'getAllLedgerUnderAdvanceReceived',
            type: 'GET',
            success: function (res) {

                res.unshift({
                    id: "N/A",
                    text: "Not applicable"
                });

                $('#deductedFrom').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text, amount: value.receiptAmount}
                    }), onSelect: function (suggestion) {

                        $('#deductedFrom').val(suggestion.value);
                        $('#deductedAmount').val(suggestion.amount);

                        calDeductedAmount(suggestion.data);
                    }
                })
            }
        });
    }

    function reset() {
        $('#btnReset').on('click', function () {
            window.location.reload();
        });
    }

    return {
        calTDSOnAmountChange: calTDSOnAmountChange,
        calculateTDSAmount: calculateTDSAmount,
        // fetchPaidToList: fetchPaidToList,
        onSelectEnableDisable: onSelectEnableDisable,
        saveAutoVoucherDetails: saveAutoVoucherDetails,
        onChangePaidIn: onChangePaidIn,
        calAmountToPay: calAmountToPay,
        getAllLedgerUnderAdvanceReceived: getAllLedgerUnderAdvanceReceived,
        reset: reset
    }
})
();

$(document).ready(function () {
    payment.calTDSOnAmountChange();
    payment.calculateTDSAmount();
    // payment.fetchPaidToList();
    payment.onSelectEnableDisable();
    payment.saveAutoVoucherDetails();
    payment.onChangePaidIn();
    payment.calAmountToPay();
    payment.getAllLedgerUnderAdvanceReceived();
    payment.reset();
});