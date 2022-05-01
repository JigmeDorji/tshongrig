otherRemittance = (function () {

    let btnExport = $('#btnExport');
    let otherRemitBtn = $('#otherRemitBtn');
    let otherRemittanceGrid = $('#otherRemittanceGrid');

    function baseURL() {
        return 'otherRemittance'
    }

    function getOtherRemittanceDetails() {
        $('#cost').on('change', function () {
            if($(this).val()!==null && $(this).val()!==''){
                let monthId = $('#monthId').val();
                let cost = $(this).val();
                if (monthId === '') {
                    $(this).val('');
                    errorMsg("Please select month.");
                    return false;
                }
                fetchOtherRemittanceDetail(monthId, cost, $('#bankLedgerId').val())
            }

        });
        // $('#bankLedgerId').on('change', function () {
        //     fetchStatutoryDetail($('#monthId').val(), $(this).val());
        // });
    }

    function fetchOtherRemittanceDetail(month, cost, bankLedgerId) {
        $.ajax({
            url: baseURL() + '/getOtherRemittanceDetails',
            type: 'GET',
            data: {month: month, cost: cost, bankLedgerId: bankLedgerId},
            success: function (res) {
                if (res.status === 0) {
                    $('.totalAmount').val('');
                    otherRemittanceGrid.dataTable().fnClearTable();
                    btnExport.attr('disabled', true);
                    otherRemitBtn.attr('disabled', true);
                    errorMsg(res.text)
                } else {
                    otherRemittanceGrid.dataTable().fnDestroy();
                    btnExport.attr('disabled', false);
                    otherRemitBtn.attr('disabled', false);

                    if (res.dto !== null) {
                        let columnDef = [
                            {
                                data: 'salarySheetId', class: 'hidden',
                                render: function (data) {
                                    return '<input name="statutoryRemittanceListDTO[0].salarySheetId" value="' + data + '">'
                                }
                            },
                            {data: 'empName'},
                            {data: 'basicSalary'},
                            {data: 'pF',class: 'pF'},
                            {data: 'gIS',class: 'gIS'},
                            {data: 'totalAmount',class: 'totalAmount'},
                        ];

                        let table = otherRemittanceGrid.DataTable({
                            data: res.dto,
                            columns: columnDef
                        });

                       /* let totalPF = table.column(3);
                        let totalPFSum = 0
                        let totalGIS = table.column(4);
                        let totalGISSum = 0
                        let totalAmount = table.column(5);
                        let totalAmountSum = 0
                        if (res.dto.length > 1) {
                            totalPFSum = totalPF.data().reduce(function (a, b) {
                                return '<input class="form-control text-center pF"  readonly name="pF" value="' + parseFloat(a + b) + '">';
                            });
                            totalGISSum = totalGIS.data().reduce(function (a, b) {
                                return '<input class="form-control text-center gIS" readonly name="gIS" value="' + parseFloat(a + b) + '">';
                            });
                            totalAmountSum = totalAmount.data().reduce(function (a, b) {
                                return '<input class="form-control text-center totalAmount"  readonly name="totalAmount" value="' + parseFloat(a + b) + '">';
                            });
                        } else {
                            totalPFSum = totalPF.data().reduce(function (a, b) {
                                return '<input class="form-control text-center pF"  readonly name="pF" value="' + parseFloat(a + b) + '">';
                            }, 0);
                            totalGISSum = totalGIS.data().reduce(function (a, b) {
                                return '<input class="form-control text-center gIS"  readonly name="gIS" value="' + parseFloat(a + b) + '">';
                            }, 0);

                            totalAmountSum = totalAmount.data().reduce(function (a, b) {
                                return '<input class="form-control text-center totalAmount"  readonly name="totalAmount" value="' + parseFloat(a + b) + '">';
                            }, 0);
                        }
                        $(totalPF.footer()).html(totalPFSum);
                        $(totalGIS.footer()).html(totalGISSum);
                        $(totalAmount.footer()).html(totalAmountSum);
*/
                        let tableBody = $('#otherRemittanceGrid tbody');
                        calculateTotal(tableBody);
                        spms._formIndexing(tableBody, tableBody.find('tr'));

                    } else {
                        $('.totalAmount').val('');
                        otherRemitBtn.attr('disabled', true);
                        btnExport.attr('disabled', true);
                        otherRemittanceGrid.dataTable().fnClearTable();
                    }
                    if (res.dto[0].generated) {
                        otherRemitBtn.attr('disabled', true);
                    }
                }
            }
        });

    }

    function calculateTotal(tableBody) {
        let totalPF = 0;
        let totalGIS = 0;
        let grandAmt = 0;
        tableBody.find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            totalPF = totalPF + parseFloat(selectedRow.find('.pF').text());
            totalGIS = totalGIS + parseFloat(selectedRow.find('.gIS').text());
            grandAmt = grandAmt + parseFloat(selectedRow.find('.totalAmount').text());
            $('.totalPF').val(totalPF.toFixed(2));
            $('.totalGIS').val(totalGIS.toFixed(2));
            $('.grandAmt').val(grandAmt.toFixed(2));
        });
    }

    function otherRemitDetail() {
        $('#otherRemitBtn').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: baseURL() + '/remitOtherRemittance',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    timer: 800,
                                    type: "success",
                                    title: res.text,
                                    showConfirmButton: false
                                });
                                $('#monthId').val('');
                                $('.totalAmount').val('');
                                $('#remitBtn').attr('disabled', true);
                            } else {
                                $('.pF').val('');
                                $('.gIS').val('');
                                $('.totalAmount').val('');
                                btnExport.attr('disabled', true);
                                otherRemitBtn.attr('disabled', true);
                                errorMsg(res.text)
                                errorMsg(res.text)
                            }
                            otherRemittanceGrid.dataTable().fnClearTable();
                            $('.totalPF').val('')
                            $('.totalGIS').val('')
                            $('.grandAmt').val('')
                        }
                    });
                }
            });
        })
    }

    function exportReport() {
        $('#generateReportBtn').on('click', function () {
            $.ajax({
                url: baseURL() + '/generateReport',
                type: 'GET',
                data: {
                    reportFormat: $("#xls").prop("checked") ? $("#xls").val() : ($("#pdf").prop("checked") ? $("#pdf").val() : $("#docx").val()),
                    month: $('#monthId').val(),
                    monthText: $("#monthId option:selected").text(),
                    cost: $('#cost').val()
                },
                async: false,
                success: function (res) {
                    if (res.status === 1) {
                        window.open(spms.baseReportLocation() + res.dto.reportName, '_blank');
                    }
                }
            });
        })
    }

    return {
        getOtherRemittanceDetails: getOtherRemittanceDetails,
        otherRemitDetail: otherRemitDetail,
        exportReport: exportReport
    }
})();

$(document).ready(function () {
    $('#btnExport').attr('disabled', true);
    $('#remitBtn').attr('disabled', true);
    otherRemittance.getOtherRemittanceDetails();
    otherRemittance.otherRemitDetail();
    otherRemittance.exportReport();
});