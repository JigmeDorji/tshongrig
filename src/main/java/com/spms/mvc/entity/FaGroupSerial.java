package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: FixAssetGroupSerial
 * Date:  2021-Oct-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-08
 * Change Description:
 * Search Tag:
 */

@Entity
@Table(name = "tbl_fa_group_serial")
public class FaGroupSerial {

    @Id
    @Column(name = "assetSerialId")
    private Integer assetSerialId;

    @Column(name = "assetClassId")
    private Integer assetClassId;

    @Column(name = "assetNoSerial")
    private Integer assetNoSerial;

    @Column(name = "companyId")
    private Integer companyId;

    public Integer getAssetSerialId() {
        return assetSerialId;
    }

    public void setAssetSerialId(Integer assetSerialId) {
        this.assetSerialId = assetSerialId;
    }

    public Integer getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Integer assetClassId) {
        this.assetClassId = assetClassId;
    }

    public Integer getAssetNoSerial() {
        return assetNoSerial;
    }

    public void setAssetNoSerial(Integer assetNoSerial) {
        this.assetNoSerial = assetNoSerial;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
