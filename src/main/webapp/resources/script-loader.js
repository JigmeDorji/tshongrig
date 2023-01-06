/**
 * Project Name: Spare part management system
 * Description: <Replace description>
 * Date:11/28/2016
 * Year :2016
 *
 * @author: bikash.rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:
 * Author:
 * Date:
 * Change Description:
 * Search Tag:
 **/
$(document).ready(function () {

    if (document.URL.search("user") > 1)
        scriptLoader("resources/js/user.js");

    if (document.URL.search("changePassword") > 1)
        scriptLoader("resources/js/changePassword.js");

    if (document.URL.search("locationCreation") > 1)
        scriptLoader("resources/js/locationCreation.js");

    if (document.URL.search("companyCreation") > 1)
        scriptLoader("resources/js/companyCreation.js");

    if (document.URL.search("receivedItem") > 1)
        scriptLoader("resources/js/receivedItem.js");

    if (document.URL.search("viewItem") > 1)
        scriptLoader("resources/js/viewItem.js");

    if (document.URL.search("saleItem") > 1)
        scriptLoader("resources/js/saleItem.js");

    if (document.URL.search("saleRecord") > 1)
        scriptLoader("resources/js/saleRecord.js");

    if (document.URL.search("barcode") > 1)
        scriptLoader("resources/js/barcode.js");

    if (document.URL.search("login") > 1)
        scriptLoader("resources/js/login.js");

    if (document.URL.search("home") > 1)
        scriptLoader("resources/js/home.js");

    if (document.URL.search("index") > 1)
        scriptLoader("resources/js/index.js");

    if (document.URL.search("locationSetUp") > 1)
        scriptLoader("resources/js/locationSetUp.js");

    if (document.URL.search("employeeSetup") > 1)
        scriptLoader("resources/js/employeeSetup.js");

    if (document.URL.search("viewPrintCopy") > 1)
        scriptLoader("resources/js/viewPrintCopy.js");


    //Accounting
    if (document.URL.search("ledger") > 1)
        scriptLoader("resources/js/ledger.js");

    if (document.URL.search("voucherCreation") > 1)
        scriptLoader("resources/js/voucherCreation.js");

    if (document.URL.search("accProfitAndLossReport") > 1)
        scriptLoader("resources/js/accProfitAndLossReport.js");

    if (document.URL.search("voucherGroupList") > 1)
        scriptLoader("resources/js/voucherGroupList.js");

    if (document.URL.search("accBalanceSheetReport") > 1)
        scriptLoader("resources/js/accBalanceSheetReport.js");

    if (document.URL.search("accTrialBalance") > 1)
        scriptLoader("resources/js/accTrialBalance.js");

    if (document.URL.search("accCashFlow") > 1)
        scriptLoader("resources/js/accCashFlow.js");

    if (document.URL.search("accPurchaseVoucherDetail") > 1)
        scriptLoader("resources/js/accPurchaseVoucherDetail.js");

    if (document.URL.search("viewItemDetail") > 1)
        scriptLoader("resources/js/viewItemDetail.js");

    if (document.URL.search("returnItem") > 1)
        scriptLoader("resources/js/returnItem.js");

    if (document.URL.search("saleInvoiceGeneration") > 1)
        scriptLoader("resources/js/accSaleInvoiceGeneration.js");

    if (document.URL.search("supplier") > 1)
        scriptLoader("resources/js/supplier.js");

    if (document.URL.search("ledgerGroupList") > 1)
        scriptLoader("resources/js/ledgerGroupList.js");

    if (document.URL.search("moneyReceipt") > 1)
        scriptLoader("resources/js/moneyReceipt.js");

    if (document.URL.search("yearClosing") > 1)
        scriptLoader("resources/js/yearClosing.js");

    if (document.URL.search("financialYearSetup") > 1)
        scriptLoader("resources/js/financialYearSetup.js");

    if (document.URL.search("userAccessPermission") > 1)
        scriptLoader("resources/js/userAccessPermissionSetup.js");

    if (document.URL.search("payment") > 1)
        scriptLoader("resources/js/payment.js");

    if (document.URL.search("cashDepositWithdrawal") > 1)
        scriptLoader("resources/js/cashDepositWithdrawal.js");

    if (document.URL.search("receipt") > 1)
        scriptLoader("resources/js/receipt.js");

    if (document.URL.search("bankTransfer") > 1)
        scriptLoader("resources/js/bankTransfer.js");

    if (document.URL.search("adjustment") > 1)
        scriptLoader("resources/js/adjustment.js");

    if (document.URL.search("payable") > 1)
        scriptLoader("resources/js/payable.js");


    /**
     * BOQ Module
     */


    if (document.URL.search("boqSetup") > 1)
        scriptLoader("resources/js/boqSetup.js");

    if (document.URL.search("boqDetail") > 1)
        scriptLoader("resources/js/boqDetail.js");

    if (document.URL.search("raBillGeneration") > 1)
        scriptLoader("resources/js/raBillGeneration.js");

    if (document.URL.search("raBillDetail") > 1)
        scriptLoader("resources/js/raBillDetail.js");

    /**
     * Hr Module
     */

    if (document.URL.search("saleDetail") > 1)
        scriptLoader("resources/js/saleDetail.js");

    if (document.URL.search("salaryRemittance") > 1)
        scriptLoader("resources/js/salaryRemittance.js");
    if (document.URL.search("salarySheet") > 1)
        scriptLoader("resources/js/salarySheet.js");

    if (document.URL.search("statutoryRemittance") > 1)
        scriptLoader("resources/js/statutoryRemittance.js");

    if (document.URL.search("employeeAdvance") > 1)
        scriptLoader("resources/js/employeeAdvance.js");

    if (document.URL.search("otherRemittance") > 1)
        scriptLoader("resources/js/otherRemittance.js");

    if (document.URL.search("openingBalanceInventory") > 1)
        scriptLoader("resources/js/openingBalanceInventory.js");
    /**
     * Fixed asset
     */
    if (document.URL.search("assetSetup") > 1)
        scriptLoader("resources/js/assetSetup.js");

    if (document.URL.search("assetOpening") > 1)
        scriptLoader("resources/js/assetOpening.js");

    if (document.URL.search("assetBuying") > 1)
        scriptLoader("resources/js/assetBuying.js");

    if (document.URL.search("fixedAssetSchedule") > 1)
        scriptLoader("resources/js/fixedAssetSchedule.js");
    /**
     * Construction Related
     */
    if (document.URL.search("stockIssue") > 1)
        scriptLoader("resources/js/stockIssue.js");

    if (document.URL.search("fixedAssetSale") > 1)
        scriptLoader("resources/js/fixedAssetSale.js");

    if (document.URL.search("rawMaterialPurchase") > 1)
        scriptLoader("resources/js/rawMaterialPurchase.js");

    if (document.URL.search("openingRawMaterialPurchase") > 1)
        scriptLoader("resources/js/openingRawMaterialPurchase.js");

    if (document.URL.search("issueDetail") > 1)
        scriptLoader("resources/js/issueDetail.js");

    if (document.URL.search("registration") > 1)
        scriptLoader("resources/js/registration.js");

    if (document.URL.search("financialPosition") > 1)
        scriptLoader("resources/js/financialPosition.js");

    //Raw Material

    //Raw Material Location Setup
    if (document.URL.search("rawMaterialLocationSetup") > 1)
        scriptLoader("resources/js/rawMaterialLocationSetup.js");

    if (document.URL.search("openingBalanceForRawMaterial") > 1)
        scriptLoader("resources/js/openingBalanceForRawMaterial.js");

    if (document.URL.search("purchasesForRawMaterial") > 1)
        scriptLoader("resources/js/purchasesForRawMaterial.js");

    if (document.URL.search("viewItemsForRawMaterial") > 1)
        scriptLoader("resources/js/viewItemsForRawMaterial.js");


});
var scriptLoader = function (url) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: "script",
        cache: false
    });
};