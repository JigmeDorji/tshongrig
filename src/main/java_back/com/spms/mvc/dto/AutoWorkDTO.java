package com.spms.mvc.dto;

/**
 * Created by SonamPC on 15-Oct-17.
 */
public class AutoWorkDTO {

    private Integer mechanicId;
    private Double serviceWorthAmt;
    private String workOrderNumber;
    private String mechanicalInChargeName;

    public Integer getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Integer mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Double getServiceWorthAmt() {
        return serviceWorthAmt;
    }

    public void setServiceWorthAmt(Double serviceWorthAmt) {
        this.serviceWorthAmt = serviceWorthAmt;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getMechanicalInChargeName() {
        return mechanicalInChargeName;
    }

    public void setMechanicalInChargeName(String mechanicalInChargeName) {
        this.mechanicalInChargeName = mechanicalInChargeName;
    }
}
