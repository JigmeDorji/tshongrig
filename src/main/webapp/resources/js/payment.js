/**
 * Created by Bikash Rai on 5/15/2021.
 */

let payment = (function () {

    let globalForm = $('.globalForm');
    spms.isFormValid(globalForm);

    function baseURL() {
        return 'payment/'
    }

    function onSelectEnableDisable() {
        $('#paidForId').on('change', function () {


            let description = $('#description'), paidFor = parseInt($(this).val()),
                tdsType = $('#tdsType'), tdsAmount = $('#tdsAmount'),
                deductedFrom = $('#deductedFrom'), amount = $('#amount'),
                deductedAmount = $('#deductedAmount'),
                amountPaid = $('#amountPaid'), costContent = $('.costContent'), costType = $('#costType'),
                descriptionText = $('#descriptionText'), multiPaymentVoucher = $('.multiPaymentVoucher'),
                ledgerForNormalEntry = $('.ledgerForNormalEntry');

            costType.val('');
            tdsType.val('');
            description.val('');
            tdsAmount.val('');
            deductedAmount.val('');
            amountPaid.val('');
            amount.val('');
            deductedFrom.val('');
            description.attr('readonly', false);
            multiPaymentVoucher.addClass("hidden");
            ledgerForNormalEntry.addClass("hidden");

            if (paidFor === 1) {
                descriptionText.text('Description');
                tdsType.attr('disabled', false);
                tdsAmount.attr('readonly', false);
                deductedFrom.attr('readonly', false);
                deductedAmount.attr('readonly', false);
                amountPaid.attr('readonly', false);
                costContent.removeClass('hidden');
                multiPaymentVoucher.removeClass("hidden");

                $('#costAmount').val('');
                $('#costDescription').val('');
                // $('#multipleCost').empty();
                //load repayment ledger
                getAllLedgerUnderExpenseForCost('');
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
                ledgerForNormalEntry.removeClass("hidden");
                tdsType.attr('disabled', true);
                tdsAmount.attr('readonly', true);
                deductedFrom.attr('readonly', true);
                deductedAmount.attr('readonly', true);
                amountPaid.attr('readonly', true);
                costContent.addClass('hidden');

            }

            if (paidFor === 3) {

                descriptionText.text('Party');
                ledgerForNormalEntry.removeClass("hidden");

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
                ledgerForNormalEntry.removeClass("hidden");

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
        $('#btnSave').on('click', function () {

            // test if form is valid
            if ($('form.globalForm').validate().form()) {
                $.ajax({
                    url: baseURL() + 'save',
                    type: 'POST',
                    data: globalForm.serializeArray(),
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
            } else {
                // prevent default submit action
                event.preventDefault();
                console.log("not valid");
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
            if (parseInt($('#paidForId').val()) !== 1) {
                if ($('#amount').val() === '') {
                    swal({
                        type: "warning",
                        title: "Please write amount.",
                    });
                    $("#tdsType").val('');
                    return false;
                }
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

    function getAllLedgerUnderExpenseForCost(counter) {
        $.ajax({
            url: baseURL() + 'getAllLedgerUnderExpenseForCost',
            type: 'GET',
            success: function (res) {
                $('#costDescription' + counter).devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {

                        $('#costDescription' + counter).val(suggestion.value);
                        $('#costLedgerId').val(suggestion.data);

                        spms.ajax(baseURL() + 'getCostTypeByLedgerId',
                            'GET', {ledgerId: suggestion.data}, function (res) {
                                if (res !== "") {
                                    $("#costType" + counter + "").val(res);
                                } else {
                                    $("#costType" + counter + "").val('');
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

    function addMoreBtn() {
        let counter = 1;

        $('.addMoreBtn').on('click', function () {
            $('#multipleCost').append(loadMultiVoucherContent(counter));
            setTimeout(function () {
                getAllLedgerUnderExpenseForCost(counter);
                multiVoucherIndexing();
                counter = counter + 1;
            }, 100)
        });

        $('#multipleCost').on("click", ".removeBtn", function (e) { //user click on remove text
            $(this).parent('div').parent('div').remove();
            multiVoucherIndexing();
        })
    }

    function multiVoucherIndexing() {
        $('.multiPaymentVoucher').each(function (i, e) {

            $(e).find('.costDescription').attr('name',
                "multiVoucherDTO[" + i + "].costDescription");

            $(e).find('.costId').attr('name',
                "multiVoucherDTO[" + i + "].costId");

            $(e).find('.costAmount').attr('name',
                "multiVoucherDTO[" + i + "].costAmount");
        });
    }

    function calculateTotalAmount() {
        $(document).on('keyup', '.costAmount', function () {
            let sum = 0;
            $('.multiPaymentVoucher').each(function (i, e) {
                let v = parseFloat($(e).find('.costAmount').val());
                if (!isNaN(v))
                    sum += v;
            });
            $('#amount').val(sum);
        })
    }

    function loadMultiVoucherContent(item) {

        return ' <div class="form-group row multiPaymentVoucher">\n' +
            '                        <label class="col-md-2 right-align" id="descriptionText"></label>\n' +
            '                        <div class="col-md-3">\n' +
            '                            <input type="text" tabindex="2" class="form-control form-control-sm costDescription autocomplete"\n' +
            '                                   name="multiVoucherDTO[' + item + '].costDescription" required="required"\n' +
            '                                   id="costDescription' + item + '" />\n' +
            '                        </div>\n' +
            '                        <label class="col-md-1 right-align required">Amount</label>\n' +
            '                        <div class="col-md-2">\n' +
            '                            <input type="text" tabindex="3" class="form-control form-control-sm costAmount text-right"\n' +
            '                                 required="required" name="multiVoucherDTO[' + item + '].costAmount" id="costAmount' + item + ' " />\n' +
            '                        </div>\n' +
            '                        <label class="col-md-1 right-align required ">Cost</label>\n' +
            '                        <div class="col-md-2 ">\n' +
            '                            <select class="form-control form-control-sm costId" id="costType' + item + '" \n' +
            '                                    required="required" name="multiVoucherDTO[' + item + '].costId">\n' +
            '                                <option value="">---Please select ---</option>\n' +
            '                                <option value="1" id="generalId">General</option>\n' +
            '                                <option value="2" id="productionId">Production</option>\n' +
            '                            </select>\n' +
            '                        </div>\n' +
            '                        <div class="col-md-1"><button type="button" class="btn btn-danger btn-sm rounded-pill removeBtn">\n' +
            '                                <i class="icon-bin"></i>\n' +
            '                            </button></div>\n' +
            '                    </div>';
    }

    return {
        calTDSOnAmountChange: calTDSOnAmountChange,
        calculateTDSAmount: calculateTDSAmount,
        onSelectEnableDisable: onSelectEnableDisable,
        saveAutoVoucherDetails: saveAutoVoucherDetails,
        onChangePaidIn: onChangePaidIn,
        calAmountToPay: calAmountToPay,
        getAllLedgerUnderAdvanceReceived: getAllLedgerUnderAdvanceReceived,
        reset: reset,
        addMoreBtn: addMoreBtn,
        calculateTotalAmount: calculateTotalAmount
    }
})();

$(document).ready(function () {
    payment.calTDSOnAmountChange();
    payment.calculateTDSAmount();
    payment.onSelectEnableDisable();
    payment.saveAutoVoucherDetails();
    payment.onChangePaidIn();
    payment.calAmountToPay();
    payment.getAllLedgerUnderAdvanceReceived();
    payment.reset();
    payment.addMoreBtn();
    payment.calculateTotalAmount();

});