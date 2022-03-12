/**
 * Created by Bikash Rai on 1/21/2022.
 */
raBillDetail = (function () {

    let raBillDetailGrid = $('#raBillDetailGrid');
    let raBillDetailGridTBody = $('#raBillDetailGrid tbody');

    function getGeneratedRAList() {
        spms.ajax('raBillDetail/getGeneratedRAList',
            'GET', {}
            , function (res) {

                let columnDef = [
                    {
                        data: 'index',
                        class: 'text-center',
                        render: function (index) {
                            return index + 1;
                        }
                    },
                    {data: 'boqId', class: 'boqId hidden'},
                    {data: 'voucherNo', class: 'voucherNo hidden'},
                    {data: 'raSerialNo', class: 'raSerialNo text-center'},
                    {data: 'workOrderNo', class: 'workOrderNo text-center'},
                    {data: 'raBillNo', class: 'text-center'},
                    {
                        data: 'billDate', class: 'text-center',
                        render: function (data) {
                            return formatAsDate(data);
                        }
                    },
                    {
                        data: 'action',
                        class: 'text-center',
                        render: function () {
                            return '<button type="button" id="edit" class="btn btn-info btn-sm edit"><i class="fa fa-edit"></i>Edit</button>' +
                                '<button type="button" id="delete" class="btn btn-danger btn-sm delete"><i class="fa fa-trash"></i>Delete</button>'
                        }
                    },

                ];

                let t = raBillDetailGrid.DataTable({
                    data: res,
                    columns: columnDef,
                    destroy: true,
                    info: false,
                    "aLengthMenu": [[20, 50, 75, -1], [20, 50, 75, "All"]],
                    "iDisplayLength": 20
                });
                t.on('order.dt search.dt', function () {
                    t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                        cell.innerHTML = i + 1;
                    });
                }).draw()
            });
    }

    function onClickGenerate() {
        raBillDetailGridTBody.on('click', '.edit', function () {
            let selectedRow = $(this).closest('tr');
            let boqId = selectedRow.find('.boqId').text();
            let raSerialNo = selectedRow.find('.raSerialNo').text();
            let voucherNo = selectedRow.find('.voucherNo').text();

            window.location.href = spms.getUrl() + 'raBillDetail/editRaBillDetail?boqId=' + boqId + '&raSerialNo=' + raSerialNo + '&voucherNo=' + voucherNo;
        });

        raBillDetailGridTBody.on('click', '.delete', function () {
            let selectedRow = $(this).closest('tr');
            let boqId = selectedRow.find('.boqId').text(),
                raSerialNo = selectedRow.find('.raSerialNo').text(),
                voucherNo = selectedRow.find('.voucherNo').text();
            confirmMessage("Confirm Deletion ?", function (e) {
                if (e) {
                    spms.ajax('raBillDetail/deleteRaBillDetail',
                        'GET', {boqId: boqId, raSerialNo: raSerialNo, voucherNo: voucherNo}
                        , function (res) {
                            if (res.status === 1) {
                                getGeneratedRAList();
                                successMsg(res.text);
                            } else {
                                errorMsg(res.text);
                            }
                        });
                }
            });
        });
    }

    return {
        getGeneratedRAList: getGeneratedRAList,
        onClickGenerate: onClickGenerate
        // saveraBillDetail: saveraBillDetail
    }
})();

$(document).ready(function () {
    raBillDetail.getGeneratedRAList();
    raBillDetail.onClickGenerate();
    // raBillDetail.saveraBillDetail()
});