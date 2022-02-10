/**
 * Created by yonten.choden on 27-Apr-16.
 */
/**
 * Created by yonten.choden on 26-Apr-16.
 */


changePassword = (function () {
    function init() {
        save();

    }

    /**
     * save.
     */
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'changePassword/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 0) {
                            errorMsg(res.text);
                            $('#oldPassword').val('');
                            $('#confirmPassword').val('');
                            $('#newPassword').val('');
                        } else if (res.status == 1) {
                            successMsg(res.text);
                            $('#oldPassword').val('');
                            $('#confirmPassword').val('');
                            $('#newPassword').val('');
                        }
                    }
                });
            }
        });
    }


    //To validate that the password entered is same
    $('#confirmPassword').on('blur', function () {
        var password = $(this).val();
        var confirmPassword = $('#newPassword').val();

        if (password != confirmPassword) {
            errorMsg("Your password doesn't match, Please type again");
            $('#confirmPassword').val('');
        }
    })



    return {
        init: init
    }
})();

$(document).ready(function () {
    changePassword.init();
});






