/**
 * Created by jigme.dorji on 4/7/2021.
 */
salarySheet = (function () {


    function _baseURL() {
        return 'salarySheet/'
    }


    // function getEmpList(selectedMonthId) {
    //     $.ajax({
    //         url: 'salarySheet/getEmpListDetails',
    //         type: 'GET',
    //         data:{selectedMonthId:selectedMonthId},
    //         success: function (res) {
    //             let columnDef = [
    //                 {
    //                     data: 'empId', class: 'hidden',
    //                     render: function (data) {
    //                         return '<input type="text" name="employeeSetupDTOS[0].empId"  class="form-control" value="' + data + '">'
    //                     }
    //                 },
    //                 {data: 'empName'},
    //                 {
    //                     data: 'empName', class: 'hidden',
    //                     render: function (data) {
    //                         return '<input type="text" name="employeeSetupDTOS[0].empName"  class="form-control" value="' + data + '">'
    //                     }
    //                 },
    //                 {data: 'tpnNo'},
    //                 {
    //                     data: 'basicSalary',
    //                     render: function (data) {
    //                         return '<input type="text"   class="form-control basicSalary amount" readonly value="' + data + '">';
    //                     }
    //                 },
    //                 {
    //                     data: 'allowance',
    //                     render: function (data) {
    //                         data === '' ? 0 : data;
    //                         return '<input type="text"   class="form-control allowance amount" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'deduction',
    //                     render: function (data) {
    //                         data === '' ? 0 : data;
    //                         return '<input type="text" name="employeeSetupDTOS[0].deduction"  class="form-control deduction" value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'grossSalary',
    //                     render: function (data) {
    //                         return '<input type="text" class="form-control grossSalary" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'pF',
    //                     render: function (data) {
    //                         return '<input type="text" class="form-control pF" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'gIS',
    //                     render: function (data) {
    //                         return '<input type="text" class="form-control gIS" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'netSalary',
    //                     render: function (data) {
    //                         return '<input type="text"  name="employeeSetupDTOS[0].netSalary" class="form-control netSalary" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'tDS',
    //                     render: function (data) {
    //                         return '<input type="text" name="employeeSetupDTOS[0].tDS" id="tDS" class="form-control tDS" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'hC',
    //                     render: function (data) {
    //                         return '<input type="text" name="employeeSetupDTOS[0].hC" class="form-control hC" readonly value="' + data + '">'
    //                     }
    //                 },
    //                 {
    //                     data: 'advance',
    //                     render: function (data) {
    //                         data === '' ? 0 : data;
    //                         return '<input type="text" name="employeeSetupDTOS[0].advance"  class="form-control advance" value="' + data + '">'
    //                     }
    //                 }, {
    //                     data: 'totalRecovery',
    //                     render: function (data, type, row) {
    //                         let totalRecover = row.hC + row.tDS + row.advance
    //                         return '<input type="text" name="employeeSetupDTOS[0].totalRecovery" class="form-control totalRecovery" readonly value="' + totalRecover + '">'
    //                     }
    //                 }, {
    //                     data: 'takeHome',
    //                     render: function (data, type, row) {
    //                         let takeHome = row.netSalary - (row.hC + row.tDS + row.advance)
    //                         return '<input type="text" name="employeeSetupDTOS[0].takeHome"  readonly class="form-control takeHome" value="' + takeHome + '">'
    //                     }
    //                 },
    //             ];
    //             $('#salarySheetTable').DataTable({
    //                 data: res,
    //                 columns: columnDef,
    //             });
    //             spms._formIndexing($('#salarySheetTable tbody'), $('#salarySheetTable tbody').find('tr'))
    //             calculateTotal();
    //         }
    //     });
    // }

    function calculateTotal() {
        let totalBasicSalary = 0;
        let totalAllowance = 0;
        let totalDeduction = 0;
        let totalGrossSalary = 0;
        let totalPF = 0;
        let totalGIS = 0;
        let totalNetSalary = 0;
        let totalTDS = 0;
        let totalHC = 0;
        let totalAdvance = 0;
        let totalTotalRecovery = 0;
        let totalTakeHome = 0;
        $('#salarySheetTable tbody').find('tr').each(function () {
            let selectedRow = $(this).closest('tr');
            totalBasicSalary = totalBasicSalary + parseFloat(selectedRow.find('.basicSalary').val());
            totalAllowance = totalAllowance + parseFloat(selectedRow.find('.allowance').val());
            let currentDeduction = isNaN(parseFloat(selectedRow.find('.deduction').val())) ? parseFloat(0) : parseFloat(selectedRow.find('.deduction').val());
            totalDeduction = parseFloat(totalDeduction) + parseFloat(currentDeduction);
            totalGrossSalary = totalGrossSalary + parseFloat(selectedRow.find('.grossSalary').val());
            totalPF = totalPF + parseFloat(selectedRow.find('.pF').val());
            totalGIS = totalGIS + parseFloat(selectedRow.find('.gIS').val());
            totalNetSalary = totalNetSalary + parseFloat(selectedRow.find('.netSalary').val());
            totalTDS = totalTDS + parseFloat(selectedRow.find('.tDS').val());
            totalHC = totalHC + parseFloat(selectedRow.find('.hC').val());
            let currentAdvance = isNaN(parseFloat(selectedRow.find('.advance').val())) ? parseFloat(0) : parseFloat(selectedRow.find('.advance').val());
            totalAdvance = totalAdvance + currentAdvance;
            totalTotalRecovery = totalTotalRecovery + parseFloat(selectedRow.find('.totalRecovery').val());
            totalTakeHome = totalTakeHome + parseFloat(selectedRow.find('.takeHome').val());
            $('#totalBasicSalary').val(totalBasicSalary.toFixed(2));
            $('#totalAllowance').val(totalAllowance.toFixed(2));
            $('#totalDeduction').val(totalDeduction.toFixed(2));
            $('#totalGrossSalary').val(totalGrossSalary.toFixed(2));
            $('#totalPF').val(totalPF.toFixed(2));
            $('#totalGIS').val(totalGIS.toFixed(2));
            $('#totalNetSalary').val(totalNetSalary.toFixed(2));
            $('#totalHC').val(totalHC.toFixed(2));
            $('#totalAdvance').val(totalAdvance.toFixed(2));
            $('#totalTotalRecovery').val(totalTotalRecovery.toFixed(2));
            $('#totalTakeHome').val(totalTakeHome.toFixed(2));
            $('#totalTDS').val(totalTDS.toFixed(2));
        });
    }

    function onSelectMonth() {
        $('#cost').on('change', function () {
            if ($('#monthId').val() !== '' && $('#cost').val() !== '') {
                _getSalarySheetDetail();
            }

        })

        $('#monthId').on('change', function () {
            if ($('#monthId').val() !== '' && $('#cost').val() !== '') {
                _getSalarySheetDetail();
            }

        })
    }

    function _getSalarySheetDetail() {
        let selectedMonthId = $('#monthId').val();
        let cost = $('#cost').val();
        $('#salarySheetTable').dataTable().fnDestroy();
        $.ajax({
            url: 'salarySheet/getEmpListDetails',
            type: 'GET',
            data: {selectedMonthId: selectedMonthId, cost: cost},
            async: false,
            success: function (res) {
                if (res.length !== 0) {
                    let columnDef = [
                        {
                            data: 'empId', class: 'hidden',
                            render: function (data) {
                                return '<input type="text" name="employeeSetupDTOS[0].empId"  class="form-control" value="' + data + '">'
                            }
                        },
                        {data: 'empName', width: "15%"},
                        {
                            data: 'empName', class: 'hidden',
                            render: function (data) {
                                return '<input type="text" name="employeeSetupDTOS[0].empName"  class="form-control" value="' + data + '">'
                            }
                        },
                        {data: 'tpnNo', class: 'hidden'},
                        {
                            data: 'basicSalary',
                            render: function (data) {
                                return '<input type="text"   class="form-control basicSalary amount" readonly value="' + data + '">';
                            }
                        },
                        {
                            data: 'allowance',
                            render: function (data) {
                                data === '' ? 0 : data;
                                return '<input type="text"   class="form-control allowance amount" readonly value="' + data + '">'
                            }
                        }, {
                            data: 'absentDays', width: "3%",
                            render: function (data) {
                                let noOfAbsentDays = data === '' || data === null ? 0 : data;
                                return '<input type="text"   class="form-control absentDays numeric" readonly value="' + noOfAbsentDays + '">'
                            }
                        },
                        {
                            data: 'deduction',
                            render: function (data) {
                                data === '' ? 0 : data;
                                return '<input type="text" name="employeeSetupDTOS[0].deduction"  class="form-control deduction" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'grossSalary',
                            render: function (data) {
                                return '<input type="text" name="employeeSetupDTOS[0].grossSalary" class="form-control grossSalary" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'pF', width: "7%",
                            render: function (data) {
                                return '<input type="text" class="form-control pF" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'gIS', width: "7%",
                            render: function (data) {
                                return '<input type="text" class="form-control gIS" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'netSalary',
                            render: function (data) {
                                return '<input type="text"  name="employeeSetupDTOS[0].netSalary" class="form-control netSalary" readonly value="' + data.toFixed(2) + '">'
                            }
                        },
                        {
                            data: 'tDS',
                            render: function (data) {
                                return '<input type="text" name="employeeSetupDTOS[0].tDS" id="tDS" class="form-control tDS" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'hC', width: "7%",
                            render: function (data) {
                                return '<input type="text" name="employeeSetupDTOS[0].hC" class="form-control hC" readonly value="' + data + '">'
                            }
                        },
                        {
                            data: 'advance', width: "5%",
                            render: function (data, type, row) {
                                data === '' ? 0 : data;
                                if (row.balanceAdvance > 0) {
                                    return '<input type="text" name="employeeSetupDTOS[0].advance"  class="form-control advance" value="' + data + '">'
                                } else {
                                    return '<input type="text" name="employeeSetupDTOS[0].advance"  readonly class="form-control advance" value="' + data + '">'
                                }
                            }
                        },
                        {
                            data: 'totalRecovery', class: 'hidden',
                            render: function (data, type, row) {
                                let totalRecover = row.hC + row.tDS + row.advance
                                return '<input type="text" name="employeeSetupDTOS[0].totalRecovery" class="form-control totalRecovery" readonly value="' + totalRecover + '">'
                            }
                        },
                        {
                            data: 'takeHome',
                            render: function (data, type, row) {
                                let takeHome = row.takeHome
                                return '<input type="text" name="employeeSetupDTOS[0].takeHome"  readonly class="form-control takeHome" value="' + takeHome.toFixed(2) + '">'
                            }
                        }, {
                            data: 'balanceAdvance', class: 'hidden',
                            render: function (data) {
                                return '<input type="text"   readonly class="form-control balanceAdvance" value="' + data + '">'
                            }
                        }
                    ];
                    $('#salarySheetTable').DataTable({
                        data: res,
                        ordering: false,
                        columns: columnDef,
                        "bAutoWidth": false,
                    });
                    spms._formIndexing($('#salarySheetTable tbody'), $('#salarySheetTable tbody').find('tr'))
                    $('#salarySheetTable').css('width', '100%');
                    calculateTotal();
                } else {
                    errorMsg("Data not found.")
                    // $('#btnSalarySheet').addClass('hidden');
                    // $('#salarySheetDetail').addClass('hidden');
                    // $('#salarySheetTable tbody tr').find('.advance').attr('readOnly', false);
                    // $('#salarySheetTable tbody tr').find('.deduction').attr('readOnly', false);
                    // $('#salarySheetTable tbody tr').find('.absentDays').attr('readOnly', false);
                    $('#salarySheetDetail').attr('hidden', true);
                }


            }
        });

        $.ajax({
            url: 'salarySheet/getMaxMonthId',
            type: 'GET',
            data: {cost: cost},
            success: function (res) {
                if (res !== 0) {
                    if (selectedMonthId >= parseInt(res + 2)) {
                        errorMsg("Please generate salary sheet for the previous month.")
                        $('#btnSalarySheet').addClass('hidden');
                        $('#salarySheetDetail').addClass('hidden');
                        $('#salarySheetTable tbody tr').find('.advance').attr('readOnly', false);
                        $('#salarySheetTable tbody tr').find('.deduction').attr('readOnly', false);
                        $('#salarySheetTable tbody tr').find('.absentDays').attr('readOnly', false);
                    } else if (selectedMonthId <= res) {
                        $('#salarySheetDetail').removeClass('hidden');
                        $('#btnSalarySheet').addClass('hidden');
                        $('#salarySheetTable tbody').find('.absentDays ').attr('readOnly', true);
                        $('#salarySheetTable tbody tr').find('.advance').attr('readOnly', true);
                        $('#salarySheetTable tbody tr').find('.deduction').attr('readOnly', true);
                    } else {
                        $('#btnSalarySheet').removeClass('hidden');
                        $('#salarySheetDetail').removeClass('hidden');
                        $('#salarySheetTable tbody tr').find('.absentDays').attr('readOnly', false);
                        $('#salarySheetTable tbody tr').find('.advance').attr('readOnly', false);
                        $('#salarySheetTable tbody tr').find('.deduction').attr('readOnly', false);
                    }
                } else {
                    $('#btnSalarySheet').removeClass('hidden');
                    $('#salarySheetDetail').removeClass('hidden');
                    // $('#salarySheetTable tbody tr').find('.advance').attr('readOnly', false);
                    $('#salarySheetTable tbody tr').find('.deduction').attr('readOnly', false);
                    $('#salarySheetTable tbody tr').find('.absentDays').attr('readOnly', false);
                }

            }
        });
    }

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $('#btnSalarySheet').prop('disabled', true)
                $.ajax({
                    url: 'salarySheet/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                }
                            )

                            $('#btnSalarySheet').addClass('hidden');
                        } else {
                            errorMsg(res.text);
                        }
                    },
                    complete:()=>{
                        $('#btnSalarySheet').prop('disabled', false)
                    }
                });
            }
        });
    }

    function _getTDSAmount(netSalary, selectedRow) {
        $.ajax({
            url: 'salarySheet/getTDSAmount',
            type: 'get',
            data: {netSalary: netSalary},
            async: false,
            success: function (res) {
                selectedRow.find('#tDS').val(res);
                return res;
            }
        });
    }

    function exportReport() {

        $('#generateReportBtn').on('click', function () {

            $('#generateReportBtn').prop('disabled', true)
            $.ajax({
                url: _baseURL() + 'generateReport',
                type: 'GET',
                data: {
                    reportFormat: $("#xls").prop("checked") ? $("#xls").val() : ($("#pdf").prop("checked") ? $("#pdf").val() : $("#docx").val()),
                    month: $('#monthId').val(),
                    monthText: $("#monthId option:selected").text(),
                    cost: $("#cost").val()
                },
                async: false,
                success: function (res) {
                    if (res.status === 1) {
                        window.open(spms.baseReportLocation() + res.dto.reportName, '_blank');
                    }
                },
                complete:()=>{
                    $('#generateReportBtn').prop('disabled', false)
                }
            });
        })
    }

    function daysInMonth(month, year) {
        return new Date(year, month, 0).getDate();
    }

    function onChangeDeduction() {
        $('#salarySheetTable').find('tbody').on('keyup', 'tr .absentDays', function () {

            let selectedRow = $(this).closest('tr');
            let basicSal = parseFloat(selectedRow.find('.basicSalary').val());
            let allowance = parseFloat(selectedRow.find('.allowance').val());
            let selectMonth = $('#monthId').val();
            let selectYear = new Date($('#financialYearFrom').val()).getFullYear();

            if (selectYear.length >= 5) {
                selectYear = parseInt((selectYear.toString().split('-')[1]));
            }

            let noOfDaysInMonth = daysInMonth(selectMonth, parseInt(selectYear));

            let noOfAbsentDays = parseFloat(selectedRow.find('.absentDays').val());
            let absentDays = (isNaN(noOfAbsentDays)) ? 0 : noOfAbsentDays;

            let deduction = ((basicSal / noOfDaysInMonth) * absentDays).toFixed(2);
            // let deduction = parseFloat(selectedRow.find('.deduction').val());
            selectedRow.find('.deduction').val(deduction)

            deduction = isNaN(deduction) ? parseFloat(0) : deduction;
            let grossSal = (basicSal + allowance) - deduction;
            selectedRow.find('.grossSalary').val(grossSal.toFixed(2));
            let hC = 0.01 * grossSal;
            selectedRow.find('.hC').val(hC.toFixed(2));
            let pF = parseFloat(selectedRow.find('.pF').val());
            let gIS = parseFloat(selectedRow.find('.gIS').val());
            let netSalary = grossSal - (pF + gIS);
            selectedRow.find('.netSalary').val(netSalary.toFixed(2));
            _getTDSAmount(netSalary, selectedRow);

            let tDS = parseFloat(selectedRow.find('.tDS').val())
            let advance = parseFloat(selectedRow.find('.advance').val())
            let totalRecovery = tDS + hC + advance;
            selectedRow.find('.totalRecovery').val(totalRecovery.toFixed(2));
            selectedRow.find('.takeHome').val((netSalary - totalRecovery).toFixed(2));


            calculateTotal();
        });

    }

    function onChangeAdvance() {
        $('#salarySheetTable').find('tbody').on('keyup', 'tr .advance', function () {
            let selectedRow = $(this).closest('tr');
            let netSalary = parseFloat(selectedRow.find('.netSalary').val());
            let hC = parseFloat(selectedRow.find('.hC').val());
            let tDS = parseFloat(selectedRow.find('.tDS').val());
            let advanceBal = parseFloat(selectedRow.find('.balanceAdvance').val())
            let advance = parseFloat(selectedRow.find('.advance').val())
            if (advance > advanceBal) {
                $(this).val(0);
                errorMsg("Deduction is more then advance balance.")
                return false;
            }
            advance = isNaN(advance) ? parseFloat(0) : advance;
            let totalRecovery = tDS + hC + advance;
            selectedRow.find('.totalRecovery').val(totalRecovery.toFixed(2));
            selectedRow.find('.takeHome').val((netSalary - totalRecovery).toFixed(2));
            calculateTotal();
        });
    }

    return {
        // getEmpList: getEmpList,
        save: save,
        onSelectMonth: onSelectMonth,
        exportReport: exportReport,
        onChangeDeduction: onChangeDeduction,
        onChangeAdvance: onChangeAdvance,
    }
})();

$(document).ready(function () {
    // salarySheet.getEmpList();
    salarySheet.save();
    salarySheet.onSelectMonth();
    salarySheet.exportReport();
    salarySheet.onChangeDeduction();
    salarySheet.onChangeAdvance();
});
