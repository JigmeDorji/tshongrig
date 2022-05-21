/**
 * Created by Bcass Sawa on 10/24/2019.
 */

accSaleInvoiceGeneration = (function () {

    var ledgerList = [];
    var baseURL = 'saleInvoiceGeneration/';
    var saleInvoiceGenerationGridTBody = $('#saleInvoiceGenerationGrid tbody');

    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                if (validateRequiredField()) {
                    var data = $(form).serializeArray();
                    $.ajax({
                        url: baseURL + 'save',
                        type: 'POST',
                        data: data,
                        success: function (res) {
                            swal({
                                timer: 800,
                                type: "success",
                                title: res.text,
                                showConfirmButton: false
                            }, function () {
                                if (parseInt($('#isCash').val()) === 3) {
                                    window.location = spms.getUrl() + baseURL +
                                        '/generateSaleInvoice?saleInvoiceId=' + encodeURIComponent(res.dto.saleInvoiceId) +
                                        "&partyName=" + $('#partyName').val();
                                    $('.amount').val('');
                                } else {
                                    window.location.reload();
                                }
                            });
                        }
                    })
                }
            }
        })
    }

    function validateRequiredField() {
        let validForm = true;
        let isCash = $('#isCash');
        let amtReceived = $('#amtReceived');
        let amtReturn = $('#amtReturn');
        let partyName = $('#partyName');
        let partyAddress = $('#partyAddress');
        let partyContactNo = $('#partyContactNo');
        let bankLedgerId = $('#bankLedgerId');
        let amountReceivedInBank = $('#amountReceivedInBank');

        if (parseFloat($('#grandTotal').val()) > 0) {

            //check sale in type
            if (isCash.val() === '') {
                errorMsg('Please select sale in type.');
                isCash.addClass('error');
                validForm = false;
            } else {
                isCash.removeClass('error');
            }

            //supply or counter
            if (parseInt($('#counterOrSupply').val()) === 2) {
                if (isCash.val() !== 3) {
                    errorMsg('It is credit sale. Please check');
                    isCash.addClass('error');
                    validForm = false;
                }
            }

            //validate on cash selection
            if (parseInt(isCash.val()) === 1) {
                if (amtReturn.val() < 0) {
                    if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                        errorMsg('Please enter party detail.');
                        validForm = false;
                    }
                }
            }

            //validate on bank selection
            if (parseInt(isCash.val()) === 2) {
                if (amtReceived.val() === '' || amtReceived.val() <= 0) {
                    errorMsg('Please enter amount received.');
                    amtReceived.addClass('error');
                    validForm = false;
                } else if (bankLedgerId.val() === '') {
                    errorMsg('Please select bank account.');
                    bankLedgerId.addClass('error');
                    validForm = false;
                } else if (amtReturn.val() < 0) {
                    if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                        errorMsg('Please enter party detail.');
                        validForm = false;
                    }
                }
                amtReceived.removeClass('error');
                bankLedgerId.removeClass('error');
            }

            //validate on credit selection
            if (parseInt(isCash.val()) === 3) {
                if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                    errorMsg('Please enter party detail.');
                    validForm = false;
                }
            }

            //validate on cash and bank selection
            if (parseInt(isCash.val()) === 4) {
                if (amtReceived.val() === '' || amtReceived.val() <= 0) {
                    errorMsg('Please enter amount received in cash.');
                    amtReceived.addClass('error');
                    validForm = false;
                } else if (bankLedgerId.val() === '') {
                    errorMsg('Please select bank account.');
                    bankLedgerId.addClass('error');
                    amtReceived.removeClass('error');
                    validForm = false;
                } else if (amountReceivedInBank.val() <= 0 || amountReceivedInBank.val() === '') {
                    errorMsg('Please enter amount received in bank.');
                    bankLedgerId.removeClass('error');
                    validForm = false;
                }
            }
        }
        return validForm;
    }


    function getLedgerList() {
        $.ajax({
            url: baseURL + 'getLedgerList',
            type: 'GET',
            success: function (res) {
                ledgerList = $.map(res, function (value) {
                    return {value: value.ledgerName};
                });

                $("#itemName").devbridgeAutocomplete({
                    lookup: ledgerList
                });
            }
        });
    }

    function getPartyList() {
        $.ajax({
            url: baseURL + 'getPartyList',
            type: 'GET',
            success: function (res) {
                var partyList = $.map(res, function (value) {
                    return {value: value.partyName};
                });

                $("#partyName").devbridgeAutocomplete({
                    lookup: partyList,
                    onSelect: function (suggestion) {
                        $.ajax({
                            url: baseURL + 'getPartyDetail',
                            type: 'GET',
                            data: {partyName: suggestion.value},
                            success: function (res) {
                                populate(res);
                            }
                        })
                    }
                });
            }
        });
    }

    function removeFromGrid() {
        saleInvoiceGenerationGridTBody.on('click', '.btnRemoveRow', function () {
            var row = $('#saleInvoiceGenerationGrid tbody tr');
            removeRow($(this), saleInvoiceGenerationGridTBody, row);

            var allRow = saleInvoiceGenerationGridTBody.find('tr');
            spms._formIndexing(saleInvoiceGenerationGridTBody, allRow);
            calculateTotalCharges();
        });
    }

    function removeRow(clickedRemoveBtn, tableBody, row) {
        var removableRow = clickedRemoveBtn.parent().parent();
        if (row.length === 0) {
            removableRow.find('input[type="text"]').val('');
        } else {
            removableRow.remove();
            spms._formIndexing(tableBody, row);
            if ($(tableBody).children().length == 0) {
                $('#btnSave').attr('disabled', true);
            }
        }
    }

    $("#partyName").on('blur', function () {
        $.ajax({
            url: baseURL + 'getPartyDetail',
            type: 'GET',
            data: {partyName: $(this).val()},
            success: function (res) {
                if (res.length <= 0) {
                    $('.resetfield').val('')
                } else {
                    populate(res);
                }
            }
        })
    });

    function calculateTotalServiceCharges() {
        saleInvoiceGenerationGridTBody.on('keyup change', '.amount', function () {
            calculateTotalCharges();
        })
    }

    function calculateTotalCharges() {
        var totalAmount = 0;
        saleInvoiceGenerationGridTBody.find('tr').each(function () {
            var selectedRow = $(this).closest('tr');
            if (typeof selectedRow.find('.amount').val() !== 'undefined' && selectedRow.find('.amount').val() !== '') {
                totalAmount = totalAmount + parseFloat(selectedRow.find('.amount').val());
            }
        });
        $('#grandTotal').val(totalAmount);
    }

    function addMoreToGrid() {
        saleInvoiceGenerationGridTBody.on('click', '.btnAddRow', function () {
            var row = $('#saleInvoiceGenerationGrid tbody tr');
            var lastRow = $(this).parent('td').parent('tr').prev('tr');
            if ((lastRow.find('.particular').val()) && (lastRow.find('.amount').val())) {
                spms.addRowWithAutoSearchId(saleInvoiceGenerationGridTBody, row, ledgerList);
                lastRow = $(this).parent('td').parent('tr').prev('tr');
                lastRow.find('.id').val('')
            }

            var allRow = saleInvoiceGenerationGridTBody.find('tr');
            spms._formIndexing(saleInvoiceGenerationGridTBody, allRow);
        })
    }

    function addRowToGrid() {

        saleInvoiceGenerationGridTBody.on('keydown', '.amount', function (e) {
            if (e.keyCode == 13) {
                var row = $('#saleInvoiceGenerationGrid tbody tr');
                var lastRow = saleInvoiceGenerationGridTBody.find('.btnAddRow').parent('td').parent('tr').prev('tr');
                if ((lastRow.find('.particular').val()) && (lastRow.find('.amount').val())) {
                    spms.addRowWithAutoSearchId(saleInvoiceGenerationGridTBody, row, ledgerList);
                    lastRow = $(this).parent('td').parent('tr').prev('tr');
                    lastRow.find('.id').val('')
                }

                let allRow = saleInvoiceGenerationGridTBody.find('tr');
                spms._formIndexing(saleInvoiceGenerationGridTBody, allRow);
            }
        })
    }

    function onPaidInChange() {
        $('#isCash').on('change', function () {

            let amountReceivedText = $('#amountReceivedText')
            amountReceivedText.text("Amount Received");

            $('#discountRate').val(0);
            $('#amtReceived').val(0);
            $('#amountReceivedInBank').val(0);

            calculateTotalCharges();

            if (parseInt($(this).val()) === 3) {
                $('.creditDetails').attr('hidden', false);
            } else {
                $('.creditDetails').attr('hidden', true);
            }

            if (parseInt($(this).val()) === 2 || parseInt($(this).val()) === 4) {
                $('#bankDetails').attr('hidden', false);
                if (parseInt($(this).val()) === 4) {
                    amountReceivedText.text("Amount in Cash");
                    $('#bankAmountId').attr('hidden', false);
                } else {
                    $('#bankAmountId').attr('hidden', true);
                }
            } else {
                $('#bankDetails').attr('hidden', true);
                $('#bankAmountId').attr('hidden', true);
            }
        })
    }

    function calculateReturnAmt() {

        let calculatedAmount = 0,
            amtReceived = 0,
            discountRate = 0,
            amountReceivedInBank = 0,
            amtReturn = $('#amtReturn'),
            grandTotal = $('#grandTotal'),
            discountRateId = $('#discountRate'),
            amtReceivedId = $('#amtReceived'),
            amountReceivedInBankId = $('#amountReceivedInBank'),
            isCash = $('#isCash');

        amtReceivedId.on('keyup', function () {
                amtReceived = $(this).val() === '' ? 0 : parseFloat($(this).val());
                if (parseInt(isCash.val()) !== 4) {
                    calculatedAmount = amtReceived - (parseFloat(grandTotal.val()) - parseFloat(discountRateId.val()));
                    amtReturn.val(calculatedAmount);
                } else {
                    if (parseFloat(grandTotal.val()) <= parseFloat(amtReceivedId.val())) {

                        if (parseFloat(grandTotal.val()) < parseFloat(amtReceivedId.val())) {
                            amountReceivedInBankId.val(0);
                        }
                        amountReceivedInBank = amountReceivedInBankId.val() === '' || amountReceivedInBankId.val() === 0 ? 0 : parseFloat(amountReceivedInBankId.val());
                        calculatedAmount = (amountReceivedInBank + amtReceived)

                        if (discountRateId.val() > 0) {
                            calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount) - parseFloat(grandTotal.val());
                        } else {
                            calculatedAmount = calculatedAmount - parseFloat(grandTotal.val());
                        }

                        if (parseFloat(grandTotal.val()) > parseFloat(amtReceivedId.val())) {
                            amountReceivedInBankId.val(calculatedAmount);
                        }
                        amtReturn.val(calculatedAmount);
                    } else {
                        if (parseInt(isCash.val()) === 4) {
                            calculatedAmount = grandTotal.val() - amtReceivedId.val();
                            if (parseFloat(discountRateId.val()) > 0) {
                                calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                            }
                            if (parseFloat(calculatedAmount) > 0) {
                                amountReceivedInBankId.val(calculatedAmount);
                            } else {
                                amountReceivedInBankId.val(0);
                            }
                            amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
                        } else {
                            calculatedAmount = grandTotal.val() - amtReceivedId.val();
                            if (discountRateId.val() > 0) {
                                calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount);
                            }
                            amountReceivedInBankId.val(calculatedAmount);
                            amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val())));
                        }

                    }
                }

                if (parseInt(isCash.val()) !== 4) {
                    if (amtReturn.val() < 0) {
                        $('#amountReceivedText').text("Amount Received");
                        $('#bankDetails').attr('hidden', true);
                        $('#bankAmountId').attr('hidden', true);
                        $('.creditDetails').attr('hidden', false);

                    } else {
                        $('#amountReceivedText').text("Amount Received");
                        $('.creditDetails').attr('hidden', true);
                        resetPartRelated();
                    }
                    if (parseInt(isCash.val()) === 2) {
                        $('#bankDetails').attr('hidden', false);
                    } else {
                        $('#bankDetails').attr('hidden', true);
                    }
                }
            }
        );

        amountReceivedInBankId.on('keyup', function () {
            amountReceivedInBank = $(this).val() === '' ? 0 : parseFloat($(this).val());
            if (parseInt(isCash.val()) === 4) {
                calculatedAmount = grandTotal.val() - amtReceivedId.val();
                if (parseFloat(discountRateId.val()) > 0) {
                    calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                }
                amountReceivedInBankId.val(calculatedAmount);
                amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
            } else {
                calculatedAmount = (parseFloat(amtReceivedId.val()) + amountReceivedInBank);
                if (discountRate > 0 && calculatedAmount > 0) {
                    calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount) - parseFloat(grandTotal.val());
                } else {
                    calculatedAmount = calculatedAmount - parseFloat(grandTotal.val());
                }
                amtReturn.val(calculatedAmount);
            }
        });

        discountRateId.on('keyup', function () {

            if (parseFloat($(this).val()) > parseFloat(grandTotal.val())) {
                $(this).val('');
                amtReceivedId.attr('readonly', false);
                errorMsg('Discount figure more than sales');
            } else {

                if (parseInt(isCash.val()) === 2) {
                    if (parseFloat($(this).val()) === parseFloat(grandTotal.val())) {
                        amtReceivedId.val(0);
                        amtReceivedId.attr('readonly', true);
                    } else {
                        amtReceivedId.attr('readonly', false);
                    }
                }

                if (parseInt(isCash.val()) === 4) {
                    calculatedAmount = grandTotal.val() - amtReceivedId.val();
                    if (parseFloat(discountRateId.val()) > 0) {
                        calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                    }
                    amountReceivedInBankId.val(calculatedAmount);
                    amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
                } else {
                    discountRate = $(this).val() === '' ? 0 : parseFloat($(this).val());
                    calculatedAmount = (parseFloat(amountReceivedInBankId.val()) + parseFloat(amtReceivedId.val()) + discountRate);
                    amtReturn.val(calculatedAmount - grandTotal.val());
                }

                if (parseInt(isCash.val()) !== 4) {

                    if (amtReturn.val() < 0) {
                        $('#amountReceivedText').text("Amount Received");
                        $('#bankDetails').attr('hidden', true);
                        $('#bankAmountId').attr('hidden', true);
                        $('.creditDetails').attr('hidden', false);

                    } else {
                        $('#amountReceivedText').text("Amount Received");
                        $('.creditDetails').attr('hidden', true);
                        resetPartRelated();
                    }
                    if (parseInt(isCash.val()) === 2) {
                        $('#bankDetails').attr('hidden', false);
                    } else {
                        $('#bankDetails').attr('hidden', true);
                    }
                }

            }
        })
    }

    function resetPartRelated() {
        $('#partyName').val('');
        $('#partyAddress').val('');
        $('#partyContactNo').val('');
        $('#partyEmail').val('');
    }

    return {
        getLedgerList: getLedgerList,
        getPartyList: getPartyList,
        addMoreToGrid: addMoreToGrid,
        save: save,
        removeFromGrid: removeFromGrid,
        calculateTotalServiceCharges: calculateTotalServiceCharges,
        addRowToGrid: addRowToGrid,
        onPaidInChange: onPaidInChange,
        calculateReturnAmt: calculateReturnAmt
    }
})();

$(document).ready(function () {
    accSaleInvoiceGeneration.getLedgerList();
    accSaleInvoiceGeneration.getPartyList();
    accSaleInvoiceGeneration.addMoreToGrid();
    accSaleInvoiceGeneration.save();
    accSaleInvoiceGeneration.removeFromGrid();
    accSaleInvoiceGeneration.calculateTotalServiceCharges();
    accSaleInvoiceGeneration.addRowToGrid();
    accSaleInvoiceGeneration.onPaidInChange();
    accSaleInvoiceGeneration.calculateReturnAmt();

});