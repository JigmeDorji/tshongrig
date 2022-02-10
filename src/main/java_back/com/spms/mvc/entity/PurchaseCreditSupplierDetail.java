package com.spms.mvc.entity;

import javax.persistence.*;

/**
 * Created by jigmePc on 10/21/2019.
 */
@Entity
@Table(name = "tbl_inv_purchase_credit_supplier")
public class PurchaseCreditSupplierDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "purchaseInvoiceNo")
    private String purchaseInvoiceNo;

    @Column(name = "supplierId")
    private Integer supplierId;

    @Column(name = "purchaseId")
    private Integer purchaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
}
