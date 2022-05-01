/**
 * Created by Bikash Rai on 1/24/2022.
 */
adjustment = (function () {

    function adjustmentDetail() {
        $('.adjustmentForm').validate({
            submitHandler: function (form) {

                if ($('#amount').val() !== $('#totalAdjustableAmount').val()) {
                    errorMsg("Please check, total amount did not match the adjustable amount.")
                } else {
                    $.ajax({
                        url: 'adjustment/adjustmentDetail',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    type: "success",
                                    title: res.text,
                                }, function () {
                                    window.location = spms.getUrl() + 'payment/' + 'generateReport?voucherNo=' +
                                        encodeURIComponent(res.dto.voucherNo) + '&type=' + 3;
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
            }
        })
    }

    function getAllLedgerUnderExpenses(counter) {

        counter = typeof counter === 'undefined' ? '' : counter;

        $.ajax({
            url: 'adjustment/getAllLedgerUnderExpenses',
            type: 'GET',
            success: function (res) {
                $('#adjustedAgainst' + counter).devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {

                        if (suggestion.data != null) {
                            $('.costContent').addClass('hidden');

                            $('#adjustedAgainst' + counter).val(suggestion.value);
                            $('#adjustedAgainstHiddenText' + counter).val(suggestion.value);

                            spms.ajax('adjustment/getCostTypeByLedgerId',
                                'GET', {ledgerId: suggestion.data}, function (res) {
                                    if (res !== "") {
                                        $("#costType" + counter + "").val(res);
                                    } else {
                                        $("#costType" + counter + "").val('');
                                    }
                                })
                        }
                    }
                })
            }
        });
    }

    /*    function onAdjustedOnChange() {
            $('#adjustedAgainst').on('keyup', function () {
                $('.multiPaymentVoucher').removeClass('hidden');
                if ($('#adjustedAgainstHiddenText').val() === $(this).val()) {
                    $('.multiPaymentVoucher').addClass('hidden');
                }
            })
        }*/

    function onAdjustFromChange() {
        $('#adjustedFrom').on('change', function () {
            $.ajax({
                url: 'adjustment/getAmountByLedgerId',
                type: 'GET',
                data: {ledgerId: $(this).val()},
                success: function (res) {
                    $('#totalAdjustableAmount').val(res);
                }
            });
        })
    }

    function addMoreBtn() {
        let counter = 1;
        $('.addMoreBtn').on('click', function () {
            $('#multipleCost').append(loadMultiVoucherContent(counter));
            setTimeout(function () {
                getAllLedgerUnderExpenses(counter);
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

            $(e).find('.adjustedAgainst').attr('name',
                "multiVoucherDTO[" + i + "].adjustedAgainst");

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
            '                            <input type="text" tabindex="2" class="form-control form-control-sm adjustedAgainst autocomplete"\n' +
            '                                   name="multiVoucherDTO[' + item + '].adjustedAgainst" required="required"\n' +
            '                                   id="adjustedAgainst' + item + '" />\n' +
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

    function calculateTDSAmount() {
        $('#tdsType').on('change', function () {
            if ($(this).val() !== '' && $(this).val() !== null) {
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
                spms.calculateTdsAmount(parseInt($(this).val()), tdsAmount, $('#amount').val());
                calTotalTDSPayableAmount($('#amount').val(), tdsAmount.val());
            }

        })
    }

    function calTotalTDSPayableAmount(amount, tdsDeduction) {
        if (amount !== '' && tdsDeduction !== '') {
            $('#amountPaid').val((parseFloat(amount) - (parseFloat(tdsDeduction)))
                .toFixed(2));
        } else {
            $('#amountPaid').val('')
        }
    }

    return {
        adjustmentDetail: adjustmentDetail,
        getAllLedgerUnderExpenses: getAllLedgerUnderExpenses,
        // onAdjustedOnChange: onAdjustedOnChange,
        onAdjustFromChange: onAdjustFromChange,
        addMoreBtn: addMoreBtn,
        calculateTotalAmount: calculateTotalAmount,
        calculateTDSAmount: calculateTDSAmount
    }
})();

$(document).ready(function () {
    adjustment.adjustmentDetail()
    adjustment.getAllLedgerUnderExpenses()
    // adjustment.onAdjustedOnChange()
    adjustment.onAdjustFromChange()
    adjustment.addMoreBtn()
    adjustment.calculateTotalAmount()
    adjustment.calculateTDSAmount()
});