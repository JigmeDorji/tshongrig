package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bcass Sawa on 2/13/2018.
 */
@Entity
@Table(name = "tbl_credit_payment_for_receiveitem")
public class CreditPaymentForReceiveItem implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "agency_id")
    private String agencyId;

    @Column(name = "bill_reference")
    private String bill_reference;

    @Column(name = "total_amount_paid")
    private Double total_amount_paid;

    @Column(name = "setDate")
    private Date setDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getBill_reference() {
        return bill_reference;
    }

    public void setBill_reference(String bill_reference) {
        this.bill_reference = bill_reference;
    }

    public Double getTotal_amount_paid() {
        return total_amount_paid;
    }

    public void setTotal_amount_paid(Double total_amount_paid) {
        this.total_amount_paid = total_amount_paid;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }
}
