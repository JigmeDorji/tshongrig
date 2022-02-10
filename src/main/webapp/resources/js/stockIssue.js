/**
 * Created by Bikash Rai on 9/16/2021.
 */
stockIssue = (function () {

        let issueItemGrid = $('#issueItemGrid').dataTable({
            info: false,
            paging: false,
            sorting: false,
            searching: false
        });
        let saleItemGridTBody = $('#issueItemGrid tbody');

        function getItemDetails() {
            $('#itemCode').on('keyup', function () {
                if ($(this).val() !== '') {
                    fetchDetail($(this).val());
                }
            });
        }

        function fetchDetail(itemCode) {
            $.ajax({
                url: 'saleItem/getIssueItemDetails',
                type: 'GET',
                data: {itemCode: itemCode},
                success: function (res) {
                    if (res.length > 0) {
                        $('#itemName').val(res[0].itemName);
                        $('#costPrice').val(res[0].costPrice);
                        $('#unitName').val(res[0].unitName);
                    } else {
                        $('#itemName').val('');
                        $('#costPrice').val('');
                    }
                }
            })
        }

        function addGridToDetails() {
            let i = 1;
            let qty = $('#qty');
            let unitName = $('#unitName');
            let saleDate = $('#saleDate');
            let itemCode = $('#itemCode');
            let itemName = $("#itemName");
            let costPrice = $('#costPrice');

            if (!fieldValidation(qty, itemCode, itemName)) {
                qty.removeClass('error');
                unitName.removeClass('error');
                itemCode.removeClass('error');
                itemName.removeClass('error');
                costPrice.removeClass('error');
                saleDate.removeClass('error');

                let row = "<tr>" +
                    "<td><input type='text' id='index' readonly class='form-control' value='" + i + "'></td>" +
                    "<td><input type='hidden' id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control' value='" + itemCode.val().toUpperCase() + "'><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control' value='" + itemName.val() + "'></td>" +
                    "<td><input type='text' id='unitName' readonly class='form-control right-align'  value=" + unitName.val() + " ></td>" +
                    "<td><input type='hidden' id='initialQty' readonly class='form-control initialQty right-align'><input type='text' id='qty' readonly class='form-control qty right-align amount'   name='saleItemListDTO[" + i + "].qty' value=" + qty.val() + " ></td>" +
                    "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-danger btn-xs fa fa-trash' value='Delete'></td>" +
                    "</tr>";
                i++;
                let tableGrid = $('#issueItemGrid');
                let tableBody = tableGrid.find('tbody');
                if (!isValid(tableBody, itemCode)) {
                    let noMatch = $(tableBody).find('td').first().html();
                    if (noMatch === 'No data available in table') {
                        $(tableBody).find('tr').remove();
                    }
                    tableGrid.find('tbody').append(row);
                    let allRow = tableBody.find('tr');
                    spms._formIndexing(tableBody, allRow);
                    $('.resetField').val('');
                    $('#btnSave').attr('disabled', false)
                }

                $('#itemCode').val('');
                $('#itemName').val('');
                $('#costPrice').val('');
                $('#qty').val('');
            }
        }

        function isValid(tableData, itemCode) {
            let isItemCodeExist = false;
            tableData.find('tr').each(function () {
                if ($(this).find('.itemCode').val() === itemCode.val().toUpperCase()) {
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

        function fieldValidation(qty, itemCode, itemName, saleDate) {
            let errorExists = false;

            if (qty.val() === '') {
                qty.addClass('error');
                errorExists = true;
            }

            if (itemCode.val() === '') {
                itemCode.addClass('error');
                errorExists = true;
            }
            if (itemName.val() === '') {
                itemName.addClass('error');
                errorExists = true;
            }

            // if (saleDate.val() === '') {
            //     saleDate.addClass('error');
            //     errorExists = true;
            // }

            return errorExists;
        }

        function checkItemCodeAlreadyExists(itemCode) {
            let exists = false;
            $('#issueItemGrid').find('tr').each(function () {
                if ($(this).closest('tr').find('#itemCode').val() === itemCode.val().toUpperCase()) {
                    exists = true;
                }
            });
            return exists;
        }

        function saveIssueItemDetails() {
            $('#btnSave').on('click', function () {
                spms.isFormValid($('#stockIssueForm'));
                if ($('#stockIssueForm').valid()) {
                    $.ajax({
                        url: 'saleItem/saveItemDetails',
                        type: 'POST',
                        data: $('#stockIssueForm').serializeArray(),
                        success: function () {
                            successMsg("Successfully saved");
                            window.location.reload();
                        }
                    });
                }
            })
        }

        function onBtnClickDeleteItem() {
            $('#issueItemGrid').find('tbody').on('click', 'tr #btnDeleteItem', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.remove();
                let allRow = saleItemGridTBody.find('tr');
                spms._formIndexing(saleItemGridTBody, allRow);
            })
        }

        function onBtnClickEditItem() {
            $('#issueItemGrid').find('tbody').on('click', 'tr #itemEditBtn', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.find('.qty').focus();
                selectedRow.find('.qty').attr('readonly', false);
                selectedRow.find('.discount').attr('readonly', false);
            })
        }

        function validateQty() {
            $('#issueItemGrid').find('tbody').on('keyup', 'tr #qty', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                checkAvailabilityOfItem(selectedRow.find('#itemCode').val(),
                    selectedRow.find('.qty').val(), selectedRow);
            })
        }

        saleItemGridTBody.on('keyup', '.qty', function () {
            if ($(this).val() < 0) {
                let selectedRow = $(this).closest('tr');
                selectedRow.find('.qty').val(selectedRow.find('.initialQty').val());
                errorMsg("Invalid quantity");
            }
        });

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

                            issueItemGrid.dataTable().fnClearTable();
                            for (let i in res.saleItemListDTO) {
                                issueItemGrid.fnAddData(
                                    [
                                        "<td><input type='text' id='index' readonly class='form-control' value='" + iterator + "'></td>",
                                        "<td><input type='hidden' id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control' value='" + res.saleItemListDTO[i].itemCode + "'><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control' value='" + res.saleItemListDTO[i].itemName + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].unitName' class='form-control' value='" + res.saleItemListDTO[i].unitName + "'></td>",
                                        "<td><input type='hidden' id='initialQty' readonly class='form-control initialQty right-align' value=" + res.saleItemListDTO[i].qty + " >" +
                                        "<input type='text' id='qty' readonly class='form-control qty right-align amount'   name='saleItemListDTO[" + i + "].qty' value=" + res.saleItemListDTO[i].qty + " ></td>",
                                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-xs fa fa-trash' value='Edit'></td>"
                                    ]
                                );
                                iterator = iterator + 1;
                            }
                            $('#btnSave').attr('disabled', false)
                        }
                    }
                });
            }
        }

        function onFocusOut() {
            $('#qty').on('focusout', function () {
                let itemCode = $('#itemCode').val();
                if (itemCode !== '' && typeof itemCode !== 'undefined') {
                    checkAvailabilityOfItem(itemCode, $('#qty').val(), '');
                }
            })
        }

        function checkAvailabilityOfItem(itemCode, qty, selectedRow) {
            if (itemCode !== '' && qty !== '') {
                $.ajax({
                    url: 'saleItem/getAvailableQty',
                    type: 'GET',
                    data: {itemCode: itemCode},
                    success: function (res) {

                        let qtyId = $('#qty');
                        let storeQty = parseFloat(res);

                        if (selectedRow !== '') {

                            let initialQty = selectedRow.find('.initialQty').val() === '' ? 0 : parseFloat(selectedRow.find('.initialQty').val());

                            if (initialQty !== 0) {
                                if (initialQty <= qty && storeQty === 0) {
                                    selectedRow.find('.qty').val(initialQty);
                                    errorMsg('Qty exceeded than available qty ' + (initialQty - qty).toFixed(1));
                                    return false;
                                }
                                if (initialQty <= qty && storeQty === 0 || (initialQty + storeQty) < qty) {
                                    errorMsg('Qty exceeded than available qty ' + ((initialQty + storeQty) - qty).toFixed(1));
                                    selectedRow.find('.qty').val(initialQty);
                                    return false;
                                }
                            } else {
                                if (storeQty < parseFloat(qty)) {
                                    selectedRow.find('.qty').val(initialQty);
                                    errorMsg('Qty exceeded than available qty ' + (parseFloat(qty) - storeQty).toFixed(1));
                                }
                            }
                        } else {
                            if (storeQty <= 0) {
                                qtyId.val('');
                                $('#itemCode').focus();
                                errorMsg('Item out of stock.');
                            }
                            if (qtyId.val() > storeQty) {
                                qtyId.focus();
                                qtyId.val('');
                                errorMsg('Qty exceeded than available qty ' + (parseFloat(qty) - storeQty).toFixed(1));
                            } else {
                                addGridToDetails();
                            }
                        }
                    }
                })
            }
        }

        function returnItemOnClick() {
            $('#returnItem').on('click', function () {
                $('#replacementItemModal').modal('show');
            });
        }

        function getItemCodeList() {
            $.ajax({
                url: 'saleItem/getItemCodeList',
                type: 'GET',
                success: function (res) {
                    $('#itemCode').devbridgeAutocomplete({
                        lookup: $.map(res, function (value) {
                            return {data: value.id, value: value.text}
                        }), onSelect: function (suggestion) {
                            fetchDetail(suggestion.data);
                            $('#itemCode').val(suggestion.data);
                        }
                    })
                }
            })
        }

        return {
            // generateReceipt: generateReceipt,
            getItemDetails: getItemDetails,
            onBtnClickDeleteItem: onBtnClickDeleteItem,
            onBtnClickEditItem: onBtnClickEditItem,
            getSaleDetail: getSaleDetail,
            onFocusOut: onFocusOut,
            saveIssueItemDetails: saveIssueItemDetails,
            // returnItemOnClick: returnItemOnClick,
            getItemCodeList: getItemCodeList,
            validateQty: validateQty,
        }
    }

)();
$(document).ready(function () {
    // stockIssue.generateReceipt();
    stockIssue.getItemDetails();
    stockIssue.onBtnClickDeleteItem();
    stockIssue.onBtnClickEditItem();
    stockIssue.getSaleDetail();
    stockIssue.onFocusOut();
    stockIssue.saveIssueItemDetails();
    // stockIssue.returnItemOnClick();
    stockIssue.getItemCodeList();
    stockIssue.validateQty();
});
