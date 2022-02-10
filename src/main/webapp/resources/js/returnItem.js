/**
 * Created by jigmePc on 8/31/2019.
 */
returnItem = (function () {
    function onReceiptNoChange() {
        $('#receiptNo').on('change', function () {
            var a = 0, b = 0, c = 0;
            if ($(this).val() != '') {
                $.ajax({
                    url: 'returnItem/getReceiptItemDetails',
                    type: 'GET',
                    data: {receiptNo: $(this).val()},
                    success: function (res) {
                        if (res != '') {
                            var columnDef = [
                                {
                                    data: 'itemCode',
                                    render: function (data) {
                                        return '<input type="text" readonly="true" class="form-control" id="itemCode"  name="saleItemListDTO[' + (a++) + '].itemCode" value="' + data + '">'
                                    }
                                },
                                {data: 'itemName'},
                                {data: 'sellingPrice'},
                                {data: 'qty'},
                                {data: 'totalAmount'},
                                {
                                    data: 'returnQty',
                                    render: function (data) {
                                        return '<input type="text" class="form-control returnQty" id="returnQty"  name="saleItemListDTO[' + (b++) + '].returnQty">'
                                    }
                                },
                                {
                                    data: 'action',
                                    render: function (data) {
                                        return '<input type="checkbox" id="isReturn" name="saleItemListDTO[' + (c++) + '].isReturn">'
                                    }
                                }
                            ];
                            $('#returnItemGrid').DataTable({
                                data: res,
                                columns: columnDef
                            });
                        } else {
                            $('#itemName').val('');
                            $('#sellingPrice').val('');
                        }
                    }
                })
            }
        });
    }

    function saveReturnQty() {
        $('#updateBtn').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'returnItem/saveReturnQty',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    timer: 800,
                                    type: "success",
                                    title: res.text,
                                    showConfirmButton: false
                                });
                                $('#tBodyId').empty();
                                $('#totalAmount').val('');
                                $('#replaceItemCode').attr('readonly', true);

                            } else {
                                errorMsg(res.text)
                            }
                        }
                    });
                }
            });
            //$('#updateBtn').attr('disabled',true);
        })
    }

    function validateReturnQty() {
        $('#returnItemGrid').find('tbody').on('keyup', 'tr #returnQty', function () {
            var selectedRow = $(this).closest('tr').addClass('selected');
            var returnQty = parseInt(selectedRow.find('.returnQty').val());
            var qty = parseInt(selectedRow.find("td:eq( 3 )").text());
            if (returnQty > qty) {
                $(this).val('');
                errorMsg("Return qty exceed then actual sale qty.");
            }
        })
    }

    function addItemReplaceDetailsToGrid() {
        var i = 0;
        $('#returnItemCode').on('focusout', function () {
            var itemCode = $('#returnItemCode');
            var itemName = $("#returnItemName");
            var qty = $('#qty');
            var rate = $('#sellingPrice');
            var amount = $('#amount');
            if (!fieldCheck(itemName, itemCode)) {
                itemName.removeClass('error');
                var row = "<tr>" +
                    "<td><input type='hidden'  id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control itemCode' value='" + itemCode.val() + "'><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control itemName' style='text-align:center' value='" + itemName.val() + "'></td>" +
                    "<td><input type='hidden' name='saleItemListDTO[" + i + "].returnItem' class='form-control return' value='" + 0 + "'><input type='text' readonly name='saleItemListDTO[" + i + "].qty' class='form-control' style='text-align:center;' value='" + 1 + "'></td>" +
                    "<td><input type='text' id='rate' readonly name='saleItemListDTO[" + i + "].rate' class='form-control' style='text-align:center;' value='" + rate.val() + "'></td>" +
                    "<td><input type='text' id='amount' class='form-control amount' style='text-align:center;' readonly='true' value='" + rate.val() + "'>" +
                    "</tr>";
                i++;
                var tableGrid = $('#itemReplacementGrid');
                var tableBody = tableGrid.find('tbody');
                //if (!isValid(tableBody, itemCode)) {
                var noMatch = $(tableBody).find('td').first().html();
                if (noMatch == 'No data available in table') {
                    $(tableBody).find('tr').remove();
                }
                tableGrid.find('tbody').append(row);
                var allRow = tableBody.find('tr');
                spms._formIndexing(tableBody, allRow);
                $('.resetField').val('');
                $('#replaceItemCode').attr('readonly', false);
                $('#replaceItemCode').focus();
                calculateTotal();
            }
        });
    }

    function addItemRReturnDetailsToGrid() {
        var i = 0;
        $('#replaceItemCode').on('focusout', function () {
            var itemCode = $('#replaceItemCode');
            var itemName = $("#replaceItemName");
            var qty = $('#qty');
            var rate = $('#sellingPrice');
            var amount = $('#amount');
            if (!fieldCheck(itemName, itemCode)) {
                itemName.removeClass('error');
                var row = "<tr>" +
                    "<td><input type='hidden'  id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control itemCode' value='" + itemCode.val() + "'><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control' style='text-align:center;' value='" + itemName.val() + "'></td>" +
                    "<td><input type='hidden' name='saleItemListDTO[" + i + "].replaceItem' class='form-control replace'  value='" + 1 + "'><input type='text' readonly name='saleItemListDTO[" + i + "].qty' class='form-control' style='text-align:center;' value='" + 1 + "'></td>" +
                    "<td><input type='text' id='rate' readonly name='saleItemListDTO[" + i + "].rate' class='form-control' style='text-align:center;' value='" + rate.val() + "'></td>" +
                    "<td><input type='text' id='amount' class='form-control amount' style='text-align:center;' readonly='true' value='" + rate.val() + "'>" +
                    "</tr>";
                i++;
                var tableGrid = $('#itemReplacementGrid');
                var tableBody = tableGrid.find('tbody');
                //if (!isValid(tableBody, itemCode)) {
                var noMatch = $(tableBody).find('td').first().html();
                if (noMatch == 'No data available in table') {
                    $(tableBody).find('tr').remove();
                }
                tableGrid.find('tbody').append(row);
                var allRow = tableBody.find('tr');
                spms._formIndexing(tableBody, allRow);
                $('.resetField').val('');
                //}
                calculateTotal();
            }
        });
    }


    function fieldCheck(itemName, returnItemCode) {
        var errorExists = false;
        if (returnItemCode.val() === '') {
            errorExists = true;
        }
        if (itemName.val() === '') {
            errorExists = true;
        }
        return errorExists;
    }


    /*function isValid(tableData, itemCode) {
     var isItemCodeExist = false;
     tableData.find('tr').each(function () {
     if ($(this).find('.itemCode').val() == itemCode.val()) {
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
     }*/

    function onItemCodeChange() {
        $('#returnItemCode').on('keyup', function () {
            if ($(this).val() != '') {
                getItemDetails($(this).val(), 1);
            }
        });
        $('#replaceItemCode').on('keyup', function () {
            if ($(this).val() != '') {
                getItemDetails($(this).val(), 2);
            }
        })
    }

    function getItemDetails(itemCode, status) {
        $.ajax({
            url: 'saleItem/getItemDetails',
            type: 'GET',
            data: {itemCode: itemCode},
            success: function (res) {
                if (res != '') {
                    if (status == 1) {
                        $('#returnItemName').val(res[0].itemName);
                    } else {
                        $('#replaceItemName').val(res[0].itemName);
                    }
                    $('#sellingPrice').val(res[0].sellingPrice);
                } else {
                    $('#itemName').val('');
                    $('#sellingPrice').val('');
                }
            }
        })
    }

    function calculateTotal() {
        var totalReturnAmt = 0;
        var totalReplaceAmt = 0;
        $('#itemReplacementGrid').find('tr').each(function () {
            var selectedRow = $(this).closest('tr');
            if (selectedRow.find('.return').val() == 0) {
                totalReturnAmt = totalReturnAmt + parseInt(selectedRow.find('.amount').val());
            } else if (selectedRow.find('.replace').val() == 1) {
                totalReplaceAmt = totalReplaceAmt + parseInt(selectedRow.find('.amount').val());
            }
        });
        $('#totalAmount').val(totalReturnAmt - totalReplaceAmt);
    }


    return {
        onReceiptNoChange: onReceiptNoChange,
        saveReturnQty: saveReturnQty,
        validateReturnQty: validateReturnQty,
        addItemReplaceDetailsToGrid: addItemReplaceDetailsToGrid,
        onItemCodeChange: onItemCodeChange,
        addItemRReturnDetailsToGrid: addItemRReturnDetailsToGrid
    }
})
();
$(document).ready(function () {
    returnItem.onReceiptNoChange();
    returnItem.saveReturnQty();
    returnItem.validateReturnQty();
    returnItem.addItemReplaceDetailsToGrid();
    returnItem.onItemCodeChange();
    returnItem.addItemRReturnDetailsToGrid();

});
