package com.spms.mvc.dto;

/**
 * Description: MultiVoucherDTO
 * Date:  2022-Mar-25
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Mar-25
 * Change Description:
 * Search Tag:
 */

public class MultiVoucherDTO {

    private String costDescription;
    private Double costAmount;
    private Integer costId;

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
    }

    public Double getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }
}
