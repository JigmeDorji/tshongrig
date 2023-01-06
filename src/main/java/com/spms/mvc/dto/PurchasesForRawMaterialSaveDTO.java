package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



public class PurchasesForRawMaterialSaveDTO {


//    private Integer itemId;
//    private Integer purchaseId;

    private Integer voucherNo;
    private String supplierName;
    private String isOpeningEntry;

    private Date purchaseDate;
    private String purchaseInvoiceNo;
    private Integer isCash;
    private  String itemName;
    private String qty;
    private  String unitId;
    private String  price;
    private  String   locationId;

    private Integer supplierId;
    private String bankLedgerId;


    private Double totalTranAmount;






    private String storageModifier;

    private Date openBalanceEntryDate;
    private String rawMaterialParticularName;

    private int rawMaterialParticularQty;
    private int rawMaterialParticularUnit;

    private BigDecimal rawMaterialParticularPrice;
    private int rawMaterialParticularLocation;







    List<PurchasesForRawMaterialListDTO> purchaseDTOS;


    public Double getTotalTranAmount() {
        return totalTranAmount;
    }

    public void setTotalTranAmount(Double totalTranAmount) {
        this.totalTranAmount = totalTranAmount;
    }


    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getIsOpeningEntry() {
        return isOpeningEntry;
    }

    public void setIsOpeningEntry(String isOpeningEntry) {
        this.isOpeningEntry = isOpeningEntry;
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

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
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

    public List<PurchasesForRawMaterialListDTO> getPurchaseDTOS() {
        return purchaseDTOS;
    }

    public void setPurchaseDTOS(List<PurchasesForRawMaterialListDTO> purchaseDTOS) {
        this.purchaseDTOS = purchaseDTOS;
    }


    public String getStorageModifier() {
        return storageModifier;
    }

    public void setStorageModifier(String storageModifier) {
        this.storageModifier = storageModifier;
    }

    public Date getOpenBalanceEntryDate() {
        return openBalanceEntryDate;
    }

    public void setOpenBalanceEntryDate(Date openBalanceEntryDate) {
        this.openBalanceEntryDate = openBalanceEntryDate;
    }

    public String getRawMaterialParticularName() {
        return rawMaterialParticularName;
    }

    public void setRawMaterialParticularName(String rawMaterialParticularName) {
        this.rawMaterialParticularName = rawMaterialParticularName;
    }

    public int getRawMaterialParticularQty() {
        return rawMaterialParticularQty;
    }

    public void setRawMaterialParticularQty(int rawMaterialParticularQty) {
        this.rawMaterialParticularQty = rawMaterialParticularQty;
    }

    public int getRawMaterialParticularUnit() {
        return rawMaterialParticularUnit;
    }

    public void setRawMaterialParticularUnit(int rawMaterialParticularUnit) {
        this.rawMaterialParticularUnit = rawMaterialParticularUnit;
    }

    public BigDecimal getRawMaterialParticularPrice() {
        return rawMaterialParticularPrice;
    }

    public void setRawMaterialParticularPrice(BigDecimal rawMaterialParticularPrice) {
        this.rawMaterialParticularPrice = rawMaterialParticularPrice;
    }

    public int getRawMaterialParticularLocation() {
        return rawMaterialParticularLocation;
    }

    public void setRawMaterialParticularLocation(int rawMaterialParticularLocation) {
        this.rawMaterialParticularLocation = rawMaterialParticularLocation;
    }
}
