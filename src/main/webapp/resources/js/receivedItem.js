/**
 * Created by SonamPC on 12-Dec-16.
 */


let countItem = 0;
isGeneralTrader = generalTrader().isGeneralTrader("openingBalanceInventory");


if (isGeneralTrader) {
    $('#itemName').on('change', () => {
        defaultValueSetter();
    });

    function defaultValueSetter() {
        $.ajax({
            url: 'receivedItem/getBrandList',
            type: 'GET',
            success: function (res) {
                $('#brandId').val(res[0].text).attr("readonly", true);

                $.ajax({
                    url: 'receivedItem/getSlNo',
                    type: 'GET',
                    data: {brandId: res[0].value},
                    success: function (res) {

                        $.ajax({
                            url: 'receivedItem/getSINo',
                            type: 'POST',
                            success: function (response) {
                                let SiNo = parseInt(response) + 1;
                                if (countItem === 0) {
                                    $('#itemCode').val(res.itemCode + SiNo).attr("readonly", true);
                                    $('#currentSerial').val(SiNo)
                                } else {
                                    $('#itemCode').val(res.itemCode + (SiNo + countItem)).attr("readonly", true);
                                    $('#currentSerial').val(SiNo)
                                }


                            }
                        });

                        $('#currentSerial').val(res.itemCode)
                        $('#itemNamePrefix').val(res.prefixCode).attr("readonly", true);
                        $('#type').val('GENERAL-' + res.itemCode).attr("readonly", true);
                        $('#partNo').val(res.itemCode + res.itemCode).attr("readonly", true);
                    }
                });

            }
        })
        $('#btnAddBrand').attr("disabled", true)
    }

}
receivedItem = (function () {
    let purchaseItemTableBody = $('#purchaseItemTable tbody');

    if ($('#purchaseDateEdit').val() !== '') {
        $('#purchaseDate').val(formatAsDate($('#purchaseDateEdit').val()));
    }

    function saveItem() {
        $('#btnSave').on('click', function () {
            var itemCode = $('#itemCode').val();
            var itemName = $('#itemName').val();
            var qty = $('#qty').val();
            var sellingPrice = $('#sellingPrice').val();
            $('.globalForm').validate({
                submitHandler: function (form) {
                    // $('#btnSave').prop('disabled',true)
                    var data = $(form).serializeArray();
                    $('#btnSave').attr('disabled', true);
                    $.ajax({
                        url: 'receivedItem/save',
                        type: 'POST',
                        data: data,
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

                        }, complete: function () {
                            $('#btnSave').attr('disabled', false);
                        }, error: () => {
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
        $('#itemName').on('changeeee', function () {
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


    function getPurchaseDetail() {
        var purchaseId = $('#purchaseId').val();
        var voucherNo = $('#purchaseVoucherNo').val();
        if (purchaseId !== '' && purchaseId !== null) {
            $.ajax({
                url: 'receivedItem/getPurchaseDetail',
                type: 'GET',
                data: {purchaseId: purchaseId, purchaseDate: $('#purchaseDate').val(), voucherNo: voucherNo},
                success: function (res) {
                    _disPlayData(res);

                    if (res !== '') {
                        populate(res);

                        $('#brandId').val(res.brandName)
                        $('#itemName').val(res.itemName.split(':')[1])
                        $('#itemNamePrefix').val(res.itemName.split(':')[0])

                        if (res.isCash === 2) {
                            $('.bankDetails').attr('hidden', false);
                        } else {
                            $('.bankDetails').attr('hidden', true);
                        }
                        if (res.isCash === 3) {
                            getSupplierDropdownList();
                            $('.creditDetails').attr('hidden', false);
                            $('#supplierId').val(res.supplierId);
                        } else {
                            $('.creditDetails').attr('hidden', true);
                        }
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
                    // $('#purchaseDate').val(formatAsDate(res.purchaseDate));
                    // $('#purchaseInvoiceNo').val(res.purchaseInvoiceNo);
                }
            }
        });
    }

    function onCreditPurchaseSelect() {
        $('#isCash').on('change', function () {
            if ($(this).val() == 2) {
                $('.bankDetails').attr('hidden', false);
                $('.creditDetails').attr('hidden', true);
            } else if ($(this).val() == 3) {
                getSupplierDropdownList();
                $('.bankDetails').attr('hidden', true);
                $('.creditDetails').attr('hidden', false);
            } else {
                $('.bankDetails').attr('hidden', true);
                $('.creditDetails').attr('hidden', true);
            }
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
                        // $('.common').val('');
                        // errorMsg("No matching item found.");
                    }
                }
            });
        })
    }

    /*  $('#isCash').on('change', function () {
          if ($(this).val() === 2) {
              $('.bankDetails').attr('hidden', true);
          } else {
              $('.bankDetails').attr('hidden', false);
          }
      });*/


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
                                    $('#type').focus();
                                    $('#itemCode').val(res.itemCode);
                                    $('#itemNamePrefix').val(res.itemCode);
                                    getItemCodeList(suggestion.data);
                                    $('#currentSerialNo').val(res.serialNo);
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
                    $('#pModalButton').prop('disabled', true)
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
                        }, error: () => {
                            $('#pModalButton').prop('disabled', false)
                        }, complete: () => {
                            $('#pModalButton').prop('disabled', false)
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

    function getItemDetails() {
        $('#itemCode').on('change', function () {
                $.ajax({
                    url: 'receivedItem/getItemDetails',
                    type: 'GET',
                    data: {itemCode: $('#itemCode').val()},
                    success: function (res) {
                        if (res !== '') {
                            populate(res);
                            $('#brandId').val(res.brandName)
                            $('#itemName').val(res.itemName)
                            $('#qty').val('')
                            $('#costPrice').val('')
                            // $('#itemNamePrefix').val(res.itemName.split(':')[0])
                            $.ajax({
                                url: 'receivedItem/getSlNo',
                                type: 'GET',
                                data: {brandId: res.brandId},
                                success: function (res) {
                                    $('#currentSerial').val(res.itemCode);
                                    $('#currentSerialNo').val(res.serialNo);
                                }
                            });
                        } else {
                            $('#itemCode').val($('#currentSerial').val());
                            $('.common').val('');
                        }

                    }, complete: function (res) {
                        if ($('#purchaseInvoiceNo').val() !== '' && $('#itemCode').val() !== '') {
                            _checkMultiplePurchaseForSameItem();
                        }

                    }
                });
            }
        )
    }

    function _checkMultiplePurchaseForSameItem() {
        $.ajax({
            url: 'receivedItem/checkPurchaseInvoiceNoAlreadyEntered',
            type: 'GET',
            data: {itemCode: $('#itemCode').val(), purchaseInvoiceNo: $('#purchaseInvoiceNo').val()},
            success: function (res) {
                if (res.status === 0) {
                    errorMsg(res.text)
                    $('#purchaseInvoiceNo').val('')
                }
            }
        });
    }

    function onChangePurchaseInvoiceNo() {
        $('#purchaseInvoiceNo').on('change', function () {
            if ($(this).val() !== '' && $('#itemCode').val() !== '') {
                _checkMultiplePurchaseForSameItem();
            }
        })
    }

    function getItemCodeList(brandId) {
        $.ajax({
            url: 'receivedItem/getItemCodeListByBrandId',
            type: 'GET',
            data: {brandId: brandId},
            success: function (res) {
                $('#itemCode').devbridgeAutocomplete({
                    lookup: $.map(res, function (value) {
                        return {data: value.id, value: value.text}
                    }), onSelect: function (suggestion) {
                        $.ajax({
                            url: 'receivedItem/getItemDetails',
                            type: 'GET',
                            data: {itemCode: suggestion.data},
                            success: function (res) {
                                if (res !== '') {

                                    populate(res);
                                    $('#brandId').val(res.brandName)
                                    // $('#itemName').val(res.itemName.split(':')[1])
                                    $('#prefixCode').val(res.prefixCode)
                                    $('#qty').val('')
                                    $('#costPrice').val('')
                                } else {
                                    $('#itemCode').val('');
                                    errorMsg("No matching item found.");
                                }
                            }
                        });
                        $('#itemCode').val(suggestion.data);
                    }
                })
            }
        })
    }

    let i = 1;

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
            let itemCode = $('#itemCode');
            let itemCodeSerial = parseInt(itemCode.val().match(/\d+/)[0]);
            let currentSerialNo = parseInt($('#currentSerialNo').val());

            if (currentSerialNo === itemCodeSerial) {
                //Update the serial number
                $.ajax({
                    url: 'receivedItem/updateItemSerialNumber',
                    type: 'GET',
                    data: {brandName: brandId.val()},
                    success: function (res) {
                        if (res.status !== 1) {
                            errorMsg("Problem while updating the item serial number.")
                        }
                    }
                });
            }
            let type = $('#type');

            let itemName = $("#itemName");
            let sellingPrice = $('#sellingPrice');
            let costPrice = $('#costPrice');
            let partNo = $('#partNo');
            let locationId = $('#locationId');
            let prefixCode = $('#prefixCode');
            let purchaseId = $('#purchaseId');
            let totalAmount = parseFloat(costPrice.val()) * parseFloat(qty.val());
            if (!fieldValidation(qty, unitId, itemCode, itemName, sellingPrice, costPrice, brandId, type, partNo, locationId)) {
                qty.removeClass('error');
                unitId.removeClass('error');
                itemCode.removeClass('error');
                itemName.removeClass('error');
                sellingPrice.removeClass('error');
                type.removeClass('error');
                costPrice.removeClass('error');
                brandId.removeClass('error');
                partNo.removeClass('error');
                locationId.removeClass('error');

                let row;
                if (isGeneralTrader) {
                    //  <th width="2%">SL.NO</th>
                    //                                     <th width="10%">Item Code</th>
                    //                                     <th width="15%">Item Name</th>
                    //                                         <%--                                <th width="10%" hidden>Part Number</th>--%>
                    //                                     <th width="10%">SP</th>
                    //                                     <th width="10%">CP</th>
                    //                                     <th width="5%">Qty</th>
                    //                                     <th width="5%">Unit</th>
                    //                                     <th width="5%">Amount</th>
                    //                                     <th width="15%">Action</th>
                    row = "<tr>" +
                        "<td><input type='text' id='index' readonly class='form-control form-control-sm input-group-sm' value='" + i + "'>" +
                        "<input type='hidden' id='type' name='purchaseDTOS[" + i + "].type'  class='form-control form-control-sm input-group-sm' value='" + type.val() + "'>" +
                        "<input type='hidden' id='purchaseId' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control form-control-sm input-group-sm' value='" + purchaseId.val() + "'>" +
                        "<input type='hidden' id='brandName' name='purchaseDTOS[" + i + "].brandName'  class='form-control form-control-sm input-group-sm' value='" + brandId.val() + "'>" +
                        "<input type='hidden' id='prefixCode' name='purchaseDTOS[" + i + "].prefixCode'  class='form-control form-control-sm input-group-sm' value='" + prefixCode.val() + "'>" +
                        "<input type='hidden' id='locationId' name='purchaseDTOS[" + i + "].locationId'  class='form-control form-control-sm input-group-sm' value='" + locationId.val() + "'></td>" +
                        "<td><input type='text' id='itemCode' readonly name='purchaseDTOS[" + i + "].itemCode' class='form-control form-control-sm input-group-sm' value='" + itemCode.val().toUpperCase() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control form-control-sm input-group-sm' value='" + itemName.val() + "'></td>" +
                        "<td hidden><input type='text' readonly name='purchaseDTOS[" + i + "].partNo' class='form-control form-control-sm input-group-sm partNo' value='" + partNo.val() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].sellingPrice' class='form-control form-control-sm input-group-sm sellingPrice right-align' value='" + sellingPrice.val() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].costPrice' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + costPrice.val() + "'></td>" +
                        "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm input-group-sm initialQty right-align'><input type='text' id='qty' readonly class='form-control form-control-sm input-group-sm qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + qty.val() + " ></td>" +
                        "<td><input type='hidden' readonly class='form-control form-control-sm input-group-sm right-align' id='unitId'  name='purchaseDTOS[" + i + "].unitId' value=" + unitId.val() + " ><input type='text' readonly class='form-control form-control-sm input-group-sm right-align' value=" + unitText + " ></td>" +
                        "<td><input type='text' readonly class='form-control form-control-sm input-group-sm totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + totalAmount.toFixed(2) + " ></td>" +
                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-sm btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-sm btn-danger btn-xs fa fa-trash' value='Delete'></td>" +
                        "</tr>";

                    countItem += 1;
                } else {
                    row = "<tr>" +
                        "<td><input type='text' id='index' readonly class='form-control form-control-sm input-group-sm' value='" + i + "'>" +
                        "<input type='hidden' id='type' name='purchaseDTOS[" + i + "].type'  class='form-control form-control-sm input-group-sm' value='" + type.val() + "'>" +
                        "<input type='hidden' id='purchaseId' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control form-control-sm input-group-sm' value='" + purchaseId.val() + "'>" +
                        "<input type='hidden' id='brandName' name='purchaseDTOS[" + i + "].brandName'  class='form-control form-control-sm input-group-sm' value='" + brandId.val() + "'>" +
                        "<input type='hidden' id='prefixCode' name='purchaseDTOS[" + i + "].prefixCode'  class='form-control form-control-sm input-group-sm' value='" + prefixCode.val() + "'>" +
                        "<input type='hidden' id='locationId' name='purchaseDTOS[" + i + "].locationId'  class='form-control form-control-sm input-group-sm' value='" + locationId.val() + "'></td>" +
                        "<td><input type='text' id='itemCode' readonly name='purchaseDTOS[" + i + "].itemCode' class='form-control form-control-sm input-group-sm' value='" + itemCode.val().toUpperCase() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control form-control-sm input-group-sm' value='" + itemName.val() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].partNo' class='form-control form-control-sm input-group-sm partNo' value='" + partNo.val() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].sellingPrice' class='form-control form-control-sm input-group-sm sellingPrice right-align' value='" + sellingPrice.val() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].costPrice' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + costPrice.val() + "'></td>" +
                        "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm input-group-sm initialQty right-align'><input type='text' id='qty' readonly class='form-control form-control-sm input-group-sm qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + qty.val() + " ></td>" +
                        "<td><input type='hidden' readonly class='form-control form-control-sm input-group-sm right-align' id='unitId'  name='purchaseDTOS[" + i + "].unitId' value=" + unitId.val() + " ><input type='text' readonly class='form-control form-control-sm input-group-sm right-align' value=" + unitText + " ></td>" +
                        "<td><input type='text' readonly class='form-control form-control-sm input-group-sm totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + totalAmount.toFixed(2) + " ></td>" +
                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-sm btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-sm btn-danger btn-xs fa fa-trash' value='Delete'></td>" +
                        "</tr>";
                }


                i = i + 1;
                let tableGrid = $('#purchaseItemTable');
                let tableBody = tableGrid.find('tbody');
                if (!isValid(tableBody, itemCode)) {
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
                $('#itemCode').focus();
                spms.addRowNumber($('#purchaseItemTable tbody tr'));
            }

        })

    }


    function fieldValidation(qty, unitId, itemCode, itemName, sellingPrice, costPrice, brandId, type, partNo, locationId) {
        let errorExists = false;

        if (checkItemCodeAlreadyExists(itemCode)) {
            $('#itemCode').focus();
            errorMsg("This item already exists in the grid.");

            errorExists = true;
        }

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

        if (sellingPrice.val() === '') {
            sellingPrice.addClass('error');
            errorExists = true;
        }
        if (costPrice.val() === '') {
            costPrice.addClass('error');
            errorExists = true;
        }
        if (brandId.val() === '') {
            brandId.addClass('error');
            errorExists = true;
        }
        if (type.val() === '') {
            type.addClass('error');
            errorExists = true;
        }
        if (partNo.val() === '') {
            partNo.addClass('error');
            errorExists = true;
        }
        if (locationId.val() === '') {
            locationId.addClass('error');
            errorExists = true;
        }
        return errorExists;
    }

    function checkItemCodeAlreadyExists(itemCode) {
        let exists = false;
        $('#purchaseItemTable').find('tr').each(function () {
            if ($(this).closest('tr').find('#itemCode').val() === itemCode.val().toUpperCase()) {
                exists = true;
            }
        });
        return exists;
    }

    function isValid(tableData, itemCode) {
        var isItemCodeExist = false;
        tableData.find('tr').each(function () {
            if ($(this).find('.itemCode').val() === itemCode.val().toUpperCase()) {
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
            if (typeof row.find('.costPrice').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined') {
                row.find('.totalAmount').val((row.find('.costPrice').val() === '' ? 1 : parseFloat(row.find('.costPrice').val())) *
                    (row.find('.qty').val() === '' ? 1 : parseFloat(row.find('.qty').val())));
                grandTotalAmount = grandTotalAmount + (row.find('.costPrice').val() === '' ? 1 : parseFloat(row.find('.costPrice').val())) *
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
            if (parseInt(purchaseItemTableBody.find('tr').length) === 0) {
                $('#btnSave').attr('disabled', true);
            } else {
                $('#btnSave').attr('disabled', false);
            }
            calculateTotal();
        })
    }

    function onBtnClickEditItem() {
        purchaseItemTableBody.on('click', 'tr #itemEditBtn', function () {
            let selectedRow = $(this).closest('tr').addClass('selected');
            selectedRow.find('.qty').focus();
            selectedRow.find('.qty').attr('readonly', false);
            selectedRow.find('.costPrice').attr('readonly', false);
            selectedRow.find('.sellingPrice').attr('readonly', false);
            selectedRow.find('.partNo').attr('readonly', false);
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
            $('#supplierName').val(res[0].supplierName);
            for (let i = 0; i < res.length; i++) {
                $('#purchaseDate').val(formatAsDate(res[i].purchaseDate))
                $('#purchaseVoucherNo').val(res[i].purchaseVoucherNo);
                $('#purchaseInvoiceNo').val(res[i].purchaseInvoiceNo);
                $('#isCash').val(res[i].isCash);
                if (res[i].isCash === 2) {
                    $('.bankDetails').attr('hidden', false);
                    $('#bankLedgerId').val(res[0].bankLedgerId);
                } else {
                    $('.bankDetails').attr('hidden', true);
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
                let delBtn = res[i].purchaseId !== null ? '' : "<input type='button'  id='btnDeleteItem' class='btn btn-danger btn-xs fa fa-trash' value='Delete'>";
                let row;
                if (isGeneralTrader) {
                    row = "<tr>" +
                        "<td><input type='text' id='index' readonly class='form-control form-control-sm input-group-sm' value='" + parseInt(i + 1) + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].type'  class='form-control form-control-sm input-group-sm' value='" + res[i].type + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control form-control-sm input-group-sm' value='" + res[i].purchaseId + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].purchaseAuditId'  class='form-control form-control-sm input-group-sm' value='" + res[i].purchaseAuditId + "'>" +
                        "<input type='hidden' id='brandName' name='purchaseDTOS[" + i + "].brandName'  class='form-control form-control-sm input-group-sm' value='" + res[i].brandName + "'>" +
                        "<input type='hidden' id='prefixCode' name='purchaseDTOS[" + i + "].prefixCode'  class='form-control form-control-sm input-group-sm' value='" + res[i].prefixCode + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].locationId'  class='form-control form-control-sm input-group-sm' value='" + res[i].locationId + "'></td>" +
                        "<td><input type='text' id='itemCode' readonly name='purchaseDTOS[" + i + "].itemCode' class='form-control form-control-sm input-group-sm' value='" + res[i].itemCode.toUpperCase() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control form-control-sm input-group-sm' value='" + res[i].itemName + "'></td>" +
                        "<td hidden><input type='text' readonly name='purchaseDTOS[" + i + "].partNo' class='form-control form-control-sm input-group-sm partNo' value='" + res[i].partNo + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].sellingPrice' class='form-control form-control-sm input-group-sm sellingPrice right-align' value='" + res[i].sellingPrice + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].costPrice' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].costPrice + "'></td>" +
                        "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm input-group-sm initialQty right-align' value=" + res[i].qty + " ><input type='text' id='qty' readonly class='form-control form-control-sm input-group-sm qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + res[i].qty + " ></td>" +
                        "<td><input type='hidden' readonly name='purchaseDTOS[" + i + "].unitId' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].unitId + "'><input type='text' readonly  class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].unitName + "'></td>" +
                        "<td><input type='text' readonly class='form-control form-control-sm input-group-sm totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + res[i].costPrice + " ></td>" +
                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-sm btn-xs fa fa-trash' value='Edit'>" + delBtn + "</td>" +
                        "</tr>";

                    countItem += 1;
                } else {
                    row = "<tr>" +
                        "<td><input type='text' id='index' readonly class='form-control form-control-sm input-group-sm' value='" + parseInt(i + 1) + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].type'  class='form-control form-control-sm input-group-sm' value='" + res[i].type + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].purchaseId'  class='form-control form-control-sm input-group-sm' value='" + res[i].purchaseId + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].purchaseAuditId'  class='form-control form-control-sm input-group-sm' value='" + res[i].purchaseAuditId + "'>" +
                        "<input type='hidden' id='brandName' name='purchaseDTOS[" + i + "].brandName'  class='form-control form-control-sm input-group-sm' value='" + res[i].brandName + "'>" +
                        "<input type='hidden' id='prefixCode' name='purchaseDTOS[" + i + "].prefixCode'  class='form-control form-control-sm input-group-sm' value='" + res[i].prefixCode + "'>" +
                        "<input type='hidden' id='' name='purchaseDTOS[" + i + "].locationId'  class='form-control form-control-sm input-group-sm' value='" + res[i].locationId + "'></td>" +
                        "<td><input type='text' id='itemCode' readonly name='purchaseDTOS[" + i + "].itemCode' class='form-control form-control-sm input-group-sm' value='" + res[i].itemCode.toUpperCase() + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].itemName' class='form-control form-control-sm input-group-sm' value='" + res[i].itemName + "'></td>" +
                        "<td ><input type='text' readonly name='purchaseDTOS[" + i + "].partNo' class='form-control form-control-sm input-group-sm partNo' value='" + res[i].partNo + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].sellingPrice' class='form-control form-control-sm input-group-sm sellingPrice right-align' value='" + res[i].sellingPrice + "'></td>" +
                        "<td><input type='text' readonly name='purchaseDTOS[" + i + "].costPrice' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].costPrice + "'></td>" +
                        "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm input-group-sm initialQty right-align' value=" + res[i].qty + " ><input type='text' id='qty' readonly class='form-control form-control-sm input-group-sm qty right-align amount'   name='purchaseDTOS[" + i + "].qty' value=" + res[i].qty + " ></td>" +
                        "<td><input type='hidden' readonly name='purchaseDTOS[" + i + "].unitId' class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].unitId + "'><input type='text' readonly  class='form-control form-control-sm input-group-sm costPrice right-align' value='" + res[i].unitName + "'></td>" +
                        "<td><input type='text' readonly class='form-control form-control-sm input-group-sm totalAmount right-align totalAmount' id='totalAmount'  name='purchaseDTOS[" + i + "].totalAmount' value=" + res[i].costPrice + " ></td>" +
                        "<td><input type='button'  id='itemEditBtn' class='btn btn-primary btn-sm btn-xs fa fa-trash' value='Edit'>" + delBtn + "</td>" +
                        "</tr>";
                }


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
        $('#purchaseItemTable tbody').on('keyup', '.qty', function () {
            calculateTotal()
        });
        $('#purchaseItemTable tbody').on('keyup', '.costPrice', function () {
            calculateTotal()
        });

    }

    function _validateDeletePurchase() {
        let isDeleteValid = true;
        $('#purchaseItemTable').find('tbody').find('tr').each(function () {
            let row = $(this).closest('tr');
            let itemCode = row.find('#itemCode').val();
            let qty = row.find('#qty').val();
            let initialQty = row.find('#initialQty').val();
            let asOnDate = new Date();
            if (itemCode !== '' && qty !== '') {
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
                        totalPurchaseQty = (totalPurchaseQty) - parseFloat(initialQty);
                        if ((totalPurchaseQty - totalSaleQty) < 0) {
                            errorMsg("Negative Balance.");
                            return false;
                        }
                    }

                });


            }
        });
        return isDeleteValid;
    }

    function deleteLedgerDetails() {
        $('#btnDelete').on('click', function () {
            if (_validateDeletePurchase()) {
                let purchaseVoucherNo = $('#purchaseVoucherNo').val();
                let purchaseInvoiceNo = $('#purchaseInvoiceNo').val();
                if (purchaseVoucherNo !== '') {
                    confirmMessage("Please Confirmmm", function (e) {
                        if (e) {
                            $.ajax({
                                url: 'receivedItem/deleteAllThePurchaseEntry',
                                type: 'get',
                                data: {purchaseVoucherNo: purchaseVoucherNo, purchaseInvoiceNo: purchaseInvoiceNo},
                                success: function (res) {
                                    if (res.status === 0) {
                                        errorMsg(res.text)
                                    } else {
                                        $.Toast("Success", res.text, "success", {
                                            has_icon: true,
                                            has_close_btn: true,
                                            stack: true,
                                            fullscreen: false,
                                            timeout: 5000,
                                            sticky: false,
                                            has_progress: true,
                                            rtl: false,
                                        }, function (e) {
                                            window.location.reload();
                                        });
                                    }
                                }
                            });
                        }
                    })
                } else {
                    errorMsg("Delete is not possible.");
                }
            }
        })
    }

    function validateQty() {
        $('#purchaseItemTable').find('tbody').on('keyup', 'tr #qty', function () {
            let selectedRow = $(this).closest('tr').addClass('selected');
            let itemCode = selectedRow.find('#itemCode').val();
            let qty = selectedRow.find('#qty').val();
            let initialQty = selectedRow.find('#initialQty').val();
            let asOnDate = new Date();
            if (itemCode !== '' && qty !== '') {
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

                        totalPurchaseQty = (totalPurchaseQty + parseFloat(qty)) - parseFloat(initialQty);
                        if ((totalPurchaseQty - totalSaleQty) < 0) {
                            errorMsg("Negative Balance.");
                            selectedRow.find('#qty').val(initialQty);
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
        populateData: populateData,
        getPurchaseDetail: getPurchaseDetail,
        getItemDetails: getItemDetails,
        getBrandList: getBrandList,
        saveBrandDetail: saveBrandDetail,
        createSupplier: createSupplier,
        saveSupplierDetail: saveSupplierDetail,
        getSupplierList: getSupplierList,
        onClickBtnEdit: onClickBtnEdit,
        addNewBrand: addNewBrand,
        getItemDetailsByPartNo: getItemDetailsByPartNo,
        getType: getType,
        selectLedgerName: selectLedgerName,
        // onChangePurchaseInvoiceNo: onChangePurchaseInvoiceNo,
        addGridToDetails: addGridToDetails,
        onBtnClickDeleteItem: onBtnClickDeleteItem,
        onBtnClickEditItem: onBtnClickEditItem,
        getPurchaseInvoiceItemList: getPurchaseInvoiceItemList,
        onQtyChange: onQtyChange,
        deleteLedgerDetails: deleteLedgerDetails,
        getItemCodeList: getItemCodeList,
        onCreditPurchaseSelect: onCreditPurchaseSelect,
        validateQty: validateQty,


    }
})
();

$(document).ready(function () {
    $('#supplierId').val(1);
    $('#supplierListTable').css('width', '100%');

    receivedItem.saveItem();
    receivedItem.onClickBack();
    // receivedItem.checkItemName();
    receivedItem.populateData();
    receivedItem.getPurchaseDetail();
    receivedItem.getItemDetails();
    receivedItem.selectLedgerName();
    receivedItem.getBrandList();
    receivedItem.saveBrandDetail();
    receivedItem.createSupplier();
    receivedItem.saveSupplierDetail();
    receivedItem.getSupplierList();
    receivedItem.onClickBtnEdit();
    receivedItem.addNewBrand();
    receivedItem.getItemDetailsByPartNo();
    receivedItem.getType();
    receivedItem.getItemCodeList();
    // receivedItem.onChangePurchaseInvoiceNo();
    receivedItem.addGridToDetails();
    receivedItem.onBtnClickDeleteItem();
    receivedItem.onBtnClickEditItem();
    receivedItem.getPurchaseInvoiceItemList();
    receivedItem.onQtyChange();
    receivedItem.deleteLedgerDetails();
    receivedItem.onCreditPurchaseSelect();
    receivedItem.validateQty();

});