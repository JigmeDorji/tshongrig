package com.spms.mvc.entity;

import javax.persistence.*;

/**
 * Created by Bcass Sawa on 10/24/2019.
 */
@Entity
@Table(name = "tbl_acc_sale_invoice_counter")
public class AccSaleInvoiceNoGeneration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "saleInvoiceNoCounter")
    private String saleInvoiceNoCounter;

    @Column(name = "companyId")
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleInvoiceNoCounter() {
        return saleInvoiceNoCounter;
    }

    public void setSaleInvoiceNoCounter(String saleInvoiceNoCounter) {
        this.saleInvoiceNoCounter = saleInvoiceNoCounter;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
