package com.spms.mvc.dto;

/**
 * Created by SonamPC on 28-Dec-16.
 */
public class ReceiptCounterDTO {
    private Integer id;
    private Integer receiptSerial;
    private Integer autoMobileSerial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiptSerial() {
        return receiptSerial;
    }

    public void setReceiptSerial(Integer receiptSerial) {
        this.receiptSerial = receiptSerial;
    }

    public Integer getAutoMobileSerial() {
        return autoMobileSerial;
    }

    public void setAutoMobileSerial(Integer autoMobileSerial) {
        this.autoMobileSerial = autoMobileSerial;
    }
}
