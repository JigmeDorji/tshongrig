package com.spms.mvc.dto;

/**
 * Created by Bcass Sawa on 10/24/2019.
 */
public class AccSaleInvoiceGenerationListDTO {
    private String ledgerName;
    private String particular;
    private Double amount;
    private String particularId;


    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getParticularId() {
        return particularId;
    }

    public void setParticularId(String particularId) {
        this.particularId = particularId;
    }
}
