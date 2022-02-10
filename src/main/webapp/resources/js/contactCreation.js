/**
 * Created by Phurba Wangdi on, 14-Apr-2016.
 */
contactCreation = (function () {
    function init() {

        save();
        loadExistingContactList();
        displayGridValueToField();
        deleteContactInformation();
        contactCreationGrid.dataTable({
            "paging": false,
            "searching": false

        });

    }

    var contactCreationGrid = $('#contactCreationGrid');

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $(form).find('input[type="checkbox"]').each(function () {
                    if ($(this).is(':checked')) {
                        $(this).next('input[type="hidden"]').val('A');
                    } else {
                        $(this).next('input[type="hidden"]').val('I');
                    }
                });
                $.ajax({
                    url: 'contactCreation/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 1) {
                            $(form)[0].reset();
                            successMsg(res.text);
                            contactCreationGrid.fnClearTable();
                            loadExistingContactList();
                            $('#btnDeleteContactInfo').attr('disabled', true);
                        }
                    }
                });
            }
        });
    }


    //to load the list of existing station list
    function loadExistingContactList() {
        $.ajax({
            url: 'contactCreation/getExistingContactList',
            type: 'GET',
            success: function (res) {
                var serialNo = 0;
                for (var i in res) {

                    serialNo = serialNo + 1;
                    contactCreationGrid.fnAddData(
                        [
                            '<input type="text" id="serialNo" class="form-control" readonly="true" value="' + serialNo + '">',
                            '<input type="hidden" id="contactId" class="form-control" readonly="true" value="' + res[i].contactId + '">' +
                            '<input type="text" id="contactName" class="form-control" readonly="true" value="' + res[i].contactName + '">',
                            '<input type="text" id="contactDesignation" class="form-control" readonly="true" value="' + res[i].contactDesignation + '">',
                            '<input type="hidden" id="contactAgencyId" class="form-control" readonly="true" value="' + res[i].contactAgencyId + '">' +
                            '<input type="text" id="contactAgencyName" class="form-control" readonly="true" value="' + res[i].contactAgencyName + '">',
                            '<input type="hidden" id="dzongkhagId" class="form-control" readonly="true" value="' + res[i].dzongkhagId + '">' +
                            '<input type="text" id="dzongkhagName" class="form-control" readonly="true" value="' + res[i].dzongkhagName + '">',
                            '<input type="text" id="contactMobileNo" class="form-control" readonly="true" value="' + res[i].contactMobileNo + '">',
                            '<input type="text" id="contactPhoneNo" class="form-control" readonly="true" value="' + res[i].contactPhoneNo + '">',
                            '<input type="text" id="contactEmailId" class="form-control" readonly="true" value="' + res[i].contactEmailId + '">',
                        ]);
                }
            }
        });
    }

    //to display list values to field
    function displayGridValueToField() {
        $('#contactCreationForm tbody').on('click', 'tr', function () {
            var noMatch = $('#contactCreationForm tbody').find('td').first().html();
            if (noMatch == 'No data available in table') {

            } else {
                var contactId = $(this).find('input[id=contactId]').val();
                var contactName = $(this).find('input[id=contactName]').val();
                var contactDesignation = $(this).find('input[id=contactDesignation]').val();
                var contactAgencyId = $(this).find('input[id=contactAgencyId]').val();
                var contactAgencyName = $(this).find('input[id=contactAgencyName]').val();
                var contactMobileNo = $(this).find('input[id=contactMobileNo]').val();
                var contactPhoneNo = $(this).find('input[id=contactPhoneNo]').val();
                var contactEmailId = $(this).find('input[id=contactEmailId]').val();
                var dzongkhagId = $(this).find('input[id=dzongkhagId]').val();

                $('#contactId').val(contactId);
                $('#contactName').val(contactName);
                $('#contactDesignation').val(contactDesignation);
                $('#contactAgencyId').val(contactAgencyId);
                $('#contactAgencyName').val(contactAgencyName);
                $('#contactMobileNo').val(contactMobileNo);
                $('#contactPhoneNo').val(contactPhoneNo);
                $('#contactEmailId').val(contactEmailId);
                $('#dzongkhagId').val(dzongkhagId);

                $('#btnDeleteContactInfo').attr('disabled', false);
            }
        });
    }

    $('#btnReset').on('click', function () {
        $('#btnDeleteContactInfo').attr('disabled', true);
    })

    $('#contactEmailId').on('blur', function (e) {
        var email = $(this).val();
        var emailReg =
            /^[a-z][a-z|0-9|]*([_][a-z|0-9]+)*([.][a-z|0-9]+([_][a-z|0-9]+)*)?@[a-z][a-z|0-9|]*\.([a-z][a-z|0-9]*(\.[a-z][a-z|0-9]*)?)$/;
        if ($('#contactCreationForm')) {
            if (email == "" || email == null) {
                e.preventDefault();
            } else if (this.readOnly) {
                e.preventDefault();
            } else if (!emailReg.test(email)) {
                $('#contactEmailId').val('').focus();
                errorMsg("Please enter valid email id", $(this));
            } else {
                $('.isEnable').each(function () {
                    $(this).removeClass('error');
                });
            }
        }
    });

    //to delete road block information
    function deleteContactInformation() {
        $('#btnDeleteContactInfo').on('click', function (e) {
            swal({
                title: "Are you Sure?",
                text: " You will not be able to recover this information",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                closeOnConfirm: false
            }, function () {
                deleteContactInformationCallBack();
            });

        });
    }

    //call function to confirm and delete road block information
    function deleteContactInformationCallBack() {
        var form = $('#contactCreationForm');
        var data = $(form).serializeArray();
        var url = 'contactCreation/deleteContactInformation';
        $.ajax({
            url: url,
            type: "POST",
            data: data,
            success: function (res) {
                if (res.status == 1) {
                    successMsg(res.text);
                    $(form)[0].reset();
                    contactCreationGrid.fnClearTable();
                    loadExistingContactList();
                    $('#btnDeleteContactInfo').attr('disabled', true);
                }
                else if (res.status == 0) {
                    $('.globalForm')[0].reset();
                    errorMsg(res.text);
                }

            }
        });
    }



    return {
        init: init,
        deleteContactInformation:deleteContactInformation
    }
})();

$(document).ready(function () {
    contactCreation.init();
    contactCreation.deleteContactInformation();
});