package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jigmePc on 5/5/2019.
 */
@Entity
@Table(name = "tbl_acc_ledger")
public class Ledger {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledgerId")
    private String ledgerId;

    @Column(name = "bankId")
    private Integer bankId;

    @Column(name = "ledgerName")
    private String ledgerName;

    @Column(name = "accTypeId")
    private Integer accTypeId;

    @Column(name = "openingBal")
    private Double openingBal;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;


    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public Double getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(Double openingBal) {
        this.openingBal = openingBal;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
