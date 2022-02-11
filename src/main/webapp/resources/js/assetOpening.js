/**
 * Created by Bikash Rai on 9/16/2021.
 */


assetOpening = (function () {

    let valueList;
    let iterator = 0;
    let fixedAssetOpeningGrid = $('#fixedAssetOpeningGrid');
    let fixedAssetOpeningGridTBody = $('#fixedAssetOpeningGrid tbody');

    function baseURL() {
        return 'assetOpening/';
    }

    function saveItem() {
        $('#btnSave').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'assetOpening/save',
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
                                    window.location = 'assetOpening';
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


    function populateData() {
        $.ajax({
            url: 'receivedItem/getRecentPurchaseData',
            type: 'GET',
            success: function (res) {
                if (res !== '') {
                    $('#purchaseDate').val(formatAsDate(res.purchaseDate));
                    $('#purchaseInvoiceNo').val(res.purchaseInvoiceNo);
                }
            }
        });
    }

    function addMoreRow() {
        fixedAssetOpeningGridTBody.on('click', '#addBtn', function () {
            let selectedRow = $(this).closest('tr');
            fixedAssetOpeningGrid.find('tbody').append(returnRow());
            selectedRow.find('.addBtn').addClass('hidden');
            selectedRow.find('.removeBtn').removeClass('hidden');
            spms._formIndexing(fixedAssetOpeningGrid.find('tbody'), fixedAssetOpeningGrid.find('tbody tr'));
            // $('#autoCompleteId' + iterator).devbridgeAutocomplete({
            //     lookup: valueList
            // });
            let items = new Bloodhound({
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
            indexRowNo(fixedAssetOpeningGrid);
        });
    }

    function returnRow() {
        iterator = iterator + 1;
        return "<tr>" +
            "<td><input type='text' readonly class='form-control rowNumber'>" +
            "<input type='hidden' id='assetDetailId" + iterator + "' name='openingAndBuyingListDTO[0].assetDetailId'  class='form-control'>" +
            "<input type='hidden' id='fixedAssetGroupId' name='openingAndBuyingListDTO[0].fixedAssetGroupId'  class='form-control'>" +
            "<input type='hidden' id='faPurchaseId' name='openingAndBuyingListDTO[0].faPurchaseId'  class='form-control'></td>" +
            "<td><input type='text' id='autoCompleteId" + iterator + "' name='openingAndBuyingListDTO[0].particular' class='form-control particular'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].purchaseDate' class='form-control formatDate'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].openingBalance' class='form-control openingBalance right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].depreciatedValue' class='form-control depreciatedValue right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].rate' class='form-control rate right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].qty' class='form-control qty  right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].total' readonly class='form-control total right-align'></td>" +
            "<td><button class='btn btn-sm btn-danger btn-xs ml-3 d-none d-sm-inline-block removeBtn hidden' type='button' id='removeBtn'><i class='fa fa-trash'></i> Delete</button>" +
            "<button class='btn  btn-sm btn-success d-sm-inline-block addBtn' type='button' id='addBtn'><i class='fa fa-plus'></i> Add More</button></td>" +
            "</tr>";
    }

    function deleteItem() {
        fixedAssetOpeningGridTBody.on('click', 'tr #removeBtn', function () {
                let selectedRow = $(this).closest('tr');
                let faPurchaseId = selectedRow.find('.faPurchaseId').val();

                if (faPurchaseId !== '' && typeof faPurchaseId !== 'undefined') {
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
                indexRowNo(fixedAssetOpeningGrid);
            }
        );
    }

    function calculateTotal() {
        let grandTotalAmount = 0;

        $('#fixedAssetOpeningGrid tbody').find('tr').each(function () {
            let row = $(this).closest('tr');
            if (typeof row.find('.rate').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined') {
                row.find('.total').val((row.find('.rate').val() === '' ? 0 : parseFloat(row.find('.rate').val())) *
                    (row.find('.qty').val() === '' ? 0 : parseFloat(row.find('.qty').val())));
                grandTotalAmount = grandTotalAmount + (row.find('.rate').val() === '' ? 1 : parseFloat(row.find('.rate').val())) *
                    (row.find('.qty').val() === '' ? 1 : parseFloat(row.find('.qty').val()));
            }
        });
        $('#grandTotalAmount').val(grandTotalAmount.toFixed(2));
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
                // if (!isValid(tableBody, itemCode)) {
                //     let noMatch = $(tableBody).find('td').first().html();
                //     if (noMatch == 'No data available in table') {
                //         $(tableBody).find('tr').remove();
                //     }
                //     tableGrid.find('tbody').append(row);
                //
                //     let allRow = tableBody.find('tr');
                //     spms._formIndexing(tableBody, allRow);
                //     $('.common').val('');
                //     calculateTotal();
                //     // $('#printBtn').attr('disabled', false)
                // }
                // $('#itemCode').focus();
            }
        } else {
            // $('#purchaseInvoiceNo').val('')
            // $('#purchaseInvoiceNo').focus()
            // errorMsg(data.text);
        }
    }

    function onQtyChange() {
        fixedAssetOpeningGridTBody.on('keyup', '.qty', function () {
            calculateTotal()
        });
        fixedAssetOpeningGridTBody.on('keyup', '.rate', function () {
            calculateTotal()
        });

    }

    function loadInitialGrid() {
        $.ajax({
            url: baseURL() + 'getItemSuggestionList',
            type: 'GET',
            success: function (res) {

                valueList = $.map(res, function (value) {
                    return {value: value.particular, id: value.assetDetailId};
                });

                fixedAssetOpeningGrid.find('tbody').append(returnRow());
                spms._formIndexing(fixedAssetOpeningGrid.find('tbody'), fixedAssetOpeningGrid.find('tbody tr'));
                // $('#autoCompleteId' + iterator).devbridgeAutocomplete({
                //     lookup: valueList
                // });
                let items = new Bloodhound({
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

                indexRowNo(fixedAssetOpeningGrid);
            }
        });
    }


    function itemOnChange() {
        fixedAssetOpeningGrid.on('change', 'tr .particular', function () {
            let selectedRow = $(this).closest('tr');
            // $(this).autocomplete({
            //     lookup: valueList,
            //     triggerSelectOnValidInput: true,
            //     onSelect: function (suggestion) {
            //         selectedRow.find('#assetDetailId').val(suggestion.id)
            //     }
            // });
        });
    }

    return {
        loadInitialGrid: loadInitialGrid,
        addMoreRow: addMoreRow,
        deleteItem: deleteItem,
        saveItem: saveItem,
        populateData: populateData,
        onQtyChange: onQtyChange,
        itemOnChange: itemOnChange
    }
})();

$(document).ready(function () {
    assetOpening.loadInitialGrid();
    assetOpening.addMoreRow();
    assetOpening.deleteItem();
    assetOpening.saveItem();
    assetOpening.onQtyChange();
    assetOpening.itemOnChange();
});
