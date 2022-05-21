/**
 * Created by Bikash Rai on 1/24/2022.
 */
bankTansfer = (function () {

    function bankTransferDetail() {
        $('.bankTransferForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'bankTransfer/bankTransferDetail',
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

    function validateBankSelection() {
        $('#bankLedgerFromId').on('change', function () {
            if($(this).val()!==null && $(this).val()!==''){
                if ($(this).val() === $('#bankLedgerToId').val()) {
                    errorMsg("From and To account is same.");
                    $('#bankLedgerFromId').val('');
                }
            }
            if($(this).val()!==null && $(this).val()!==''){
                if ($(this).val() === $('#bankLedgerToId').val()) {
                    errorMsg("From and To account is same.");
                    $('#bankLedgerFromId').val('');
                }
            }

        });
        $('#bankLedgerToId').on('change', function () {
            if($(this).val()!==null && $(this).val()!==''){
                if ($(this).val() === $('#bankLedgerFromId').val()) {
                    errorMsg("From and To account is same.");
                    $('#bankLedgerToId').val('');
                }
            }

        })
    }

    return {
        bankTransferDetail: bankTransferDetail,
        validateBankSelection: validateBankSelection
    }
})();

$(document).ready(function () {
    bankTansfer.bankTransferDetail()
    bankTansfer.validateBankSelection()
});