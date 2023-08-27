package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by jigmePc on 8/24/2019.
 */
public class PurchaseDTO {



    private Date purchaseDate;

    private String purchaseInvoiceNo;

    private String itemCode;

    private String itemName;

    private BigDecimal qty;

    private Integer unitId;
    private String unitName;

    private BigInteger bigQty;

    private BigDecimal qtyBig;

    private BigDecimal balance;

    private Double costPrice;

    private Double sellingPrice;

    private String locationId;

    private Integer purchaseId;
    private Integer purchaseAuditId;

    private Double amount;

    private String particular;

    private Integer isCash;

    private Integer supplierId;

    private String supplierName;

    private Integer brandId;

    private String serialNo;

    private String prefixCode;

    private BigDecimal sumQty;

    private String bankLedgerId;

    private String description;

    private String partNo;

    private String brandName;

    private String type;

    private Integer purchaseVoucherNo;

    private Integer voucherNo;

    private Character isOpeningEntry;

    private BigDecimal closingStockAmount;

    private String SerialNumber;


    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public BigDecimal getClosingStockAmount() {
        return closingStockAmount;
    }

    public void setClosingStockAmount(BigDecimal closingStockAmount) {
        this.closingStockAmount = closingStockAmount;
    }

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigInteger getBigQty() {
        return bigQty;
    }

    public void setBigQty(BigInteger bigQty) {
        this.bigQty = bigQty;
    }

    public BigDecimal getQtyBig() {
        return qtyBig;
    }

    public void setQtyBig(BigDecimal qtyBig) {
        this.qtyBig = qtyBig;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }

    public BigDecimal getSumQty() {
        return sumQty;
    }

    public void setSumQty(BigDecimal sumQty) {
        this.sumQty = sumQty;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Character getIsOpeningEntry() {
        return isOpeningEntry;
    }

    public void setIsOpeningEntry(Character isOpeningEntry) {
        this.isOpeningEntry = isOpeningEntry;
    }

    public Integer getPurchaseVoucherNo() {
        return purchaseVoucherNo;
    }

    public void setPurchaseVoucherNo(Integer purchaseVoucherNo) {
        this.purchaseVoucherNo = purchaseVoucherNo;
    }

    public Integer getPurchaseAuditId() {
        return purchaseAuditId;
    }

    public void setPurchaseAuditId(Integer purchaseAuditId) {
        this.purchaseAuditId = purchaseAuditId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
