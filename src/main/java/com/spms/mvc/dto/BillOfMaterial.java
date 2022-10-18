package com.spms.mvc.dto;

import java.util.Date;

public class BillOfMaterial {

    private String productName;
    private Date billOfMaterialDate;
    private String unit;
    private  RawMaterial rawMaterial;
    private  OverHeadCost overHeadCost;

    public BillOfMaterial() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getBillOfMaterialDate() {
        return billOfMaterialDate;
    }

    public void setBillOfMaterialDate(Date billOfMaterialDate) {
        this.billOfMaterialDate = billOfMaterialDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public OverHeadCost getOverHeadCost() {
        return overHeadCost;
    }

    public void setOverHeadCost(OverHeadCost overHeadCost) {
        this.overHeadCost = overHeadCost;
    }
}

class  RawMaterial{
      private String particularName;
      private  int quantity;
      private String unit;

    public RawMaterial() {
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
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
class OverHeadCost{
    private String particularName;
    private  int quantity;

    public OverHeadCost() {
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
}


