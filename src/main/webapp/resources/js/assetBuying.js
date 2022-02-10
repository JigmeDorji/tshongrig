/**
 * Created by Bikash Rai on 10/4/2021.
 */

assetBuying = (function () {

        let valueList;
        let items;
        let iterator = 0;
        let fixedAssetBuyingGrid = $('#fixedAssetBuyingGrid');
        let fixedAssetBuyingGridTBody = $('#fixedAssetBuyingGrid tbody');

        function baseURL() {
            return 'assetBuying';
        }


        function saveItem() {
            $('#btnSave').on('click', function () {
                $('.globalForm').validate({
                    submitHandler: function (form) {
                        $.ajax({
                            url: 'assetBuying/save',
                            type: 'POST',
                            data: $(form).serializeArray(),
                            success: function (res) {
                                if (res.status === 1) {
                                    swal({
                                        timer: 800,
                                        type: "success",
                                        title: res.text,
                                        showConfirmButton: false
                                    }, function () {
                                        window.location = 'assetBuying';
                                    });
                                } else {
                                    swal({
                                        title: res.text,
                                        text: "Click OK to exit",
                                        type: "warning"
                                    }, function () {
                                    });
                                }
                                $('#btnSave').attr('disabled', false);
                            }
                        });
                    }
                });
            });
        }

        function getItemSuggestionList() {
            $.ajax({
                url: 'assetOpening/getItemSuggestionList',
                type: 'GET',
                success: function (res) {
                    valueList = $.map(res, function (value) {
                        return {value: value.particular, id: value.assetDetailId};
                    });
                    fixedAssetBuyingGrid.find('tbody').append(returnRow());

                    items = new Bloodhound({
                        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
                        queryTokenizer: Bloodhound.tokenizers.whitespace,
                        local: valueList
                    });

                    // items.initialize();

                    $('#autoCompleteId' + iterator).typeahead({
                            hint: true,
                            highlight: true,
                        },
                        {
                            name: 'items',
                            displayKey: 'value',
                            source: items.ttAdapter(),
                            templates: {
                                notFound: '<div>Not Found</div>',
                                pending: '<div>Loading...</div>',
                                suggestion: function (data) {
                                    return '<div>' + data.value + '</div>'
                                },
                            }
                        }).on('typeahead:selected', function (event, data) {
                        $('#assetDetailId' + iterator).val(data.id);
                    });

                    spms.loadGridDropDown(fixedAssetBuyingGridTBody.find('tr').find('.fixedAssetGroupId'), res);
                    indexRowNo(fixedAssetBuyingGrid);
                }
            });
        }

        function addMoreRow() {
            fixedAssetBuyingGridTBody.on('click', '#addBtn', function () {
                let selectedRow = $(this).closest('tr');
                fixedAssetBuyingGrid.find('tbody').append(returnRow());
                selectedRow.find('.addBtn').addClass('hidden');
                selectedRow.find('.removeBtn').removeClass('hidden');

                items = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    local: valueList
                });

                items.initialize();

                $('#autoCompleteId' + iterator).typeahead({
                        hint: true,
                        highlight: true,
                    },
                    {
                        name: 'items',
                        displayKey: 'value',
                        source: items.ttAdapter(),
                        templates: {
                            notFound: '<div>Not Found</div>',
                            pending: '<div>Loading...</div>',
                            // header: '<div>Found Records:</div>',
                            suggestion: function (data) {
                                return '<div>' + data.value + '</div>'
                            },
                            // footer: '<div>Footer Content</div>',
                        }
                    }).on('typeahead:selected', function (event, data) {
                    $('#assetDetailId' + iterator).val(data.id);
                });
                spms._formIndexing(fixedAssetBuyingGrid.find('tbody'), fixedAssetBuyingGrid.find('tbody tr'));
                indexRowNo(fixedAssetBuyingGrid);
            });
        }

        function returnRow() {
            iterator = iterator + 1;
            return "<tr>" +
                "<td><input type='text' readonly class='form-control rowNumber'>" +
                "<input type='hidden'  id='assetDetailId" + iterator + "' name='openingAndBuyingListDTO[0].assetDetailId'  class='form-control assetDetailId'></td>" +
                "<td>" +
                "<input type='hidden' id='faPurchaseId' name='openingAndBuyingListDTO[0].faPurchaseId'  class='form-control faPurchaseId'>" +
                "<input type='text' id='autoCompleteId" + iterator + "' class='form-control particular' name='openingAndBuyingListDTO[0].particular'>" +
                "</td>" +
                "<td><input type='text'  name='openingAndBuyingListDTO[0].rate' class='form-control rate'></td>" +
                "<td><input type='text'  name='openingAndBuyingListDTO[0].qty' class='form-control qty right-align'></td>" +
                "<td><input type='text' readonly name='openingAndBuyingListDTO[0].totalAmount' class='form-control  totalAmount right-align'></td>" +
                "<td><button class='btn btn-danger btn-xs ml-3 d-none d-sm-inline-block removeBtn hidden' type='button' id='removeBtn'><i class='fa fa-trash'></i> Delete</button>" +
                "<button class='btn  btn-xs btn-success d-sm-inline-block addBtn' type='button' id='addBtn'><i class='fa fa-plus'></i> Add More</button></td></tr>";
        }

        function deleteItem() {
            fixedAssetBuyingGridTBody.on('click', 'tr #removeBtn', function () {
                    let selectedRow = $(this).closest('tr');
                    let faPurchaseId = selectedRow.find('.faPurchaseId').val();
                    if (faPurchaseId !== '') {
                        confirmMessage("Are you sure you want to delete ?", function () {
                                spms.ajax(baseURL() + 'deleteItem',
                                    'POST', {faPurchaseId: faPurchaseId}
                                    , function (res) {
                                        if (res.status === 1) {
                                            successMsg(res.text);
                                            _disPlayData();
                                        } else {
                                            errorMsg(res.text);
                                        }
                                    })
                            }
                        );
                    } else {
                        selectedRow.remove();
                    }
                    indexRowNo(fixedAssetBuyingGrid);
                }
            );
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
                    resetPartRelated();
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

        function resetPartRelated() {
            $('#partyName').val('');
            $('#partyAddress').val('');
            $('#partyContactNo').val('');
            $('#partyEmail').val('');
        }

        function calculateTotal() {
            let grandTotalAmount = 0;
            fixedAssetBuyingGridTBody.find('tr').each(function () {
                let totalAmount = 0;
                let row = $(this).closest('tr');
                if (typeof row.find('.totalAmount ').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined') {
                    totalAmount = (row.find('.rate').val() === '' ? 0 : parseFloat(row.find('.rate').val())) *
                        (row.find('.qty').val() === '' ? 0 : parseFloat(row.find('.qty').val()));
                    // grandTotalAmount = grandTotalAmount + (parseFloat(row.find('.totalAmount').val()));
                }
                row.find('.totalAmount').val(totalAmount);
                grandTotalAmount = grandTotalAmount + totalAmount;
            });
            $('#grandTotal').val(grandTotalAmount);
        }

        function onBtnClickDeleteItem() {
            fixedAssetBuyingGrid.on('click', 'tr #btnDeleteItem', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.remove();
                let allRow = fixedAssetBuyingGrid.find('tr');
                spms._formIndexing(fixedAssetBuyingGrid, allRow);
                calculateTotal();
            })
        }

        function onBtnClickEditItem() {
            fixedAssetBuyingGrid.on('click', 'tr #itemEditBtn', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.find('.qty').focus();
                selectedRow.find('.qty').attr('readonly', false);
                selectedRow.find('.costPrice').attr('readonly', false);
                selectedRow.find('.sellingPrice').attr('readonly', false);
                selectedRow.find('.partNo').attr('readonly', false);
            })
        }

        function getPurchaseInvoiceItemList() {
            $('#purchaseInvoiceNo').on('change', function () {
                $.ajax({
                    url: 'receivedItem/getItemListByInvoiceNo',
                    type: 'GET',
                    data: {purchaseInvoiceNo: $(this).val()},
                    success: function (data) {
                        if (data.status === 1) {
                            _disPlayData(data);
                        } else {
                            $('#purchaseItemTable tbody tr').remove();
                            $('#isCash').val('');
                            $('#bankLedgerId').val('');
                            $('#grandTotalAmount').val('');
                        }
                    }
                });
            })
        }

        function _disPlayData(data) {
            if (data.status === 1) {
                $('#purchaseItemTable tbody tr').remove();
                let res = data.dto;
                for (let i = 0; i < res.length; i++) {
                    $('#purchaseDate').val(formatAsDate(res[i].purchaseDate))
                    $('#purchaseVoucherNo').val(res[i].purchaseVoucherNo);
                    $('#purchaseInvoiceNo').val(res[i].purchaseInvoiceNo);
                    $('#supplierName').val(res[i].supplierName);
                    $('#isCash').val(res[i].isCash);
                    if (res[i].isCash === 2) {
                        $('#bankDetails').attr('hidden', false);
                        $('#bankLedgerId').val(res[0].bankLedgerId);
                    } else {
                        $('#bankDetails').attr('hidden', true);
                    }
                    if (res[i].isCash === 3) {
                        getSupplierDropdownList();
                        $('.creditDetails').attr('hidden', false);
                        setTimeout(function () {
                            $('#supplierId').val(res[0].supplierId);
                        }, 100);
                    } else {
                        $('.creditDetails').attr('hidden', true);
                    }

                    let tableGrid = $('#purchaseItemTable');
                    let tableBody = tableGrid.find('tbody');
                    let row = "<tr>" +
                        "<td><input type='text' id='index' readonly class='form-control' value='" + parseInt(i + 1) + "'>" +
                        "<input type='hidden' id='index' name='purchaseDTOS[" + i + "].type'  class='form-control' value='" + res[i].type + "'>" +
                        "<input type='hidden' id='index' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control' value='" + res[i].purchaseId + "'>" +
                        "<input type='hidden' id='index' name='purchaseDTOS[" + i + "].purchaseAuditId'  class='form-control' value='" + res[i].purchaseAuditId + "'>" +
                        "<input type='hidden' id='brandName' name='purchaseDTOS[" + i + "].brandName'  class='form-control' value='" + res[i].brandName + "'>" +
                        "<input type='hidden' id='prefixCode' name='purchaseDTOS[" + i + "].prefixCode'  class='form-control' value='" + res[i].prefixCode + "'>" +
                        "<input type='hidden' id='index' name='purchaseDTOS[" + i + "].locationId'  class='form-control' value='" + res[i].locationId + "'></td>" +
                        "<td><input type='text' id='itemCode' readonly name='purchaseDTOS[" + i + "].itemCode' class='form-control' value='" + res[i].itemCode.toUpperCase() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control' value='" + res[i].itemName + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].partNo' class='form-control partNo' value='" + res[i].partNo + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].sellingPrice' class='form-control sellingPrice right-align' value='" + res[i].sellingPrice + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].costPrice' class='form-control costPrice right-align' value='" + res[i].costPrice + "'></td>" +
                        "<td><input type='hidden' id='initialQty' readonly class='form-control initialQty right-align'><input type='text' id='qty' readonly class='form-control qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + res[i].qty + " ></td>" +
                        "<td><input type='hidden' readonly name='purchaseDTOS[" + i + "].unitId' class='form-control costPrice right-align' value='" + res[i].unitId + "'><input type='text' readonly  class='form-control costPrice right-align' value='" + res[i].unitName + "'></td>" +
                        "<td><input type='text' readonly class='form-control totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + res[i].costPrice + " ></td>" +
                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-danger btn-xs fa fa-trash' value='Delete'></td>" +
                        "</tr>";
                    tableGrid.find('tbody').append(row);
                    calculateTotal();
                    $('#btnSave').attr('disabled', false)
                }
            }
        }

        function onQtyChange() {
            fixedAssetBuyingGridTBody.on('keyup', '.qty', function () {
                calculateTotal()
            });
            fixedAssetBuyingGridTBody.on('keyup', '.purchaseValue', function () {
                calculateTotal()
            });

        }

        function deleteLedgerDetails() {
            $('#btnDelete').on('click', function () {
                let purchaseVoucherNo = $('#purchaseVoucherNo').val();
                let purchaseInvoiceNo = $('#purchaseInvoiceNo').val();
                if (purchaseVoucherNo !== '') {
                    confirmMessage("Please Confirm", function (e) {
                        if (e) {
                            $.ajax({
                                url: 'receivedItem/deleteAllThePurchaseEntry',
                                type: 'get',
                                data: {purchaseVoucherNo: purchaseVoucherNo, purchaseInvoiceNo: purchaseInvoiceNo},
                                success: function (res) {
                                    if (res.status === 0) {
                                        swal({
                                            title: res.text,
                                            text: "Click OK to exit",
                                            type: "warning"
                                        });
                                    } else {
                                        swal({
                                            title: res.text,
                                            text: "Click OK to exit",
                                            type: "success"
                                        });
                                        window.location.reload();
                                    }
                                }
                            });
                        }
                    })
                } else {
                    errorMsg("Delete is not possible.");
                }
            })
        }


        function itemOnChange() {
            fixedAssetBuyingGrid.on('change', 'tr .particular', function () {
                let selectedRow = $(this).closest('tr');
                // $(this).devbridgeAutocomplete({
                //     lookup: valueList,
                //     onSelect: function (suggestion) {
                //         selectedRow.find('#assetDetailId').val(suggestion.id)
                //     },
                //     noSuggestionNotice: 'Sorry, no matching results'
                //     // , onInvalidateSelection: function () {
                //     //     $(this).val("");
                //     // }
                // });
            });
        }


        return {
            getItemSuggestionList: getItemSuggestionList,
            addMoreRow: addMoreRow,
            saveItem: saveItem,
            deleteItem: deleteItem,
            calculateReturnAmt: calculateReturnAmt,
            onCreditPurchaseSelect: onCreditPurchaseSelect,
            onBtnClickDeleteItem: onBtnClickDeleteItem,
            onBtnClickEditItem: onBtnClickEditItem,
            getPurchaseInvoiceItemList: getPurchaseInvoiceItemList,
            onQtyChange: onQtyChange,
            deleteLedgerDetails: deleteLedgerDetails,
            itemOnChange: itemOnChange

        }
    }

)();

$(document).ready(function () {

    assetBuying.getItemSuggestionList();
    assetBuying.addMoreRow();
    assetBuying.saveItem();
    assetBuying.deleteItem();
    assetBuying.onQtyChange();
    assetBuying.calculateReturnAmt();
    assetBuying.onCreditPurchaseSelect();
    assetBuying.itemOnChange();
    // receivedItem.onBtnClickDeleteItem();
    // receivedItem.onBtnClickEditItem();
    // receivedItem.getPurchaseInvoiceItemList();
    // receivedItem.deleteLedgerDetails();
});
