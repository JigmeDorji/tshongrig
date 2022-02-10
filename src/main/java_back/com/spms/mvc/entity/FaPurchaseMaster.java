package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: FaPurchaseMaster
 * Date:  2021-Oct-13
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-13
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_fa_purchase_mater")
public class FaPurchaseMaster extends BaseEntity {

    @Id
    @Column(name = "purchaseMasterId")
    private BigInteger purchaseMasterId;

    @Column(name = "purchaseInvoiceNo")
    private String purchaseInvoiceNo;

    @Column(name = "paidInType")
    private Integer paidInType;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "voucherNo")
    private Integer voucherNo;

    @Column(name = "partyId")
    private Integer partyId;

    @Column(name = "asOnDate")
    private Date asOnDate;

    public BigInteger getPurchaseMasterId() {
        return purchaseMasterId;
    }

    public void setPurchaseMasterId(BigInteger purchaseMasterId) {
        this.purchaseMasterId = purchaseMasterId;
    }

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public Integer getPaidInType() {
        return paidInType;
    }

    public void setPaidInType(Integer paidInType) {
        this.paidInType = paidInType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }
}
