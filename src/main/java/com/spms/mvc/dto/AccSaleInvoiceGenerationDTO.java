package com.spms.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 10/24/2019.
 */

public class AccSaleInvoiceGenerationDTO {

    private String invoiceNo;
    private Date invoiceDate;
    private String partyName;
    private String partyAddress;
    private String partyContactNo;
    private String partyEmail;
    private String physicalInvoiceNo;
    private Integer saleInvoiceId;
    private Double amount;
    private Integer isCash;
    private String bankLedgerId;
    private Double amtReceived;
    private Double amtReturn;
    private Integer voucherNo;
    private Double discountRate;
    private Double amountReceivedInBank;

    private List<AccSaleInvoiceGenerationListDTO> accSaleInvoiceGenerationListDTOList;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }

    public String getPartyContactNo() {
        return partyContactNo;
    }

    public void setPartyContactNo(String partyContactNo) {
        this.partyContactNo = partyContactNo;
    }

    public String getPartyEmail() {
        return partyEmail;
    }

    public void setPartyEmail(String partyEmail) {
        this.partyEmail = partyEmail;
    }

    public Integer getSaleInvoiceId() {
        return saleInvoiceId;
    }

    public void setSaleInvoiceId(Integer saleInvoiceId) {
        this.saleInvoiceId = saleInvoiceId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public Double getAmtReceived() {
        return amtReceived;
    }

    public void setAmtReceived(Double amtReceived) {
        this.amtReceived = amtReceived;
    }

    public Double getAmtReturn() {
        return amtReturn;
    }

    public void setAmtReturn(Double amtReturn) {
        this.amtReturn = amtReturn;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public List<AccSaleInvoiceGenerationListDTO> getAccSaleInvoiceGenerationListDTOList() {
        return accSaleInvoiceGenerationListDTOList;
    }

    public void setAccSaleInvoiceGenerationListDTOList(List<AccSaleInvoiceGenerationListDTO> accSaleInvoiceGenerationListDTOList) {
        this.accSaleInvoiceGenerationListDTOList = accSaleInvoiceGenerationListDTOList;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Double getAmountReceivedInBank() {
        return amountReceivedInBank;
    }

    public void setAmountReceivedInBank(Double amountReceivedInBank) {
        this.amountReceivedInBank = amountReceivedInBank;
    }

    public String getPhysicalInvoiceNo() {
        return physicalInvoiceNo;
    }

    public void setPhysicalInvoiceNo(String physicalInvoiceNo) {
        this.physicalInvoiceNo = physicalInvoiceNo;
    }
}
