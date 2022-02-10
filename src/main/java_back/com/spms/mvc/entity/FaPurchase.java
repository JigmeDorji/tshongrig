package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: Fa_Purchase
 * Date:  2021-Oct-03
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-03
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_fa_purchase")
public class FaPurchase extends BaseEntity {

    @Id
    @Column(name = "faPurchaseId")
    private BigInteger faPurchaseId;

    @Column(name = "purchaseMasterId")
    private BigInteger purchaseMasterId;

    @Column(name = "assetDetailId")
    private BigInteger assetDetailId;

    @Column(name = "purchaseDate")
    private Date purchaseDate;

    @Column(name = "openingBalance")
    private Double openingBalance;

    @Column(name = "depreciatedValue")
    private Double depreciatedValue;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "qty")
    private BigDecimal qty;

    public BigInteger getFaPurchaseId() {
        return faPurchaseId;
    }

    public void setFaPurchaseId(BigInteger faPurchaseId) {
        this.faPurchaseId = faPurchaseId;
    }

    public BigInteger getPurchaseMasterId() {
        return purchaseMasterId;
    }

    public void setPurchaseMasterId(BigInteger purchaseMasterId) {
        this.purchaseMasterId = purchaseMasterId;
    }

    public BigInteger getAssetDetailId() {
        return assetDetailId;
    }

    public void setAssetDetailId(BigInteger assetDetailId) {
        this.assetDetailId = assetDetailId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(Double depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
