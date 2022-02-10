package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by Bcass Sawa on 2/9/2018.
 */
public class ItemReceiveInCreditDTO {

    private String agencyId;
    private String agencyName;
    private String billNumber;
    private Date dateOfPurchase;
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

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
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

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
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

    public Integer getMinimumQtyNotify() {
        return minimumQtyNotify;
    }

    public void setMinimumQtyNotify(Integer minimumQtyNotify) {
        this.minimumQtyNotify = minimumQtyNotify;
    }
}
