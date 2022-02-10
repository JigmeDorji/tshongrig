package com.spms.mvc.dto;

/**
 * Created by SonamPC on 12-Dec-16.
 */
public class AddItemCategoryDTO {
    private Integer itemCategoryId;
    private String partNumber;
    private String partDescription;
    private String partName2;
    private String fits;
    private Integer locationId;
    private String stringLocationId;
    private String itemCategoryPrefix;
    private Double pricePerQty;
    private Integer minimumQtyNotify;
    private String description;
    private String unit;

    //for retrieve

    private String itemCode;

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }


    public String getItemCategoryPrefix() {
        return itemCategoryPrefix;
    }

    public void setItemCategoryPrefix(String itemCategoryPrefix) {
        this.itemCategoryPrefix = itemCategoryPrefix;
    }

    public Double getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Double pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public String getPartName2() {
        return partName2;
    }

    public void setPartName2(String partName2) {
        this.partName2 = partName2;
    }

    public String getFits() {
        return fits;
    }

    public void setFits(String fits) {
        this.fits = fits;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getStringLocationId() {
        return stringLocationId;
    }

    public void setStringLocationId(String stringLocationId) {
        this.stringLocationId = stringLocationId;
    }

    public Integer getMinimumQtyNotify() {
        return minimumQtyNotify;
    }

    public void setMinimumQtyNotify(Integer minimumQtyNotify) {
        this.minimumQtyNotify = minimumQtyNotify;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
