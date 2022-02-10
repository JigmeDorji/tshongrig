package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by SonamPC on 07-Jan-19.
 */
@Entity
@Table(name = "tbl_agencyledger")
public class AgencyLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "agentId")
    private String agentId;

    @Column(name = "billNo")
    private String billNo;

    @Column(name = "totalBillAmt")
    private Double totalBillAmt;

    @Column(name = "paymentStatus")
    private char paymentStatus;

    @Column(name = "generateDate")
    private Date generateDate;

    @Column(name = "workOrderNo")
    private String workOrderNo;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "supplyOrderNo")
    private String supplyOrderNo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Double getTotalBillAmt() {
        return totalBillAmt;
    }

    public void setTotalBillAmt(Double totalBillAmt) {
        this.totalBillAmt = totalBillAmt;
    }

    public char getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(char paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getSupplyOrderNo() {
        return supplyOrderNo;
    }

    public void setSupplyOrderNo(String supplyOrderNo) {
        this.supplyOrderNo = supplyOrderNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
