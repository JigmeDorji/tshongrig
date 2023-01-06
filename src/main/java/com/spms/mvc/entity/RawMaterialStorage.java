package com.spms.mvc.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_raw_material_opening_balance_storage")
public class RawMaterialStorage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "openingBalanceId")
    private Integer id;
    @Column(name = "particular_itemName")
    private String particularItemName;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unitId")
    private int unitId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "locationId")
    private int locationId;
    @Column(name = "openingBalanceDate")
    private Date openingBalanceDate;
    @Column(name = "storageModifier")
    private String storageModifier;
    @Column(name = "companyId")
    private int companyId;
    @Column(name = "companyName")
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParticularItemName() {
        return particularItemName;
    }

    public void setParticularItemName(String particularItemName) {
        this.particularItemName = particularItemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public String getStorageModifier() {
        return storageModifier;
    }

    public void setStorageModifier(String storageModifier) {
        this.storageModifier = storageModifier;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

