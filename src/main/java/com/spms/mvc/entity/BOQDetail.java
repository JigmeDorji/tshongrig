package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Description: BOQDetail
 * Date:  2022-Jan-10
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-10
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_boq_detail")
public class BOQDetail extends BaseEntity {
    @Id
    @Column(name = "boqDetailId")
    private BigInteger boqDetailId;

    @Column(name = "boqId")
    private BigInteger boqId;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "unit")
    private String unit;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "rateInWords")
    private String rateInWords;

    @Column(name = "totalAmountInWords")
    private String totalAmountInWords;

    public BigInteger getBoqDetailId() {
        return boqDetailId;
    }

    public void setBoqDetailId(BigInteger boqDetailId) {
        this.boqDetailId = boqDetailId;
    }

    public BigInteger getBoqId() {
        return boqId;
    }

    public void setBoqId(BigInteger boqId) {
        this.boqId = boqId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}
