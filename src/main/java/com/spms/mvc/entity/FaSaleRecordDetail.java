package com.spms.mvc.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Description: FaSaleRecordDetail
 * Date:  2021-Oct-21
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-21
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_fa_sale_record_detail")
public class FaSaleRecordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "saleRecordId")
    private BigInteger saleRecordId;

    @Column(name = "faPurchaseDetailId")
    private BigInteger faPurchaseDetailId;

    @Column(name = "sellingPrice")
    private Double sellingPrice;

    @Column(name = "netAmount")
    private Double netAmount;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "assetCode")
    private String assetCode;


    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getSaleRecordId() {
        return saleRecordId;
    }

    public void setSaleRecordId(BigInteger saleRecordId) {
        this.saleRecordId = saleRecordId;
    }

    public BigInteger getFaPurchaseDetailId() {
        return faPurchaseDetailId;
    }

    public void setFaPurchaseDetailId(BigInteger faPurchaseDetailId) {
        this.faPurchaseDetailId = faPurchaseDetailId;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
