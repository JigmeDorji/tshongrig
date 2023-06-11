/**
 * Created by SonamPC on 12-Dec-16.
 */

employeeSetup = (function () {
    function init() {
        save();
        getEmpSetupListList();
        displayGridValueToField();
        btnDelete();
        calculatePF();
        onClickNewEmp();
    }

    /* var employeeSetUpList = $('#employeeSetUpList').dataTable({
     "paging": true,
     "searching": false
     });*/

    //to load the existing list of users
    function getEmpSetupListList() {
        $.ajax({
            url: 'employeeSetup/getEmpSetupListList',
            type: 'GET',
            success: function (res) {

                var index = 0;
                var columnDef = [
                    {
                        data: 'index',
                        render: function (index) {
                            return index + 1;
                        }
                    },
                    {data: 'empId', class: 'hidden'},
                    {data: 'empName'},
                    {
                        data: 'cost',
                        render: function (data) {
                            return data === 1 ? "General" : "Production"
                        }
                    },
                    {
                        data: 'dateOfBirth',
                        render: function (data) {
                            return formatAsDate(data);
                        }
                    },
                    {data: 'cidNo'},
                    {data: 'contactNo'},
                    {data: 'tpnNo'},
                    {
                        data: 'basicSalary',
                        render: function (data) {
                            return spms.formatAmount(data.toFixed(2));
                        }
                    }
                ];
                var order = [[1, 'asc']];
                var t = $('#employeeSetUpList').DataTable({
                    data: res,
                    columns: columnDef,
                    bSort: false
                });
                t.on('order.dt search.dt', function () {
                    t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                        cell.innerHTML = i + 1;
                    });
                }).draw()
            }

        });
    }


    function displayGridValueToField() {
        $('#employeeSetUpList tbody').on('click', 'tr', function () {
            $('#employeeDetailModal').modal('show');
            $('#employeeSetUpList tbody tr').removeClass('selected');
            $(this).closest('tr').addClass('selected');
            $('#empId').val($(this).find("td:eq( 1 )").text());
            $.ajax({
                url: 'employeeSetup/getEmpSetUpDetail',
                type: 'GET',
                data: {empId: $(this).find("td:eq( 1 )").text()},
                success: function (res) {
                    populate(res);
                    $('#dateOfBirth').val(formatAsDate(res.dateOfBirth))
                    $('#dateOfAppointment').val(formatAsDate(res.dateOfAppointment))
                    $('#dateOfBirth').val(formatAsDate(res.dateOfBirth))
                    $('#incrementEffectDate').val(formatAsDate(res.incrementEffectDate))
                }
            });
        });
    }


    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $('#btnSave').prop('disabled', true)

                if ($('#incrementEffectDate').val() == '') {
                    $('#incrementEffectDate').attr("disabled", true);
                }
                $.ajax({
                    url: 'employeeSetup/save',
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
                        } else {
                            errorMsg(res.text)
                        }
                    }, complete: () => {
                        $(this).prop('disabled', false)
                    }
                });
            }
        });
    }

    //To save the data
    function btnDelete() {
        $('#btnDelete').on('click', function () {
            var categoryName = $('#categoryName').val();
            if (categoryName == '') {
                errorMsg("Select item name to be deleted.");
            } else {
                $.ajax({
                    url: 'addItemCategory/deleteCategoryName',
                    type: 'POST',
                    data: {categoryName: categoryName},
                    success: function (res) {
                        if (res.status == 1) {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function (e) {
                                location.reload();
                            });
                        } else {
                            errorMsg(res.text);
                            $('.resetfield').val('');
                        }
                    }
                });
            }

        })
    }

    function calculatePF() {
        $('#basicSalary').on('keyup', function () {
            let pfPercentage = $('#pfPercentage').val();
            let basicPay = $(this).val();
            $('#pF').val(((pfPercentage / 100) * basicPay).toFixed(2))

        })
    }

    function onClickNewEmp() {
        $('#btnAddNewEmp').on('click', function () {
            $('#empId').val('');
            $('#itemSetupFrom').trigger("reset");
            $('#employeeDetailModal').modal('show');
        })
    }


    return {
        init: init,
        save: save,
        getEmpSetupListList: getEmpSetupListList,
        displayGridValueToField: displayGridValueToField,
        btnDelete: btnDelete,
        calculatePF: calculatePF,
        onClickNewEmp: onClickNewEmp
    }
})();

$(document).ready(function () {
    employeeSetup.init();
});
