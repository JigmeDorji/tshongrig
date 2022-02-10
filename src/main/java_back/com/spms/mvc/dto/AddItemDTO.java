package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by SonamPC on 13-Dec-16.
 */
public class AddItemDTO {

    private Integer item_ref_Id;
    private Double cPPerQty;
    private Double pricePerQty;
    private Integer itemQty;
    private String partDescription;

    private Integer itemId;
    private String partNumber;
    private String locationId;
    private String itemDescription;
    private Date receivedDate;
    private String createdBy;
    private  String itemCode;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getItem_ref_Id() {
        return item_ref_Id;
    }

    public void setItem_ref_Id(Integer item_ref_Id) {
        this.item_ref_Id = item_ref_Id;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Double getcPPerQty() {
        return cPPerQty;
    }

    public void setcPPerQty(Double cPPerQty) {
        this.cPPerQty = cPPerQty;
    }

    public Double getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Double pricePerQty) {
        this.pricePerQty = pricePerQty;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
