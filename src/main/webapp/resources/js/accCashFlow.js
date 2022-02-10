/**
 * Created by jigmePc on 8/4/2019.
 */


accCashFlow = (function () {
    var baseURL = 'accCashFlow/';
    var cashFlowGrid = $('#cashFlowGrid');

    function getCashFlow(fromDate, toDate) {

        if (typeof fromDate === '' || typeof fromDate === 'undefined') {
            fromDate = $('#fromDate').val()
        }
        if (typeof toDate === '' || typeof toDate === 'undefined') {
            toDate = $('#toDate').val()
        }
        if (fromDate != '' && toDate != '') {
            $.ajax({
                url: baseURL + 'getCashFlow',
                type: 'GET',
                async: false,
                data: {fromDate: fromDate, toDate: toDate},
                success: function (res) {
                    //trialBalanceGrid.fnDestroy();
                    cashFlowGrid.dataTable().fnDestroy();

                    var columnDef = [
                        {data: 'isTopParent', class: 'hidden'},
                        {data: 'particular', class: 'left-align'},
                        {
                            data: 'amount', class: 'right-align',
                            render: function (data) {
                                return data !== 0 ? spms.formatAmount(data.toFixed(2)) : '';
                            }
                        }

                    ];
                    cashFlowGrid.DataTable({
                        data: res,
                        columns: columnDef,
                        sorting: false,
                        paging: false,
                        info: false,
                        searching: false,
                        ordering: false,
                        'rowCallback': function (row, data) {
                            if ($(row).children(':nth-child(1)').text() === 'Y') {
                                $(row).children(':nth-child(2)').addClass('parentText');
                                $(row).children(':nth-child(3)').addClass('parentText');
                            } else {
                                $(row).children(':nth-child(2)').addClass('childText');
                                $(row).children(':nth-child(3)').addClass('childText');
                            }
                        }
                    });
                }
            })
        }
    }

    function fetchCashFlowDetailsByDate() {
        $('#fromDate').on('change', function () {
            getCashFlow($(this).val(), $('#toDate').val());
        });

        $('#toDate').on('change', function () {
            getCashFlow($('#fromDate').val(), $(this).val());
        });
    }


    return {
        getCashFlow: getCashFlow,
        fetchCashFlowDetailsByDate: fetchCashFlowDetailsByDate
    }

})
();

$(document).ready(function () {
    accCashFlow.getCashFlow();
    accCashFlow.fetchCashFlowDetailsByDate();

});



