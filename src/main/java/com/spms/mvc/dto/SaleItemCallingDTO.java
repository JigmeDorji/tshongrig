package com.spms.mvc.dto;


import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 16-Dec-16.
 */
public class SaleItemCallingDTO {

    private Integer mechanicalInCharge;

    private Boolean isPartsSold;
    private Boolean isServiceGiven;
    private Boolean isDealer;
    private String receiptMemoNo;
    private String autoMobileMemoNo;
    private Integer dealerId;
    private String workOrderNumber;
    private Double netPayableAmt;
    private Double paymentAmount;
    private Date saleDate;
    private Double amount;
    private Boolean isCash;


    private List<SaleItemDTO> saleItemDTOs;
    private List<ServiceChargesDTO> serviceChargesDTOs;
    private List<AutoWorkDTO> autoWorkDTOs;

    public Boolean getIsCash() {
        return isCash;
    }

    public void setIsCash(Boolean isCash) {
        this.isCash = isCash;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public List<AutoWorkDTO> getAutoWorkDTOs() {
        return autoWorkDTOs;
    }

    public void setAutoWorkDTOs(List<AutoWorkDTO> autoWorkDTOs) {
        this.autoWorkDTOs = autoWorkDTOs;
    }

    public List<SaleItemDTO> getSaleItemDTOs() {
        return saleItemDTOs;
    }

    public void setSaleItemDTOs(List<SaleItemDTO> saleItemDTOs) {
        this.saleItemDTOs = saleItemDTOs;
    }

    public List<ServiceChargesDTO> getServiceChargesDTOs() {
        return serviceChargesDTOs;
    }

    public void setServiceChargesDTOs(List<ServiceChargesDTO> serviceChargesDTOs) {
        this.serviceChargesDTOs = serviceChargesDTOs;
    }

    public Boolean getIsDealer() {
        return isDealer;
    }

    public void setIsDealer(Boolean isDealer) {
        this.isDealer = isDealer;
    }

    public Integer getMechanicalInCharge() {
        return mechanicalInCharge;
    }

    public void setMechanicalInCharge(Integer mechanicalInCharge) {
        this.mechanicalInCharge = mechanicalInCharge;
    }

    public Boolean getIsPartsSold() {
        return isPartsSold;
    }

    public void setIsPartsSold(Boolean isPartsSold) {
        this.isPartsSold = isPartsSold;
    }

    public Boolean getIsServiceGiven() {
        return isServiceGiven;
    }

    public void setIsServiceGiven(Boolean isServiceGiven) {
        this.isServiceGiven = isServiceGiven;
    }

    public String getReceiptMemoNo() {
        return receiptMemoNo;
    }

    public void setReceiptMemoNo(String receiptMemoNo) {
        this.receiptMemoNo = receiptMemoNo;
    }

    public String getAutoMobileMemoNo() {
        return autoMobileMemoNo;
    }

    public void setAutoMobileMemoNo(String autoMobileMemoNo) {
        this.autoMobileMemoNo = autoMobileMemoNo;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Double getNetPayableAmt() {
        return netPayableAmt;
    }

    public void setNetPayableAmt(Double netPayableAmt) {
        this.netPayableAmt = netPayableAmt;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
