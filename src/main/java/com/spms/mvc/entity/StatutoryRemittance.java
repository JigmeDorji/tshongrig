package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: StatutoryRemittance
 * Date:  2021-Apr-17
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-17
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_hr_statutory_remittance")
public class StatutoryRemittance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "salarySheetId")
    private Integer salarySheetId;

    @Column(name = "bankLedgerId")
    private String bankLedgerId;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "month")
    private Integer month;

    @Column(name = "financialYearId")
    private Integer financialYearId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalarySheetId() {
        return salarySheetId;
    }

    public void setSalarySheetId(Integer salarySheetId) {
        this.salarySheetId = salarySheetId;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
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