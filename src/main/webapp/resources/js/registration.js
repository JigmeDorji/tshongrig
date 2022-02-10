/**
 * Created by SonamPC on 04-Nov-17.
 */


registration = (function () {
    function init() {
        onBtnCancelClick();
        onBillNoClick();
        onCashClick();
        getGeneratedRegistrationNumber();
        searchDetails();
        saveRegistrationDetails();
        onclickAddWorkOrder();
        onBtnReset();
        getRegistrationInList();
        onClickBtnAddToWorKOrderDetail();
        addMechanicDetails();
        removeFromServiceGrid();
        mechanicOnClickDelete();
        mechanicDeleteConfirm();
        onClickEditBtn();


    }

    /**
     * show and hide the bill number field
     */
    $('#billNo').hide();
    function onBillNoClick() {
        $('#bill_no').on('click', function () {
            $('#billNo').show();
        });
    }

    function onCashClick() {
        $('#cash').on('click', function () {
            $('#billNo').hide();
            $('#bill_no_id').val('');
        });
    }

    /**
     * get auto generated registration number
     */
    function getGeneratedRegistrationNumber() {
        $('#autoGeneRegNo').on('click', function () {
            $.ajax({
                url: 'registration/getGeneratedRegistrationNumber',
                type: 'GET',
                success: function (res) {
                    $('#registration_no').val(res);
                    $('#vehicleTypeId').val('');
                    $('#serviceType').val('');
                    $('#sectionId').val('');
                    $('#contact_no').val('');
                    $('#promise_date').val('');
                    $('#customer_name').val('');
                    $('#cash').prop('checked', true);
                    $('#bill_no_id').val('');
                    $('#vehicleNo').val('');
                    $('#billNo').hide();
                    $('#addOrderNo').attr('disabled', true);
                    serviceDetailsGrids.dataTable().fnClearTable();


                }
            });
        });
    }

    /**
     * get registration information details
     */
    let serviceDetailsGrids = $('#serviceDetailsGrid').dataTable({
        "paging": false,
        "searching": false
    });

    function searchDetails() {
        $('#registration_no').on('blur change', function () {
                $('#addOrderNo').attr('disabled', false);
                let registration_no = $('#registration_no').val();
                if (registration_no === '') {
                    swal("Warning", "Please provide registration number.");
                } else {
                    $.ajax({
                        url: 'registration/getDetails',
                        type: 'GET',
                        data: {registrationNo: registration_no},
                        success: function (res) {
                            if (res.status == 0) {
                                swal("Warning", res.text);
                                $('#registration_no').val('');
                            } else if (res.status == 1) {
                                if (res.dto.bill_no !== null && res.dto.bill_no !== '') {
                                    $('#billNo').show();
                                    $('#bill_no').prop('checked', true);
                                } else {
                                    $('#billNo').hide();
                                    $('#cash').prop('checked', true);
                                }
                                $('.resetfield').attr('disabled', true)
                                $('#btnEdit').attr('disabled', false)
                                populate(res.dto);
                                $('#registration_date').val(formatAsDate(res.dto.registration_date));

                                $.ajax({
                                    url: 'addListToWorkOrderNo/getMechanicDetails',
                                    type: 'GET',
                                    data: {workOrderNumber: registration_no},
                                    success: function (res) {
                                        serviceDetailsGrids.dataTable().fnClearTable();
                                        for (var i in res) {
                                            serviceDetailsGrids.fnAddData(
                                                [
                                                    '<input style="width:100px" type="hidden" id="mechanicId" name="autoWorkDTOs[' + i + '].mechanicId" class="form-control mechanicId" readonly="true" value="' + res[i].mechanicId + '"><input type="text" id="mechanicalInChargeName" name="autoWorkDTOs[' + i + '].mechanicalInChargeName" class="form-control mechanicalInChargeName" readonly="true" value="' + res[i].mechanicalInChargeName + '">',
                                                    '<a class="mechanicDeleteId" id="mechanicDeleteId" href="javascript:void(0);"><span class="glyphicon glyphicon-minus-sign"/></a> '
                                                    /*'<input type="button" id="mechanicDeleteId" class="mechanicDeleteId" value="Delete">'*/
                                                ]);
                                        }
                                    }

                                });


                                $('#registration_time').val(res.dto.registration_time);
                                $('#addOrderNo').attr('disabled', false);


                                if (res.dto.statusFlag === 'D') {
                                    swal({
                                        title: 'This registration number is deleted. You can only view the information.',
                                        text: "Click OK to exit",
                                        type: "warning"
                                    }, function () {
                                        $('#bill_no_id').focus();
                                        return;
                                    });
                                    $('#addOrderNo').attr('disabled', true);
                                }
                            }

                        }
                    });
                }
            }
        )
        ;
    }

    function onClickEditBtn() {
        $('#btnEdit').on('click', function () {
            $('#isEdit').val(1);
            $('.resetfield').attr('disabled', false)
            $('#btnEdit').attr('disabled', true)
        })
    }

    /**
     * get registration information details
     */
    function saveRegistrationDetails() {
        $('#saveBtn').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {

                    if ($('#promise_date').val() == '') {
                        $('#promise_date').attr('disabled', true);
                    }

                    if ($('#bill_no').is(':checked', true)) {
                        if ($('#bill_no_id').val() === '' || $('#bill_no_id').val() === null) {
                            swal({
                                title: 'Bill no is mandatory.',
                                text: "Click OK to exit",
                                type: "warning"
                            }, function () {
                                $('#bill_no_id').focus();
                                return;
                            });
                        }
                    }
                    var tableData = $('#serviceDetailsGrid tbody');
                    var noMatch = $(tableData).find('td').first().html();
                    if (noMatch == 'No data available in table') {
                        swal({
                            title: 'Please add mechanic in charge for this auto works.',
                            text: "Click OK to exit",
                            type: "warning"
                        }, function () {
                            return;
                        });
                    } else {
                        $.ajax({
                            url: 'registration/saveRegistrationDetails',
                            type: 'POST',
                            data: $(form).serializeArray(),
                            success: function (res) {
                                if (res.status == 1) {
                                    // successMsg(res.text);
                                    swal({
                                        title: res.text,
                                        text: "Click OK to exit",
                                        type: "success"
                                    }, function () {
                                        //getRegistrationInList();
                                        window.location.reload();
                                    });
                                    $(this).attr('readonly', true)
                                    $('#addOrderNo').attr('disabled', false);
                                    $('#promise_date').attr('disabled', false);
                                } else {
                                    swal({
                                        title: res.text,
                                        text: "Click OK to exit",
                                        type: "warning"
                                    }, function () {
                                        getRegistrationInList();
                                        //location.reload();
                                    });
                                }
                            }
                        });
                    }
                }
            })
        });
    }

    function onBtnReset() {
        $('#reRestBtn').on('click', function () {
            $('#addOrderNo').attr('disabled', false);
        });
    }

    function onclickAddWorkOrder() {
        $('#addOrderNo').on('click', function () {
            var workOrderNumber = $('#registration_no').val();

            if (workOrderNumber) {
                $.ajax({
                    url: 'registration/getDetails',
                    type: 'GET',
                    data: {registrationNo: workOrderNumber},
                    success: function (res) {
                        if (res === '') {
                            errorMsg("Please provide the registration information.")
                        } else {
                            window.location.href = spms.getUrl() + 'addListToWorkOrderNo?workOrderNumber=' + workOrderNumber;
                            //window.location.href = '/addListToWorkOrderNo?workOrderNumber=' + workOrderNumber;
                            return false
                        }
                    }
                });
            }
        });

    }

    /* var registrationInListTable = $('#registrationInListTable').dataTable({
     "paging": true,
     "searching": false

     });*/
    /* var agencyWiseWorkOrderDetailGrid = $('#agencyWiseWorkOrderDetailGrid').dataTable({
     "paging": true,
     "searching": false

     });*/

    function getRegistrationInList() {


        $.ajax({
            url: 'registration/getAgencyWiseWorkOrderDetailList',
            type: 'GET',
            success: function (res) {

                var columnDef = [
                    {
                        data: 'registration_no',
                        render: function (data) {
                            return '<input type="button" class="btn btn-primary btn-xs" id="agencyWiseWorkOrderDetail" value="Add Detail">'
                        }
                    },
                    {data: 'agencyId', class: 'hidden'},
                    {data: 'agencyName'},
                    {data: 'registration_no'},
                    {data: 'customer_name'},
                    {data: 'vehicle_no'},
                    {data: 'vehicle_type'},
                    {data: 'service_type'},
                    {
                        data: 'cash',
                        render: function (data) {
                            var paymentMode = data ? "Cash" : "Bill";
                            return paymentMode;
                        }
                    },
                    {data: 'registration_date'},
                    {data: 'mechanicName'},
                    {
                        data: 'serviceWorth',
                        render: function (data) {
                            var workStatus = (data > 0 ? "On-Going" : "On-Going");
                            return workStatus;
                        }
                    }
                ];
                $('#agencyWiseWorkOrderDetailGrid').DataTable({
                    data: res,
                    columns: columnDef
                });
            }

        }).then(function () {
            $.ajax({
                url: 'registration/getRegistrationInList',
                type: 'GET',
                success: function (res) {
                    $('#registrationInListTable').dataTable().fnDestroy();
                    var columnDef = [
                        {
                            data: 'registration_no',
                            render: function (data) {
                                return '<input type="button" class="btn btn-primary btn-xs" id="addToWorKOrderDetail" value="Add Detail">'
                            }
                        },
                        {data: 'registration_no'},
                        {data: 'customer_name'},
                        {data: 'vehicle_no'},
                        {data: 'vehicle_type'},
                        {data: 'service_type'},
                        {
                            data: 'cash',
                            render: function (data) {
                                var paymentMode = data ? "Cash" : "Bill";
                                return paymentMode;
                            }
                        },
                        {data: 'registration_date'},
                        {data: 'mechanicName'},
                        {
                            data: 'serviceWorth',
                            render: function (data) {
                                var workStatus = (data > 0 ? "Completed" : "On-Going");
                                return workStatus;
                            }
                        }
                    ];
                    $('#registrationInListTable').DataTable({
                        data: res,
                        columns: columnDef
                    });


                }

            })
        })
    }

    function onClickBtnAddToWorKOrderDetail() {
        $('#registrationInListTable').find('tbody').on('click', 'tr #addToWorKOrderDetail', function () {
            var selectedRow = $(this).closest('tr').addClass('selected');
            var workOrderNumber = selectedRow.find("td:eq( 1 )").text();
            window.location.href = spms.getUrl() + 'addListToWorkOrderNo?workOrderNumber=' + workOrderNumber + '&&isFromLedger=' + 0;
        });

        $('#agencyWiseWorkOrderDetailGrid').find('tbody').on('click', 'tr #agencyWiseWorkOrderDetail', function () {
            var selectedRow = $(this).closest('tr').addClass('selected');
            var workOrderNumber = selectedRow.find("td:eq( 3 )").text();
            var agencyId = selectedRow.find("td:eq( 1 )").text();
            //var agencyId = selectedRow.find('td input[id=agencyId]').val();
            if (agencyId != 'null') {
                window.location.href = spms.getUrl() + 'workOrderWiseAgencyDetail?workOrderNumber=' + workOrderNumber + '&&agencyId=' + agencyId;
            }
        });
    }


    var serviceDetailsGrid = $('#serviceDetailsGrid tbody');

    function addMechanicDetails() {
        var i = 0;
        $('#addToServiceGrid').on('click', function () {
            if ($('#mechanicalInCharge').val() === '') {
                $('#mechanicalInCharge').focus();
                errorMsg('Please select the mechanic in charge.');
                return;
            }
            serviceDetailsGrid.find('tr').each(function () {
                $(this).find('.mechanicEditBtn').attr('disabled', false);
                $(this).find('.mechanicDeleteId').attr('disabled', false);
            });

            var mechanicalInCharge = $('#mechanicalInCharge').val();
            var mechanicalInChargeName = $("#mechanicalInCharge option:selected").text();
            var tableData = $('#serviceDetailsGrid tbody');
            var row = "<tr>" +
                "<td><input type='hidden' readonly name='autoWorkDTOs[" + i + "].mechanicId'  id='mechanicId' class='form-control mechanicId right-align' value=" + mechanicalInCharge + ">" +
                "<input type='text' readonly   id='mechanicalInChargeName' class='form-control' value='" + mechanicalInChargeName + "'></td>" +
                "<td><a class='btnRemove' id='btnRemove' href='javascript:void(0);'><span class='glyphicon glyphicon-minus-sign'/></a> </td>" +
                "</tr>";
            i++
            var tableGrid = $('#serviceDetailsGrid');
            if (!isValidServiceData(tableData)) {
                var noMatch = $(tableData).find('td').first().html();
                if (noMatch == 'No data available in table') {
                    $(tableData).find('tr').remove();
                }
                tableGrid.find('tbody').append(row);
                var tableBody = $('#serviceDetailsGrid').find('tbody');
                var allRow = tableBody.find('tr');
                _formIndexing(tableBody, allRow);
                $('.resetField').val('');
            }
            $('#btnSave').attr('disabled', false);
        });
    }

    function isValidServiceData(tableData) {
        var mechanicalInCharge = $('#mechanicalInCharge').val();
        var isItemCodeExist = false;
        tableData.find('tr').each(function () {
            if ($(this).find('.mechanicId').val() == mechanicalInCharge) {
                swal("Error", "Mechanic is already added to list.");
                $('.resetField').val('');
                isItemCodeExist = true;
                return false;
            }
        });
        return isItemCodeExist;
    }

    function removeFromServiceGrid() {
        $('#serviceDetailsGrid tbody').on('click', '.btnRemove', function () {
            $('#paymentDetailsArea').attr('hidden', true);
            var tableBody = $('#serviceDetailsGrid tbody');
            var row = $('#serviceDetailsGrid tbody tr');
            removeRow($(this), tableBody, row);

            tableBody = $('#serviceDetailsGrid').find('tbody');
            var allRow = tableBody.find('tr');
            _formIndexing(tableBody, allRow);

        });
    }

    function _formIndexing(tableBody, row, serialNo, iterator) {
        if (!iterator)
            iterator = 0;

        for (var i = 0; i < row.length; i++) {
            tableBody.children().eq(i).children().children().each(
                function () {
                    if (this.name) {
                        this.name = this.name.replace(
                            /\[(\d+)\]/, function (str, p) {
                                return '[' + (i + iterator) + ']';
                            }
                        );
                    }

                    if ($(this).hasClass(serialNo)) {
                        $(this).val(i + 1);
                    }
                }
            );
        }
    }


    function removeRow(clickedRemoveBtn, tableBody, row) {
        var removableRow = clickedRemoveBtn.parent().parent();
        if (row.length === 0) {
            removableRow.find('input[type="text"]').val('');
        } else {
            removableRow.remove();
            _formIndexing(tableBody, row);

            if ($(tableBody).children().length == 0) {
                $('#btnSave').attr('disabled', true);
            }
        }
    }

    var mechanicId;
    var workOrderNumberForMechanic;

    function mechanicOnClickDelete() {
        serviceDetailsGrid.on('click', '.mechanicDeleteId', function () {
            var selectedRow = $(this).closest('tr');
            mechanicId = selectedRow.find('.mechanicId').val();
            workOrderNumberForMechanic = $('#registration_no').val();
            $('#myModal').modal('show');
        });
    }


    function mechanicDeleteConfirm() {
        $('#btnConfirm').on('click', function () {
            $.ajax({
                url: 'addListToWorkOrderNo/deleteMechanicDetails',
                type: 'GET',
                async: true,
                data: {
                    workOrderNumber: workOrderNumberForMechanic, mechanicId: mechanicId
                },
                success: function (res) {
                    if (res.status == 1) {
                        swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                $.ajax({
                                    url: 'addListToWorkOrderNo/getMechanicDetails',
                                    type: 'GET',
                                    async: true,
                                    data: {
                                        workOrderNumber: $('#registration_no').val()
                                    },
                                    success: function (res) {
                                        serviceDetailsGrids.dataTable().fnClearTable();
                                        for (var i in res) {
                                            serviceDetailsGrids.fnAddData(
                                                [

                                                    '<input style="width:100px" type="hidden" id="mechanicId" name="autoWorkDTOs[' + i + '].mechanicId" class="form-control mechanicId" readonly="true" value="' + res[i].mechanicId + '"><input type="text" id="mechanicalInCharge" name="autoWorkDTOs[' + i + '].mechanicalInChargeName" class="form-control mechanicalInChargeName" readonly="true" value="' + res[i].mechanicalInChargeName + '">',
                                                    '<a class="mechanicDeleteId" id="mechanicDeleteId" href="javascript:void(0);"><span class="glyphicon glyphicon-minus-sign"/></a> '
                                                    /* '<input type="button" id="mechanicDeleteId" class="mechanicDeleteId" value="Delete">'*/
                                                ]);
                                        }
                                    }

                                });

                            }
                        );
                        $('#myModal').modal('hide');
                    } else {
                        swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "warning"
                            }, function () {
                                $.ajax({
                                    url: 'addListToWorkOrderNo/getMechanicDetails',
                                    type: 'GET',
                                    async: true,
                                    data: {
                                        workOrderNumber: $('#registration_no').val()
                                    },
                                    success: function (res) {
                                        serviceDetailsGrids.dataTable().fnClearTable();
                                        for (var i in res) {
                                            serviceDetailsGrids.fnAddData(
                                                [

                                                    '<input style="width:100px" type="hidden" id="mechanicId" name="autoWorkDTOs[' + i + '].mechanicId" class="form-control mechanicId" readonly="true" value="' + res[i].mechanicId + '"><input type="text" id="mechanicalInCharge" name="autoWorkDTOs[' + i + '].mechanicalInChargeName" class="form-control mechanicalInChargeName" readonly="true" value="' + res[i].mechanicalInChargeName + '">',
                                                    '<a class="mechanicDeleteId" id="mechanicDeleteId" href="javascript:void(0);"><span class="glyphicon glyphicon-minus-sign"/></a> '
                                                    /* '<input type="button" id="mechanicDeleteId" class="mechanicDeleteId" value="Delete">'*/
                                                ]);
                                        }
                                    }

                                });

                            }
                        );
                        $('#myModal').modal('hide');
                    }
                }

            });
        })

    }

    function onBtnCancelClick() {
        $('#cancelBtn').on('click', function () {
            $('#myModal').modal('hide');
        });
    }

    return {
        init: init,
        onBtnCancelClick: onBtnCancelClick,
        onBillNoClick: onBillNoClick,
        onCashClick: onCashClick,
        onclickAddWorkOrder: onclickAddWorkOrder,
        getGeneratedRegistrationNumber: getGeneratedRegistrationNumber,
        searchDetails: searchDetails,
        saveRegistrationDetails: saveRegistrationDetails,
        onBtnReset: onBtnReset,
        getRegistrationInList: getRegistrationInList,
        onClickBtnAddToWorKOrderDetail: onClickBtnAddToWorKOrderDetail,
        addMechanicDetails: addMechanicDetails,
        removeFromServiceGrid: removeFromServiceGrid,
        mechanicOnClickDelete: mechanicOnClickDelete,
        mechanicDeleteConfirm: mechanicDeleteConfirm,
        onClickEditBtn: onClickEditBtn
    }
})
();
$(document).ready(function () {
    registration.init();
});

