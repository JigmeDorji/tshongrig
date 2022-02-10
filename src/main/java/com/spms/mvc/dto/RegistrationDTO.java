package com.spms.mvc.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by vcass on 12/27/2017.
 */
public class RegistrationDTO {

    //region private variable
    private String vehicle_type;
    private String service_type;
    private String section;
    private Integer contact_no;
    private Boolean paymentMode;
    private Boolean cash;
    private String bill_no;
    private Date registration_date;
    private String registration_no;
    private String vehicle_no;
    private String customer_name;
    private Date promise_date;
    private Time registration_time;
    private Character statusFlag;
    private String mechanicName;
    private Double serviceWorth;
    private String agencyId;
    private String agencyName;
    private String department;
    private String supplyOrderNo;
    private Double totalAmt;
    private Boolean isEdit;
    private List<AutoWorkDTO> autoWorkDTOs;
    //endregion

    //region getters and setters


    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getContact_no() {
        return contact_no;
    }

    public void setContact_no(Integer contact_no) {
        this.contact_no = contact_no;
    }

    public Boolean getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Boolean paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Boolean getCash() {
        return cash;
    }

    public void setCash(Boolean cash) {
        this.cash = cash;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Date getPromise_date() {
        return promise_date;
    }

    public void setPromise_date(Date promise_date) {
        this.promise_date = promise_date;
    }

    public Time getRegistration_time() {
        return registration_time;
    }

    public void setRegistration_time(Time registration_time) {
        this.registration_time = registration_time;
    }

    public Character getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Character statusFlag) {
        this.statusFlag = statusFlag;
    }

    public List<AutoWorkDTO> getAutoWorkDTOs() {
        return autoWorkDTOs;
    }

    public void setAutoWorkDTOs(List<AutoWorkDTO> autoWorkDTOs) {
        this.autoWorkDTOs = autoWorkDTOs;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public Double getServiceWorth() {
        return serviceWorth;
    }

    public void setServiceWorth(Double serviceWorth) {
        this.serviceWorth = serviceWorth;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getSupplyOrderNo() {
        return supplyOrderNo;
    }

    public void setSupplyOrderNo(String supplyOrderNo) {
        this.supplyOrderNo = supplyOrderNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    //endregion
}
