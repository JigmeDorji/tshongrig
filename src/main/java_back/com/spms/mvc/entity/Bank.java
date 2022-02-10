package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description: Bank
 * Date:  2020-Dec-29
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Dec-29
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_bank")
public class Bank {

    @Id
    @Column(name = "bankId")
    private Integer bankId;

    @Column(name = "accNo")
    private String accNo;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "bankAccHolderDetail")
    private String bankAccHolderDetail;

    @Column(name = "accHolderName")
    private String accHolderName;

    @Column(name = "branch")
    private String branch;

    @Column(name = "reconciliationDate")
    private Date reconciliationDate;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccHolderDetail() {
        return bankAccHolderDetail;
    }

    public void setBankAccHolderDetail(String bankAccHolderDetail) {
        this.bankAccHolderDetail = bankAccHolderDetail;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(Date reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }
}
