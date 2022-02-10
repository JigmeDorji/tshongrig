/**
 * Created by Bikash Rai on 11/14/2020.
 */
yearClosing = (function () {
    function performYearClosing() {
        $('#btnYearClose').on('click', function () {
            $('.globalForm').validate({
                submitHandler: function (form) {
                    swal({
                        title: "Confirmation",
                        text: "Are you sure that you want to proceed with year closing ?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Confirm",
                        cancelButtonText: "Cancel",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            $.ajax({
                                url: 'yearClosing/performYearClosing',
                                type: 'POST',
                                data: $(form).serializeArray(),
                                success: function (res) {
                                    if (res.status === 1) {
                                        successMsg(res.text, function () {
                                            window.location = '/logout';
                                        });
                                    } else {
                                        errorMsg(res.text);
                                    }
                                }
                            });
                        } else {
                            swal("Cancelled", "You have cancelled the operation.", "warning");
                        }
                    });
                }
            });
        })
    }


    return {
        performYearClosing: performYearClosing
    }
})();

$(document).ready(function () {
    yearClosing.performYearClosing();
});
