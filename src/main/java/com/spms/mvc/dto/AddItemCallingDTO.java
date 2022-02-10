package com.spms.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 14-Jan-17.
 */
public class AddItemCallingDTO {
    private String agencyId;
    private Date receivedDate;
    private Boolean isCash;
    private String bill_reference;
    private Double amount;
    private List<AddItemDTO> addItemDTOs;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIsCash() {
        return isCash;
    }

    public void setIsCash(Boolean isCash) {
        this.isCash = isCash;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
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

    public List<AddItemDTO> getAddItemDTOs() {
        return addItemDTOs;
    }

    public void setAddItemDTOs(List<AddItemDTO> addItemDTOs) {
        this.addItemDTOs = addItemDTOs;
    }


}
