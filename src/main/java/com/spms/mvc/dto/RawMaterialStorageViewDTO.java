package com.spms.mvc.dto;

import java.util.Date;

public class RawMaterialStorageViewDTO {


    private int id;
    private String rawMaterialParticularName;
    private int rawMaterialParticularQty;
    private String rawMaterialParticularUnit;
    private double rawMaterialParticularPrice;
    private String rawMaterialParticularLocation;
    private String storageModifier;
    private Date asOnDate;

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRawMaterialParticularUnit() {
        return rawMaterialParticularUnit;
    }

    public void setRawMaterialParticularUnit(String rawMaterialParticularUnit) {
        this.rawMaterialParticularUnit = rawMaterialParticularUnit;
    }

    public double getRawMaterialParticularPrice() {
        return rawMaterialParticularPrice;
    }

    public void setRawMaterialParticularPrice(double rawMaterialParticularPrice) {
        this.rawMaterialParticularPrice = rawMaterialParticularPrice;
    }

    public String getRawMaterialParticularLocation() {
        return rawMaterialParticularLocation;
    }

    public void setRawMaterialParticularLocation(String rawMaterialParticularLocation) {
        this.rawMaterialParticularLocation = rawMaterialParticularLocation;
    }

    public String getStorageModifier() {
        return storageModifier;
    }

    public void setStorageModifier(String storageModifier) {
        this.storageModifier = storageModifier;
    }
}
