createUser = (function () {
    function init() {
        save();
        loadCreatedUserList();
        displayGridValueToField();
        deleteFunction();
        onDeleteBtnClick();
        onBtnCancelClick();
        userListGrid.dataTable({
            "paging": false,
            "searching": false

        });
    }

    var userListGrid = $('#createdUserGrid');

    //to display list values to field
    function displayGridValueToField() {
        $('#createdUserGrid tbody').on('click', 'tr', function () {
            var adminStatus = $(this).find('input[id=adminStatus]').val();
            var data = {loginId: $(this).find('input[id=loginId]').val()};
            $.ajax({
                url: 'createUser/getGridListToField',
                type: 'GET',
                data: data,
                success: function (res) {
                    if (res.permissionType == 'B') {
                        $('#both').attr('checked', true)
                    } else if (res.permissionType == 'S') {
                        $('#saleOnly').attr('checked', true)
                    } else {
                        $('#entryOnly').attr('checked', true)
                    }
                    ;
                    populate(res);
                    $('#loginId').disableElements(true);
                    $('#createdDate').disableElements(true);
                    if (adminStatus == "Yes") {
                        $('#adminStatus').prop('checked', true);
                    }
                    $('#btnDelete').attr('disabled',false);
                }
            });
        });
    }

    //To save the data
    function save() {
        $('#btnSave').on('click', function () {
            $('#createUsersForm').validate({
                submitHandler: function (form) {
                    // handleCheckboxBeforeSave(form);
                    $.ajax({
                        url: 'createUser/save',
                        type: 'POST',
                        data: $(form).serializeArray(),
                        success: function (res) {
                            if (res.status == 1) {
                                $('#loginId').val('');
                                $('#loginId').disableElements(false);
                                $('#txtUserName').val('');
                                $('#txtPassword').val('');
                                $('#txtConfirmPassword').val('');
                                successMsg(res.text);
                                userListGrid.fnClearTable();
                                loadCreatedUserList();
                            } else {
                                errorMsg(res.text);
                            }

                        }
                    });
                }
            });
        })

    }


    //to format account status
    function formatAccountStatus(data, type, row) {
        if (data == 'A') {
            return "Active";
        } else if (data == 'I') {
            return "Inactive";
        } else {
            return "Closed";
        }
    }

    //to format account status
    function formatIsAdmin(data, type, row) {
        if (data == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }

    /**
     * on button new click.
     */
    $('#btnNew').on('click', function () {
        $('#btnDelete').attr('disabled',true);
        $('#loginId').attr('readonly', false);
    });

    //to validate that similar login id doesnt exists
    $('#loginId').on('blur', function () {
        var loginValue = $('#loginId').val();
        if (loginValue == "") {

        } else {
            $.ajax({
                url: 'createUser/isLoginIdAlreadyExists',
                type: 'GET',
                data: {
                    loginValue: loginValue
                },
                success: function (res) {
                    if (res.status == 0) {
                        errorMsg(res.text);
                        $('#loginId').val('');
                    } else {

                    }
                }
            });
        }
    })

    //to load the existing list of users
    function loadCreatedUserList() {
        $.ajax({
            url: 'createUser/getUserList',
            type: 'GET',
            success: function (res) {
                for (var i in res) {

                    var createDate = res[i].createdDate;
                    var userStatus = res[i].userStatus;
                    var adminStatus = res[i].adminStatus;
                    userListGrid.fnAddData(
                        [
                            '<input type="text" id="loginId" class="form-control" readonly="true" value="' + res[i].loginId + '">',
                            '<input type="text" id="txtUserName" class="form-control" readonly="true" value="' + res[i].txtUserName + '">',
                            '<input type="text" id="createdDate" class="form-control" readonly="true" value="' + formatAsDate(createDate) + '">',
                            '<input type="text" id="userStatus" class="form-control" readonly="true" value="' + formatAccountStatus(userStatus) + '">',
                            '<input type="text" id="adminStatus" class="form-control" readonly="true" value="' + formatIsAdmin(adminStatus) + '">',
                        ]);
                }
            }

        });
    }

    //To validate that the password entered is same
    $('#txtConfirmPassword').on('blur', function () {
        var password = $('#txtPassword').val();
        var confirmPassword = $('#txtConfirmPassword').val();

        if (password != confirmPassword) {
            errorMsg("Your password doesn't match, Please type again");
            $('#txtConfirmPassword').val('');
        }
    });
    function deleteFunction() {
        $('#btnDeleteConfirm').on('click', function () {
            var userId = $('#loginId').val();
            $.ajax({
                url: 'createUser/deleteUser',
                type: 'GET',
                data:{userId:userId},
                success: function (res) {
                    if (res.status == 0) {
                        swal({
                            title: res.text,
                            text: "Click OK to exit",
                            type: "success"
                        }, function (e) {
                            window.location.reload();
                        });
                    }
                }

            });

        })
    }

    function onDeleteBtnClick() {
        $('#btnDelete').on('click', function () {
            $('#myModal').modal('show');
        });
    }
    function onBtnCancelClick() {
        $('#btnCancel').on('click', function () {
            $('#myModal').modal('hide');
        });
    }

    return {
        init: init
    }
})();

$(document).ready(function () {

    createUser.init();
});