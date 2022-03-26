/**
 * Created by Bcass Sawa on 5/3/2019.
 */

companyCreation = (function () {

    function saveCompanyDetails() {
        $('.companyCreationForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'companyCreation/saveCompanyDetails',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                $("#companyCreationForm")[0].reset();
                                $('#companyDetailModal').modal('hide');
                                getCompanyDetailList();
                            });

                        } else {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "warning"
                            });
                        }
                    }
                });
            }
        })
        //});
    }

    function onBtnReset() {
        $('#reRestBtn').on('click', function () {
            $('#addOrderNo').attr('disabled', false);
        });
    }

    function getCompanyDetailList() {
        $('#companyCreationGrid').dataTable().fnDestroy();
        $.ajax({
            url: 'companyCreation/getCompanyDetailList',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {data: 'companyId', class: 'hidden companyId'},
                    {data: 'companyName', class: 'text-center text-sm'},
                    {data: 'contactPerson', class: 'text-center'},
                    {data: 'mobileNo', class: 'text-center'},
                    {data: 'email', class: 'text-center'},
                    {
                        data: 'businessType', class: 'text-center',
                        render: function (data) {
                            return data === 1 ? "Trading" : data === 2 ? "Service" : data === 3 ? "Manufacturing" : data === 4 ? "Construction" : data === 5 ? "Hotel" : data === 6 ? "Restaurant" : "NGO"
                        }
                    },
                    {
                        data: 'trialExpiryDate', class: 'text-center',
                        render: function (data) {
                            if(data!==''){
                                return formatAsDate(data);
                            }else {
                                return '';
                            }

                        }
                    },
                    {
                        data: 'status', class: 'text-center',
                        render: function (data) {
                            return data === 'P' ? '<p class="badge badge-success">New Request</p>' : data === 'A' ? '<p class="text-success">Approved</p>':'<p class="text-danger">Not Approved</p>';
                        }
                    },
                    {
                        data: 'status', class: 'text-center',
                        render: function (data) {
                            return '<div class="list-icons">\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t<div class="dropdown position-static">\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t<a href="#" class="list-icons-item" data-toggle="dropdown" aria-expanded="false">\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t\t<i class="icon-menu9"></i>\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t</a>\n' +
                                '\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t<div class="dropdown-menu dropdown-menu-right" style="">\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t\t<a href="#" class="dropdown-item btnView"><i class="icon-eye"></i> View</a>\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t\t<a href="#" class="dropdown-item btnSelect"><i class="icon-hand"></i> Select</a>\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
                                '\t\t\t\t\t\t\t\t\t\t\t</div>\n' +
                                '\t\t\t\t\t\t\t\t\t\t</div>'
                        }
                    },

                ];
                $('#companyCreationGrid').DataTable({
                    data: res,
                    columns: columnDef,
                    bSort: false,
                    autoWidth: false
                });
            }

        })
    }

    function populateCompanyDetail() {
        $('#companyCreationGrid').find('tbody').on('click', 'tr .btnView', function () {
            $('.field').val('');
            $('#companyCreationGrid').find('tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr').addClass('selected');
            let companyId = selectedRow.find("td:eq( 0 )").text();
            $.ajax({
                url: 'companyCreation/populateCompanyDetail',
                type: 'GET',
                data: {
                    companyId: companyId
                },
                success: function (res) {
                    if (res.email == '') {
                        $('#email').val('')
                    }
                    if (res.website == '') {
                        $('#website').val('')
                    }
                    populate(res);
                    $('#pfPercentage').val(res.pfPercentage);
                    $('#companyId').val(companyId);
                    $('#companyDetailModal').modal('show');

                }
            })
        });
    }

    function onSelect() {
        $('#companyCreationGrid').find('tbody').on('click', 'tr .btnSelect', function () {
            $('.field').val('');
            $('#companyCreationGrid').find('tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr').addClass('selected');
            let companyId = selectedRow.find("td:eq( 0 )").text();
            $.ajax({
                url: 'companyCreation/selectCompany',
                type: 'GET',
                data: {
                    companyId: companyId
                },
                success: function (res) {
                    if (res.status===1) {
                        swal({
                            title: res.text,
                            text: "Click OK to exit",
                            type: "success"
                        }, function () {
                            window.location.reload();
                        });

                    }

                }
            })
        });
    }


    function companyOnClickDelete() {
        $('btnDelete').on('click', function () {
            $('#myModal').modal('show');
        });
    }

    $('#closeBtn').on('click', function () {
        window.location.reload();
    });

    function deleteCompanyDeleteConfirm() {
        $('#btnConfirm').on('click', function () {
            $.ajax({
                url: 'companyCreation/deleteCompanyDetails',
                type: 'GET',
                async: true,
                data: {
                    companyId: $('#companyId').val()
                },
                success: function (res) {
                    if (res.status == 1) {

                    } else {
                    }
                    $('#myModal').modal('hide');
                }

            });
        })
    }

    $('#fnYrStart').on('keydown', function (e) {
        if (e.keyCode == 13) {
            if ($(this).val() != '') {
                $('#bookYrStart').val($(this).val())
            } else {
                $('#fnYrStart').focus();
            }
        }
    });

    return {
        getCompanyDetailList: getCompanyDetailList,
        populateCompanyDetail: populateCompanyDetail,
        saveCompanyDetails: saveCompanyDetails,
        companyOnClickDelete: companyOnClickDelete,
        deleteCompanyDeleteConfirm: deleteCompanyDeleteConfirm,
        onBtnReset: onBtnReset,
        onSelect: onSelect
    }
})();
$(document).ready(function () {
    companyCreation.getCompanyDetailList();
    companyCreation.populateCompanyDetail();
    companyCreation.saveCompanyDetails();
    companyCreation.companyOnClickDelete();
    companyCreation.deleteCompanyDeleteConfirm();
    companyCreation.onBtnReset();
    companyCreation.onSelect();
});

