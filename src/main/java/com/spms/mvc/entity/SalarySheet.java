package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jigme.dorji on 4/9/2021.
 */
@Entity
@Table(name = "tbl_hr_salary_sheet")
public class SalarySheet {
    @Id
    @Column(name = "salarySheetId")
    private Integer salarySheetId;

    @Column(name = "empId")
    private String empId;

    @Column(name = "monthId")
    private Integer monthId;

    @Column(name = "financialYearId")
    private Integer financialYearId;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "allowance")
    private Double allowance;

    @Column(name = "basicSalary")
    private Double basicSalary;

    @Column(name = "grossSalary")
    private Double grossSalary;

    @Column(name = "pF")
    private double pF;

    @Column(name = "gIS")
    private double gIS;

    @Column(name = "tDS")
    private double tDS;

    @Column(name = "hc")
    private double hC;

    @Column(name = "advance")
    private double advance;

    @Column(name = "deduction")
    private double deduction;

    @Column(name = "totalRecovery")
    private double totalRecovery;

    @Column(name = "takeHome")
    private double takeHome;

    @Column(name = "netSalary")
    private double netSalary;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;


    public Integer getSalarySheetId() {
        return salarySheetId;
    }

    public void setSalarySheetId(Integer salarySheetId) {
        this.salarySheetId = salarySheetId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getpF() {
        return pF;
    }

    public void setpF(double pF) {
        this.pF = pF;
    }

    public double getgIS() {
        return gIS;
    }

    public void setgIS(double gIS) {
        this.gIS = gIS;
    }

    public double gettDS() {
        return tDS;
    }

    public void settDS(double tDS) {
        this.tDS = tDS;
    }

    public double gethC() {
        return hC;
    }

    public void sethC(double hC) {
        this.hC = hC;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public double getTakeHome() {
        return takeHome;
    }

    public void setTakeHome(double takeHome) {
        this.takeHome = takeHome;
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

    public Integer getMonthId() {
        return monthId;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public double getTotalRecovery() {
        return totalRecovery;
    }

    public void setTotalRecovery(double totalRecovery) {
        this.totalRecovery = totalRecovery;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }
}
