package com.spms.mvc.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_rawmaterial_storage")
public class RawMaterialStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "particular_name")
    private String particularName;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "Unit")
    private String Unit;
    @Column(name = "Price")
    private double Price;
    @Column(name = "location")
    private String location;
    @Column(name = "rawMaterial_date")
    private Date date;
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

    public String getParticularName() {
        return particularName;
    }

    public void setParticularName(String particularName) {
        this.particularName = particularName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

/*


CREATE TABLE `bigapplebrand_db`.`tbl_rawmaterial_storage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `particular_name` VARCHAR(100) NULL,
  `quantity` INT NULL,
  `Unit` VARCHAR(45) NULL,
  `Price` DOUBLE NULL,
  `location` VARCHAR(45) NULL,
  `date` DATE NULL,
  `createdby` VARCHAR(45) NULL,
  `companyId` INT NULL,
  `companyName` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

 */