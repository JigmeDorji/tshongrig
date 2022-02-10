package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Description: RABillDetail
 * Date:  2022-Jan-19
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-19
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_acc_boq_ra_bill_detail")
public class RABillDetail {

    @Id
    @Column(name = "raBillDetailId")
    private BigInteger raBillDetailId;

    @Column(name = "raBillId")
    private BigInteger raBillId;

    @Column(name = "boqDetailId")
    private BigInteger boqDetailId;

    @Column(name = "qty")
    private BigDecimal qty;

    public BigInteger getRaBillDetailId() {
        return raBillDetailId;
    }

    public void setRaBillDetailId(BigInteger raBillDetailId) {
        this.raBillDetailId = raBillDetailId;
    }

    public BigInteger getRaBillId() {
        return raBillId;
    }

    public void setRaBillId(BigInteger raBillId) {
        this.raBillId = raBillId;
    }

    public BigInteger getBoqDetailId() {
        return boqDetailId;
    }

    public void setBoqDetailId(BigInteger boqDetailId) {
        this.boqDetailId = boqDetailId;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
