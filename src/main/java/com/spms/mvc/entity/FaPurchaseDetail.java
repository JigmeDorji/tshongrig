package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Description: FaPurchaseDetail
 * Date:  2021-Oct-13
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-13
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_fa_purchase_detail")
public class FaPurchaseDetail extends BaseEntity {

    @Id
    @Column(name = "faPurchaseDetailId")
    private BigInteger faPurchaseDetailId;

    @Column(name = "faPurchaseId")
    private BigInteger faPurchaseId;

    @Column(name = "assetCode")
    private String assetCode;

    @Column(name = "status")
    private Character status;

    public BigInteger getFaPurchaseDetailId() {
        return faPurchaseDetailId;
    }

    public void setFaPurchaseDetailId(BigInteger faPurchaseDetailId) {
        this.faPurchaseDetailId = faPurchaseDetailId;
    }

    public BigInteger getFaPurchaseId() {
        return faPurchaseId;
    }

    public void setFaPurchaseId(BigInteger faPurchaseId) {
        this.faPurchaseId = faPurchaseId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}
