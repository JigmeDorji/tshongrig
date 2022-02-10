package com.spms.mvc.dto;

/**
 * Created by SonamPC on 25-Dec-16.
 */
public class BarcodeDTO {

    private Integer itemCategoryId;
    private Integer qty;

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
