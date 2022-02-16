package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: RABill
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
@Table(name = "tbl_acc_boq_ra_bill")
public class RABill {
    @Id
    @Column(name = "raBillId")
    private BigInteger raBillId;

    @Column(name = "boqId")
    private BigInteger boqId;

    @Column(name = "raSerialNo")
    private Integer raSerialNo;

    @Column(name = "raBillNo")
    private String raBillNo;

    @Column(name = "billDate")
    private Date billDate;

    @Column(name = "voucherNo")
    private Integer voucherNo;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    public BigInteger getRaBillId() {
        return raBillId;
    }

    public void setRaBillId(BigInteger raBillId) {
        this.raBillId = raBillId;
    }

    public BigInteger getBoqId() {
        return boqId;
    }

    public void setBoqId(BigInteger boqId) {
        this.boqId = boqId;
    }

    public Integer getRaSerialNo() {
        return raSerialNo;
    }

    public void setRaSerialNo(Integer raSerialNo) {
        this.raSerialNo = raSerialNo;
    }

    public String getRaBillNo() {
        return raBillNo;
    }

    public void setRaBillNo(String raBillNo) {
        this.raBillNo = raBillNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
