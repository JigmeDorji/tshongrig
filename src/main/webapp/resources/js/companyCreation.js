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
                            },function (){
                                window.location.reload()
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
        $.ajax({
            url: 'companyCreation/getCompanyDetailList',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {data: 'companyId', class: 'hidden companyId'},
                    {data: 'companyName'},
                    {data: 'mailingAddress'},
                    {data: 'mobileNo'},
                    {data: 'email'},
                    {data: 'website'},
                    {data: 'fnYrStart'},
                    // {data: 'bookYrStart'},
                    {data: 'businessType', class: 'hidden'}
                ];
                $('#companyCreationGrid').DataTable({
                    data: res,
                    columns: columnDef,
                    iDisplayLength: 3,
                    bSort: false
                });
            }

        })
    }

    function populateCompanyDetail() {
        $('#companyCreationGrid').find('tbody').on('click', 'tr', function () {
            $('#companyCreationGrid').find('tbody tr').removeClass('selected');
            var selectedRow = $(this).closest('tr').addClass('selected');
            var companyId = selectedRow.find("td:eq( 0 )").text();
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
                }
            })
        });
    }

    var companyId;

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
        onBtnReset: onBtnReset
    }
})();
$(document).ready(function () {
    companyCreation.getCompanyDetailList();
    companyCreation.populateCompanyDetail();
    companyCreation.saveCompanyDetails();
    companyCreation.companyOnClickDelete();
    companyCreation.deleteCompanyDeleteConfirm();
    companyCreation.onBtnReset();
    companyCreation.createNewCompany();
});

