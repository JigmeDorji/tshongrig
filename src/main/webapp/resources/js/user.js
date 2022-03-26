/**
 * Created by jigme.dorji on 24-Apr-2020.
 */
user = (function () {
    "use strict";
    let form = $('#userFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'user/';
    }

    function btnView() {
        $('#userListTableId tbody').on('click', 'tr #btnView', function () {
            let row = $(this).closest('tr');
            let selectedRow = row.addClass('selected');
            let userId = selectedRow.find('.userId').text();
            $('#userId').val(userId);

            let username = selectedRow.find('.username').text();
            $.ajax({
                url: _baseURL() + 'getUserDetail',
                type: 'GET',
                data: {username: username},
                success: function (res) {
                    populate(res);
                    $('#txtConfirmPassword').removeAttr('required');
                    $('#userPassword').removeAttr('required');
                    $('#username').attr('readonly', true);
                    $('.viewUser').removeClass('hidden');
                    $('.newUser').addClass('hidden');
                    $('#btnUpdate').removeClass('hidden');
                    $('#btnDelete').removeClass('hidden');
                    $('.btnAdd').hide();
                    $('.btnUpdate').show();
                    $('#createdDate').val(formatAsDate(res.createdDate));
                    $('#userId').prop('disabled', true);
                    $('#createdDate').attr('readonly', true);
                    $('.field').removeClass('is-invalid');
                    $('#userFullName').focus();
                    selectedRow.removeClass('selected');

                    // if (res.userRoleTypeId === 1) {
                    //     $('#mappingId').attr('hidden', false);
                    //     $('.companyHiddenId').attr('hidden', true);
                    //     $('#companyId').val('');
                    //     loadMappingCompany(userId)
                    // } else {
                    //     $('.companyHiddenId').attr('hidden', false);
                    //     $('#mappingId').attr('hidden', true);
                    // }
                    $('#mappingId').attr('hidden', false);
                    $('.companyHiddenId').attr('hidden', true);
                    $('#companyId').val('');
                    loadMappingCompany(userId)
                }
            });
        });
    }

    /**
     * to add new user
     */
    function addUser() {
        $('#btnSave').on('click', function () {
            $.validator.setDefaults({
                submitHandler: function (form) {
                    isSubmitted = true;
                    $('#userId').prop('disabled', false);
                    $('#btnSave').attr('disabled', true);
                    let data = new FormData($('.userFormId')[0]);
                    $.ajax({
                        url: _baseURL() + 'addUser',
                        type: 'POST',
                        data: data,
                        processData: false,
                        contentType: false,
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                        title: res.text,
                                        text: "Click OK to exit",
                                        type: "success"
                                    }, function () {
                                        window.location.reload();
                                    }
                                );
                            } else {
                                $('#btnSave').attr("disabled", false);
                                errorMsg(res.text);
                            }
                        }, complete: function (res) {
                            $('#btnSave').attr("disabled", false);
                        }
                    });
                }
            });
            form.validate({
                errorElement: 'span',
                errorPlacement: function (error, element) {
                    error.addClass('invalid-feedback');
                    element.closest('.col-4').append(error);
                },
                highlight: function (element, errorClass, validClass) {
                    $(element).addClass('is-invalid');
                },
                unhighlight: function (element, errorClass, validClass) {
                    $(element).removeClass('is-invalid');
                }
            });
        });
    }

    function updateUser() {
        $('#btnUpdate').on('click', function () {
            spms.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('#userId').prop('disabled', false);
                $('#btnSave').attr('disabled', true);
                let data = new FormData($('.userFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'updateUser',
                    type: 'POST',
                    data: data,
                    processData: false,
                    contentType: false,
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
                            $('#btnSave').attr("disabled", false);
                            errorMsg(res.text);
                            isSubmitted = false;
                        }
                        $('#username').attr('readonly', false);
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('#btnSave').attr("disabled", false);
                        errorMsg(res.text);
                    }
                });
            }
        });
    }

    //to validate that similar login id doesnt exists
    function checkIfExist() {
        $('#username').on('blur', function () {
            let username = $('#username').val();
            if (username === "") {
            } else {
                $.ajax({
                    url: _baseURL() + 'isLoginIdAlreadyExists',
                    type: 'GET',
                    data: {
                        username: username
                    },
                    success: function (res) {
                        if (res.status === 0) {
                            errorMsg(res.text);
                            $('#username').val('');
                        } else {

                        }
                    }
                });
            }
        });
    }

    function getUserList() {

        let statusActive = $('#statusActive').val();
        let statusInactive = $('#statusInactive').val();
        $.ajax({
            url: _baseURL() + 'getUserList',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },
                    {
                        data: 'userStatus',
                        render: function (data) {
                            let status = null;
                            if (data === statusActive) {
                                status = "<i class='status-icon bg-success'></i>" + "Active";
                            }
                            if (data === statusInactive) {
                                status = "<i class='status-icon bg-danger'></i>" + "Inactive";
                            }
                            return status;
                        }
                    },
                    {data: 'userId', class: "userId align-middle hidden"},
                    {data: 'username', class: "username align-middle"},
                    {data: 'userFullName'},
                    {data: 'userMobileNo'},
                    {
                        data: 'createdDate',
                        render: function (data) {
                            return formatAsDate(data)
                        }
                    },
                    {data: 'userRoleTypeName'},
                    {
                        data: "null",
                        mRender: function () {
                            return '<a href="#" id="btnView" class="btn btn-primary btn-sm ml-3 d-none d-sm-inline-block" data-toggle="modal" data-target="#userDetailModal"><i class="fa fa-edit"></i> Edit</a>';
                        }
                    }
                ];
                $('#userListTableId').DataTable({
                    data: res,
                    columns: columnDef
                });
            }
        });
    }

    //To validate that the password entered is same
    function checkPasswordMatching() {
        $('#txtConfirmPassword').on('change', function () {
            let password = $('#userPassword').val();
            let confirmPassword = $('#txtConfirmPassword').val();
            if (password !== confirmPassword) {
                swal({
                    title: "Your password doesn't match, Please type again",
                    text: "Click OK to exit",
                    type: "warning"
                });
                $('#txtConfirmPassword').val('');
            }
        });
    }

    function deleteUserDetail() {
        $('#btnDelete').on('click', function () {
            let username = $('#username').val();
            swal({
                title: "Are you sure to delete user " + username + "?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Confirm",
                closeOnConfirm: false
            }, function (isConfirm) {
                if (!isConfirm) return;
                $.ajax({
                    url: _baseURL() + 'deleteUser',
                    type: 'GET',
                    data: {companyId: $('#companyId').val(), userId: $('#userId').val()},
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                        } else {
                            errorMsg(res.text)
                        }
                        $('#username').attr('readonly', false);
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        swal("Error deleting!", "Please try again", "error");
                    }
                });
            });
        })
    }


    function isEmailValid() {
        $('#email').on('blur', function () {
            if ($(this).val() !== '') {
                if (!isEmail($(this).val())) {
                    $(this).val('');
                    errorMsg("Invalid email Id. please check");
                }
            }
        });
    }

    function onChangeRoleType() {
        $('#userRoleTypeId').on('change', function () {
            // if (parseInt($(this).val()) === 1) {
            //     $('#mappingId').attr('hidden', false);
            //     $('#companyId').val('');
            //     $('.companyHiddenId').attr('hidden', true);
            // } else {
            //     $('#mappingId').attr('hidden', true);
            //     $('.companyHiddenId').attr('hidden', false);
            // }
            $('#mappingId').attr('hidden', false);
            $('#companyId').val('');
            $('.companyHiddenId').attr('hidden', true);
        });
    }

    function deleteMappedCompany() {
        $("select").on('select2:unselecting', function (evt) {

            let companyId = evt.params.args.data.id;

            if (parseInt(companyId) === parseInt($('#loginCompanyId').val())) {
                if (!confirm("Are you sure you want to delete the login company?")) {
                    evt.preventDefault();
                } else {
                    validateDelete(companyId);
                    window.location.href = spms.getUrl() + 'logout';
                }
            } else {
                if (!confirm("Are you sure you want to remove \"" + evt.params.args.data.text + "\"?")) {
                    evt.preventDefault();
                } else {
                    validateDelete(companyId);
                }
            }
        });
    }

    function validateDelete(companyId) {
        $.ajax({
            url: _baseURL() + 'isCompanyMappedAlready',
            type: 'GET',
            async: false,
            data: {
                userId: $('#userId').val(),
                companyId: companyId
            },
            success: function (res) {
                if (!res) {
                    $.ajax({
                        url: _baseURL() + 'deleteMappedCompany',
                        type: 'GET',
                        async: false,
                        data: {
                            userId: $('#userId').val(),
                            companyId: companyId
                        },
                        success: function (res) {
                            if (res.status === 1) {
                                loadMappingCompany($('#userId').val())
                                successMsg(res.text);
                            } else {
                                errorMsg(res.text)
                            }
                        }
                    });
                }
            }
        });
    }

    function loadMappingCompany(userId) {
        $.ajax({
            url: _baseURL() + 'loadMappingCompany',
            type: 'GET',
            data: {userId: userId},
            success: function (res) {
                $('#companyMappingId').select2('val', [res]);
            }
        });
    }


    $('#companyAbbreviation').text("@"+getFirstLetters($('#companyName').val()));
    function getFirstLetters(str) {
        const firstLetters = str
            .split(' ')
            .map(word => word[0])
            .join('');

        return firstLetters;
    }

    return {
        addUser: addUser,
        updateUser: updateUser,
        checkIfExist: checkIfExist,
        getUserList: getUserList,
        btnView: btnView,
        deleteUserDetail: deleteUserDetail,
        isEmailValid: isEmailValid,
        checkPasswordMatching: checkPasswordMatching,
        onChangeRoleType: onChangeRoleType,
        deleteMappedCompany: deleteMappedCompany
    }
})();

$(document).ready(function () {
    user.addUser();
    user.updateUser();
    user.checkIfExist();
    user.getUserList();
    user.btnView();
    user.deleteUserDetail();
    user.isEmailValid();
    user.checkPasswordMatching();
    user.onChangeRoleType();
    user.deleteMappedCompany();
})
;