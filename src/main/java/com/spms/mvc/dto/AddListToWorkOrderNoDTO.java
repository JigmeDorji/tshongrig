package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by SonamPC on 20-Oct-17.
 */
public class AddListToWorkOrderNoDTO {
    private String workOrderNumber;
    private Date workStartDate;
    private Double totalAmount;
    private Character status;
    private String status_of_Payment;
    private Date promiseDate ;
    private Integer paymentDueDate;
    private String billNo;


    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getStatus_of_Payment() {
        return status_of_Payment;
    }

    public void setStatus_of_Payment(String status_of_Payment) {
        this.status_of_Payment = status_of_Payment;
    }

    public Date getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(Date promiseDate) {
        this.promiseDate = promiseDate;
    }

    public Integer getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Integer paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
