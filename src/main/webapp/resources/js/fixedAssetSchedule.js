/**
 * Created by Bikash Rai on 10/23/2021.
 */
fixedAssetSchedule = (function () {

    function onChangeAsOnDate() {
        $('#asOnDate').on('change', function () {
            loadInitialTable($(this).val())
        })
    }

    function loadInitialTable(asOnDate) {

        asOnDate = typeof asOnDate === 'undefined' ? $('#asOnDate').val() : asOnDate;
        $('.asOnStartFinancialYearText').text(asOnDate)

        $.ajax({
            url: 'fixedAssetSchedule/getFixedAssetSchedule',
            type: 'GET',
            data: {asOnDate: asOnDate},
            success: function (res) {
                $('#tableContent').empty();
                let status = false,
                    totalRate = 0,
                    totalAsOnStartFinancialYear = 0,
                    totalAdditional = 0,
                    totalDisposal = 0,
                    totalDepAsOnStartFinancialYear = 0,
                    totalAsOnEndFinancialYear = 0,
                    totalDepCurrentYear = 0,
                    totalDepAsOnEndFinancialYear = 0,
                    totalNetValue = 0,
                    grandTotalRate = 0,
                    grandTotalAsOnStartFinancialYear = 0,
                    grandTotalAdditional = 0,
                    grandTotalDisposal = 0,
                    grandTotalDepAsOnStartFinancialYear = 0,
                    grandTotalAsOnEndFinancialYear = 0,
                    grandTotalDepCurrentYear = 0,
                    grandTotalDepAsOnEndFinancialYear = 0,
                    grandTotalNetValue = 0,
                    totalSize = parseInt(res.length),
                    characterCounter = -1;

                $.each(res, function (index, item) {
                    let htmlTableContent = '';

                    function resetTotalAmount() {
                        totalRate = 0;
                        totalAsOnStartFinancialYear = 0;
                        totalAdditional = 0;
                        totalDisposal = 0;
                        totalDepAsOnStartFinancialYear = 0;
                        totalAsOnEndFinancialYear = 0;
                        totalDepCurrentYear = 0;
                        totalDepAsOnEndFinancialYear = 0;
                        totalNetValue = 0;
                    }

                    if (parseInt(item.groupId) === 1) {
                        if (status) {
                            totalHTMLContent()
                            htmlTableContent = htmlTableContent + '<tr class="text-center">\n' +
                                '                 <td><b>' + item.particular + '</b></td>\n' +
                                '              </tr>\n';

                            status = false;
                            calculateGrandTotal();
                            resetTotalAmount();
                        } else {
                            if (characterCounter % 2) {
                                if (characterCounter !== -1) {
                                    calculateGrandTotal();
                                    totalHTMLContent();
                                    resetTotalAmount();
                                }
                            }
                            status = true;
                            htmlTableContent = htmlTableContent + '<tr class="text-center">\n' +
                                '                 <td><b>' + item.particular + '</b></td>\n' +
                                '              </tr>\n';
                        }
                        characterCounter = characterCounter + 1;
                    } else {
                        let addition = item.addition == null ? 0 : item.addition,
                            purchaseDate = item.purchaseDate == null ? 0 : item.purchaseDate,
                            rate = item.rate == null ? 0 : item.rate,
                            asOnStartFinancialYear = item.asOnStartFinancialYear == null ? 0 : item.asOnStartFinancialYear,
                            disposal = item.disposal == null ? 0 : item.disposal,
                            depAsOnStartFinancialYear = item.depAsOnStartFinancialYear == null ? 0 : item.depAsOnStartFinancialYear,
                            asOnEndFinancialYear = (asOnStartFinancialYear + addition) - disposal,
                            depCurrentYear = item.depCurrentYear == null ? 0 : item.depCurrentYear,
                            depAsOnEndFinancialYear = item.depAsOnEndFinancialYear == null ? 0 : item.depAsOnEndFinancialYear,
                            netValue = item.netValue == null ? 0 : item.netValue;

                        totalRate = totalRate + rate;
                        totalAsOnStartFinancialYear = totalAsOnStartFinancialYear + asOnStartFinancialYear;
                        totalAdditional = totalAdditional + addition;
                        totalDisposal = totalDisposal + disposal;
                        totalAsOnEndFinancialYear = totalAsOnEndFinancialYear + asOnEndFinancialYear;
                        totalDepAsOnStartFinancialYear = totalDepAsOnStartFinancialYear + depAsOnStartFinancialYear;
                        totalDepCurrentYear = totalDepCurrentYear + depCurrentYear;
                        totalDepAsOnEndFinancialYear = totalDepAsOnEndFinancialYear + depAsOnEndFinancialYear;
                        totalNetValue = totalNetValue + netValue;
                        let deleteBtn = addition === 0.00 ? '<i class="icon-trash text-warning btnDelete"></i>' : '';
                        htmlTableContent = htmlTableContent + '<tr  class="text-center">\n' +
                            '                        <td class="particular">' + item.particular + '</td>\n' +
                            '                        <td>' + purchaseDate + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(rate.toFixed(2)) + '</td>\n' +
                            '                        <td>' + (item.rateOfDep) * 100 + "%" + '</td>\n' +
                            '                        <td class="asOnStartFinancialYear">' + spms.numberWithCommas(asOnStartFinancialYear.toFixed(2)) + '</td>\n' +
                            '                        <td class="addition">' + spms.numberWithCommas(addition.toFixed(2)) + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(disposal.toFixed(2)) + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(asOnEndFinancialYear.toFixed(2)) + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(depAsOnStartFinancialYear.toFixed(2)) + '</td>\n' +
                            '                        <td class="depCurrentYear">' + spms.numberWithCommas(depCurrentYear.toFixed(2)) + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(depAsOnEndFinancialYear.toFixed(2)) + '</td>\n' +
                            '                        <td>' + spms.numberWithCommas(netValue.toFixed(2)) + '</td>\n' +
                            '                    <td>' + deleteBtn + '</td></tr>';
                    }

                    function totalHTMLContent() {
                        htmlTableContent = htmlTableContent + '<tr class="text-center">\n' +
                            '                        <td><b>Total(' + arrayCharacter()[characterCounter] + ')</b></td>\n' +
                            '                        <td></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalRate.toFixed(2)) + '</b></td>\n' +
                            '                        <td></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalAsOnStartFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalAdditional.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalDisposal.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalAsOnEndFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalDepAsOnStartFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalDepCurrentYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalDepAsOnEndFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(totalNetValue.toFixed(2)) + '</b></td>\n' +
                            '                    <td></td></tr>';
                    }

                    function calculateGrandTotal() {
                        grandTotalRate = grandTotalRate + totalRate;
                        grandTotalAsOnStartFinancialYear = grandTotalAsOnStartFinancialYear + totalAsOnStartFinancialYear;
                        grandTotalAdditional = grandTotalAdditional + totalAdditional;
                        grandTotalDisposal = grandTotalDisposal + totalDisposal;
                        grandTotalDepAsOnStartFinancialYear = grandTotalDepAsOnStartFinancialYear + totalDepAsOnStartFinancialYear;
                        grandTotalAsOnEndFinancialYear = grandTotalAsOnEndFinancialYear + totalAsOnEndFinancialYear;
                        grandTotalDepCurrentYear = grandTotalDepCurrentYear + totalDepCurrentYear;
                        grandTotalDepAsOnEndFinancialYear = grandTotalDepAsOnEndFinancialYear + totalDepAsOnEndFinancialYear;
                        grandTotalNetValue = grandTotalNetValue + totalNetValue;
                    }

                    if (totalSize === 1) {
                        calculateGrandTotal();
                        totalHTMLContent();
                        htmlTableContent = htmlTableContent + '<tr class="text-center">\n' +
                            '                        <td><b>Grand Total(' + getFinalCharacter(characterCounter) + ')</b></td>\n' +
                            '                        <td></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalRate.toFixed(2)) + '</b></td>\n' +
                            '                        <td></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalAsOnStartFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalAdditional.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalDisposal.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalAsOnEndFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalDepAsOnStartFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalDepCurrentYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalDepAsOnEndFinancialYear.toFixed(2)) + '</b></td>\n' +
                            '                        <td><b>' + spms.numberWithCommas(grandTotalNetValue.toFixed(2)) + '</b></td>\n' +
                            '                    </tr>';
                    }
                    totalSize = totalSize - 1;
                    $('#tableContent').append(htmlTableContent);
                })
            }
        });
    }

    function arrayCharacter() {
        return ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
    }

    function getFinalCharacter(iterator) {
        let finalCharter = '';
        for (let i = 0; i <= iterator; i++) {
            if (i === 0) {
                finalCharter = arrayCharacter()[i];
            } else {
                finalCharter = finalCharter + '+' + arrayCharacter()[i];
            }
        }
        return finalCharter;
    }

    function onCLickBtnDelete() {
        $('#tableAssetSchedule tbody').on('click', 'tr .btnDelete', function () {
            let selectedRow = $(this).closest('tr');
            confirmMessage("Please confirm", function (e) {

                if (e) {
                    $.ajax({
                        url: 'fixedAssetSchedule/deleteFixedAsset',
                        type: 'GET',
                        data: {
                            particular: selectedRow.find('.particular').text(),
                            depCurrentYear: selectedRow.find('.depCurrentYear').text(),
                            openingBalance: spms.removeCommaSeparation(selectedRow.find('.asOnStartFinancialYear').text()),
                            entryDate: $('#asOnDate').val()
                        },
                        success: function (res) {
                            if (res.status === 1) {
                                successMsg(res.text);
                                loadInitialTable();
                            } else {
                                errorMsg(res.text);
                            }
                        }
                    })
                }
            })
        });
    }

    return {
        loadInitialTable: loadInitialTable,
        onChangeAsOnDate: onChangeAsOnDate,
        onCLickBtnDelete: onCLickBtnDelete
    }
})();
$(document).ready(function () {
    fixedAssetSchedule.loadInitialTable();
    fixedAssetSchedule.onChangeAsOnDate();
    fixedAssetSchedule.onCLickBtnDelete();
});

