/**
 * Created by SonamPC on 12-Dec-16.
 */

itemSale = (function () {
    function init() {
        addGridToDetails();
        removeRowFromGrid();
        deleteItem();
        on_Change_ItemCode();
    }

    var addDetailsGrid = $('#addDetailsGrid').dataTable({
        "paging": false,
        "searching": false

    });



    function addGridToDetails() {
        $('#addToGrid').on('click', function () {
            var itemCode = $('#itemCode');
            var itemName = $('#itemName');
            var pricePerQty = $('#pricePerQty');

            if (!fieldValidation()) {
                itemCode.removeClass('error');
                itemName.removeClass('error');
                pricePerQty.removeClass('error');
                $('#grandTotalArea').attr('hidden',false);
                /*$('#grandTotal').val(pricePerQty.val());*/
                var tableData = $('#addDetailsGrid tbody');
                var prependRow = '';
                prependRow += '\
                    <tr><td><input type="text" name="saleItemDTOs[' + i + '].itemCode"  id="itemCode" class="form-control" value="' + itemCode.val() + '"></td>\
                    <td><input type="text" name="saleItemDTOs[' + i + '].itemName"  id="txtLocation" class="form-control" value="' + itemName.val() + '"></td>\
                    <td> <input type="text" name="saleItemDTOs[' + i + '].pricePerQty"  id="expectedDateOfOpening" class="form-control" value="' + pricePerQty.val() + '"></td>\
                    <td> <a class="btnRemoveRow" href="javascript:void(0);"><span class="glyphicon glyphicon-minus-sign"/></a> </td>\
                  </tr>';

                i = i + 1;


                if (!isValid(tableData)) {
                    var noMatch = $(tableData).find('td').first().html();

                    if (noMatch == 'No data available in table') {
                        $(tableData).find('tr').remove();
                    }
                    tableData.prepend(prependRow);

                    $('.resetField').val('');
                    formIndexing(tableData, tableData.find('tr'));


                }
            }
        });


    }

    function isValid(tableData) {
        var itemCode = $('#itemCode').val();
        var isItemCodeExist = false;
        tableData.find('tr').each(function () {
            if ($(this).find('input[id=itemCode]').val() == itemCode) {
                swal("Error", "This item already added in sale list.")
                isItemCodeExist = true;
                return false;
            }
        });
        return isItemCodeExist;
    }

    function removeRowFromGrid() {
        $('#addDetailsGrid tbody').on('click', '.btnRemoveRow', function () {
            var tableBody = $('#addDetailsGrid tbody');
            var row = $('#addDetailsGrid tbody tr');
            _removeRow($(this), tableBody, row);

        });
    }

    function _removeRow(clickedRemoveBtn, tableBody, row) {
        var removableRow = clickedRemoveBtn.parent().parent();
        if (row.length === 0) {
            removableRow.find('input[type="text"]').val('');
        } else {
            removableRow.remove();
            if ($(tableBody).children().length == 0) {
                $('#btnSave').attr('disabled', true);
            }
            _formIndexing(tableBody, row);
        }
    }

    //for placing serial no and then incrementing by one
    function _formIndexing(tableBody, row) {
        for (var i = 0; i < row.length; i++) {
            tableBody.children().eq(i).children().children().each(function () {
                if (this.name) {
                    this.name = this.name.replace(/\[(\d+)\]/, function (str, p) {
                        return '[' + i + ']';
                    })
                }
                if ($(this).hasClass('serialNo')) {
                    $(this).val(i + 1);
                }
            });
        }
    }

    //To save the data
    function deleteItem() {
        $('#btnDeleteConfirm').on('click', function () {
            var tableData = $('#addDetailsGrid tbody');
            var noMatch = $(tableData).find('td').first().html();
            if (noMatch == 'No data available in table') {
                errorMsg("No item added to be delete.");
            }
            $('.globalForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'saleItem/deleteItem',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status == 1) {
                                successMsg(res.text);
                                addDetailsGrid.fnClearTable();
                                $('#resetField').val('');
                            }
                        }
                    });
                }
            });
        })

    }

    function on_Change_ItemCode(){
        $('#itemCode').on('change', function(){
            var itemCode = $('#itemCode').val();
            $.ajax({
                url: 'saleItem/getItemDetail',
                type: 'POST',
                data: {itemCode:itemCode},
                success: function (res) {
                    if(res.itemName==null){
                        $('#itemCode').val('');
                        $('#itemCode').focus();
                        errorMsg('Invalid item code')

                    }else{
                        populate(res);
                    }

                }
            });
        })
    }


    return {
        init: init,
        addGridToDetails: addGridToDetails,
        removeRowFromGrid: removeRowFromGrid,
        deleteItem: deleteItem,
        on_Change_ItemCode:on_Change_ItemCode
    }
})();
$(document).ready(function () {
    itemSale.init();
});
