package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by SonamPC on 22-Jul-17.
 */
public class ServiceChargesDTO {
    private Integer id;
    private String particularOfService;
    private Double chargesAmount;
    private Date serviceDate;
    private String referenceMemoNo;
    private  String employeeName;
    private  String workOrderNo;

    private String registrationDateAndTime;
    private String serviceCompletionDateAndTime;
    private Double discount;
    private Integer noOfSide;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParticularOfService() {
        return particularOfService;
    }

    public void setParticularOfService(String particularOfService) {
        this.particularOfService = particularOfService;
    }

    public Double getChargesAmount() {
        return chargesAmount;
    }

    public void setChargesAmount(Double chargesAmount) {
        this.chargesAmount = chargesAmount;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getReferenceMemoNo() {
        return referenceMemoNo;
    }

    public void setReferenceMemoNo(String referenceMemoNo) {
        this.referenceMemoNo = referenceMemoNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getRegistrationDateAndTime() {
        return registrationDateAndTime;
    }

    public void setRegistrationDateAndTime(String registrationDateAndTime) {
        this.registrationDateAndTime = registrationDateAndTime;
    }

    public String getServiceCompletionDateAndTime() {
        return serviceCompletionDateAndTime;
    }

    public void setServiceCompletionDateAndTime(String serviceCompletionDateAndTime) {
        this.serviceCompletionDateAndTime = serviceCompletionDateAndTime;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getNoOfSide() {
        return noOfSide;
    }

    public void setNoOfSide(Integer noOfSide) {
        this.noOfSide = noOfSide;
    }
}
