package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 16-Dec-16.
 */
public class SaleItemDTO {

    private String SerialNumber;
    private Integer itemCategoryId;
    private String partNumber;
    private String partDescription;
    private Double pricePerQty;
    private String locationId;
    private Integer isCash;

    private Integer qty;
    private String unitName;
    private BigDecimal sumQty;
    private Double amount;
    private Double advanceAmountPaid;
    private Double discountRate;

    private BigInteger qtyLeft;
    private Date saleDate;

    private Integer id;
    private String unit;

    private Double sellingPrice;
    private String itemCode;
    private String itemName;

    private String partyName;
    private String partyEmail;
    private String partyContactNo;
    private String partyAddress;
    private String bankLedgerId;

    private Double amtReceived;
    private Double amtReturn;
    private Integer voucherNo;
    private String ledgerId;
    private Integer accTypeId;
    private String receiptMemoNo;

    private List<SaleItemListDTO> saleItemListDTO;
    private Double amountReceivedInBank;
    private Integer partyId;
    private Integer saleInType;
    private String invoiceNo;
    private double costPrice;
    private String issueTo;

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Double getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(Double pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getSumQty() {
        return sumQty;
    }

    public void setSumQty(BigDecimal sumQty) {
        this.sumQty = sumQty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public BigInteger getQtyLeft() {
        return qtyLeft;
    }

    public void setQtyLeft(BigInteger qtyLeft) {
        this.qtyLeft = qtyLeft;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<SaleItemListDTO> getSaleItemListDTO() {
        return saleItemListDTO;
    }

    public void setSaleItemListDTO(List<SaleItemListDTO> saleItemListDTO) {
        this.saleItemListDTO = saleItemListDTO;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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

    public String getReceiptMemoNo() {
        return receiptMemoNo;
    }

    public void setReceiptMemoNo(String receiptMemoNo) {
        this.receiptMemoNo = receiptMemoNo;
    }

    public Double getAmountReceivedInBank() {
        return amountReceivedInBank;
    }

    public void setAmountReceivedInBank(Double amountReceivedInBank) {
        this.amountReceivedInBank = amountReceivedInBank;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getSaleInType() {
        return saleInType;
    }

    public void setSaleInType(Integer saleInType) {
        this.saleInType = saleInType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getIssueTo() {
        return issueTo;
    }

    public void setIssueTo(String issueTo) {
        this.issueTo = issueTo;
    }


    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "SaleItemDTO{" +
                "itemCategoryId=" + itemCategoryId +
                ", partNumber='" + partNumber + '\'' +
                ", partDescription='" + partDescription + '\'' +
                ", pricePerQty=" + pricePerQty +
                ", locationId='" + locationId + '\'' +
                ", isCash=" + isCash +
                ", qty=" + qty +
                ", unitName='" + unitName + '\'' +
                ", sumQty=" + sumQty +
                ", amount=" + amount +
                ", advanceAmountPaid=" + advanceAmountPaid +
                ", discountRate=" + discountRate +
                ", qtyLeft=" + qtyLeft +
                ", saleDate=" + saleDate +
                ", id=" + id +
                ", unit='" + unit + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", partyName='" + partyName + '\'' +
                ", partyEmail='" + partyEmail + '\'' +
                ", partyContactNo='" + partyContactNo + '\'' +
                ", partyAddress='" + partyAddress + '\'' +
                ", bankLedgerId='" + bankLedgerId + '\'' +
                ", amtReceived=" + amtReceived +
                ", amtReturn=" + amtReturn +
                ", voucherNo=" + voucherNo +
                ", ledgerId='" + ledgerId + '\'' +
                ", accTypeId=" + accTypeId +
                ", receiptMemoNo='" + receiptMemoNo + '\'' +
                ", saleItemListDTO=" + saleItemListDTO +
                ", amountReceivedInBank=" + amountReceivedInBank +
                ", partyId=" + partyId +
                ", saleInType=" + saleInType +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", costPrice=" + costPrice +
                ", issueTo='" + issueTo + '\'' +
                '}';
    }
}
