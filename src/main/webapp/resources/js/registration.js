
registration = (function () {
    function saveCompanyDetails() {
        $('.registrationForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'registration/saveCompanyDetails',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg2(res.text);
                                $('.field').val('');
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

    return {
        saveCompanyDetails: saveCompanyDetails,
    }
})();
$(document).ready(function () {
    registration.saveCompanyDetails();
});

