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
                                    // localStorage.setItem('faPurchaseId', '');
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

            spms._formIndexing(fixedAssetOpeningGrid.find('tbody'),
                fixedAssetOpeningGrid.find('tbody tr'));
            loadDropdown() //load dropdown
            indexRowNo(fixedAssetOpeningGrid);
        });
    }

    function loadDropdown() {
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
    }

    function returnRow() {
        iterator = iterator + 1;
        return "<tr>" +
            "<td><input type='text' readonly class='form-control rowNumber'>" +
            "<input type='hidden' id='assetDetailId" + iterator + "' name='openingAndBuyingListDTO[0].assetDetailId'  class='form-control'>" +
            "<input type='hidden' id='fixedAssetGroupId' name='openingAndBuyingListDTO[0].fixedAssetGroupId'  class='form-control'>" +
            "<input type='hidden' id='faPurchaseId' name='openingAndBuyingListDTO[0].faPurchaseId'  class='form-control' ></td>" +
            "<td><input type='text' id='autoCompleteId" + iterator + "' name='openingAndBuyingListDTO[0].particular' class='form-control particular'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].purchaseDate' class='form-control formatDate'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].openingBalance' class='form-control openingBalance right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].depreciatedValue' class='form-control depreciatedValue right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].rate' class='form-control rate right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].qty' class='form-control qty  right-align'></td>" +
            "<td><input type='text'  name='openingAndBuyingListDTO[0].total' readonly class='form-control total right-align'></td>" +
            "<td class='text-center'><button class='btn btn-danger btn-xm  d-sm-inline-block removeBtn' type='button' id='removeBtn'><i class='fa fa-trash'></i> Delete</button>" +
            "<button class='btn btn-xm btn-success d-sm-inline-block addBtn' type='button' id='addBtn'><i class='fa fa-plus'></i> Add More</button></td>" +
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

                    if (parseInt($('#fixedAssetOpeningGrid tbody tr').length) === 1) {
                        $('#fixedAssetOpeningGrid tr').last().find('.removeBtn').addClass('hidden');
                    }
                    $('#fixedAssetOpeningGrid tr').last().find('.addBtn').removeClass('hidden');
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

    function _disPlayData() {
        let faPurchaseId = $('#faPurchaseId');

        // if (faPurchaseId.val() !== '') {
        //     localStorage.setItem('faPurchaseId', faPurchaseId.val())
        // }
        spms.ajax(baseURL() + 'loadAssetOpeningList', 'GET', {
            faPurchaseId: faPurchaseId.val() === '' ? localStorage.getItem('faPurchaseId')
                : faPurchaseId.val()
        }, function (res) {
            for (let i = 0; i < res.length; i++) {

                let totalAmount = (res[i].rate * res[i].qty)

                let row = "<tr>" +
                    "<td><input type='text' readonly class='form-control rowNumber'>" +
                    "<input type='hidden' id='assetDetailId" + iterator + "' name='openingAndBuyingListDTO[0].assetDetailId'  class='form-control' value='" + res[i].assetDetailId + "'>" +
                    "<input type='hidden' id='fixedAssetGroupId' name='openingAndBuyingListDTO[0].fixedAssetGroupId'  class='form-control'>" +
                    "<input type='hidden' id='faPurchaseId' name='openingAndBuyingListDTO[0].faPurchaseId'  class='form-control faPurchaseId' value='" + res[i].faPurchaseId + "'></td>" +
                    "<td><input type='text' name='openingAndBuyingListDTO[0].particular' value='" + res[i].particular + "' class='form-control particular'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].purchaseDate' class='form-control formatDate' value='" + formatAsDate(res[i].purchaseDate) + "'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].openingBalance' class='form-control openingBalance right-align' value='" + res[i].amount + "'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].depreciatedValue' class='form-control depreciatedValue right-align'value='" + res[i].depreciatedValue + "'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].rate' class='form-control rate right-align' value='" + res[i].rate + "'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].qty' class='form-control qty  right-align' value='" + res[i].qty + "'></td>" +
                    "<td><input type='text'  name='openingAndBuyingListDTO[0].total' readonly class='form-control total right-align' value='" + totalAmount + "'></td>" +
                    "<td class='text-center'><button class='btn btn-danger btn-xm  d-sm-inline-block removeBtn' type='button' id='removeBtn'><i class='fa fa-trash'></i> Delete</button>" +
                    "<button class='btn btn-xm btn-success d-sm-inline-block addBtn' type='button' id='addBtn'><i class='fa fa-plus'></i> Add More</button></td>" +
                    "</tr>";

                $('#fixedAssetOpeningGrid tr').last().find('.addBtn').removeClass('hidden');
                fixedAssetOpeningGrid.find('tbody').append(row);
                indexRowNo(fixedAssetOpeningGrid);
                iterator = iterator + 1;
            }
        })
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
                spms._formIndexing(fixedAssetOpeningGrid.find('tbody'),
                    fixedAssetOpeningGrid.find('tbody tr'));

                fixedAssetOpeningGrid.find('tbody tr')
                    .find('.removeBtn').addClass('hidden');
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

    // $('#resetBtn').on('click', function () {
    //     localStorage.setItem('faPurchaseId', '');
    //     window.location.reload();
    // });

    return {
        loadInitialGrid: loadInitialGrid,
        addMoreRow: addMoreRow,
        deleteItem: deleteItem,
        saveItem: saveItem,
        populateData: populateData,
        onQtyChange: onQtyChange,
        _disPlayData: _disPlayData
    }
})();

$(document).ready(function () {
    assetOpening.loadInitialGrid();
    assetOpening.addMoreRow();
    assetOpening.deleteItem();
    assetOpening.saveItem();
    assetOpening.onQtyChange();
    assetOpening._disPlayData();
});
