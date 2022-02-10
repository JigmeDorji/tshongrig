package com.spms.mvc.dto;

/**
 * Description: BrandDTO
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
public class BrandDTO {

    private Integer brandId;
    private String brandName;
    private String brandPrefix;
    private String remarks;

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
}
