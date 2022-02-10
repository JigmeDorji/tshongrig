package com.spms.mvc.dto;

/**
 * Created by SonamPC on 15-Dec-16.
 */
public class ItemCounterDTO {
    private Integer itemCategoryId;
    private Integer itemSerialCounter;

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Integer getItemSerialCounter() {
        return itemSerialCounter;
    }

    public void setItemSerialCounter(Integer itemSerialCounter) {
        this.itemSerialCounter = itemSerialCounter;
    }
}
