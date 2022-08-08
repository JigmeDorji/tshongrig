/**
 * Created by Bikash Rai on 10/8/2021.
 */
assetSetup = (function () {

    let fixedAssetItemDetailGridTBody = $('#fixedAssetItemDetailGrid tbody');
    let assetTxnGridTBody = $('#assetTxnGrid tbody');

    function baseURL() {
        return 'assetSetup/'
    }

    function groupOnChange() {
        $('#groupId').on('change', function () {
            $.ajax({
                url: 'assetSetup/getAssetCodeSerialNo',
                type: 'GET',
                data: {groupId: $(this).val()},
                success: function (res) {
                    $('#assetNo').val(res);
                }
            });
        })
    }

    function addNewParticular() {
        $('#btnAddParticular').on('click', function () {
            $('#descriptionName').val('');
            $('#assetTopParentModal').modal('show');
        })
    }

    function saveAssetClass() {
        $('#btnSaveDescriptionId').on('click', function () {
            $('.assetClassForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: baseURL() + '/saveAssetClass',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {

                            if (res.status === 1) {
                                successMsg(res.text);
                                loadDescriptionList();
                            } else {
                                errorMsg(res.text);
                            }
                            $('#assetTopParentModal').modal('hide');
                        }
                    })
                }
            })
        })
    }

    function loadDescriptionList() {
        $.ajax({
            url: baseURL() + '/loadDescriptionList', type: 'GET', success: function (res) {
                $('#assetId').empty();
                spms.loadGridDropDown($('#assetId'), res);
            }
        });
    }

    function saveAssetSubClassCategories() {
        $('#btnSave').on('click', function () {
            $('#assetSetupForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: baseURL() + '/saveAssetSubClassCategories',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status === 1) {
                                successMsg(res.text);
                                $('#particular').val('');
                                getFixedAssetDetail();
                            } else {
                                errorMsg(res.text);
                            }
                        }
                    })
                }
            })
        })
    }

    function getFixedAssetDetail() {
        $.ajax({
            url: baseURL() + 'getFixedAssetDetail', type: 'GET', success: function (res) {
                let i = 1;

                if (res.length > 0) {
                    $('#initialTable').removeClass('hidden');
                }

                let columnDef = [{data: 'assetId', class: 'assetId hidden'}, {
                    data: 'faPurchaseId',
                    class: 'faPurchaseId hidden'
                }, {data: 'assetDetailId', class: 'assetDetailId hidden'}, {
                    data: 'rowNo', class: 'text-center', render: function () {
                        return i++;
                    }
                }, {data: 'description', class: 'description text-center'}, {
                    data: 'particular', class: 'particular text-center'
                },

                    {data: 'groupName', class: 'groupName text-center'}, {data: 'qty', class: 'qty text-center'}, {
                        data: 'action', class: 'text-center', render: function (detail, type, row) {

                            let deleteBtn = '';
                            let editBtn = '';
                            let hasDeleteRole = $('#hasDeleteRole').val();
                            let hasEditRole = $('#hasEditRole').val();

                            if (hasDeleteRole.toString() === 'true') {
                                deleteBtn = '<button  type="button" class="btn btn-danger btn-xs" id="btnDelete"><i class="fa fa-trash"></i> Delete</button>';
                            }
                            if (hasEditRole.toString() === 'true') {
                                editBtn = '<button type="button" class="btn btn-primary btn-sm" id="btnEdit"><i class="fa fa-edit"></i> Edit</button>';
                            }

                            return editBtn + '<a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#itemSaleDetailModal"><button  type="button" class="btn btn-info btn-sm" id="btnView"><i class="fa fa-info-circle"></i> View</button></a>';
                        }
                    },];
                spms.populateTableData($('#fixedAssetItemDetailGrid'), columnDef, res)
            }
        });
    }

    function onClickEdit() {
        fixedAssetItemDetailGridTBody.on('click', 'tr #btnEdit', function () {
            let selectedRow = $(this).closest('tr');
            $('#assetDetailId').val(selectedRow.find('.assetDetailId').text())
            $('#assetId').val(selectedRow.find('.assetId').text())
            $('#particular').val(selectedRow.find('.particular').text())
        });
    }

    function getItemDetailView() {
        fixedAssetItemDetailGridTBody.on('click', 'tr #btnView', function () {

            fixedAssetItemDetailGridTBody.find('tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');

            selectedRow.addClass('selected');
            $('#itemTxnModal').modal('show');

            $('#modalParticular').val(selectedRow.find('.particular').text())
            $('#modalGroup').val(selectedRow.find('.groupName').text())

            $.ajax({
                url: baseURL() + 'getAssetItemTxnDetail',
                type: 'GET',
                data: {assetDetailId: selectedRow.find('.assetDetailId').text()},
                success: function (res) {
                    let i = 1;
                    let columnDef = [{
                        data: 'rowNo', class: 'text-center', render: function () {
                            return i++;
                        }
                    }, {data: 'faPurchaseId', class: 'faPurchaseId hidden'}, {
                        data: 'purchaseMasterId',
                        class: 'purchaseMasterId hidden'
                    }, {data: 'voucherNo', class: 'voucherNo hidden'}, {
                        data: 'purchaseDate',
                        class: 'purchaseDate text-center'
                    }, {data: 'description', class: 'description text-center'}, {
                        data: 'rate',
                        class: 'rate text-center'
                    }, {data: 'qty', class: 'qty text-center'}, {
                        data: 'action', class: 'text-center', render: function (detail, type, row) {
                            let editBtn = '', hasEditRole = $('#hasEditRole').val();
                            // if (hasEditRole.toString() === 'true') {
                            let url = row.description === "Opening" ? 'navigateToOpening' : 'navigateToPurchase';
                            editBtn = '<a href="assetSetup/' + url + '?faPurchaseId=' + encodeURIComponent(row.faPurchaseId) + '&&purchaseMasterId=' + encodeURIComponent(row.purchaseMasterId) + '&&voucherNo=' + encodeURIComponent(row.voucherNo) + '"><button type=button"  class="btn btn-primary btn-sm">Edit</button></a>';
                            // }
                            return editBtn + '<a href="#"><button  type="button" class="btn btn-info btn-sm" id="btnDetailView"><i class="fa fa-info-circle"></i> Detail</button></a>';
                        }
                    },];
                    spms.populateTableData($('#assetTxnGrid'), columnDef, res);
                    $('#assetTxnGrid').css('width', '100%');

                }
            });

            /*$.ajax({
                url: baseURL() + 'getAssetItemDetail',
                type: 'GET',
                data: {faPurchaseId: selectedRow.find('.faPurchaseId').text()},
                success: function (res) {
                    let i = 1;

                    let columnDef = [
                        {
                            data: 'rowNo',
                            render: function () {
                                return i++;
                            }
                        },
                        {data: 'particular', class: 'particular'},
                        {data: 'assetCode', class: 'assetCode'},
                        {data: 'rate', class: 'rate'},
                        {data: 'status', class: 'status'},
                    ];
                    spms.populateTableData($('#itemDetailGrid'), columnDef, res);
                    $('#itemDetailGrid').css('width', '100%');

                }
            });*/
        });

        assetTxnGridTBody.on('click', 'tr #btnDetailView', function () {

            assetTxnGridTBody.find('tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');

            selectedRow.addClass('selected');
            $('#itemDetailModal').modal('show');

            $.ajax({
                url: baseURL() + 'getAssetItemDetail',
                type: 'GET',
                data: {faPurchaseId: selectedRow.find('.faPurchaseId').text()},
                success: function (res) {
                    let i = 1;

                    let columnDef = [{
                        data: 'rowNo', class: 'text-center', render: function () {
                            return i++;
                        }
                    }, {data: 'particular', class: 'particular text-center'}, {
                        data: 'assetCode',
                        class: 'assetCode text-center'
                    }, {data: 'rate', class: 'rate text-center'}, {data: 'status', class: 'status text-center'},];
                    spms.populateTableData($('#itemDetailGrid'), columnDef, res);
                    $('#itemDetailGrid').css('width', '100%');

                }
            });
        });
    }

    return {
        groupOnChange: groupOnChange,
        loadDescriptionList: loadDescriptionList,
        addNewParticular: addNewParticular,
        saveAssetClass: saveAssetClass,
        getFixedAssetDetail: getFixedAssetDetail,
        saveAssetSubClassCategories: saveAssetSubClassCategories,
        onClickEdit: onClickEdit,
        getItemDetailView: getItemDetailView
    }
})();

$(document).ready(function () {
    assetSetup.loadDescriptionList();
    assetSetup.groupOnChange();
    assetSetup.addNewParticular();
    assetSetup.saveAssetClass();
    assetSetup.saveAssetSubClassCategories();
    assetSetup.getFixedAssetDetail();
    assetSetup.onClickEdit();
    assetSetup.getItemDetailView();

});
