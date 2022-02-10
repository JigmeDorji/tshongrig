/**
 * Created by Bikash Rai on 4/11/2021.
 */

statutoryRemittance = (function () {

    let btnExport = $('#btnExport');
    let remitStatutoryBtn = $('#remitStatutoryBtn');
    let statutoryRemittanceGrid = $('#statutoryRemittanceGrid');

    function init() {
        getCompanyStatutoryDetails();
        remitStatutoryDetail();
        exportReport();
    }

    function baseURL() {
        return 'statutoryRemittance'
    }

    function getCompanyStatutoryDetails() {
        $('#cost').on('change', function () {
            let monthId = $('#monthId').val();
            let cost = $(this).val();
            if (monthId === '') {
                $(this).val('');
                errorMsg("Please select month.");
                return false;
            }
            fetchStatutoryDetail(monthId, cost, $('#bankLedgerId').val())
        });
        // $('#bankLedgerId').on('change', function () {
        //     fetchStatutoryDetail($('#monthId').val(), $(this).val());
        // });
    }

    function fetchStatutoryDetail(month, cost, bankLedgerId) {
        $.ajax({
            url: baseURL() + '/getStatutoryDetails',
            type: 'GET',
            data: {month: month, cost: cost, bankLedgerId: bankLedgerId},
            success: function (res) {
                if (res.status === 0) {
                    $('.totalAmount').val('');
                    statutoryRemittanceGrid.dataTable().fnClearTable();
                    btnExport.attr('disabled', true);
                    remitStatutoryBtn.attr('disabled', true);
                    errorMsg(res.text)
                } else {

                    statutoryRemittanceGrid.dataTable().fnDestroy();
                    btnExport.attr('disabled', false);
                    remitStatutoryBtn.attr('disabled', false);

                    if (res.dto !== null) {
                        let columnDef = [
                            {
                                data: 'salarySheetId', class: 'hidden',
                                render: function (data) {
                                    return '<input name="statutoryRemittanceListDTO[0].salarySheetId" value="' + data + '">'
                                }
                            },
                            {data: 'empName'},
                            {data: 'tpnNo'},
                            {data: 'basicSalary'},
                            {data: 'allowance'},
                            {data: 'grossSalary'},
                            {data: 'pF'},
                            {data: 'gIS'},
                            {data: 'netSalary'},
                            {data: 'tDS', class: 'tDS'},
                            {data: 'hC', class: 'hC'},
                            {
                                data: 'totalAmount', class: 'totalAmount',
                                render: function (data, type, row) {
                                    return (row.tDS + row.hC).toFixed(2);
                                }
                            },
                        ];

                        let table = statutoryRemittanceGrid.DataTable({
                            data: res.dto,
                            columns: columnDef,
                            bSort:false,
                            'autoWidth':false
                        });

                        /*let totalTDS = table.column(9);
                        let totalTDSSum = 0
                        let totalHC = table.column(10);
                        let totalHCSum = 0
                        let totalAmount = table.column(11);
                        let totalAmountSum = 0
                        if (res.dto.length > 1) {
                            totalTDSSum = totalTDS.data().reduce(function (a, b) {
                                alert(a+" and "+b);
                                return '<input class="form-control text-center tDS"  readonly name="tDS" value="' + parseFloat(a + b).toFixed(2) + '">';
                            });
                            totalHCSum = totalHC.data().reduce(function (a, b) {
                                return '<input class="form-control text-center hC"  readonly name="hC" value="' + parseFloat(a + b).toFixed(2) + '">';
                            });
                            totalAmountSum = totalAmount.data().reduce(function (a, b) {
                                return '<input class="form-control text-center totalAmount"  readonly name="totalAmount" value="' + parseFloat(a + b).toFixed(2) + '">';
                            });
                        } else {
                            totalTDSSum = totalTDS.data().reduce(function (a, b) {
                                return '<input class="form-control text-center tDS"  readonly name="tDS" value="' + parseFloat(a + b) + '">';
                            }, 0);
                            totalHCSum = totalHC.data().reduce(function (a, b) {
                                return '<input class="form-control text-center hC"  readonly name="hC" value="' + parseFloat(a + b) + '">';
                            }, 0);

                            totalAmountSum = totalAmount.data().reduce(function (a, b) {
                                return '<input class="form-control text-center totalAmount"  readonly name="totalAmount" value="' + parseFloat(a + b) + '">';
                            }, 0);
                        }
                        $(totalTDS.footer()).html(totalTDSSum);
                        $(totalHC.footer()).html(totalHCSum);
                        $(totalAmount.footer()).html(totalAmountSum);*/

                        let tableBody = $('#statutoryRemittanceGrid tbody');
                        calculateTotal(tableBody);
                        spms._formIndexing(tableBody, tableBody.find('tr'));

                    } else {
                        $('.totalAmount').val('');
                        remitStatutoryBtn.attr('disabled', true);
                        btnExport.attr('disabled', true);
                        statutoryRemittanceGrid.dataTable().fnClearTable();
                    }
                    if (res.dto[0].generated) {
                        remitStatutoryBtn.attr('disabled', true);
                    }
                }
            }
        });

    }

    function calculateTotal(tableBody) {
        let totalTDS = 0;
        let totalHC = 0;
        let grandTotalAmt = 0;
        tableBody.find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            totalTDS = totalTDS + parseFloat(selectedRow.find('.tDS').text());
            totalHC = totalHC + parseFloat(selectedRow.find('.hC').text());
            grandTotalAmt = grandTotalAmt + parseFloat(selectedRow.find('.totalAmount').text());
            $('.tDS').val(totalTDS.toFixed(2));
            $('.hC').val(totalHC.toFixed(2));
            $('.totalAmount').val(grandTotalAmt.toFixed(2));
        });
    }

    function remitStatutoryDetail() {
        $('#remitStatutoryBtn').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: baseURL() + '/remitStatutoryDetail',
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
                                $('.hC').val('');
                                $('.tDS').val('');
                                $('.totalAmount').val('');
                                btnExport.attr('disabled', true);
                                remitStatutoryBtn.attr('disabled', true);
                                errorMsg(res.text)
                                errorMsg(res.text)
                            }
                            statutoryRemittanceGrid.dataTable().fnClearTable();
                            $('.tDS').val('')
                            $('.hC').val('')
                            $('.totalAmount').val('')
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
        init: init
    }
})();

$(document).ready(function () {
    $('#btnExport').attr('disabled', true);
    $('#remitBtn').attr('disabled', true);
    statutoryRemittance.init();
});