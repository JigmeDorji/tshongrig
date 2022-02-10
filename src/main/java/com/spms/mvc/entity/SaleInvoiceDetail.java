package com.spms.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jigmePc on 10/22/2019.
 */
@Entity
@Table(name = "tbl_acc_sale_invoice_detail")
public class SaleInvoiceDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleInvoiceDetailId")
    private Integer saleInvoiceDetailId;

    @Column(name = "saleInvoiceId")
    private Integer saleInvoiceId;

    @Column(name = "particular")
    private String particular;


    @Column(name = "amount")
    private Double amount;

    public Integer getSaleInvoiceDetailId() {
        return saleInvoiceDetailId;
    }

    public void setSaleInvoiceDetailId(Integer saleInvoiceDetailId) {
        this.saleInvoiceDetailId = saleInvoiceDetailId;
    }

    public Integer getSaleInvoiceId() {
        return saleInvoiceId;
    }

    public void setSaleInvoiceId(Integer saleInvoiceId) {
        this.saleInvoiceId = saleInvoiceId;
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


}
