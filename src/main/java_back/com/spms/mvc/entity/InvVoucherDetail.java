package com.spms.mvc.entity;

import javax.persistence.*;

/**
 * Created by jigmePc on 8/10/2019.
 */
@Entity
@Table(name = "tbl_acc_inv_voucherdetail")
public class InvVoucherDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseVoucherDetailId")
    private Integer purchaseVoucherDetailId;

    @Column(name = "voucherId")
    private Integer voucherId;

    @Column(name = "itemCategoryId")
    private String itemCategoryId;

    @Column(name = "debitAmount")
    private Double debitAmount;

    @Column(name = "creditAmount")
    private Double creditAmount;

    public Integer getPurchaseVoucherDetailId() {
        return purchaseVoucherDetailId;
    }

    public void setPurchaseVoucherDetailId(Integer purchaseVoucherDetailId) {
        this.purchaseVoucherDetailId = purchaseVoucherDetailId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }
}
