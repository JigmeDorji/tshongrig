/**
 * Created by SonamPC on 12-Dec-16.
 */

saleRecord = (function () {
    //To save the data
    function generateReport() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'saleRecord/generateReport',
                    type: 'POST',
                    data: $(form).serializeArray()
                });
            }
        });

    }

    return {
        //generateReport:generateReport


    }
})
();

$(document).ready(function () {
    //saleRecord.generateReport();
});
