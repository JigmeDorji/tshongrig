package com.spms.mvc.dto;

/**
 * Created by SonamPC on 27-Jan-17.
 */
public class SaleReportDTO {

    private String itemName;
    private Double sellingPrice;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
