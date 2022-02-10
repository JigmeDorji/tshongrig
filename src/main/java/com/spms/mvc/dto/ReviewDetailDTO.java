package com.spms.mvc.dto;

import java.math.BigInteger;

/**
 * Created by SonamPC on 25-Jan-18.
 */
public class ReviewDetailDTO {
    private String workOrderNo;
    private String vehicleNo;
    private String vehicleType;
    private String servicesProvided;
    private Double totalAmount;

    private String partName;
    private String partNo;
    private BigInteger qty;


    private Double amountRelease;
    private Double balanceAmount;
    private String customerName;
    private String partsDetails;

    private Double discountAmount;
    private String isDealer;
    private Double partialPayment;


    private Double totalServiceAmount;
    private Double totalPartsAmount;

    private Double discountServiceAmount;
    private Double discountPartsAmount;


    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(String servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public BigInteger getQty() {
        return qty;
    }

    public void setQty(BigInteger qty) {
        this.qty = qty;
    }

    public Double getAmountRelease() {
        return amountRelease;
    }

    public void setAmountRelease(Double amountRelease) {
        this.amountRelease = amountRelease;
    }

    public String getPartsDetails() {
        return partsDetails;
    }

    public void setPartsDetails(String partsDetails) {
        this.partsDetails = partsDetails;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getIsDealer() {
        return isDealer;
    }

    public void setIsDealer(String isDealer) {
        this.isDealer = isDealer;
    }

    public Double getPartialPayment() {
        return partialPayment;
    }

    public void setPartialPayment(Double partialPayment) {
        this.partialPayment = partialPayment;
    }

    public Double getTotalServiceAmount() {
        return totalServiceAmount;
    }

    public void setTotalServiceAmount(Double totalServiceAmount) {
        this.totalServiceAmount = totalServiceAmount;
    }



    public Double getDiscountServiceAmount() {
        return discountServiceAmount;
    }

    public void setDiscountServiceAmount(Double discountServiceAmount) {
        this.discountServiceAmount = discountServiceAmount;
    }

    public Double getTotalPartsAmount() {
        return totalPartsAmount;
    }

    public void setTotalPartsAmount(Double totalPartsAmount) {
        this.totalPartsAmount = totalPartsAmount;
    }

    public Double getDiscountPartsAmount() {
        return discountPartsAmount;
    }

    public void setDiscountPartsAmount(Double discountPartsAmount) {
        this.discountPartsAmount = discountPartsAmount;
    }
}
