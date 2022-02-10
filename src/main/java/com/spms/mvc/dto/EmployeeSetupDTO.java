package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SonamPC on 05-Mar-17.
 */
public class EmployeeSetupDTO {
    private String empId;
    private String empName;
    private Date dateOfBirth;
    private String cidNo;
    private String contactNo;
    private String tpnNo;
    private Date dateOfAppointment;
    private Double basicSalary;
    private String post;
    private Double incrementAmount;
    private Date incrementEffectDate;
    private Integer serviceType;
    private Double allowance;
    private BigDecimal absentDays;
    private String emailAddress;
    private String village;
    private String gewog;
    private String dzongkhag;
    private String accNo;

    private double pF;
    private double gIS;
    private double tDS;
    private double hC;
    private double advance;
    private double deduction;

    private double grossSalary;
    private double netSalary;
    private double totalRecovery;
    private double takeHome;
    private double totalAmount;
    private double balanceAdvance;

    private Integer companyId;
    private String createdBy;
    private Date createdDate;
    private Integer salarySheetId;
    private Integer monthId;
    private Integer financialYearId;
    private Integer cost;
    private Boolean isGenerated;
    private Character status;

    public BigDecimal getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(BigDecimal absentDays) {
        this.absentDays = absentDays;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCidNo() {
        return cidNo;
    }

    public void setCidNo(String cidNo) {
        this.cidNo = cidNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getTpnNo() {
        return tpnNo;
    }

    public void setTpnNo(String tpnNo) {
        this.tpnNo = tpnNo;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Double getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(Double incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public Date getIncrementEffectDate() {
        return incrementEffectDate;
    }

    public void setIncrementEffectDate(Date incrementEffectDate) {
        this.incrementEffectDate = incrementEffectDate;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getGewog() {
        return gewog;
    }

    public void setGewog(String gewog) {
        this.gewog = gewog;
    }

    public String getDzongkhag() {
        return dzongkhag;
    }

    public void setDzongkhag(String dzongkhag) {
        this.dzongkhag = dzongkhag;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
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

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getTotalRecovery() {
        return totalRecovery;
    }

    public void setTotalRecovery(double totalRecovery) {
        this.totalRecovery = totalRecovery;
    }

    public double getTakeHome() {
        return takeHome;
    }

    public void setTakeHome(double takeHome) {
        this.takeHome = takeHome;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Integer getSalarySheetId() {
        return salarySheetId;
    }

    public void setSalarySheetId(Integer salarySheetId) {
        this.salarySheetId = salarySheetId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public double getBalanceAdvance() {
        return balanceAdvance;
    }

    public void setBalanceAdvance(double balanceAdvance) {
        this.balanceAdvance = balanceAdvance;
    }

    public Boolean getGenerated() {
        return isGenerated;
    }

    public void setGenerated(Boolean generated) {
        isGenerated = generated;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}

