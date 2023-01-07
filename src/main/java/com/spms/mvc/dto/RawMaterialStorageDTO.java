package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RawMaterialStorageDTO {
    private String storageModifier;

    private Date openBalanceEntryDate;
    private String rawMaterialParticularName;

    private int rawMaterialParticularQty;
    private int rawMaterialParticularUnit;

    private BigDecimal rawMaterialParticularPrice;
    private int rawMaterialParticularLocation;


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
