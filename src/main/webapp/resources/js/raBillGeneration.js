/**
 * Created by Bikash Rai on 1/13/2022.
 */
raBillGeneration = (function () {

    let boqDataListGrid = $('#boqDataListGrid');
    let boqDataListGridTBody = $('#boqDataListGrid tbody');

    function onChangeWorkOrderNo() {
        $('#workOrderNo').on('change', function () {
            loadBOQListDetail($(this).val());
            loadRaSerialNo($(this).val());
        });
    }

    function loadRaSerialNo(boqId) {
        if (boqId !== '' && $('#raSerialNo').val() === '') {
            spms.ajax('raBillGeneration/getRaSerialNo',
                'GET', {
                    boqId: boqId
                }, function (res) {
                    $('#serialNo').val(res)
                })
        }
    }

    function getBOQListById() {
        let boqId = $('#boqId');
        // if (boqId.val() !== '') {
        //     window.localStorage.clear();
        //     window.localStorage.setItem('boqId', boqId.val());
        //     $("#workOrderNo").select2().select2('val', boqId.val());
        //     loadRaSerialNo(boqId.val())
        // } else {
        //     $("#workOrderNo").select2().select2('val', window.localStorage.getItem("boqId"));
        //     loadRaSerialNo(window.localStorage.getItem("boqId"))
        // }
        $("#workOrderNo").select2().select2('val', boqId.val());
        // loadBOQListDetail(boqId.val());
        // loadRaSerialNo(boqId.val());
    }

    function loadBOQListDetail(boqId) {
        if (boqId !== '' && $('#raSerialNo').val() === '') {
            spms.ajax('raBillGeneration/getBOQList',
                'GET', {boqId: boqId}
                , function (res) {
                    displayInfo(res);
                });
        }
    }

    function displayInfo(res) {
        let columnDef = [
            {
                data: 'index',
                render: function (index) {
                    return index + 1;
                }
            },
            {
                data: 'boqDetailId', class: 'hidden',
                render: function (data) {
                    return '<input class="form-control text-center" name="boqDetailsListDTO[0].boqDetailId" id="boqDetailId" value="' + data + '" >'
                }
            }, {
                data: 'raBillDetailId', class: 'hidden',
                render: function (data) {
                    data = data == null ? '' : data;
                    return '<input class="form-control text-center" name="boqDetailsListDTO[0].raBillDetailId" id="raBillDetailId" value="' + data + '" >'
                }
            },
            {data: 'code', class: 'code'},
            {data: 'description', class: 'description'},
            {data: 'unitOfMeasurement'},
            {
                data: 'rate', class: 'rate',
                render: function (data) {
                    return data.toFixed(3)
                }
            },
            {
                data: 'qty', class: 'qty',
                render: function (data) {
                    data = data == null ? '' : data;
                    return '<input class="form-control text-center numeric qty-container" name="boqDetailsListDTO[0].qty" id="qty" value="' + data + '"/>'
                }
            },
            {
                data: 'amount', class: 'amount',
                render: function (index, type, row) {
                    if (row.qty !== null) {
                        return (row.rate * row.qty);
                    } else {
                        return '';
                    }
                }
            }
        ];

        let t = boqDataListGrid.DataTable({
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
        }).draw();

        let tableBody = boqDataListGridTBody;
        spms._formIndexing(tableBody, tableBody.find('tr'));
        spms.autoSizeInputField(boqDataListGridTBody);
    }

    function getBOQListForUpdate() {
        let raSerialNo = $('#raSerialNo'), boqId = $('#boqId');

        if (raSerialNo.val() !== '' && boqId.val() !== '') {
            spms.ajax('raBillGeneration/getBOQListForUpdate',
                'GET', {boqId: boqId.val(), raSerialNo: raSerialNo.val()}
                , function (res) {
                    displayInfo(res.boqDetailsListDTO);
                    $("#raBillNo").val(res.raBillNo);
                    $("#raBillId").val(res.raBillId);
                    $("#billDate").val(formatAsDate(res.billDate));
                    $("#serialNo").val(raSerialNo.val());
                    $("#workOrderNo").select2().select2('val', boqId.val());
                    $("#workOrderNo").attr('disabled', true);
                    calculateTotalBillAmount();
                })
        }
    }

    function saveBOQDetail() {
        $('.raBillForm').validate({
            submitHandler: function (form) {
                $("#workOrderNo").attr('disabled', false);
                $.ajax({
                    url: 'raBillGeneration/saveRABillDetail',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            window.location.reload();
                            window.location = spms.getUrl() + 'raBillGeneration/generateRABill?boqId=' + $('#workOrderNo').val() + '&raSerialNo=' + $("#serialNo").val();
                        }
                    }
                })
            }
        })

    }

    function onQtyEntry() {
        boqDataListGridTBody.on('keyup', '.qty', function () {
            let selectedRow = $(this).closest('tr');
            let qty = selectedRow.find('#qty').val(),
                rate = parseFloat(selectedRow.find('.rate').text());
            selectedRow.find('.amount').text(qty * rate);
            calculateTotalBillAmount();
        })
    }

    function calculateTotalBillAmount() {
        let totalAmount = 0;
        let totalBillAmount = $('.totalBillAmount')
        boqDataListGridTBody.find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            let amount = selectedRow.find('.amount').text();
            if (amount !== '') {
                totalAmount = totalAmount + parseFloat(selectedRow.find('.amount').text());
            }
        })
        totalBillAmount.text(totalAmount)
        totalBillAmount.val(totalAmount)
    }

    return {
        saveBOQDetail: saveBOQDetail,
        onChangeWorkOrderNo: onChangeWorkOrderNo,
        onQtyEntry: onQtyEntry,
        getBOQListForUpdate: getBOQListForUpdate
    }
})
();

$(document).ready(function () {
    $("#raBillNo").val('');
    $("#raBillId").val(null);
    raBillGeneration.saveBOQDetail()
    raBillGeneration.onChangeWorkOrderNo()
    raBillGeneration.onQtyEntry()
    raBillGeneration.getBOQListForUpdate()
});