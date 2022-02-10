package com.spms.mvc.dto;

import java.math.BigInteger;

/**
 * Created by SonamPC on 28-Dec-16.
 */
public class ReceiptDT {
    private String itemName;
    private Double pricePerQty;
    private BigInteger qty;
    private Double amount;
    private Double discountAmt;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Double pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public BigInteger getQty() {
        return qty;
    }

    public void setQty(BigInteger qty) {
        this.qty = qty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Double discountAmt) {
        this.discountAmt = discountAmt;
    }
}
