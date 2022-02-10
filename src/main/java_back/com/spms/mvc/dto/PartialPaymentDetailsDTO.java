package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by vcass on 12/28/2017.
 */
public class PartialPaymentDetailsDTO {

    private String registration_no;
    private String vehicle_no;
    private Double partial_amount;
    private Double total_amount;
    private Double initial_total_amount;
    private Double tot;
    private Date payment_date;

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

    public Double getPartial_amount() {
        return partial_amount;
    }

    public void setPartial_amount(Double partial_amount) {
        this.partial_amount = partial_amount;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Double getInitial_total_amount() {
        return initial_total_amount;
    }

    public void setInitial_total_amount(Double initial_total_amount) {
        this.initial_total_amount = initial_total_amount;
    }
}
