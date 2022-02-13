/**
 * Created by jigmePc on 5/20/2019.
 */

accBalanceSheetReport = (function () {

    var idx;
    var start;
    var sibling;
    var sibling_length;
    var baseURL = 'accBalanceSheetReport/';
    var date1 = new Date(new Date().getFullYear(), 0, 1);
    //$('#fromDate').val(date1);
    //$('#toDate').val(new Date());
    function getAccBalanceSheetReport(toDate) {
        if (typeof toDate === '' || typeof toDate === 'undefined') {
            toDate = $('#toDate').val()
        }
        if (toDate != '') {
            $('#balanceSheetTable').dataTable().fnDestroy();
            $.ajax({
                url: baseURL + 'getAccBalanceSheetReport',
                type: 'GET',
                async: false,
                data: {asOnDate: toDate},
                success: function (res) {
                    var columnDef = [
                        {data: 'particular', class: "particular"},
                        {
                            data: 'amount',
                            render: function (data) {
                                data = data != null ? data.toFixed(2) : 0.00;
                                return spms.formatAmount(data);
                            }
                        },
                        {data: 'groupLevel', class: 'hidden isTopParent'},
                        {data: 'accTypeId', class: 'hidden accTypeId'}
                    ];
                    $('#balanceSheetTable').DataTable({
                        data: res,
                        sorting: false,
                        paging: false,
                        info: false,
                        searching: false,
                        ordering: false,
                        columns: columnDef,
                        /*dom: 'Bfrtip',
                            buttons: [{
                                customize: function (doc) {
                                    doc.defaultStyle.alignment = 'right';
                                    doc.styles.tableHeader.alignment = 'center';
                                    doc.content[1].table.widths = ["*", "*"];
                                    var tblBody = doc.content[1].table.body;
                                    doc.content[1].layout = {
                                        hLineWidth: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 1 : 1;},
                                        vLineWidth: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 1 : 1;},
                                        hLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 'gray' : 'gray';},
                                        vLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 'gray' : 'gray';}
                                    };

                                },
                                extend: 'pdfHtml5',
                                // messageTop: "Financial Statement",
                                download: 'open',
                                pageSize: 'B5',
                                exportOptions: {
                                    columns: [0,1]
                                },
                                orientation: 'portrait',
                                // customize: function (doc) {
                                //     doc.defaultStyle.alignment = 'center';
                                //     doc.content[1].table.widths =
                                //         Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                                // },
                            }, {
                                extend: 'excelHtml5',
                                // messageTop: "Financial Statement",
                                download: 'open',
                                exportOptions: {
                                    columns: [0,1]
                                },
                                orientation: 'portrait',
                                customize: function (doc) {
                                    doc.defaultStyle.alignment = 'center';
                                    doc.content[1].table.widths =
                                        Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                                },
                            }, {
                                extend: 'print',
                                // messageTop: "Financial Statement",
                                exportOptions: {
                                    columns: [0,1]
                                },
                            orientation: 'portrait',
                            customize: function (doc) {
                                doc.defaultStyle.alignment = 'center';
                                doc.content[1].table.widths =
                                    Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                            },
                        }],*/
                        'rowCallback': function (row, data) {
                            if(parseFloat($(row).children(':nth-child(2)').text())===parseFloat(0.00)){
                                $(row).hide();
                            }

                            if ($(row).children(':nth-child(3)').text() == 1) {
                                $(row).children(':nth-child(2)').addClass('parentText right-align');
                                $(row).children(':nth-child(1)').addClass('parentText left-align');
                            } else {
                                $(row).children(':nth-child(2)').addClass('right-align');
                                $(row).children(':nth-child(1)').addClass('childText left-align');
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


    $(document).keydown(function (e) {
        var is_first_row = start.parent().is("tr:first-child");
        var is_last_row = start.parent().is("tr:last-child");
        var is_first_table = start.closest(".navigation_table").is(".navigation_table:first-child");
        var is_last_table = start.closest(".navigation_table").is(".navigation_table:first tr td:last-of-type");

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
                    sibling = start.closest(".navigation_table").prev(".navigation_table").find("tr:last").find("td").eq(idx);
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
                    sibling = start.closest(".navigation_table").next(".navigation_table").find("tr:first").find("td").eq(idx);
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
                        sibling = start.closest(".navigation_table").prev(".navigation_table").find("td:last");
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
                        sibling = start.closest(".navigation_table").next(".navigation_table").find("td:first");
                        selectedSibling(sibling);
                    }
                }
            }
        } else if (e.which == '13') {
            var isTopParent = start.closest(".navigation_table tr").find('.isTopParent').text();
            //alert(isTopParent);
            //var accTypeId = start.closest(".navigation_table tr").find('.accTypeId').text();
            //var isLedgerGroup = start.closest(".navigation_table tr").find('.isLedgerGroup').text();
            //var ledgerId = start.closest(".navigation_table tr").find('.ledgerId').text();
            var ledgerName = start.closest(".navigation_table tr").find('.particular').text();
            var accTypeId = start.closest(".navigation_table tr").find('.accTypeId').text();
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


    $(document).on("click", ".navigation_table td", function () {
        var $this = $(this);
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

    function getFinancialStatement() {
        $('#fromDate').on('change', function () {
            getAccBalanceSheetReport($(this).val(), $('#toDate').val());
        });

        $('#toDate').on('change', function () {
            getAccBalanceSheetReport($('#fromDate').val(), $(this).val());
        });
    }


    return {
        getAccBalanceSheetReport: getAccBalanceSheetReport,
        getFinancialStatement: getFinancialStatement

    }

})();

$(document).ready(function () {
    // $('#toDate').datepicker('setStartDate', '04/01/2018');
    // $('#toDate').datepicker('setEndDate', '03/30/2019');
    accBalanceSheetReport.getAccBalanceSheetReport();
    accBalanceSheetReport.getFinancialStatement();
});

