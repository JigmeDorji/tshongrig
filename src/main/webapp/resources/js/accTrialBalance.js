/**
 * Created by jigmePc on 7/30/2019.
 */

accTrialBalance = (function () {
    var baseURL = 'accTrialBalance/';
    var trialBalanceGrid = $('#trialBalanceGrid');

    function getTrialBalance(fromDate, toDate) {
        if (typeof fromDate === '' || typeof fromDate === 'undefined') {
            fromDate = $('#fromDate').val()
        }
        if (typeof toDate === '' || typeof toDate === 'undefined') {
            toDate = $('#toDate').val()
        }
        if (fromDate !== '' && toDate !== '') {
            $.ajax({
                url: baseURL + 'getTrialBalance',
                type: 'GET',
                data: {fromDate: fromDate, toDate: toDate},
                async: false,
                success: function (res) {
                    var totalDrAmount = 0;
                    var totalCrAmount = 0;
                    //trialBalanceGrid.fnDestroy();
                    var columnDef = [
                        {data: 'particular', class: 'text-left'},
                        {
                            data: 'drAmount', class: 'text-right',
                            render: function (data) {
                                return data !== null ? spms.formatAmount(data.toFixed(2)) : '';
                            }
                        },
                        {
                            data: 'crAmount', class: 'text-right',
                            render: function (data) {
                                return data !== null ? spms.formatAmount(data.toFixed(2)) : '';
                            }
                        },
                        {data: 'groupLevel', class: 'hidden isTopParent'}

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
                            if (parseInt($(row).children(':nth-child(4)').text()) === 1) {

                                if ($(row).children(':nth-child(3)').text() !== '') {
                                    if ($(row).children(':nth-child(1)').text() === "Opening Balance Difference") {
                                        totalCrAmount = totalCrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(3)').text()));
                                    }
                                }
                                if ($(row).children(':nth-child(2)').text() !== '') {
                                    if ($(row).children(':nth-child(1)').text() === "Opening Balance Difference") {
                                        totalDrAmount = totalDrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(2)').text()));
                                    }
                                }
                                $(row).children(':nth-child(1)').addClass('parentText text-left');
                                $(row).children(':nth-child(2)').addClass('parentText text-right');
                                $(row).children(':nth-child(3)').addClass('parentText text-right');
                            } else {

                                if ($(row).children(':nth-child(3)').text() !== '') {
                                    totalCrAmount = totalCrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(3)').text()));
                                }
                                if ($(row).children(':nth-child(2)').text() !== '') {
                                    totalDrAmount = totalDrAmount + parseFloat(spms.removeCommaSeparation($(row).children(':nth-child(2)').text()));
                                }
                                $(row).children(':nth-child(1)').addClass('childText');
                            }
                        }
                    });
                    $('#totalDrAmount').text(spms.formatAmount(totalDrAmount.toFixed(2)));
                    $('#totalCrAmount').text(spms.formatAmount(totalCrAmount.toFixed(2)));

                }
            })
        }
    }

    function fetchTrialBalanceDetailsByDate() {
        $('#fromDate').on('change', function () {
            getTrialBalance($(this).val(), $('#toDate').val());
        });

        $('#toDate').on('change', function () {
            getTrialBalance($('#fromDate').val(), $(this).val());
        });
    }

    return {
        getTrialBalance: getTrialBalance,
        fetchTrialBalanceDetailsByDate: fetchTrialBalanceDetailsByDate
    }

})();

$(document).ready(function () {
    accTrialBalance.getTrialBalance();
    accTrialBalance.fetchTrialBalanceDetailsByDate();

});


