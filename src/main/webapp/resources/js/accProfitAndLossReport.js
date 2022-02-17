/**
 * Created by jigmePc on 5/17/2019.
 */

accProfitAndLossReport = (function () {

    var start;
    var sibling;
    var sibling_length;
    var income = $('#income');
    var expense = $('#expense');
    var baseURL = 'accProfitAndLossReport/';


    function getProfitAndLossDetails(fromDate, toDate) {
        if (typeof fromDate === '' || typeof fromDate === 'undefined') {
            fromDate = $('#fromDate').val()
        }
        if (typeof toDate === '' || typeof toDate === 'undefined') {
            toDate = $('#toDate').val()
        }
        if (fromDate != '' && toDate != '') {
            $.ajax({
                url: baseURL + 'getProfitAndLossDetails',
                type: 'GET',
                data: {fromDate: fromDate, toDate: toDate},
                success: function (res) {

                    $('#pnlTable').dataTable().fnDestroy();

                    var columnDef = [
                        {data: 'ledgerName', class: "ledgerName"},
                        {
                            data: 'amount', class: "amount",
                            render: function (data) {
                                return spms.formatAmount(data.toFixed(2));
                            }
                        },
                        {data: 'isTopParent', class: 'hidden isTopParent'},
                        {data: 'accTypeId', class: 'hidden accTypeId'}
                    ];

                    $('#pnlTable').DataTable({
                        data: res,
                        sorting: false,
                        paging: false,
                        info: false,
                        searching: false,
                        ordering: false,
                        columns: columnDef,
                        /* dom: 'Bfrtip',
                         buttons: [{
                             extend: 'pdf',
                             download: 'open',
                             pageSize: 'B5',
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
                             extend: 'excelHtml5',
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
                        'rowCallback': function (row) {
                            if (parseFloat($(row).children(':nth-child(2)').text()) === parseFloat(0.00)) {
                                $(row).hide();
                            }
                            if ($(row).children(':nth-child(3)').text() == 'true') {
                                $(row).children(':nth-child(2)').addClass('right-align');
                                $(row).children(':nth-child(1)').addClass('childText left-align');
                            } else {
                                $(row).children(':nth-child(2)').addClass('parentText right-align');
                                $(row).children(':nth-child(1)').addClass('parentText left-align');
                            }
                        }
                    });
                }
            }).then(function () {
                start = $('#start');
                start.addClass('myGlower');
            });
        }
    }


    $(document).keydown(function (e) {

        let is_first_row = start.parent().is("tr:first-child");
        let is_last_row = start.parent().is("tr:last-child");
        let is_first_table = start.closest(".navigatable_table").is(".navigatable_table:first-child");
        let is_last_table = start.closest(".navigatable_table").is(".navigatable_table:first tr td:last-of-type");

        // BEGIN handle up arrow
        if (e.which === 38) {
            // BEGIN up arrow variables
            let idx = start.index();
            let previous_row = start.parent().prev("tr");
            let previous_row_length = previous_row.length;
            if (previous_row_length !== 0) {
                sibling = previous_row.children().eq(idx);
                selectedSibling(sibling);
            } else if (previous_row_length === 0) {
                if (is_first_table === false) {
                    sibling = start.closest(".navigatable_table")
                        .prev(".navigatable_table").find("tr:last").find("td").eq(idx);
                    selectedSibling(sibling);
                }
            }
        } else if (e.which === 40) {
            // BEGIN down arrow variables
            idx = start.index();
            let nextRow = start.parent().next("tr");
            let nextRow_length = nextRow.length;

            if (nextRow_length !== 0) {
                sibling = nextRow.children().eq(idx);
                selectedSibling(sibling);
            } else if (nextRow_length === 0) {

                sibling = start.closest(".navigatable_table")
                    .find("tr td:first");
                selectedSibling(sibling);
            }
        } else if (e.which === 37) {
            sibling = start.prev("td");
            sibling_length = sibling.length;
            if (sibling.length !== 0) {
                if (start.hasClass("ledgerName")) {
                    if (is_last_row) {
                        sibling = start.closest(".navigatable_table")
                            .find("tr td:first");
                    } else {
                        sibling = start.parent().prev("tr").find("td:last-child");
                    }
                }
                selectedSibling(sibling);
            } else if (sibling_length === 0) {
                if (is_first_row === false) {
                    sibling = start.parent().prev("tr").find("td:last-child");
                    selectedSibling(sibling);
                }
            }
        } else if (e.which === 39) {
            sibling = start.next("td");
            // BEGIN if there is a next td
            if (sibling.length !== 0) {
                if (start.hasClass("amount")) {
                    if (is_last_row) {
                        sibling = start.closest(".navigatable_table")
                            .find("tr td:first");
                    } else {
                        sibling = start.parent().next("tr").find("td:first-child");
                    }
                }
                selectedSibling(sibling);
            } else if (sibling_length === 0) {
                // BEGIN if there is a next row, move to the row below
                if (is_last_row === false) {
                    sibling = start.parent().next("tr").find("td:first-child");
                    selectedSibling(sibling);
                } else if (is_last_row === true) {
                    if (is_last_table === false) {
                    }
                }
            }
        } else if (e.which === 13) {
            let isTopParent = start.closest(".navigatable_table tr").find('.isTopParent').text();
            let ledgerName = start.closest(".navigatable_table tr").find('.ledgerName').text();
            let accTypeId = start.closest(".navigatable_table tr").find('.accTypeId').text();
            let ledgerId = start.closest(".navigatable_table tr").find('.ledgerId').text();

            if (accTypeId !== '' && isTopParent === 'true') {
                window.location.href = spms.getUrl() + 'ledgerGroupList?accTypeId='
                    + accTypeId + '&&ledgerName=' + ledgerName + '&&report=' + "PL";
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

    function fetchPNLDetailsByDate() {
        $('#fromDate').on('change', function () {
            getProfitAndLossDetails($(this).val(), $('#toDate').val());
        });

        $('#toDate').on('change', function () {
            getProfitAndLossDetails($('#fromDate').val(), $(this).val());
        });
    }

    return {
        getProfitAndLossDetails: getProfitAndLossDetails,
        fetchPNLDetailsByDate: fetchPNLDetailsByDate
    }

})
();

$(document).ready(function () {
    accProfitAndLossReport.getProfitAndLossDetails();
    accProfitAndLossReport.fetchPNLDetailsByDate();
});

