package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: OpeningAndBuyingListDTO
 * Date:  2021-Sep-16
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Sep-16
 * Change Description:
 * Search Tag:
 */
public class OpeningAndBuyingListDTO {

    private BigInteger purchaseMasterId;
    private BigInteger faPurchaseId;
    private BigInteger faPurchaseDetailId;
    private BigInteger assetDetailId;
    private BigInteger fixedAssetGroupId;
    private String particular;
    private Date purchaseDate;
    private Double purchaseValue;
    private Double openingBalance;
    private Double depreciatedValue;
    private BigDecimal qty;
    private Integer paidInType;
    private String purchaseInvoiceNo;
    private String voucherNo;
    private Double rate;
    private Integer accTypeId;
    private Double totalAmount;

    public BigInteger getPurchaseMasterId() {
        return purchaseMasterId;
    }

    public void setPurchaseMasterId(BigInteger purchaseMasterId) {
        this.purchaseMasterId = purchaseMasterId;
    }

    public BigInteger getAssetDetailId() {
        return assetDetailId;
    }

    public BigInteger getFaPurchaseDetailId() {
        return faPurchaseDetailId;
    }

    public void setFaPurchaseDetailId(BigInteger faPurchaseDetailId) {
        this.faPurchaseDetailId = faPurchaseDetailId;
    }

    public void setAssetDetailId(BigInteger assetDetailId) {
        this.assetDetailId = assetDetailId;
    }

    public BigInteger getFaPurchaseId() {
        return faPurchaseId;
    }

    public void setFaPurchaseId(BigInteger faPurchaseId) {
        this.faPurchaseId = faPurchaseId;
    }

    public BigInteger getFixedAssetGroupId() {
        return fixedAssetGroupId;
    }

    public void setFixedAssetGroupId(BigInteger fixedAssetGroupId) {
        this.fixedAssetGroupId = fixedAssetGroupId;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(Double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(Double depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Integer getPaidInType() {
        return paidInType;
    }

    public void setPaidInType(Integer paidInType) {
        this.paidInType = paidInType;
    }

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
