package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Description: FaItemSetupDetail
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
@Table(name = "tbl_fa_item_setup_detail")
public class FaItemSetupDetail extends BaseEntity {

    @Id
    @Column(name = "assetDetailId")
    private BigInteger assetDetailId;

    @Column(name = "assetId")
    private BigInteger assetId;

    @Column(name = "particular")
    private String particular;

    public BigInteger getAssetDetailId() {
        return assetDetailId;
    }

    public void setAssetDetailId(BigInteger assetDetailId) {
        this.assetDetailId = assetDetailId;
    }

    public BigInteger getAssetId() {
        return assetId;
    }

    public void setAssetId(BigInteger assetId) {
        this.assetId = assetId;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }
}
