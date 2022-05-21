package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Description: OpeningAndBuyingDTO
 * Date:  2021-Sep-16
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Sep-16
 * Change Description:
 * Search Tag:
 */
public class OpeningAndBuyingDTO {

    private String purchaseInvoiceNo;
    private BigInteger assetDetailId;
    private BigInteger assetId;
    private BigInteger faPurchaseId;
    private String particular;

    private Double amtReceived;
    private Double amtReturn;
    private Integer voucherNo;
    private String ledgerId;
    private Integer accTypeId;

    private String partyName;
    private String partyEmail;
    private String partyContactNo;
    private String partyAddress;
    private String bankLedgerId;


    private Double amount;
    private Double rate;
    private BigDecimal qty;
    private Double depreciatedValue;
    private Double advanceAmountPaid;
    private Double discountRate;
    private Integer isCash;
    private Double amountReceivedInBank;
    private Date purchaseDate;

    private Integer paidInType;
    private BigInteger purchaseMasterId;

    private List<OpeningAndBuyingListDTO> openingAndBuyingListDTO;

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public BigInteger getAssetDetailId() {
        return assetDetailId;
    }

    public void setAssetDetailId(BigInteger assetDetailId) {
        this.assetDetailId = assetDetailId;
    }

    public BigInteger getAssetId() {
        return assetId;
    }

    public void setAssetId(BigInteger assetId) {
        this.assetId = assetId;
    }

    public BigInteger getFaPurchaseId() {
        return faPurchaseId;
    }

    public void setFaPurchaseId(BigInteger faPurchaseId) {
        this.faPurchaseId = faPurchaseId;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
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

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyEmail() {
        return partyEmail;
    }

    public void setPartyEmail(String partyEmail) {
        this.partyEmail = partyEmail;
    }

    public String getPartyContactNo() {
        return partyContactNo;
    }

    public void setPartyContactNo(String partyContactNo) {
        this.partyContactNo = partyContactNo;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Double getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(Double depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public Double getAdvanceAmountPaid() {
        return advanceAmountPaid;
    }

    public void setAdvanceAmountPaid(Double advanceAmountPaid) {
        this.advanceAmountPaid = advanceAmountPaid;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public Double getAmountReceivedInBank() {
        return amountReceivedInBank;
    }

    public void setAmountReceivedInBank(Double amountReceivedInBank) {
        this.amountReceivedInBank = amountReceivedInBank;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPaidInType() {
        return paidInType;
    }

    public void setPaidInType(Integer paidInType) {
        this.paidInType = paidInType;
    }

    public BigInteger getPurchaseMasterId() {
        return purchaseMasterId;
    }

    public void setPurchaseMasterId(BigInteger purchaseMasterId) {
        this.purchaseMasterId = purchaseMasterId;
    }

    public List<OpeningAndBuyingListDTO> getOpeningAndBuyingListDTO() {
        return openingAndBuyingListDTO;
    }

    public void setOpeningAndBuyingListDTO(List<OpeningAndBuyingListDTO> openingAndBuyingListDTO) {
        this.openingAndBuyingListDTO = openingAndBuyingListDTO;
    }
}
