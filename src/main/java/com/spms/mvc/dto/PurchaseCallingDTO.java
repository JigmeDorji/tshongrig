package com.spms.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 8/4/2021.
 */
public class PurchaseCallingDTO {
    private Date purchaseDate;
    private String purchaseInvoiceNo;
    private Character isOpeningEntry;
    private Integer voucherNo;
    private Double totalTranAmount;

    private Integer isCash;
    private Integer supplierId;
    private String supplierName;
    private String bankLedgerId;


    List<PurchaseDTO> purchaseDTOS;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public Character getIsOpeningEntry() {
        return isOpeningEntry;
    }

    public void setIsOpeningEntry(Character isOpeningEntry) {
        this.isOpeningEntry = isOpeningEntry;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public List<PurchaseDTO> getPurchaseDTOS() {
        return purchaseDTOS;
    }

    public void setPurchaseDTOS(List<PurchaseDTO> purchaseDTOS) {
        this.purchaseDTOS = purchaseDTOS;
    }

    public Double getTotalTranAmount() {
        return totalTranAmount;
    }

    public void setTotalTranAmount(Double totalTranAmount) {
        this.totalTranAmount = totalTranAmount;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
