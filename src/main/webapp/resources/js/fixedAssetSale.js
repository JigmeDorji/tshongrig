/**
 * Created by jigme.dorji on 9/16/2021.
 */
fixedAssetSale = (function () {

        let faSaleItemGrid = $('#faSaleItemGrid').dataTable({
            info: false,
            bSort: false,
            paging: false,
            sorting: false,
            searching: false
        });
        let faSaleItemGridTBody = $('#faSaleItemGrid tbody');

        function baseURL() {
            return "fixedAssetSale"
        }

        function getItemDetails() {
            $('#itemCode').on('keyup', function () {
                if ($(this).val() !== '') {
                    fetchDetail($(this).val());
                }
            });
        }

        function fetchDetail(assetDetailId) {
            $.ajax({
                url: baseURL() + '/getFaItemDetails',
                type: 'GET',
                data: {assetDetailId: assetDetailId},
                success: function (res) {
                    if (res.length > 0) {
                        $('#particular').val(res[0].particular);
                        $('#assetDetailId').val(res[0].assetDetailId);
                    } else {
                        $('#itemName').val('');
                        $('#sellingPrice').val('');
                    }
                }
            })
        }

        function addGridToDetails() {
            let i = 1;
            let itemName = $('#itemName');
            let assetCode = $('#assetCode');
            let faPurchaseDetailId = $('#faPurchaseDetailId');
            let group = $('#group');
            let sellingPrice = $('#sellingPrice');
            if (!fieldValidation(assetCode, itemName, sellingPrice, group)) {

                assetCode.removeClass('error');
                group.removeClass('error');
                itemName.removeClass('error');
                sellingPrice.removeClass('error');

                let row = "<tr>" +
                    "<td><input type='text' id='index' readonly class='form-control' value='" + i + "'></td>" +
                    "<td><input type='hidden' id='faPurchaseDetailId' readonly name='saleItemListDTO[" + i + "].faPurchaseDetailId' class='form-control' value='" + faPurchaseDetailId.val() + "'><input type='text' id='itemName' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control' value='" + itemName.val() + "'></td>" +
                    "<td><input type='text' id='assetCode' readonly name='saleItemListDTO[" + i + "].assetCode' class='form-control assetCode' value='" + assetCode.val().toUpperCase() + "'></td>" +
                    "<td><input type='text' readonly  class='form-control' value='" + group.val() + "'></td>" +
                    "<td><input type='text' readonly name='saleItemListDTO[" + i + "].sellingPrice' class='form-control sellingPrice right-align' value='" + sellingPrice.val() + "'></td>" +
                    "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-xm fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-danger btn-xm fa fa-trash' value='Delete'></td>" +
                    "</tr>";
                i++;
                let tableGrid = $('#faSaleItemGrid');
                let tableBody = tableGrid.find('tbody');
                if (!isValid(tableBody, assetCode)) {
                    let noMatch = $(tableBody).find('td').first().html();
                    if (noMatch == 'No data available in table') {
                        $(tableBody).find('tr').remove();
                    }
                    tableGrid.find('tbody').append(row);

                    let allRow = tableBody.find('tr');
                    spms._formIndexing(tableBody, allRow);
                    if ($('#voucherNo').val() === '') {
                        $('#discountRate').val(0);
                        $('#amtReceived').val(0);
                    } else {
                        $('.resetField').val('');
                    }
                    $('#printBtn').attr('disabled', false)
                }

                $('#itemCode').val('');
                $('#sellingPrice').val('');
                calculateTotal();
            }
        }

        function isValid(tableData, assetCode) {
            let isItemCodeExist = false;
            tableData.find('tr').each(function () {
                if ($(this).find('.assetCode').val() === assetCode.val()) {
                    swal({
                        title: "This item is already added in the list. Please check.",
                        text: "Click OK to exit",
                        type: "warning"
                    });
                    $('.resetField').val('');
                    isItemCodeExist = true;
                    return false;
                }
            });
            return isItemCodeExist;
        }

        function fieldValidation(assetCode, itemName, sellingPrice, group) {
            let errorExists = false;

            if (assetCode.val() === '') {
                assetCode.addClass('error');
                errorExists = true;
            }
            if (itemName.val() === '') {
                itemName.addClass('error');
                errorExists = true;
            }

            if (sellingPrice.val() === '') {
                sellingPrice.addClass('error');
                errorExists = true;
            }
            if (group.val() === '') {
                group.addClass('error');
                errorExists = true;
            }
            return errorExists;
        }

        function checkItemCodeAlreadyExists(itemCode) {
            let exists = false;
            $('#faSaleItemGrid').find('tr').each(function () {
                if ($(this).closest('tr').find('#itemCode').val() === itemCode.val().toUpperCase()) {
                    exists = true;
                }
            });
            return exists;
        }

        function saveSaleItemDetails() {
            $('#printBtn').on('click', function () {
                if (validateRequiredField()) {
                    const $submitButton = $(this); // Cache the submit button element
                    $('#printBtn').prop('disabled', true); // D
                    $.ajax({
                            url: baseURL() + '/saveSaleItemDetails',
                            type: 'POST',
                            data: $('#fixedAssetSaleForm').serializeArray(),
                            success: function (res) {
                                if (res.status === 1) {
                                    successMsg(res.text)
                                    window.location.reload();
                                } else {
                                    errorMsg(res.text)
                                }
                            }, complete: function () {
                            $('#printBtn').prop('disabled', false); // D
                            },error:()=>{
                            $('#printBtn').prop('disabled', false); // D
                        }
                        },
                    );
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

        function onBtnClickDeleteItem() {
            $('#faSaleItemGrid').find('tbody').on('click', 'tr #btnDeleteItem', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.remove();
                let allRow = faSaleItemGridTBody.find('tr');
                spms._formIndexing(faSaleItemGridTBody, allRow);
                calculateTotal();
            })
        }

        function onBtnClickEditItem() {
            $('#faSaleItemGrid').find('tbody').on('click', 'tr #itemEditBtn', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.find('.sellingPrice').focus();
                selectedRow.find('.sellingPrice').attr('readonly', false);
            })
        }

        faSaleItemGridTBody.on('keyup', '.sellingPrice ', function () {
            calculateTotal()
        });


        function calculateTotal() {
            let totalAmount = 0;
            faSaleItemGridTBody.find('tr').each(function () {
                let row = $(this).closest('tr');
                if (row.find('.sellingPrice').val() !== 'undefined') {
                    totalAmount = totalAmount + parseFloat(row.find('.sellingPrice').val());
                }
            });

            $('#grandTotal').val(totalAmount);
            $('#amtReturn').val((parseFloat(Math.abs($('#amountReceivedInBank').val())) + parseFloat(Math.abs($('#amtReceived').val())) + parseFloat($('#discountRate').val())) - totalAmount)

            if (parseFloat(totalAmount) === 0) {
                $('#amtReceived').val(0);
                $('#discountRate').val(0);
                $('#amtReceived').attr('readonly', true)
                $('#isCash').val(4);
                $('#invoiceNo').val('');
                $('#amtReturn').val(0);
                $('#bankDetails').attr('hidden', true);
                $('#bankAmountId').attr('hidden', true);
                $('.creditDetails').attr('hidden', true);

            } else {
                // $('#isCash').val('');
                $('#amtReceived').attr('readonly', false)
            }
        }


        function getSaleDetail() {
            let voucherNo = $('#voucherNo').val();
            if (voucherNo !== '' && voucherNo !== null) {
                $.ajax({
                    url: 'saleItem/getSaleDetail',
                    type: 'GET',
                    data: {voucherNo: voucherNo},
                    success: function (res) {

                        let iterator = 1;
                        if (res.saleItemListDTO.length > 0) {
                            populate(res);
                            faSaleItemGrid.dataTable().fnClearTable();
                            for (let i in res.saleItemListDTO) {

                                faSaleItemGrid.fnAddData(
                                    [
                                        "<td><input type='text' id='index' readonly class='form-control' value='" + iterator + "'></td>",
                                        "<td><input type='text' id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control' value='" + res.saleItemListDTO[i].itemCode + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control' value='" + res.saleItemListDTO[i].itemName + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].unitName' class='form-control' value='" + res.saleItemListDTO[i].unitName + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].sellingPrice' class='form-control sellingPrice right-align' value='" + res.saleItemListDTO[i].sellingPrice + "'></td>",
                                        "<td><input type='text' readonly class='form-control totalAmount right-align totalAmount' id='totalAmount'   value=" + res.saleItemListDTO[i].totalAmount + " ><input type='hidden' readonly class='form-control formatDate right-align' id='saleDate'  name='saleItemListDTO[" + i + "].saleDate' value=" + formatAsDate(res.saleItemListDTO[i].saleDate) + " ></td>",
                                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-xm fa fa-trash' value='Edit'></td>"
                                    ]
                                );
                                iterator = iterator + 1;
                            }
                            calculateTotal();

                            if (res.isCash === 2) {
                                $('#bankDetails').attr('hidden', false);
                            } else {
                                $('#bankDetails').attr('hidden', true);
                            }

                            if (res.partyName !== null && res.partyName !== '') {
                                $('.creditDetails').attr('hidden', false);
                                $.ajax({
                                    url: 'saleInvoiceGeneration/getPartyDetail',
                                    type: 'GET',
                                    data: {partyName: res.partyName},
                                    success: function (res) {
                                        populate(res);
                                    }
                                })
                            } else {
                                $('.creditDetails').attr('hidden', true);
                            }

                            if (res.isCash === 4) {
                                $('#bankDetails').attr('hidden', false);
                                $('#amountReceivedText').text("Amount in Cash");
                                $('#bankAmountId').attr('hidden', false);
                            }

                            $('#printBtn').attr('disabled', false)
                        }
                    }
                });
            }
        }

        function onFocusOut() {
            $('#sellingPrice').on('focusout', function () {
                let assetCode = $('#assetCode').val();
                if (assetCode !== '' && typeof assetCode !== 'undefined') {
                    addGridToDetails();
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

        function returnItemOnClick() {
            $('#returnItem').on('click', function () {
                $('#replacementItemModal').modal('show');
            });
        }

        function onCreditPurchaseSelect() {
            $('#isCash').on('change', function () {

                let amountReceivedText = $('#amountReceivedText')
                amountReceivedText.text("Amount Received");

                $('#discountRate').val(0);
                $('#amtReceived').val(0);
                $('#amountReceivedInBank').val(0);
                calculateTotal();

                if (parseInt($(this).val()) === 3) {
                    $('.creditDetails').attr('hidden', false);
                } else {
                    $('.creditDetails').attr('hidden', true);
                }

                if (parseInt($(this).val()) === 2) {
                    $('#bankDetails').attr('hidden', false);
                    $('#bankAmountId').attr('hidden', true);
                } else {
                    $('#bankDetails').attr('hidden', true);
                    $('#bankAmountId').attr('hidden', true);
                }
            })
        }

        let partyList;

        function getPartyList() {
            $.ajax({
                url: 'saleInvoiceGeneration/getPartyList',
                type: 'GET',
                success: function (res) {
                    partyList = $.map(res, function (value) {
                        return {value: value.partyName};
                    });

                    $("#partyName").devbridgeAutocomplete({
                        lookup: partyList,
                        onSelect: function (suggestion) {
                            $.ajax({
                                url: 'saleInvoiceGeneration/getPartyDetail',
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

        function getItemList() {
            $.ajax({
                url: baseURL() + '/getItemList',
                type: 'GET',
                success: function (res) {

                    console.log(res)
                    let items = new Bloodhound({
                        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('text', 'accTypeName'),
                        queryTokenizer: Bloodhound.tokenizers.whitespace,
                        local: res
                    });

                    $('#itemName').typeahead({
                            hint: true,
                            highlight: true,
                        },
                        {
                            name: 'items',
                            displayKey: 'text',
                            source: items.ttAdapter(),
                            templates: {
                                notFound: '<div>Not Found</div>',
                                pending: '<div>Loading...</div>',
                                suggestion: function (data) {
                                    return '<div>' + data.text + '</div>'
                                },
                            }
                        }).on('typeahead:selected', function (event, data) {

                        $('#group').val(data.accTypeName);

                        $.ajax({
                            url: baseURL() + '/getItemCodeList',
                            type: 'GET',
                            data: {assetDetailId: data.valueBigInteger},
                            success: function (result) {

                                console.log(result)
                                if (result.length === 1) {
                                    $('#assetCode').val(result[0].text);
                                    $('#faPurchaseDetailId').val(result[0].valueBigInteger);
                                }else {
                                    $('#assetCode').val(result[result.length - 1].text);
                                    $('#faPurchaseDetailId').val(result[result.length - 1].valueBigInteger);

                                }





                                let assetCode = $('#assetCode');

                                let itemCode = new Bloodhound({
                                    datumTokenizer: function (data) {
                                        return Bloodhound.tokenizers.whitespace(data.assetCode);
                                    }, queryTokenizer: Bloodhound.tokenizers.whitespace,
                                    identify: function (obj) {
                                        return obj.assetCode;
                                    },
                                    local: $.map(result, function (value) {
                                        return {assetCode: value.text, value: value.valueBigInteger};
                                    })
                                });


                                assetCode.typeahead('val', '');
                                assetCode.typeahead('destroy');

                                itemCode.initialize();

                                assetCode.typeahead({
                                        hint: true,
                                        highlight: true,
                                        minLength: 1
                                    },
                                    {
                                        name: 'itemCode',
                                        displayKey: 'assetCode',
                                        source: itemCode.ttAdapter(),
                                        templates: {
                                            notFound: '<div>Not Found</div>',
                                            pending: '<div>Loading...</div>',
                                            suggestion: function (data) {
                                                return '<div>' + data.assetCode + '</div>'
                                            },
                                        }
                                    }).on('typeahead:selected', function (event, data) {
                                    $('#faPurchaseDetailId').val(data.value);
                                });

                            }
                        })
                    });
                }
            })
        }

        return {
            getPartyList: getPartyList,
            getItemList: getItemList,
            calculateReturnAmt: calculateReturnAmt,
            saveSaleItemDetails: saveSaleItemDetails,
            onBtnClickDeleteItem: onBtnClickDeleteItem,
            onBtnClickEditItem: onBtnClickEditItem,
            onFocusOut: onFocusOut,

            getItemDetails: getItemDetails,
            // getSaleDetail: getSaleDetail,
            returnItemOnClick: returnItemOnClick,
            onCreditPurchaseSelect: onCreditPurchaseSelect,
        }
    }

)();

$(document).ready(function () {
    fixedAssetSale.getItemList();
    fixedAssetSale.calculateReturnAmt();
    fixedAssetSale.saveSaleItemDetails();

    fixedAssetSale.getItemDetails();
    fixedAssetSale.onBtnClickDeleteItem();
    fixedAssetSale.onBtnClickEditItem();
    // fixedAssetSale.getSaleDetail();
    fixedAssetSale.onFocusOut();
    fixedAssetSale.returnItemOnClick();
    fixedAssetSale.onCreditPurchaseSelect();
    fixedAssetSale.getPartyList();
});
