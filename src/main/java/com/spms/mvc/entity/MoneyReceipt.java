package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jigme.dorji on 10/28/2020.
 */

@Entity
@Table(name = "tbl_acc_money_receipt")
public class MoneyReceipt  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "receiptNo")
    private String receiptNo;

    @Column(name = "receiptDate")
    private Date receiptDate;

    @Column(name = "partyLedgerId")
    private String partyLedgerId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "receivedIn")
    private Integer receivedIn;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "createdBy")
    private String createdBy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getPartyLedgerId() {
        return partyLedgerId;
    }

    public void setPartyLedgerId(String partyLedgerId) {
        this.partyLedgerId = partyLedgerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getReceivedIn() {
        return receivedIn;
    }

    public void setReceivedIn(Integer receivedIn) {
        this.receivedIn = receivedIn;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
