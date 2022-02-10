package com.spms.mvc.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: BrandItemCode
 * Date:  2020-Oct-31
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Oct-31
 * Change Description:
 * Search Tag:
 */

@Entity
@Component
@Table(name = "tbl_item_code")
public class BrandWiseItemCode {
    @Id
    @Column(name = "brandId")
    private Integer brandId;

    @Column(name = "brandName")
    private String brandName;

    @Column(name = "serialNo")
    private Integer serialNo;

    @Column(name = "brandPrefix")
    private String brandPrefix;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getBrandPrefix() {
        return brandPrefix;
    }

    public void setBrandPrefix(String brandPrefix) {
        this.brandPrefix = brandPrefix;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
