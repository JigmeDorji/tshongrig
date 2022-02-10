/**
 * Created by jigme.dorji on 5/23/2021.
 */
openingBalanceInventory = (function () {
    function saveItem() {
        $('#btnSave').on('click', function () {
            var itemCode = $('#itemCode').val();
            var itemName = $('#itemName').val();
            var qty = $('#qty').val();
            var sellingPrice = $('#sellingPrice').val();
            $('.globalForm').validate({
                submitHandler: function (form) {
                    var data = $(form).serializeArray();
                    $.ajax({
                        url: 'receivedItem/save',
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
                                    window.location.reload();
                                    // form.reset();
                                    // window.location = spms.getUrl() + 'barcode/generateBarcode?itemCode=' + encodeURIComponent(itemCode) + "&itemName=" + itemName + "&qty=" + qty;
                                    //window.print();
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

    function selectLedgerName() {
        $('#supplierId').on('change', function () {
            $('#supplierName').val($('#supplierId option:selected').text())
        })
    }

    function checkItemName() {
        $('#itemName').on('change', function () {
            $.ajax({
                url: 'receivedItem/checkItemName',
                type: 'GET',
                data: {itemName: $(this).val()},
                success: function (res) {
                    if (res.status == 0) {
                        $('#itemName').focus();
                        $('#itemName').val('');
                        errorMsg(res.text);
                    }
                }
            });
        })
    }


    function getOpeningPurchaseDetail() {
        var purchaseId = $('#purchaseId').val();
        var voucherNo = $('#purchaseVoucherNo').val();
        if (purchaseId !== '' && purchaseId !== null) {
            $.ajax({
                url: 'receivedItem/getOpeningPurchaseDetail',
                type: 'GET',
                data: {purchaseId: purchaseId, purchaseDate: $('#purchaseDate').val(), voucherNo: voucherNo},
                success: function (res) {
                    if (res !== '') {
                        $('#purchaseId').val(res.purchaseId)
                        $('#brandId').val(res.brandName)
                        $('#brandNameID').val(res.brandId)
                        $('#itemCode').val(res.itemCode)
                        $('#type').val(res.type)
                        $('#partNo').val(res.partNo)
                        $('#costPrice').val(res.costPrice)
                        $('#sellingPrice').val(res.sellingPrice)
                        $('#locationId').val(res.locationId)
                        $('#qty').val(res.sumQty)
                        $('#originalQty').val(res.sumQty)
                        $('#unitId').val(res.unitId)
                        $('#itemName').val(res.itemName)
                        $('#itemNamePrefix').val(res.itemName.split(':')[0]);
                        $('#purchaseDate').val(formatAsDate(res.purchaseDate));
                        $('#purchaseDate').attr('readOnly', true);
                    }
                }
            });
        }
    }

    function onClickBack() {
        $('#backBtn').on('click', function () {
            window.location.href = spms.getUrl() + '/viewItem';
        })
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

    function onCreditPurchaseSelect() {
        $('#isCash').on('change', function () {
            if ($(this).val() == 3) {
                getSupplierDropdownList();
                $('.creditDetails').attr('hidden', false);
            } else {
                $('.creditDetails').attr('hidden', true);
            }
        })
    }

    function getItemDetails() {
        $('#itemCode').on('change', function () {
            $.ajax({
                url: 'receivedItem/getItemDetails',
                type: 'GET',
                data: {itemCode: $('#itemCode').val()},
                success: function (res) {
                    if (res !== '') {
                        $('#itemCode').val('')
                        errorMsg("There is opening balance already against this item.")
                        // $('#purchaseId').val(res.purchaseId)
                        // $('#brandId').val(res.brandName)
                        // $('#brandNameID').val(res.brandId)
                        // $('#itemCode').val(res.itemCode)
                        // $('#type').val(res.type)
                        // $('#partNo').val(res.partNo)
                        // $('#costPrice').val(res.costPrice)
                        // $('#sellingPrice').val(res.sellingPrice)
                        // $('#locationId').val(res.locationId)
                        // $('#qty').val(res.sumQty)
                        // alert(res.sumQty)
                        // $('#itemName').val(res.itemName)
                        // $('#itemNamePrefix').val(res.itemName.split(':')[0]);
                        // $('#purchaseDate').val(formatAsDate(res.purchaseDate));
                        // $('#purchaseDate').attr('readOnly', true);
                    } else {
                        $('#itemCode').val('');
                        errorMsg("No matching item found.");
                    }
                }
            });
        })
    }

    function getItemDetailsByPartNo() {
        $('#partNo').on('change', function () {
            $.ajax({
                url: 'receivedItem/getItemDetailsByPartNo',
                type: 'GET',
                data: {partNo: $('#partNo').val()},
                success: function (res) {
                    if (res !== '') {
                        populate(res);
                        $('#brandId').val(res.brandName)
                        $('#itemName').val(res.itemName.split(':')[1])
                        $('#itemNamePrefix').val(res.itemName.split(':')[0])
                    } else {
                        $('.common').val('');
                        // errorMsg("No matching item found.");
                    }
                }
            });
        })
    }

    $('#isCash').on('change', function () {
        if ($(this).val() == 2) {
            $('#bankDetails').attr('hidden', false);
        } else {
            $('#bankDetails').attr('hidden', true);
        }
    });


    function getBrandList() {
        $.ajax({
            url: 'receivedItem/getBrandList',
            type: 'GET',
            success: function (res) {

                res.unshift({
                    value: "NEW",
                    text: "Add new Brand"
                });

                $('#brandId').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.value, value: value.text}
                    }),
                    onSelect: function (suggestion) {
                        if (suggestion.data === "NEW") {
                            $('#brandId').val('');
                            $('#brandModal').modal('show');
                        } else {
                            $('#brandNameID').val(suggestion.data);
                            $.ajax({
                                url: 'receivedItem/getSlNo',
                                type: 'GET',
                                data: {brandId: suggestion.data},
                                success: function (res) {
                                    $('#currentSerial').val(res.itemCode);
                                    $('#itemCode').val(res.itemCode);
                                    $('#itemNamePrefix').val(res.prefixCode);
                                }
                            });
                        }
                    }
                })
            }
        })
    }

    function addNewBrand() {
        $('#btnAddBrand').on('click', function () {
            $('#brandId').val('');
            $('#brandModal').modal('show');
        })
    }

    function saveBrandDetail() {
        $('#pModalButton').on('click', function () {
            $('.brandForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'receivedItem/saveBrandDetail',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {

                            if (res.status == 1) {
                                successMsg(res.text);
                                getBrandList();
                            } else {
                                errorMsg(res.text);
                            }
                            $('#brandModal').modal('hide');
                        }, complete: function (res) {
                            $('#brandNameID').val(res.dto.brandId);
                        }
                    })
                }
            })
        })
    }

    function createSupplier() {
        $('#btnAddNewSupplier').on('click', function () {
            $('#supplierModal').modal('show');
        })
    }

    //To save the data
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
                            return '<input type="button" class="btn btn-primary btn-xs" id="editBtn" value="Edit">'
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

    function getType() {
        $.ajax({
            url: 'receivedItem/getTypeDetail',
            type: 'GET',
            success: function (res) {
                $('#type').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        $('#type').val(suggestion.data);
                    }
                })
            }
        })
    }

    function checkQtyIsNegative() {
        $('#qty').on('keyup', function () {
            let itemCode = $('#itemCode').val();
            let asOnDate = new Date();
            if (itemCode !== '' && $('#qty').val()!=='') {
                $.ajax({
                    url: 'viewItemDetail/getItemDetail',
                    type: 'GET',
                    data: {itemCode: itemCode, asOnDate: asOnDate},
                    success: function (res) {
                        let totalPurchaseQty = 0;
                        let totalSaleQty = 0;

                        for (let i = 0; i < res.length; i++) {
                            let particular = res[i].particular;

                            if (particular === 'Purchase' || particular === 'Opening Balance') {
                                totalPurchaseQty = totalPurchaseQty + res[i].qty;
                            }
                            if (particular === 'Sale') {
                                totalSaleQty = totalSaleQty + res[i].qty;
                            }
                        }

                        totalPurchaseQty = (totalPurchaseQty + parseFloat($('#qty').val())) - parseFloat($('#originalQty').val());
                        if ((totalPurchaseQty - totalSaleQty) < 0) {
                            errorMsg("Negative Balance.");
                            $('#qty').val($('#originalQty').val())
                        }
                    }

                });
            }
        })

    }


    return {
        saveItem: saveItem,
        onClickBack: onClickBack,
        // checkItemName: checkItemName,
        // populateData: populateData,
        getOpeningPurchaseDetail: getOpeningPurchaseDetail,
        onCreditPurchaseSelect: onCreditPurchaseSelect,
        getItemDetails: getItemDetails,
        selectLedgerName: selectLedgerName,
        getBrandList: getBrandList,
        saveBrandDetail: saveBrandDetail,
        createSupplier: createSupplier,
        saveSupplierDetail: saveSupplierDetail,
        // getSupplierList: getSupplierList,
        onClickBtnEdit: onClickBtnEdit,
        addNewBrand: addNewBrand,
        getItemDetailsByPartNo: getItemDetailsByPartNo,
        getType: getType,
        checkQtyIsNegative: checkQtyIsNegative
        // dateFormat: dateFormat,
        // validateSellingPrice: validateSellingPrice

    }
})();

$(document).ready(function () {
    $('#supplierId').val(1);
    $('#supplierListTable').css('width', '100%');

    openingBalanceInventory.saveItem();
    openingBalanceInventory.onClickBack();
    // receivedItemOpening.checkItemName();
    // receivedItemOpening.populateData();
    openingBalanceInventory.getOpeningPurchaseDetail();
    openingBalanceInventory.onCreditPurchaseSelect();
    //receivedItem.getSlNo();
    openingBalanceInventory.getItemDetails();
    openingBalanceInventory.selectLedgerName();
    openingBalanceInventory.getBrandList();
    openingBalanceInventory.saveBrandDetail();
    openingBalanceInventory.createSupplier();
    openingBalanceInventory.saveSupplierDetail();
    // receivedItemOpening.getSupplierList();
    openingBalanceInventory.onClickBtnEdit();
    openingBalanceInventory.addNewBrand();
    openingBalanceInventory.getItemDetailsByPartNo();
    openingBalanceInventory.getType();
    openingBalanceInventory.checkQtyIsNegative();
    // receivedItem.dateFormat();
});
