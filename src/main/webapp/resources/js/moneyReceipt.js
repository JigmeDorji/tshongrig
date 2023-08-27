/**
 * Created by jigme.dorji on 10/27/2020.
 */


moneyReceipt = (function () {

    var baseURL = 'moneyReceipt/';


    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $('#btnSave').prop('disabled', true);
                var data = $(form).serializeArray();
                var partyName = $("#partyLedgerId option:selected").text();
                var paymentMode = $("#isCash option:selected").text();
                var amount = $("#amount").val();
                var tDSAmount = $("#tDSAmount").val();
                var retentionAmount = $("#retentionAmount").val();
                var mobilizationAdvAmount = $("#mobilizationAdvAmount").val();
                var materialAdvAmount = $("#materialAdvAmount").val();
                var moneyReceiptNo = $("#receiptNo").val();
                $.ajax({
                    url: baseURL + 'save',
                    type: 'POST',
                    data: data,
                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                timer: 800,
                                type: "success",
                                title: res.text,
                                showConfirmButton: false
                            }, function () {
                                if ($('#businessType').val() != 7) {
                                    window.open(
                                        spms.getUrl() + baseURL + '/generateMoneyReceipt?moneyReceiptNo=' + encodeURIComponent(moneyReceiptNo) +
                                        "&partyName=" + partyName +
                                        "&amount=" + amount +
                                        "&tDSAmount=" + tDSAmount +
                                        "&retentionAmount=" + retentionAmount +
                                        "&mobilizationAdvAmount=" + mobilizationAdvAmount +
                                        "&materialAdvAmount=" + materialAdvAmount +
                                        "&paymentMode=" + paymentMode,
                                        '_blank'
                                    );
                                } else {
                                    window.open(
                                        spms.getUrl() + baseURL + 'generateMoneyReceipt?moneyReceiptNo=' + encodeURIComponent(moneyReceiptNo) +
                                        "&partyName=" + $('#receivedFrom').val() +
                                        "&amount=" + amount +
                                        "&tDSAmount=" + 0 +
                                        "&retentionAmount=" + 0 +
                                        "&mobilizationAdvAmount=" + 0 +
                                        "&materialAdvAmount=" + 0 +
                                        "&paymentMode=" + paymentMode,
                                        '_blank'
                                    );
                                }
                                window.location.reload();
                            });
                        } else {
                            swal({
                                // timer: 500,
                                type: "warning",
                                title: res.text,
                                // showConfirmButton: false
                            });
                        }
                    }, complete: function () {
                        // window.location.reload();
                    },
                    complete: () => {
                        $('#btnSave').prop('disabled', false);
                    }
                })
            }
        })
    }

    $('#isCash').on('change', function () {
        if ($(this).val() == 2) {
            $('#bankDetails').attr('hidden', false);
        } else {
            $('#bankDetails').attr('hidden', true);
        }
    });

    function onSelectParty() {
        $('#partyLedgerId').on('change', function () {
            $.ajax({
                url: baseURL + 'getReceivableAmt',
                type: 'get',
                data: {partyLedgerId: $(this).val()},
                success: function (res) {
                    $('#amount').val(res);
                }
            });
        });
    }

    function calculateTDS() {
        $('#tDSType').on('change', function () {
            let receivedAmt = $('#amount').val();
            let tDSType = $('#tDSType').val();

            if ((receivedAmt <= 0 || receivedAmt === '') && $(this).val() !== '') {
                $(this).val('');
                $('#tDSAmount').val('');
                $('#tDSPercentage').val('');
                errorMsg("Received Amount is mandatory.");
                return false;
            }

            let tDSAmt = '';
            if (tDSType !== '') {

                if (tDSType == 1) {
                    tDSAmt = receivedAmt * 0.02;
                    $('#tDSPercentage').val('2%')
                }
                if (tDSType == 2) {
                    tDSAmt = receivedAmt * 0.05;
                    $('#tDSPercentage').val('5%')
                }
                if (tDSType == 3) {
                    tDSAmt = receivedAmt * 0.03;
                    $('#tDSPercentage').val('3%')
                }
                if (tDSType == 4) {
                    $('#tDSPercentage').val(0)
                    $('#tDSAmount').val(0)
                }
                $('#tDSAmount').val(tDSAmt.toFixed(2))
            }
        })
    }

    function getAllLedgerUnderIncome() {
        $.ajax({
            url: baseURL + 'getAllLedgerUnderIncome',
            type: 'GET',
            success: function (res) {
                $('#ngoDescription').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        $('#ngoDescription').val(suggestion.value);
                        // $('#ledgerId').val(suggestion.data);
                        // loadClosingBalance(suggestion.data);
                    }
                })
            }
        });
    }


    return {
        save: save,
        onSelectParty: onSelectParty,
        calculateTDS: calculateTDS,
        getAllLedgerUnderIncome: getAllLedgerUnderIncome
    }
})();

$(document).ready(function () {
    moneyReceipt.save();
    moneyReceipt.onSelectParty();
    moneyReceipt.calculateTDS();
    moneyReceipt.getAllLedgerUnderIncome();

});

