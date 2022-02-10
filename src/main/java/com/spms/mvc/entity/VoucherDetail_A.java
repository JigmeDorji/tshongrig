package com.spms.mvc.entity;

import javax.persistence.*;

/**
 * Description: VoucherDetail_A
 * Date:  2020-Nov-14
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Nov-14
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_voucher_entries_detail_a")
public class VoucherDetail_A {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucherDetailId")
    private Integer voucherDetailId;

    @Column(name = "voucherId")
    private Integer voucherId;

    @Column(name = "ledgerId")
    private String ledgerId;

    @Column(name = "drcrAmount")
    private Double drcrAmount;

    public Integer getVoucherDetailId() {
        return voucherDetailId;
    }

    public void setVoucherDetailId(Integer voucherDetailId) {
        this.voucherDetailId = voucherDetailId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Double getDrcrAmount() {
        return drcrAmount;
    }

    public void setDrcrAmount(Double drcrAmount) {
        this.drcrAmount = drcrAmount;
    }
}
