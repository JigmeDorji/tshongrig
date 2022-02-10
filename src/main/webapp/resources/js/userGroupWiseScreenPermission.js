/**
 * Created by Yonten choden on, 29-Apr-2016.
 */
userGroupWiseScreenPermission = (function () {
    function init() {
        save();
    }

    var assignedScreenGrid = $('#assignedScreenGrid');
    var unAssignedScreenGrid = $('#unAssignedScreenGrid');

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $(form).find('input[type="checkbox"]').each(function () {
                    if ($(this).is(':checked')) {
                        $(this).next('input[type="hidden"]').val('true');
                    } else {
                        $(this).next('input[type="hidden"]').val('false');
                    }
                });

                $.ajax({
                    url: 'userGroupWiseScreenPermission/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {

                        if (res.status == 0) {
                            errorMsg(res.text);
                        }
                        else if (res.status == 1) {
                            if (res.status == 1) {
                                $(form)[0].reset();
                                assignedScreenGrid.find('tbody').empty();
                                unAssignedScreenGrid.find('tbody').empty();
                                successMsg(res.text);
                            }
                        }
                    }
                });
            }
        });
    }

    $('#groupId').on('change', function () {
        var groupID = $('#groupId').find('option:selected').val();

        if (groupID == '') {
            assignedScreenGrid.find('tbody').empty();
            unAssignedScreenGrid.find('tbody').empty();
        } else {
            assignedScreenGrid.find('tbody').empty();
            unAssignedScreenGrid.find('tbody').empty();
            loadScreenList(groupID);
            loadUnassignedScreenList(groupID);
        }
    });

    function loadScreenList(groupId) {
        $.ajax({
            url: 'userGroupWiseScreenPermission/getScreenList',
            type: 'GET',
            data: {groupId: groupId},
            success: function (res) {
                for (var i in res) {
                    var isViewAccessAllowed = res[i].isViewAccessAllowed ? 'checked=""' : '';
                    var isAddAccessAllowed = res[i].isAddAccessAllowed ? 'checked=""' : '';
                    var isEditAccessAllowed = res[i].isEditAccessAllowed ? 'checked=""' : '';
                    var isDeleteAccessAllowed = res[i].isDeleteAccessAllowed ? 'checked=""' : '';
                    var row;
                    row = '\
                   <tr>\
                   <td><input type="text" id="screenId" name="assignedList[' + i + '].screenId" class="form-control" readonly="true" value="' + res[i].screenId + '">\
                   </td>\
                   <td><input type="text" id="screenName" class="form-control" readonly="true" value="' + res[i].screenName + '">\
                   </td>\
                   <td><div align="center">\
                    <input type="checkbox" ' + isViewAccessAllowed + ' id="checkMe">\
                     <input type="hidden" id="permission" name="assignedList[' + i + '].isViewAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" ' + isAddAccessAllowed + ' id="checkMe">\
                     <input type="hidden" id="permission" name="assignedList[' + i + '].isAddAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" ' + isEditAccessAllowed + ' id="checkMe">\
                     <input type="hidden" id="permission" name="assignedList[' + i + '].isEditAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" ' + isDeleteAccessAllowed + ' id="checkMe">\
                     <input type="hidden" id="permission" name="assignedList[' + i + '].isDeleteAccessAllowed"/> \
                     </div></td>';

                    var tableBody = $('#assignedScreenGrid tbody');
                    var noMatch = $(tableBody).find('td').first().html();
                    if (noMatch == 'No data available in table')
                        $(tableBody).find('tr').remove();
                    $('#assignedScreenGrid').find('tbody').append(row);
                }
            }

        });
    }

    function loadUnassignedScreenList(groupID) {
        $.ajax({
            url: 'userGroupWiseScreenPermission/getUnAssignedScreenListScreenList',
            type: 'GET',
            data: {groupID: groupID},
            success: function (res) {
                for (var i in res) {
                    var row;
                    row = '\
                   <tr>\
                   <td><input type="text" id="screenId" name="unassignedList[' + i + '].screenId" class="form-control" readonly="true" value="' + res[i].screenId + '">\
                   </td>\
                   <td><input type="text" id="screenName" class="form-control" readonly="true" value="' + res[i].screenName + '">\
                   </td>\
                   <td><div align="center">\
                    <input type="checkbox" id="checkMe">\
                     <input type="hidden" id="permission" name="unassignedList[' + i + '].isViewAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" id="checkMe">\
                     <input type="hidden" id="permission" name="unassignedList[' + i + '].isAddAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" id="checkMe">\
                     <input type="hidden" id="permission" name="unassignedList[' + i + '].isEditAccessAllowed"/> \
                     </div></td>\
                     <td><div align="center">\
                    <input type="checkbox" id="checkMe">\
                     <input type="hidden" id="permission" name="unassignedList[' + i + '].isDeleteAccessAllowed"/> \
                     </div></td>';

                    var tableBody = $('#unAssignedScreenGrid tbody');
                    var noMatch = $(tableBody).find('td').first().html();
                    if (noMatch == 'No data available in table')
                        $(tableBody).find('tr').remove();
                    $('#unAssignedScreenGrid').find('tbody').append(row);
                }
            }

        });
    }

    ////to validate that section name is not same
    //$('#sectionId').on('change', function(){
    //    var sectionId = $('#sectionId').find('option:selected').val();
    //    if(sectionId == ""){
    //
    //    }else{
    //        $.ajax({
    //            url: 'informationDescription/getSectionInformation',
    //            type: 'GET',
    //            data: {sectionId: sectionId},
    //            success: function (res) {
    //                populate(res);
    //            }
    //        });
    //    }
    //});

    return {
        init: init
    }
})();

$(document).ready(function () {
    userGroupWiseScreenPermission.init();
});