/**
 * Created by jigme.dorji on 24-Apr-2020.
 */

userAccessPermission = (function () {

    let userAccessPermissionGrid = $('#userAccessPermissionGrid');

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $(form).find('input[type="checkbox"]').each(function () {
                    if ($(this).is(':checked')) {
                        $(this).next('input[type="hidden"]').val('Y');
                    } else {
                        $(this).next('input[type="hidden"]').val('N');
                    }
                });
                $.ajax({
                    url: 'userAccessPermission/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 0) {
                            errorMsg(res.text);
                        } else if (res.status == 1) {
                            loadScreenList($('#userRoleTypeId').val())
                            swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                loadScreenList($('#userRoleTypeId').val())
                            });
                        }
                    }
                });
            }
        });
    }

    $('#userRoleTypeId').on('change', function () {
        let userRoleTypeId = $('#userRoleTypeId').find('option:selected').val();
        if (userRoleTypeId == '') {
            userAccessPermissionGrid.find('tbody').empty();
            userAccessPermissionGrid.find('tbody').empty();
        } else {
            userAccessPermissionGrid.find('tbody').empty();
            userAccessPermissionGrid.find('tbody').empty();
            loadScreenList(userRoleTypeId);
        }
    });

    function loadScreenList(userRoleTypeId) {
        $.ajax({
            url: 'userAccessPermission/getScreenList',
            type: 'GET',
            data: {userRoleTypeId: userRoleTypeId},
            success: function (res) {

                $('#userAccessPermissionGrid tbody').empty();

                for (let i in res) {
                    let isScreenAccessAllowed = res[i].isScreenAccessAllowed == 'Y' ? 'checked=""' : '';
                    let isEditAccessAllowed = res[i].isEditAccessAllowed == 'Y' ? 'checked=""' : '';
                    let isDeleteAccessAllowed = res[i].isDeleteAccessAllowed == 'Y' ? 'checked=""' : '';
                    let isSaveAccessAllowed = res[i].isSaveAccessAllowed == 'Y' ? 'checked=""' : '';
                    let userAccessPermissionId = res[i].userAccessPermissionId == null ? '' : res[i].userAccessPermissionId;
                    let row;
                    row = '\
                   <tr>\
                        <td><input type="hidden" id="id" name="userAccessPermissionListDTO[' + i + '].userAccessPermissionId" class="form-control form-control-sm"  value="' + userAccessPermissionId + '"><input type="hidden" id="screenId" name="userAccessPermissionListDTO[' + i + '].screenId" class=" form-control form-control-sm" readonly="true" value="' + res[i].screenId + '">' + res[i].screenId + '</td>\
                        <td class="left-align"><input type="hidden" id="screenName" class="form-control form-control-sm" readonly="true" value="' + res[i].screenName + '">&nbsp;&nbsp;' + res[i].screenName + '</td>\
                        <td><div align="center">\
                        <input type="checkbox" ' + isScreenAccessAllowed + ' id="checkMe" class="isScreenAccessAllowed">\
                        <input type="hidden" id="permission" name="userAccessPermissionListDTO[' + i + '].isScreenAccessAllowed" /></div></td>\
                        <td><div align="center">\
                        <input type="checkbox" ' + isEditAccessAllowed + ' id="checkMe" class="isEditAccessAllowed">\
                        <input type="hidden" id="permission" name="userAccessPermissionListDTO[' + i + '].isEditAccessAllowed" /> \
                        </div></td>\
                        <td><div align="center">\
                        <input type="checkbox" ' + isDeleteAccessAllowed + ' id="checkMe" class="isDeleteAccessAllowed">\
                        <input type="hidden" id="permission" name="userAccessPermissionListDTO[' + i + '].isDeleteAccessAllowed" /> \
                        </div></td>\
                        <td><div align="center">\
                        <input type="checkbox" ' + isSaveAccessAllowed + ' id="checkMe" class="isSaveAccessAllowed">\
                        <input type="hidden" id="permission" name="userAccessPermissionListDTO[' + i + '].isSaveAccessAllowed" /> \
                        </div></td>\
                    </tr>';

                    let tableBody = $('#userAccessPermissionGrid tbody');
                    let noMatch = $(tableBody).find('td').first().html();
                    if (noMatch == 'No data available in table')
                        $(tableBody).find('tr').remove();
                    $('#userAccessPermissionGrid').find('tbody').append(row);
                }
            }

        });
    }

    $('#checkAllScreenAccessAllow').on('click', function () {
        checkAll('.isScreenAccessAllowed', $(this));
    });

    $('#checkAllEditAccessAllow').on('click', function () {
        checkAll('.isEditAccessAllowed', $(this))
    });
    $('#checkAllDeleteAccessAllow').on('click', function () {
        checkAll('.isDeleteAccessAllowed', $(this))
    });
    $('#checkAllSaveAccessAllow').on('click', function () {
        checkAll('.isSaveAccessAllowed', $(this))
    });


    function checkAll(checkAll, _this) {
        $('#userAccessPermissionGrid').find('tbody tr').each(function () {
            let selectedRow = $(this).closest('tr');
            if (_this.is(':checked')) {
                selectedRow.find(checkAll).prop('checked', true);
            } else {
                selectedRow.find(checkAll).prop('checked', false);
            }
        });
    }

    return {
        save: save
    }
})();

$(document).ready(function () {
    userAccessPermission.save();
});