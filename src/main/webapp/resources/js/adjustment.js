/**
 * Created by Bikash Rai on 1/24/2022.
 */
adjustment = (function () {

    function adjustmentDetail() {
        $('.adjustmentForm').validate({
            submitHandler: function (form) {
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

    function getAllLedgerUnderExpenses() {
        $.ajax({
            url: 'adjustment/getAllLedgerUnderExpenses',
            type: 'GET',
            success: function (res) {
                $('#adjustedAgainst').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        if (suggestion.data != null) {
                            $('.costContent').addClass('hidden');
                            $('#adjustedAgainst').val(suggestion.value);
                            $('#ledgerId').val(suggestion.data);
                            $('#adjustedAgainstHiddenText').val(suggestion.value);
                        }
                    }
                })
            }
        });
    }

    function onAdjustedOnChange() {
        $('#adjustedAgainst').on('keyup', function () {
            $('.costContent').removeClass('hidden');
            if ($('#adjustedAgainstHiddenText').val() === $(this).val()) {
                $('.costContent').addClass('hidden');
            }
        })
    }

    function onAdjustFromChange() {
        $('#adjustedFrom').on('change', function () {
            $.ajax({
                url: 'adjustment/getAmountByLedgerId',
                type: 'GET',
                data: {ledgerId: $(this).val()},
                success: function (res) {
                    $('#amount').val(res);
                }
            });
        })
    }

    return {
        adjustmentDetail: adjustmentDetail,
        getAllLedgerUnderExpenses: getAllLedgerUnderExpenses,
        onAdjustedOnChange: onAdjustedOnChange,
        onAdjustFromChange: onAdjustFromChange
    }
})();

$(document).ready(function () {
    adjustment.adjustmentDetail()
    adjustment.getAllLedgerUnderExpenses()
    adjustment.onAdjustedOnChange()
    adjustment.onAdjustFromChange()
});