package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Description: BOQDetailsListDTO
 * Date:  2022-Jan-06
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-06
 * Change Description:
 * Search Tag:
 */
public class BOQDetailsListDTO {

    private BigInteger raBillDetailId;
    private BigInteger boqDetailId;
    private String code;
    private String description;
    private String rateInWords;
    private String totalAmountInWords;
    private String unitOfMeasurement;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal amount;

    public BigInteger getRaBillDetailId() {
        return raBillDetailId;
    }

    public void setRaBillDetailId(BigInteger raBillDetailId) {
        this.raBillDetailId = raBillDetailId;
    }

    public BigInteger getBoqDetailId() {
        return boqDetailId;
    }

    public void setBoqDetailId(BigInteger boqDetailId) {
        this.boqDetailId = boqDetailId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRateInWords() {
        return rateInWords;
    }

    public void setRateInWords(String rateInWords) {
        this.rateInWords = rateInWords;
    }

    public String getTotalAmountInWords() {
        return totalAmountInWords;
    }

    public void setTotalAmountInWords(String totalAmountInWords) {
        this.totalAmountInWords = totalAmountInWords;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
