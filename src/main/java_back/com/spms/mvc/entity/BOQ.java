package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: BOQ
 * Date:  2022-Jan-10
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-10
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_boq")
public class BOQ extends BaseEntity {

    @Id
    @Column(name = "boqId")
    private BigInteger boqId;

    @Column(name = "workOrderNo")
    private String workOrderNo;

    @Column(name = "nameOfWork")
    private String nameOfWork;

    @Column(name = "employingAgency")
    private String employingAgency;

    @Column(name = "workOrderDate")
    private Date workOrderDate;

    @Column(name = "workStartDate")
    private Date workStartDate;

    @Column(name = "completionDate")
    private Date completionDate;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "financialYearId")
    private Integer financialYearId;

    public BigInteger getBoqId() {
        return boqId;
    }

    public void setBoqId(BigInteger boqId) {
        this.boqId = boqId;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getNameOfWork() {
        return nameOfWork;
    }

    public void setNameOfWork(String nameOfWork) {
        this.nameOfWork = nameOfWork;
    }

    public String getEmployingAgency() {
        return employingAgency;
    }

    public void setEmployingAgency(String employingAgency) {
        this.employingAgency = employingAgency;
    }

    public Date getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(Date workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }
}
