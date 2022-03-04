/**
 * Created by Bikash Rai on 1/6/2022.
 */

boqSetup = (function () {

    function onFileChangeEvent() {

        $("#fileXlsId").on('change', function () {
            let $this = $(this);
            if (window.FormData !== undefined) {

                let formDate = new FormData();
                formDate.append("excelMultipartFile", $('input[type=file]')[0].files[0]);

                spms.ajax_with_attachment('boqSetup/importExcelFile',
                    'POST', formDate
                    , function (res) {
                        if (res.status === 1) {
                            loadBoq(res.dto);
                        } else {
                            $this.val('');
                            errorMsg(res.text);
                        }
                    }
                );
            }
        });
    }

    function loadBoq(res) {
        let columnDef = [
            {
                data: 'index',
                class: 'text-center',
                render: function (index) {
                    return index + 1;
                }
            },
            {data: 'code', class: 'text-center bvsCode'},
            {data: 'description', class: 'text-center'},
            {data: 'unitOfMeasurement', class: 'text-center'},
            {
                data: 'qty',
                class: 'text-center',
                render: function (data) {
                    return data.toFixed(3)
                }
            },
            {
                data: 'rate',
                class: 'text-center',
                render: function (data) {
                    return data.toFixed(3)
                }
            },
            {data: 'rateInWords', class: 'text-center'},
            {
                data: 'amount', class: 'text-center', render: function (index, type, row) {
                    if (row.amount == null) {
                        return (row.qty * row.rate).toFixed(3);
                    } else {
                        return (row.amount).toFixed(3);
                    }
                }
            },
            {data: 'totalAmountInWords', class: 'text-center'}
        ];

        let t = $('#boqDataListGrid').DataTable({
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
    }

    function saveBOQDetail() {
        $("#btnSave").on('click', function () {
            let form = $('.xlsFileForm');
            spms.isFormValid(form);
            if (form.valid()) {
                spms.ajax_with_attachment('boqSetup/saveBOQDetail',
                    'POST', new FormData(form[0])
                    , function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            window.location.reload();
                        } else {
                            errorMsg("Could not save the detail.")
                        }
                    });
            }
        });
    }

    function getDetailByBOQId() {
        if ($('#boqId').val() !== '') {
            spms.ajax('boqSetup/getDetailByBOQId',
                'GET', {boqId: $('#boqId').val()}
                , function (res) {

                    populate(res);
                    $('#fileXlsId').attr('disabled', true);
                    $('#workOrderDate').val(formatAsDate(res.workOrderDate))
                    $('#completionDate').val(formatAsDate(res.completionDate))
                    $('#workStartDate').val(formatAsDate(res.workStartDate))

                    spms.ajax('boqSetup/getBOQList',
                        'GET', {boqId: $('#boqId').val()}
                        , function (res) {
                            loadBoq(res);
                        });
                });
        } else {
            $('#fileXlsId').attr('disabled', false);
        }
    }

    return {
        onFileChangeEvent: onFileChangeEvent,
        saveBOQDetail: saveBOQDetail,
        getDetailByBOQId: getDetailByBOQId
    }
})();

$(document).ready(function () {
    boqSetup.onFileChangeEvent()
    boqSetup.saveBOQDetail()
    boqSetup.getDetailByBOQId()
});