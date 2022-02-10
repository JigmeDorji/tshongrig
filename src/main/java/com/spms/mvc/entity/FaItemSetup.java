package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Description: FaItemSetup
 * Date:  2021-Oct-10
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-10
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_fa_item_setup")
public class FaItemSetup extends BaseEntity {
    @Id
    @Column(name = "assetId")
    private BigInteger assetId;

    @Column(name = "assetClassId")
    private Integer assetClassId;

    @Column(name = "description")
    private String description;

    @Column(name = "assetNo")
    private String assetNo;

    @Column(name = "companyId")
    private Integer companyId;

    public BigInteger getAssetId() {
        return assetId;
    }

    public void setAssetId(BigInteger assetId) {
        this.assetId = assetId;
    }

    public Integer getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Integer assetClassId) {
        this.assetClassId = assetClassId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
