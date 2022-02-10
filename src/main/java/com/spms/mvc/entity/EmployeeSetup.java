package com.spms.mvc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by SonamPC on 05-Mar-17.
 */
@Entity
@Table(name = "tbl_hr_employeesetup")
public class EmployeeSetup {

    @Id
    @Column(name = "empId")
    private String empId;

    @NotNull
    @Column(name = "empName")
    private String empName;

    @NotNull
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;

    @Column(name = "cidNo")
    private String cidNo;

    @Column(name = "contactNo")
    private String contactNo;

    @Column(name = "tpnNo")
    private String tpnNo;

    @Column(name = "dateOfAppointment")
    private Date dateOfAppointment;

    @Column(name = "basicSalary")
    private Double basicSalary;

    @Column(name = "post")
    private String post;

    @Column(name = "incrementAmount")
    private Double incrementAmount;

    @Column(name = "incrementEffectDate")
    private Date incrementEffectDate;

    @Column(name = "serviceType")
    private Integer serviceType;

    @Column(name = "allowance")
    private Double allowance;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Column(name = "village")
    private String village;

    @Column(name = "gewog")
    private String gewog;

    @Column(name = "dzongkhag")
    private String dzongkhag;

    @Column(name = "accNo")
    private String accNo;
    @Column(name = "pF")
    private Double pF;

    @Column(name = "gIS")
    private Double gIS;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "status")
    private Character status;

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

    public Double getpF() {
        return pF;
    }

    public void setpF(Double pF) {
        this.pF = pF;
    }

    public Double getgIS() {
        return gIS;
    }

    public void setgIS(Double gIS) {
        this.gIS = gIS;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}
