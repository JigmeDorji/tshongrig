package com.spms.mvc.dto;

import java.math.BigInteger;

/**
 * Created by SonamPC on 16-Dec-16.
 */
public class ViewItemDTO {
    private Integer id;
    private Integer itemCategoryId;
    private String partNumber;
    private String partDescription;
    private String locationId;
    private BigInteger qty;
    private Double pricePerQty;
    private String fits;
    private Integer minimumQtyNotify;
    private Double costPricePerQty;
    /*--this itemName variable  is same as of part number---*/

    public Double getCostPricePerQty() {
        return costPricePerQty;
    }

    public void setCostPricePerQty(Double costPricePerQty) {
        this.costPricePerQty = costPricePerQty;
    }

    public String getFits() {
        return fits;
    }

    public void setFits(String fits) {
        this.fits = fits;
    }

    private String itemName;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public BigInteger getQty() {
        return qty;
    }

    public void setQty(BigInteger qty) {
        this.qty = qty;
    }

    public Double getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Double pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMinimumQtyNotify() {
        return minimumQtyNotify;
    }

    public void setMinimumQtyNotify(Integer minimumQtyNotify) {
        this.minimumQtyNotify = minimumQtyNotify;
    }
}
