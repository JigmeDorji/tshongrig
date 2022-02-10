/**
 * Created by Bikash Rai on 1/20/2022.
 */
viewAsset = (function () {

    let fixedAssetListGridTBody = $('#fixedAssetListGrid tbody');

    function baseURL() {
        return 'viewAsset/'
    }

    function loadDescriptionList() {
        $.ajax({
            url: baseURL() + '/loadDescriptionList',
            type: 'GET',
            success: function (res) {
                $('#assetId').empty();
                spms.loadGridDropDown($('#assetId'), res);
            }
        });
    }

    function getFixedAssetList() {
        $.ajax({
            url: baseURL() + 'getFixedAssetDetail',
            type: 'GET',
            success: function (res) {
                let i = 1;
                if (res.length > 0) {
                    $('#initialTable').removeClass('hidden');
                }

                let columnDef = [
                    {data: 'assetId', class: 'assetId hidden'},
                    {data: 'faPurchaseId', class: 'faPurchaseId hidden'},
                    {data: 'assetDetailId', class: 'assetDetailId hidden'},
                    {
                        data: 'rowNo',
                        render: function () {
                            return i++;
                        }
                    }, {data: 'description', class: 'description'},
                    {
                        data: 'particular', class: 'particular'
                    },

                    {data: 'groupName', class: 'groupName'},
                    {data: 'qty', class: 'qty'},
                    {
                        data: 'action',
                        render: function (detail, type, row) {

                            let deleteBtn = '';
                            let editBtn = '';
                            let hasDeleteRole = $('#hasDeleteRole').val();
                            let hasEditRole = $('#hasEditRole').val();

                            if (hasDeleteRole.toString() === 'true') {
                                deleteBtn = '<button  type="button" class="btn btn-danger btn-xs" id="btnDelete"><i class="fa fa-trash"></i> Delete</button>';
                            }
                            if (hasEditRole.toString() === 'true') {
                                editBtn = '<button type="button" class="btn btn-primary btn-xs" id="btnEdit"><i class="fa fa-edit"></i> Edit</button>';
                            }

                            return editBtn +
                                '<a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#itemSaleDetailModal"><button  type="button" class="btn btn-info btn-xs" id="btnView"><i class="fa fa-info-circle"></i> View</button></a>';
                        }
                    },
                ];
                spms.populateTableData($('#fixedAssetListGrid'), columnDef, res)
            }
        });
    }

    function onClickView() {
        fixedAssetListGridTBody.on('click', 'tr #btnEdit', function () {
            let selectedRow = $(this).closest('tr');
            $('#assetDetailId').val(selectedRow.find('.assetDetailId').text())
            $('#assetId').val(selectedRow.find('.assetId').text())
            $('#particular').val(selectedRow.find('.particular').text())
        });
    }

    return {
        loadDescriptionList: loadDescriptionList,
        getFixedAssetList: getFixedAssetList,
        onClickView: onClickView,
    }
})();

$(document).ready(function () {
    viewAsset.loadDescriptionList();
    viewAsset.getFixedAssetList();
    viewAsset.onClickView();
});
