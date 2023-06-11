/**
 * Created by Bikash Rai on 4/7/2021.
 */
salaryRemittance = (function () {

    let employeeSalarySheetList = $('#employeeSalarySheetList');

    function init() {
        getEmployeeSalarySheet();
        remitSalary();
        exportReport();
    }

    function baseURL() {
        return 'salaryRemittance'
    }

    function getEmployeeSalarySheet() {
        $('#cost').on('change', function () {
            let monthId = $('#monthId').val();
            let cost = $(this).val();
            if (monthId === '') {
                $(this).val('');
                // errorMsg("Please select month.");
                return false;
            }
            fetchEmployeeDetail(monthId, $('#bankLedgerId').val(), cost)
        });
        // $('#bankLedgerId').on('change', function () {
        //     let monthId = $('#monthId').val();
        //     let cost = $(this).val();
        //     fetchEmployeeDetail(monthId, $('#bankLedgerId').val(), cost)
        // });
    }

    function fetchEmployeeDetail(month, ledgerId, cost) {
        $('#employeeSalarySheetList').dataTable().fnDestroy();
        $.ajax({
            url: baseURL() + '/getEmployeeSalarySheet',
            type: 'GET',
            data: {month: month, bankLedgerId: ledgerId, cost: cost},
            success: function (res) {

                let btnExport = $('#btnExport');
                let remitBtn = $('#remitBtn');

                if (res.status === 0) {
                    employeeSalarySheetList.dataTable().fnClearTable();
                    $('.totalAmount').val('');
                    btnExport.attr('disabled', true);
                    remitBtn.attr('disabled', true);
                    errorMsg(res.text)
                } else {
                    btnExport.attr('disabled', false);
                    remitBtn.attr('disabled', false);
                    if (res.dto !== null) {

                        let columnDef = [
                            {
                                "mRender": function (data, type, row, meta) {
                                    return meta.row + 1;
                                }
                            },
                            {
                                data: 'salarySheetId', class: 'hidden',
                                render: function (data) {
                                    return '<input name="salaryRemittanceListDTOList[0].salarySheetId" value="' + data + '">'
                                }
                            },
                            {data: 'empName'},
                            {data: 'accNo'},
                            {
                                data: 'takeHome', class: 'takeHome text-right',
                                render: function (data) {
                                    return data;
                                }
                            }
                        ];

                        let table = employeeSalarySheetList.DataTable({
                            data: res.dto,
                            columns: columnDef,
                            bSort: false,
                            bInfo: false,
                            'paging': false,
                            'searching': false,
                            columnDefs: [{
                                "defaultContent": "-",
                                "targets": "_all"
                            }]
                        });

                        table.on('order.dt search.dt', function () {
                            table.column(0, {search: 'applied', order: 'applied'}).nodes()
                                .each(function (cell, i) {
                                    cell.innerHTML = i + 1;
                                });

                        }).draw();

                        /*
                                                let total = table.column(4);
                                                let totalSum = 0;
                                                if (res.dto.length > 1) {
                                                    totalSum = total.data().reduce(function (a, b) {
                                                        return '<input class="form-control text-center total"  readonly name="totalAmount" value="' + parseFloat((a + b).toFixed(2)) + '">';
                                                    })
                                                } else {
                                                    totalSum = total.data().reduce(function (a, b) {
                                                        return '<input class="form-control text-center total"  readonly name="totalAmount" value="' + parseFloat((a + b).toFixed(2)) + '">';
                                                    }, 0)
                                                }
                                                $(total.footer()).html(totalSum);*/

                        let tableBody = $('#employeeSalarySheetList tbody');
                        calculateTotal(tableBody);
                        spms._formIndexing(tableBody, tableBody.find('tr'));

                    } else {
                        $('.totalAmount').val('');
                        remitBtn.attr('disabled', true);
                        btnExport.attr('disabled', true);
                        employeeSalarySheetList.dataTable().fnClearTable();
                    }
                    if (res.dto[0].generated) {
                        remitBtn.attr('disabled', true);
                    }
                }
            }
        })
    }

    function calculateTotal(tableBody) {
        let totalTakeHome = 0;
        tableBody.find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            totalTakeHome = totalTakeHome + parseFloat(selectedRow.find('.takeHome').text());
            $('.totalAmount').val(totalTakeHome.toFixed(2));
        });
    }

    function remitSalary() {
        perClickSubmissionValidatorHandler("#remitBtn",'.globalForm',baseURL() + '/remitSalary',(res)=> {
            if (res.status === 1) {
                swal({
                    timer: 800,
                    type: "success",
                    title: res.text,
                    showConfirmButton: false
                });
                $('#monthId').val('');
                $('#bankLedgerId').val('');
                $('.totalAmount').val('');
                $('#remitBtn').attr('disabled', true);
                employeeSalarySheetList.dataTable().fnClearTable();
            } else {
                errorMsg(res.text)
            }
        })
        // $('#remitBtn').on('click', function () {
        //     $('.globalForm').validate({
        //         submitHandler: function (form) {
        //             $.ajax({
        //                 url: baseURL() + '/remitSalary',
        //                 type: 'POST',
        //                 data: $(form).serializeArray(),
        //                 success: function (res) {
        //                     if (res.status === 1) {
        //                         swal({
        //                             timer: 800,
        //                             type: "success",
        //                             title: res.text,
        //                             showConfirmButton: false
        //                         });
        //                         $('#monthId').val('');
        //                         $('#bankLedgerId').val('');
        //                         $('.totalAmount').val('');
        //                         $('#remitBtn').attr('disabled', true);
        //                         employeeSalarySheetList.dataTable().fnClearTable();
        //                     } else {
        //                         errorMsg(res.text)
        //                     }
        //                 }
        //             });
        //         }
        //     });
        // })
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
    salaryRemittance.init();
});