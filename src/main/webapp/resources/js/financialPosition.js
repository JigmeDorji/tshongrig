/**
 * Created by jigme.dorji on 5/21/2022.
 */

financialPosition = (function () {
    let start;
    let sibling;
    let sibling_length;
    let baseURL = 'financialPosition/';
    let trialBalanceGrid = $('#trialBalanceGrid');

    function getFinancialPositionData(fromDate, toDate) {
        let totalAssets = 0;
        let totalLiability = 0;
        if (typeof fromDate === '' || typeof fromDate === 'undefined') {
            fromDate = $('#fromDate').val()
        }
        if (typeof toDate === '' || typeof toDate === 'undefined') {
            toDate = $('#toDate').val()
        }
        if (fromDate !== '' && toDate !== '') {
            let pNLAmount=0;
            let openingBalDiff=0;

            //Get PNL Amount
            $.ajax({
                url: baseURL + 'getPNLAmt',
                type: 'GET',
                data: {fromDate: fromDate, toDate: toDate},
                async: false,
                success: function (res) {
                    pNLAmount=res;
                }
            })

            //financial position data
            $.ajax({
                url: baseURL + 'getFinancialPositionData',
                type: 'GET',
                data: {fromDate: fromDate, toDate: toDate},
                async: false,
                success: function (res) {
                    let totalDrAmount = 0;
                    let totalCrAmount = 0;

                    //trialBalanceGrid.fnDestroy();
                    let columnDef = [
                        {data: 'particular', class: 'text-left particular'},
                        {
                            data: 'drAmount', class: 'text-right',
                            render: function (data, type, row) {
                                let drAmount = row.drAmount;
                                let crAmount = row.crAmount;
                                let amount = 0;
                                if (row.groupId === 1 || row.groupId === 2 ||row.groupId == null) {
                                    if (drAmount !== null) {
                                        amount = amount + (drAmount);
                                    }
                                    if (crAmount !== null) {
                                        amount = amount + (crAmount);
                                    }

                                    if (row.groupLevel === 1 && row.particular!=="Total Assets") {
                                        totalAssets = totalAssets + amount;

                                    }
                                }
                                if (row.particular === "Total Assets") {
                                    amount = totalAssets;
                                }

                                if (row.groupId === 3 || row.groupId === 4 || row.groupId === 5) {
                                    if (drAmount !== null) {
                                        amount = amount + (-1 * drAmount);
                                    }
                                    if (crAmount !== null) {
                                        amount = amount + (crAmount);
                                    }
                                    if (row.groupLevel === 1) {
                                        totalLiability = totalLiability + amount;
                                    }
                                }
                                if (row.particular === "Income & Expenditure") {
                                    amount = pNLAmount;
                                }


                                if (row.particular === "Opening Balance Difference" && row.crAmount!==null) {
                                    amount = amount + drAmount==null?0:drAmount+crAmount==null?0:crAmount;
                                    openingBalDiff=crAmount;
                                }
                                if (row.particular === "Total Liability") {
                                    amount = totalLiability+pNLAmount+openingBalDiff;
                                }


                                amount=amount==null?0:amount;
                                return spms.formatAmount(amount.toFixed(2));
                            }
                        },
                        {data: 'groupLevel', class: 'hidden isTopParent'},
                        {data: 'accTypeId', class: 'hidden accTypeId'}

                    ];
                    trialBalanceGrid.DataTable({
                        data: res,
                        columns: columnDef,
                        sorting: false,
                        paging: false,
                        info: false,
                        searching: false,
                        ordering: false,
                        destroy: true,
                        'rowCallback': function (row, data) {
                            if (parseInt($(row).children(':nth-child(3)').text()) === 1) {

                                // if ($(row).children(':nth-child(3)').text() !== '') {
                                //     if ($(row).children(':nth-child(1)').text() === "Opening Balance Difference") {
                                //         // totalCrAmount = totalCrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(3)').text()));
                                //     }
                                // }
                                // if ($(row).children(':nth-child(2)').text() !== '') {
                                //     if ($(row).children(':nth-child(1)').text() === "Opening Balance Difference") {
                                //         // totalDrAmount = totalDrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(2)').text()));
                                //     }
                                // }
                                $(row).children(':nth-child(1)').addClass('parentText text-left');
                                $(row).children(':nth-child(2)').addClass('parentText text-right');
                                $(row).children(':nth-child(3)').addClass('parentText text-right');
                            } else {

                                // if ($(row).children(':nth-child(3)').text() !== '') {
                                //     totalCrAmount = totalCrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(3)').text()));
                                // }
                                // if ($(row).children(':nth-child(2)').text() !== '') {
                                //     totalDrAmount = totalDrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(2)').text()));
                                // }
                                $(row).children(':nth-child(1)').addClass('childText');
                            }
                        }
                    });
                }
            }).then(function () {
                //_load();
                start = $('#start');
                start.addClass('myGlower');
            });
        }
    }

    function fetchTrialBalanceDetailsByDate() {
        $('#fromDate').on('change', function () {
            getFinancialPositionData($(this).val(), $('#toDate').val());
        });

        $('#toDate').on('change', function () {
            getFinancialPositionData($('#fromDate').val(), $(this).val());
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
            if (sibling.length != 0) {
                console.log("there is a next td (sibling_length is not 0)");
                selectedSibling(sibling);
            } else if (sibling_length === 0) {
                // BEGIN if there is a next row, move to the row below
                if (is_last_row === false) {
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
        } else if (e.which == '13') {
            var isTopParent = start.closest(".navigatable_table tr").find('.isTopParent').text();
            //alert(isTopParent);
            //var accTypeId = start.closest(".navigatable_table tr").find('.accTypeId').text();
            //var isLedgerGroup = start.closest(".navigatable_table tr").find('.isLedgerGroup').text();
            //var ledgerId = start.closest(".navigatable_table tr").find('.ledgerId').text();
            var ledgerName = start.closest(".navigatable_table tr").find('.particular').text();
            var accTypeId = start.closest(".navigatable_table tr").find('.accTypeId').text();
            if (isTopParent == 2) {
                if (ledgerName === "Inventory") {
                    window.location.href = spms.getUrl() + 'viewItem';
                } else {
                    window.location.href = spms.getUrl() + 'ledgerGroupList?accTypeId=' + accTypeId + '&&ledgerName=' + ledgerName + '&&report=' + "BS";
                }
                //if (parseInt(accTypeId) === parseInt(111)) {
                //    window.location.href = spms.getUrl() + 'viewItem';
                //} else if (isLedgerGroup) {
                //    window.location.href = spms.getUrl() + 'ledgerGroupList?accTypeId=' + accTypeId + '&&ledgerName=' + ledgerName;
                //}
                //else {
                //    window.location.href = spms.getUrl() + 'voucherGroupList?isParentAccount=' + isTopParent + '&&accTypeId=' + accTypeId + '&&ledgerId=' + ledgerId + '&&ledgerName=' + ledgerName;
                //}
            }
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

    return {
        getFinancialPositionData: getFinancialPositionData,
        fetchTrialBalanceDetailsByDate: fetchTrialBalanceDetailsByDate
    }

})();

$(document).ready(function () {
    financialPosition.getFinancialPositionData();
    financialPosition.fetchTrialBalanceDetailsByDate();

});



