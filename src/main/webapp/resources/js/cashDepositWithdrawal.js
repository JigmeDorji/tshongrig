/**
 * Created by Bikash Rai on 5/23/2021.
 */
cashDepositWithdrawal = (function () {

    function baseURL() {
        return 'cashDepositWithdrawal/'
    }

    function saveCashDepositWithVoucherDetails() {
        $('#cashDepositWithdrawalForm').validate({

            submitHandler: function (form) {
                $('#btnCashDepositWit').attr('disabled', true);
                $.ajax({
                    url: baseURL() + 'saveCashDepositWithVoucherDetails',
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
                    }, complete: function () {
                        $('#btnCashDepositWit').attr('disabled', true);
                    }
                })
            }
        })
    }


    function onChangeCash() {
        $('#cashDepositWithdrawalType').on('change', function () {
            if (parseInt($(this).val()) === 1) {
                $('#depositWithdrawalId').text("Deposited to");
            }
            if (parseInt($(this).val()) === 2) {
                $('#depositWithdrawalId').text("Withdrawal From");
            }
        })
    }

    return {
        saveCashDepositWithVoucherDetails: saveCashDepositWithVoucherDetails,
        onChangeCash: onChangeCash
    }
})();

$(document).ready(function () {
    cashDepositWithdrawal.saveCashDepositWithVoucherDetails();
    cashDepositWithdrawal.onChangeCash();
});