openingBalanceForRawMaterial = (function () {

    function init() {
        save();

    }

    //To save the data
    function save() {
        
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    // url: 'openingBalanceForRawMaterial/save',
                    url: 'purchasesForRawMaterial/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 1) {
                            swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                }
                            )
                        }
                    }
                });
            }
        });
    }


    return {
        init: init,
        save: save,

    }
})();

$(document).ready(function () {
    openingBalanceForRawMaterial.init();
});
