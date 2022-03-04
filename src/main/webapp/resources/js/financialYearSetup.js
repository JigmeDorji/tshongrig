/**
 * Created by  Bikash Rai on 5/5/2020.
 */
financialYearSetup = (function () {
    "use strict";

    function save() {
        $('#btnSave').on('click', function () {
            $('#financialYearForm').validate({
                submitHandler: function (form) {
                    $.ajax({
                        url: 'financialYearSetup/save',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status == 0) {
                                errorMsg(res.text);
                            } else if (res.status == 1) {
                                loadFinancialYear();
                                swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                });
                                $('#btnDelete').attr('disabled', true);
                            }
                        }
                    });
                }
            });
        });
    }

    function loadFinancialYear() {
        var url = 'financialYearSetup/' + 'getFinancialYearList';
        $.ajax({
            url: url,
            type: 'GET',
            success: function (res) {
                var data = res;
                var dataTableDefinition = [
                    {
                        class: "text-center",
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },

                    {
                        "data": "financialYearId",
                        class: "financialYearId text-center hidden",
                        render: function (financialYearId) {
                            return financialYearId <= 9 ? "0" + financialYearId : financialYearId
                        }
                    }
                    , {
                        "data": "financialYearFrom",
                        class: "financialYearFrom text-center",
                        render: function (financialYearFrom) {
                            return formatAsDate(financialYearFrom)
                        }
                    }
                    , {
                        "data": "financialYearTo",
                        class: "financialYearTo text-center",
                        render: function (financialYearTo) {
                            return formatAsDate(financialYearTo)
                        }
                    }
                    , {
                        "data": "status",
                        class: "text-center",
                        render: function (status) {
                            return status === 'A' ? "Active" : "Inactive"
                        }
                    }, {
                        "data": "status",
                        class: "text-center",
                        render: function (data) {
                            if (data === 'A') {
                                return '';
                            } else {
                                return '<button type="button" id="btnEdit" class="btn btn-primary btn-sm">Activate<button'
                            }
                        }
                    }
                ];
                $('#financialYearGrid').DataTable({
                    data: data
                    , columns: dataTableDefinition
                    , destroy: true
                    , 'bSort': false
                });
            }
        });
    }

    function onClickEdit() {
        $('#financialYearGrid tbody').on('click', 'tr #btnEdit', function () {
            $('#financialYearGrid tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');
            selectedRow.addClass('selected');
            let financialYearId = selectedRow.find('.financialYearId').text();
            $.ajax({
                    url: 'financialYearSetup/activateFinancialYear',
                    type: 'POST',
                    data: {financialYearId: financialYearId},
                    success: function (res) {
                        swal({
                            title: res.text,
                            text: "Click OK to exit",
                            type: "success"
                        }, function () {
                            window.location.href = spms.getUrl() + "financialYearSetup";
                        });
                    }
                }
            );
        });
    }

    function deleteFinancialYearDetail() {
        $('#btnDelete').on('click', function () {
            $.ajax({
                    url: 'financialYearSetup/delete',
                    type: 'POST',
                    data: {
                        financialYearId: $('#financialYearId').val()
                    },
                    success: function (res) {
                        if (res.status == 1) {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                window.location.reload();
                            });
                        } else {
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "Error"
                            }, function () {
                                window.location.reload();
                            });
                        }
                    }
                }
            );
        });

    }

    return {
        save: save,
        onClickEdit: onClickEdit,
        loadFinancialYear: loadFinancialYear,
        deleteFinancialYearDetail: deleteFinancialYearDetail
    }
})
();
$(document).ready(function () {
    $('#btnDelete').attr('disabled', true);
    financialYearSetup.save();
    financialYearSetup.onClickEdit();
    financialYearSetup.loadFinancialYear();
    financialYearSetup.deleteFinancialYearDetail();
});