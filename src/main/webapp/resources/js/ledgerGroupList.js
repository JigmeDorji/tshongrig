/**
 * Created by jigmePc on 5/20/2019.
 */

ledgerGroupList = (function () {

    var idx;
    var start;
    var sibling;
    var sibling_length;
    var baseURL = 'ledgerGroupList/';
    var totalLiabilities = 0;
    var totalAssets = 0;

    var splitURL = location.search.split('?')[1];
    var accTypeId = splitURL.split('accTypeId=')[1].split('&&')[0];
    var ledgerName = splitURL.split('ledgerName=')[1].split('&&')[0];
    var report = splitURL.split('report=')[1].split('&&')[0];
    ledgerName = ledgerName.replace(/%20/g, " ");

    var span = document.getElementById("ledgerGroupHeadName");
    span.textContent = ledgerName;

    function getLedgerGroupList() {
        $.ajax({
            url: baseURL + 'getLedgerGroupList',
            type: 'GET',
            data: {accountTypeId: accTypeId},
            async: false,
            success: function (res) {
                var totalAmount = 0;
                $('#ledgerGroupList').dataTable().fnDestroy();
                var columnDef = [
                    {data: 'ledgerId', class: 'text-center ledgerId hidden'},
                    {data: 'particular', class: 'text-left ledgerName'},
                    {
                        data: 'amount', class: 'text-right',
                        render: function (data) {
                            if (data === 0) {
                                return ''
                            } else {
                                const  absValue=Math.abs(data);
                                return spms.formatAmount(absValue.toFixed(2));
                            }
                        }
                    }
                ];
                $('#ledgerGroupList').DataTable({
                    data: res,
                    sorting: false,
                    paging: false,
                    info: false,
                    searching: false,
                    ordering: false,
                    columns: columnDef,
                    'rowCallback': function (row, data) {
                        if ($(row).children(':nth-child(3)').text() === '') {
                            $(row).hide();
                        }
                    }
                });
                //$('#totalLiabilities').val(spms.formatAmount(totalAmount.toFixed(2)));
                //totalLiabilities = totalAmount.toFixed(2);
            }
        }).then(function () {
            //_load();
            start = $('#start');
            start.addClass('myGlower');
        });
    }


    $(document).keydown(function (e) {
        var is_first_row = start.parent().is("tr:first-child");
        var is_last_row = start.parent().is("tr:last-child");
        var is_first_table = start.closest(".navigatable_table").is(".navigatable_table:first-child");
        var is_last_table = start.closest(".navigatable_table").is(".navigatable_table:first tr td:last-of-type");

        // BEGIN handle up arrow
        if (e.which == '38') {
            // BEGIN up arrow variables
            var idx = start.index();
            var previous_row = start.parent().prev("tr");
            var previous_row_length = previous_row.length;
            if (previous_row_length != 0) {
                sibling = previous_row.children().eq(idx);
                selectedSibling(sibling);
            } else if (previous_row_length === 0) {
                if (is_first_table === false) {
                    console.log("this td is not in first table (is_first_table is false)");
                    sibling = start.closest(".navigatable_table").prev(".navigatable_table").find("tr:last").find("td").eq(idx);
                    selectedSibling(sibling);
                }
            }
        } else if (e.which == '40') {
            // BEGIN down arrow variables
            idx = start.index();
            var nextrow = start.parent().next("tr");
            var nextrow_length = nextrow.length;
            if (nextrow_length != 0) {
                sibling = nextrow.children().eq(idx);
                selectedSibling(sibling);
            } else if (nextrow_length === 0) {
                // BEGIN if there is a next table, move to the next table, at the same index
                if (is_last_table === false) {
                    console.log("this td is not in last table (is_last_table is false)");
                    sibling = start.closest(".navigatable_table").next(".navigatable_table").find("tr:first").find("td").eq(idx);
                    selectedSibling(sibling);
                }
            }
        } else if (e.which == '37') {
            sibling = start.prev("td");
            sibling_length = sibling.length;
            if (sibling.length != 0) {
                selectedSibling(sibling);
            } else if (sibling_length === 0) {
                if (is_first_row === false) {
                    sibling = start.parent().prev("tr").find("td:last-child");
                    selectedSibling(sibling);
                } else if (is_first_row === true) {
                    console.log("this td is in first row (is_first_row is true)");
                    if (is_first_table === false) {
                        console.log("this td is not in first table (is_first_table is false)");
                        sibling = start.closest(".navigatable_table").prev(".navigatable_table").find("td:last");
                        selectedSibling(sibling);
                    }
                    // END if there is table before
                }
            }
        } else if (e.which == '39') {
            sibling = start.next("td");
            // BEGIN if there is a next td
            if (sibling.length !== 0) {
                console.log("there is a next td (sibling_length is not 0)");
                selectedSibling(sibling);
            } else if (sibling_length === 0) {
                console.log("there is no next td (sibling_length is 0)");
                // BEGIN if there is a next row, move to the row below
                if (is_last_row === false) {
                    console.log("this td is not in last row (is_last_row is false)");
                    sibling = start.parent().next("tr").find("td:first-child");
                    selectedSibling(sibling);
                } else if (is_last_row === true) {
                    if (is_last_table === false) {
                        console.log("this td is not in last table (is_last_table is false)");
                        sibling = start.closest(".navigatable_table").next(".navigatable_table").find("td:first");
                        selectedSibling(sibling);
                    }
                }
            }
        } else if (e.which === 13) {
            var isTopParent = start.closest(".navigatable_table tr").find('.isTopParent').text();
            var accTypeId = start.closest(".navigatable_table tr").find('.accTypeId').text();
            var ledgerId = start.closest(".navigatable_table tr").find('.ledgerId').text();
            var ledgerName = start.closest(".navigatable_table tr").find('.ledgerName').text();
            if (ledgerId === '') {
                errorMsg("You cannot view the detail, since its not a ledger.")
                return false;
            }
            window.location.href = spms.getUrl() + 'voucherGroupList?ledgerId=' + ledgerId + '&&ledgerName=' + ledgerName;
            // if (isTopParent === 'false') {
            //     if (parseInt(accTypeId) === parseInt(111)) {
            //         window.location.href = spms.getUrl() + 'viewItem';
            //     } else {
            //         window.location.href = spms.getUrl() + 'voucherGroupList?ledgerId=' + ledgerId + '&&ledgerName=' + ledgerName;
            //         // window.location.href = spms.getUrl() + 'voucherGroupList?isParentAccount=' + isTopParent + '&&accTypeId=' + accTypeId + '&&ledgerId=' + ledgerId + '&&ledgerName=' + ledgerName;
            //     }
            // }
        }
    });


    $(document).on("click", ".navigatable_table td", function () {
        let $this = $(this);
        start.removeClass("myGlower");
        start.blur();
        $this.addClass("myGlower");
        $this.focus();
        start = $this;
    });

    function selectedSibling(sibling) {
        if (sibling != null) {
            start.focus();
            start.removeClass("myGlower");
            sibling.focus();
            sibling.addClass("myGlower");
            start = sibling;
        }
    }

    $('#previousPage').on('click', function () {
        if (report === 'BS') {
            window.location.href = spms.getUrl() + 'financialPosition';
        } else {
            window.location.href = spms.getUrl() + 'accProfitAndLossReport';
        }
    })

    return {
        getLedgerGroupList: getLedgerGroupList
    }

})();

$(document).ready(function () {
    ledgerGroupList.getLedgerGroupList();

});

