rawMaterialPurchase = (function () {

    function init() {
        save();
        createSupplier();
        saveSupplierDetail();

        getSupplierDropdownList();
        getSupplierList();
        onClickBtnEdit();
    }

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'purchasesForRawMaterial/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    // success: function (res) {
                    //     if (res.status == 1) {
                    //         swal({
                    //                 title: res.text,
                    //                 text: "Click OK to exit",
                    //                 type: "success"
                    //             }, function () {
                    //                 window.location.reload();
                    //             }
                    //         )
                    //     }
                    // }

                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                }
                            );
                        } else {
                            errorMsg(res.text)
                        }

                    },complete:function (){
                        $('#btnSave').attr('disabled', false);
                    }
                });
            }
        });
    }



    //----------------------------------------- Credit Supplier  ----------------------------------------------------

    function createSupplier() {
        $('#btnAddNewSupplier').on('click', function () {
            $('#supplierModal').modal('show');
        })
    }

    function saveSupplierDetail() {
        $('.supplierForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'supplierSetup/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            $(form)[0].reset();
                            successMsg(res.text);
                            $('#stationId').attr('readonly', false);
                            getSupplierList();
                            getSupplierDropdownList();
                            $('#supplierModal').modal('hide');
                        }
                    }
                });
            }
        });
    };


    function getSupplierDropdownList() {
        $.ajax({
            url: 'receivedItem/getSupplierDropdownList',
            type: 'GET',
            success: function (res) {
                $('#supplierId').empty();
                spms.loadGridDropDown($('#supplierId'), res)
            }
        });
    }


    function getSupplierList() {
        $('#supplierListTable').dataTable().fnDestroy();
        $.ajax({
            url: 'supplierSetup/getSupplierList',
            type: 'GET',
            success: function (res) {
                var columnDef = [
                    {data: 'id', class: 'hidden'},
                    {data: 'supplierName'},
                    {data: 'address'},
                    {data: 'email'},
                    {data: 'contactNo'},
                    {
                        data: 'action',
                        render: function (data) {
                            return '<input type="button" class="btn btn-primary btn-sm btn-xs" id="editBtn" value="Edit">'
                        }
                    },
                ];
                $('#supplierListTable').DataTable({
                    data: res,
                    columns: columnDef
                });

            }

        });
    }


    function onClickBtnEdit() {
        $('#supplierListTable').find('tbody').on('click', 'tr #editBtn', function () {
            $('#supplierListTable tbody tr').removeClass('selected');
            var selectedRow = $(this).closest('tr').addClass('selected');
            var id = selectedRow.find("td:eq(0)").text();
            $.ajax({
                url: 'supplierSetup/getSupplierDetails',
                type: 'GET',
                data: {id: id},
                success: function (res) {
                    populate(res);
                }
            });
        });
    }
    //----------------------------------------- !! Credit Supplier  ----------------------------------------------------

    return {
        init: init,
        save: save,
        createSupplier: createSupplier,
        saveSupplierDetail: saveSupplierDetail,
        getSupplierDropdownList: getSupplierDropdownList,
        getSupplierList: getSupplierList,
        onClickBtnEdit: onClickBtnEdit


    }
})();

$(document).ready(function () {
    rawMaterialPurchase.init();



});


$("#isCash").change(function () {
    let isCashValue = $(this).children("option:selected").val();


    if (isCashValue == 2) {
        $('.bankDetails').attr('hidden', false);
        $('.creditDetails').attr('hidden', true);


    } else if (isCashValue == 3) {
        $('.creditDetails').attr('hidden', false);
        $('.bankDetails').attr('hidden', true);

    } else {
        $('.bankDetails').attr('hidden', true);
        $('.creditDetails').attr('hidden', true);
    }

});


//----------------------------------------- Purchases Item Entry   ------------------------------------------------------

/*
 receivedItem.addGridToDetails();
    receivedItem.onBtnClickDeleteItem();
    receivedItem.onBtnClickEditItem();
    receivedItem.getPurchaseInvoiceItemList();
    receivedItem.onQtyChange();
    receivedItem.deleteLedgerDetails();
    receivedItem.onCreditPurchaseSelect();
    receivedItem.validateQty();

 */




let purchaseItemTableBody = $('#purchaseItemTable tbody');

function addGridToDetails() {

    $('#btnAdd').on('click', function () {
        if ($('#purchaseInvoiceNo').val() === '') {
            $('#purchaseInvoiceNo').focus();
            errorMsg("Please enter purchase details first.");
            return true;
        }
        let qty = $('#qty');
        let unitId = $('#unitId');
        let unitText = $("#unitId option:selected").text();
        // let saleDate = $('#saleDate');
        let brandId = $('#brandId');


        let itemName = $("#itemName");

        let price = $('#price');

        let locationId = $('#locationId');

        let purchaseId = $('#purchaseId');
        let totalAmount = parseFloat(price.val()) * parseFloat(qty.val());
        if (!fieldValidation(qty, unitId, itemName, price, locationId)) {
            qty.removeClass('error');
            unitId.removeClass('error');
            itemName.removeClass('error');
            price.removeClass('error');

            locationId.removeClass('error');

            let i = 0;

            let row = "<tr>" +
                "<td><input type='text' id='index' readonly class='form-control form-control-sm input-group-sm' value='" + i + "'>" +
                "<input type='hidden' id='purchaseId' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control form-control-sm input-group-sm' value='" + purchaseId.val() + "'>" +
                "<input type='hidden' id='locationId' name='purchaseDTOS[" + i + "].locationId'  class='form-control form-control-sm input-group-sm' value='" + locationId.val() + "'></td>" +
                "<td><input type='text' id='itemName' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control form-control-sm input-group-sm itemName' value='" + itemName.val().toUpperCase() + "'></td>" +
                "<td><input type='hidden' readonly class='form-control form-control-sm input-group-sm right-align' id='unitId'  name='purchaseDTOS[" + i + "].unitId' value=" + unitId.val() + " ><input type='text' readonly class='form-control form-control-sm input-group-sm right-align' value=" + unitText + " ></td>" +
                "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm input-group-sm initialQty right-align'><input type='text' id='qty' readonly class='form-control form-control-sm input-group-sm qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + qty.val() + " ></td>" +

                "<td><input type='text' readonly name='purchaseDTOS[" + i + "].price' class='form-control form-control-sm input-group-sm price right-align' value='" + price.val() + "'></td>" +

                "<td><input type='text' readonly class='form-control form-control-sm input-group-sm totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + totalAmount.toFixed(2) + " ></td>" +
                "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-sm btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-sm btn-danger btn-xs fa fa-trash' value='Delete'></td>" +
                "</tr>";
            i = i + 1;
            let tableGrid = $('#purchaseItemTable');
            let tableBody = tableGrid.find('tbody');
            if (!isValid(tableBody, itemName)) {
                let noMatch = $(tableBody).find('td').first().html();
                if (noMatch === 'No data available in table') {
                    $(tableBody).find('tr').remove();
                }
                tableGrid.find('tbody').append(row);

                let allRow = tableBody.find('tr');
                spms._formIndexing(tableBody, allRow);
                $('.common').val('');
                calculateTotal();
                $('#btnSave').attr('disabled', false)
                // $('#printBtn').attr('disabled', false)
            }
            $('#itemName').focus();
            spms.addRowNumber($('#purchaseItemTable tbody tr'));
        }

    })

}







function fieldValidation(qty, unitId, itemName, price,  locationId) {
    let errorExists = false;

    if (checkItemCodeAlreadyExists(itemName)) {
        $('#itemCode').focus();
        errorMsg("This item already exists in the grid.");

        errorExists = true;
    }

    if (qty.val() === '') {
        qty.addClass('error');
        errorExists = true;
    }


    if (itemName.val() === '') {
        itemName.addClass('error');
        errorExists = true;
    }


    if (price.val() === '') {
        price.addClass('error');
        errorExists = true;
    }
    if (locationId.val() === '') {
        locationId.addClass('error');
        errorExists = true;
    }
    return errorExists;
}

function checkItemCodeAlreadyExists(itemName) {
    let exists = false;
    $('#purchaseItemTable').find('tr').each(function () {
        if ($(this).closest('tr').find('#itemCode').val() === itemName.val().toUpperCase()) {
            exists = true;
        }
    });
    return exists;
}

function isValid(tableData, itemName) {
    var isItemCodeExist = false;
    tableData.find('tr').each(function () {
        if ($(this).find('.itemName').val() === itemName.val().toUpperCase()) {
            errorMsg("This item is already added in the list. Please check.")
            $('.resetField').val('');
            isItemCodeExist = true;
            return false;
        }
    });
    return isItemCodeExist;
}

function calculateTotal() {
    let grandTotalAmount = 0;
    purchaseItemTableBody.find('tr').each(function () {
        let row = $(this).closest('tr');
        if (typeof row.find('.price').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined') {
            row.find('.totalAmount').val((row.find('.price').val() === '' ? 1 : parseFloat(row.find('.price').val())) *
                (row.find('.qty').val() === '' ? 1 : parseFloat(row.find('.qty').val())));
            grandTotalAmount = grandTotalAmount + (row.find('.price').val() === '' ? 1 : parseFloat(row.find('.price').val())) *
                (row.find('.qty').val() === '' ? 1 : parseFloat(row.find('.qty').val()));
        }
    });
    $('#grandTotalAmount').val(grandTotalAmount.toFixed(2));
}

function onBtnClickDeleteItem() {
    purchaseItemTableBody.on('click', 'tr #btnDeleteItem', function () {
        let selectedRow = $(this).closest('tr').addClass('selected');
        selectedRow.remove();
        let allRow = purchaseItemTableBody.find('tr');
        spms._formIndexing(purchaseItemTableBody, allRow);
        if(parseInt(purchaseItemTableBody.find('tr').length)===0){
            $('#btnSave').attr('disabled',true);
        }else {
            $('#btnSave').attr('disabled',false);
        }
        calculateTotal();
    })
}

function onBtnClickEditItem() {
    purchaseItemTableBody.on('click', 'tr #itemEditBtn', function () {
        let selectedRow = $(this).closest('tr').addClass('selected');
        selectedRow.find('.qty').focus();
        selectedRow.find('.qty').attr('readonly', false);
        selectedRow.find('.price').attr('readonly', false);

    })
}

purchaseItemTableBody.on('keyup', '.qty', function () {
    if ($(this).val() < 0) {
        let selectedRow = $(this).closest('tr');
        selectedRow.find('.qty').val(selectedRow.find('.initialQty').val());
        errorMsg("Invalid quantity");
    }
    calculateTotal();
});


function onQtyChange() {
    $('#purchaseItemTable tbody').on('keyup', '.qty', function () {
        calculateTotal()
    });
    $('#purchaseItemTable tbody').on('keyup', '.price', function () {
        calculateTotal()
    });

}

addGridToDetails();
onBtnClickDeleteItem();
onBtnClickEditItem();
onQtyChange();


//----------------------------------------- !!Purchases Item Entry   ----------------------------------------------------


//----------------------------------------- !!Purchases Item Entry   ----------------------------------------------------

function checkingTheExistingCashLedger(){
    $('.globalForm').validate({
        submitHandler: function (form) {
            $.ajax({
                url: 'purchasesForRawMaterial/cashLedgerStatus',
                type: 'GET',
                success: function (res) {
                    if (res.status == 2) {
                        swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                window.location.reload();
                            }
                        )
                    }
                }
            });
        }
    });
}

