/**
 * Created by Bikash Rai on 1/13/2022.
 */
boqDetail = (function () {

    let boqDetailGrid = $('#boqDetailGrid');
    let boqDetailGridTBody = $('#boqDetailGrid tbody');

    function getGeneratedBOQList() {
        spms.ajax('boqDetail/getGeneratedBOQList',
            'GET', {}
            , function (res) {

                let columnDef = [
                    {
                        data: 'index',
                        render: function (index) {
                            return index + 1;
                        }
                    },
                    {data: 'boqId', class: 'boqId hidden'},
                    {data: 'workOrderNo', class: 'workOrderNo'},
                    {data: 'nameOfWork'},
                    {data: 'employingAgency'},
                    {
                        data: 'workOrderDate', render: function (data) {
                            return formatAsDate(data);
                        }
                    },
                    {
                        data: 'workStartDate', render: function (data) {
                            return formatAsDate(data);
                        }
                    },
                    {
                        data: 'completionDate', render: function (data) {
                            return formatAsDate(data);
                        }
                    },
                    {
                        data: 'action',
                        render: function () {
                            return '<button type="button" id="edit" class="btn btn-info btn-xs edit"><i class="fa fa-edit"></i>Edit</button>';
                        }
                    },

                ];

                let t = boqDetailGrid.DataTable({
                    data: res,
                    columns: columnDef,
                    destroy: true,
                    info: false,
                    paging: false,
                    sorting: false,
                    searching: false
                });
                t.on('order.dt search.dt', function () {
                    t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                        cell.innerHTML = i + 1;
                    });
                }).draw()
            });
    }

    function onClickGenerate() {
        boqDetailGridTBody.on('click', '.btnGenerate', function () {
            let selectedRow = $(this).closest('tr');
            let boqId = selectedRow.find('.boqId').text();
            window.location.href = spms.getUrl() + 'boqDetail/generateRABill?boqId=' + boqId;
        });
        boqDetailGridTBody.on('click', '.edit', function () {
            let selectedRow = $(this).closest('tr');
            let boqId = selectedRow.find('.boqId').text();
            window.location.href = spms.getUrl() + 'boqDetail/editBoqDetail?boqId=' + boqId;
        });

        boqDetailGridTBody.on('click', '.delete', function () {
            let selectedRow = $(this).closest('tr');
            let boqId = selectedRow.find('.boqId').text();
            confirmMessage("Confirm Deletion ?", function (e) {
                if (e) {
                    spms.ajax('boqDetail/delete',
                        'GET', {boqId: boqId}
                        , function (res) {
                            if (res.status === 1) {
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
        getGeneratedBOQList: getGeneratedBOQList,
        onClickGenerate: onClickGenerate
        // saveBOQDetail: saveBOQDetail
    }
})();

$(document).ready(function () {
    boqDetail.getGeneratedBOQList();
    boqDetail.onClickGenerate();
    // boqDetail.saveBOQDetail()
});