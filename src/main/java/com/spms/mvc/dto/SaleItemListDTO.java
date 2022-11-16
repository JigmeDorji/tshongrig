package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Bcass Sawa on 8/26/2019.
 */
public class SaleItemListDTO {
    private Integer serialNo;
    private BigDecimal qty;
    private Date saleDate;
    private String itemCode;
    private Double discount;
    private String receiptMemoNo;
    private Double sellingPrice;
    private Double totalAmount;
    private String itemName;
    private Boolean isReturn;
    private Integer returnQty;
    private Integer returnItem;
    private Integer replaceItem;
    private Integer saleRecordId;
    private String ledgerId;
    private Integer id;
    private Integer voucherNo;
    private String invoiceNo;
    private String unitName;
    private Double costPrice;

    private String assetCode;

    private BigInteger faPurchaseDetailId;

    public Boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getReceiptMemoNo() {
        return receiptMemoNo;
    }

    public void setReceiptMemoNo(String receiptMemoNo) {
        this.receiptMemoNo = receiptMemoNo;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getReplaceItem() {
        return replaceItem;
    }

    public void setReplaceItem(Integer replaceItem) {
        this.replaceItem = replaceItem;
    }

    public Integer getReturnItem() {

        return returnItem;
    }

    public void setReturnItem(Integer returnItem) {
        this.returnItem = returnItem;
    }

    public Integer getSaleRecordId() {
        return saleRecordId;
    }

    public void setSaleRecordId(Integer saleRecordId) {
        this.saleRecordId = saleRecordId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public BigInteger getFaPurchaseDetailId() {
        return faPurchaseDetailId;
    }

    public void setFaPurchaseDetailId(BigInteger faPurchaseDetailId) {
        this.faPurchaseDetailId = faPurchaseDetailId;
    }
}
