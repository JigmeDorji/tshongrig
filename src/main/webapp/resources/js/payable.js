/**
 * Created by Bikash Rai on 1/24/2022.
 */
payable = (function () {

    function payableDetail() {
        $('.payableForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'payable/payableDetail',
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
            url: 'payable/getAllLedgerUnderExpenses',
            type: 'GET',
            success: function (res) {
                $('#expenditure').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        if (suggestion.data != null) {
                            $('.costContent').addClass('hidden');
                            $('#expenditure').val(suggestion.value);
                            $('#ledgerId').val(suggestion.data);
                            $('#expenditureHiddenText').val(suggestion.value);
                        }
                    }
                })
            }
        });
    }

    function getPayableList() {
        $.ajax({
            url: 'payable/getPayableList',
            type: 'GET',
            success: function (res) {
                $('#partyName').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        if (suggestion.data != null) {
                            $('#partyName').val(suggestion.value);
                            $('#partyLedgerId').val(suggestion.data);
                        }
                    }
                })
            }
        });
    }

    function onAdjustedOnChange() {
        $('#expenditure').on('keyup', function () {
            $('.costContent').removeClass('hidden');
            if ($('#expenditureHiddenText').val() === $(this).val()) {
                $('.costContent').addClass('hidden');
            }
        })
    }

    return {
        payableDetail: payableDetail,
        getAllLedgerUnderExpenses: getAllLedgerUnderExpenses,
        onAdjustedOnChange: onAdjustedOnChange,
        getPayableList: getPayableList
    }
})();

$(document).ready(function () {
    payable.payableDetail()
    payable.getAllLedgerUnderExpenses()
    payable.onAdjustedOnChange()
    payable.getPayableList()
});