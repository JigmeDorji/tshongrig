package com.spms.mvc.entity;


import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "tbl_raw_material_purchases_storage")
public class RawMaterialPurchasesStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchaseId")
    private BigInteger purchaseId;


    @Column(name = "purchaseDate")
    private Date purchaseDate;

    @Column(name = "purchaseInvoiceNumber")
    private String purchaseInvoiceNumber;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "qty")
    private int qty;

    @Column(name = "price")
    private Double price;

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = " unitId")
    private int unitId;

    @Column(name = "locationId")
    private int locationId;

    @Column(name = "isCashId")
    private int isCashId;

    @Column(name = "purchaseUnder")
    private String purchaseUnder;

    @Column(name = "bankLedgerId")
    private String bankLedgerId;

    @Column(name = "supplierId")
    private String supplierId;

    @Column(name = "financialYearId")
    private int financialYearId;

    @Column(name = "companyId")
    private int companyId;

    @Column(name = "createdBy")
    private String createdBy;


    public BigInteger getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(BigInteger purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseInvoiceNumber() {
        return purchaseInvoiceNumber;
    }

    public void setPurchaseInvoiceNumber(String purchaseInvoiceNumber) {
        this.purchaseInvoiceNumber = purchaseInvoiceNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getIsCashId() {
        return isCashId;
    }

    public void setIsCashId(int isCashId) {
        this.isCashId = isCashId;
    }

    public String getPurchaseUnder() {
        return purchaseUnder;
    }

    public void setPurchaseUnder(String purchaseUnder) {
        this.purchaseUnder = purchaseUnder;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(int financialYearId) {
        this.financialYearId = financialYearId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
/*

  create table tbl_rawmateriallocationsetup(
     locationSetUpId int auto_increment not null primary key,
     locationId varchar(50),
     description varchar(50),
     companyId int
     );



create table tbl_raw_material_purchases_storage(
purchaseId bigint primary key not null auto_increment,
purchaseDate datetime,
purchaseInvoiceNumber varchar(100),
itemName varchar(100),
qty int,
price decimal(18,2),
totalAmount decimal(18,2),
unitId int,
locationId int,
isCashId int,
purchaseUnder varchar(100),
bankLedgerId varchar(50),
supplierId varchar(50),
financialYearId int,
companyId int,
createdBy varchar(100),
setDate datetime default current_timestamp
);

create table tbl_raw_material_opening_balance_storage(
openingBalanceId bigint primary key not null auto_increment,
particular_itemName varchar(100),
quantity int,
unitId int,
price Decimal(18,2),
locationId int,
openingBalanceDate date,
storageModifier varchar(50),
companyId int,
companyName varchar(50)
);








);




 */