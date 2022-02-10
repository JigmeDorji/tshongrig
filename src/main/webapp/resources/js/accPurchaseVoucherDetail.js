/**
 * Created by Bcass Sawa on 5/19/2019.
 */

accPurchaseVoucherDetail = (function () {
    var totalDrAmount = 0;
    var totalCrAmount = 0;
    var selectedRow = 0;
    var voucherListTable;
    var voucherListGrid = $('#voucherListGrid');
    var rows = document.getElementById("voucherListGrid").children[1].children;
    var splitURL = location.search.split('accTypeId=')[1];
    var accTypeId = splitURL.split('&&accTypeId=')[0];


    function loadVoucherDetails() {
        $.ajax({
            url: 'accPurchaseVoucherDetail/getInventoryVoucherDetail',
            type: 'GET',
            data: {
                accTypeId: accTypeId
            },
            success: function (res) {
                totalDrAmount = res[0].totalDebit;
                totalCrAmount = res[0].totalCredit;
                var columnDef = [
                    {data: 'voucherCreatedDate'},
                    {data: 'voucherNo'},
                    {data: 'ledgerName'},
                    {data: 'voucherTypeId', class: 'hidden'},
                    {data: 'voucherTypeName'},
                    {
                        data: 'debitAmount',
                        class: 'right-align',
                        render: function (data) {
                            return spms.formatAmount(data.toFixed(2));
                        }
                    },
                    {
                        data: 'creditAmount',
                        class: 'right-align',
                        render: function (data) {
                            return spms.formatAmount(data.toFixed(2));
                        }
                    }
                ];
                voucherListTable = voucherListGrid.DataTable({
                    data: res,
                    info: false,
                    paging: false,
                    columns: columnDef
                });

            }
        }).then(function () {
            rows[0].style.backgroundColor = "#B0BED9";
            $('#openingBalance').val((0).toFixed(2));
            $('#ledgerName').val(parseInt(accTypeId)===parseInt(333)?"Purchase":"Sales");
            $('#totalDr').val(spms.formatAmount((totalDrAmount).toFixed(2)));
            $('#totalCr').val(spms.formatAmount((totalCrAmount).toFixed(2)));
            var netDrAmount = (totalDrAmount) + (0);

            if ((netDrAmount - (totalCrAmount)).toFixed(2) < 0) {
                $('#totalClosingBalanceCr').val(spms.formatAmount(Math.abs(netDrAmount - (totalCrAmount)).toFixed(2)));
            } else {
                $('#totalClosingBalanceDr').val(spms.formatAmount(Math.abs(netDrAmount - (totalCrAmount)).toFixed(2)));
            }

        });

        document.body.onkeydown = function (e) {
            e.preventDefault();
            rows[selectedRow].style.backgroundColor = "#FFFFFF";
            if (e.keyCode == 38) {
                selectedRow--;
            } else if (e.keyCode == 40) {
                selectedRow++;
            } else if (e.keyCode == 13) {


                window.location.href = spms.getUrl() + 'voucherCreation?voucherNo=' + rows[selectedRow].cells[1].innerHTML + '&&voucherTypeId=' + rows[selectedRow].cells[3].innerHTML;
            }

            if (selectedRow >= rows.length) {
                selectedRow = 0;
            } else if (selectedRow < 0) {
                selectedRow = rows.length - 1;
            }

            rows[selectedRow].style.backgroundColor = "#B0BED9";
        };
        rows[0].style.backgroundColor = "#B0BED9";
    }

    return {
        loadVoucherDetails: loadVoucherDetails
    }
})();
$(document).ready(function () {

    accPurchaseVoucherDetail.loadVoucherDetails();
});