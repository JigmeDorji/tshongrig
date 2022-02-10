/**
 * Created by SonamPC on 12-Dec-16.
 */


barcode = (function () {
    function generateBarcode() {
        $('#generateBarcodeBtn').on('click', function () {
            var itemCode = $('#itemCode').val();
            var qty = $('#qty').val();
            $.ajax({
                url: 'barcode/generateBarcode',
                type: 'GET',
                data: {itemCode: itemCode, qty: qty},
                success: function (res) {
                }

            });


        })

    }

    return {
        generateBarcode: generateBarcode


    }
})();

$(document).ready(function () {
    // $('#itemCode').select2();
    barcode.generateBarcode();
});

