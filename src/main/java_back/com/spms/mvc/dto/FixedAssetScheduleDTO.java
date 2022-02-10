package com.spms.mvc.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Description: FixedAssetScheduleDTo
 * Date:  2021-Oct-25
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-25
 * Change Description:
 * Search Tag:
 */
public class FixedAssetScheduleDTO {

    private BigInteger assetDetailId;
    private BigInteger faPurchaseId;
    private String particular;
    private Date purchaseDate;
    private Double rate;
    private Double rateOfDep;
    private Double asOnStartFinancialYear;
    private Double addition;
    private Double disposal;
    private Double asOnEndFinancialYear;
    private Double depAsOnStartFinancialYear;
    private Double depCurrentYear;
    private Double depAsOnEndFinancialYear;
    private Double netValue;
    private Double depreciatedValue;
    private int groupId;
    private int assetClassId;

    public BigInteger getAssetDetailId() {
        return assetDetailId;
    }

    public void setAssetDetailId(BigInteger assetDetailId) {
        this.assetDetailId = assetDetailId;
    }

    public BigInteger getFaPurchaseId() {
        return faPurchaseId;
    }

    public void setFaPurchaseId(BigInteger faPurchaseId) {
        this.faPurchaseId = faPurchaseId;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateOfDep() {
        return rateOfDep;
    }

    public void setRateOfDep(Double rateOfDep) {
        this.rateOfDep = rateOfDep;
    }

    public Double getAsOnStartFinancialYear() {
        return asOnStartFinancialYear;
    }

    public void setAsOnStartFinancialYear(Double asOnStartFinancialYear) {
        this.asOnStartFinancialYear = asOnStartFinancialYear;
    }

    public Double getAddition() {
        return addition;
    }

    public void setAddition(Double addition) {
        this.addition = addition;
    }

    public Double getDisposal() {
        return disposal;
    }

    public void setDisposal(Double disposal) {
        this.disposal = disposal;
    }

    public Double getAsOnEndFinancialYear() {
        return asOnEndFinancialYear;
    }

    public void setAsOnEndFinancialYear(Double asOnEndFinancialYear) {
        this.asOnEndFinancialYear = asOnEndFinancialYear;
    }

    public Double getDepAsOnStartFinancialYear() {
        return depAsOnStartFinancialYear;
    }

    public void setDepAsOnStartFinancialYear(Double depAsOnStartFinancialYear) {
        this.depAsOnStartFinancialYear = depAsOnStartFinancialYear;
    }

    public Double getDepCurrentYear() {
        return depCurrentYear;
    }

    public void setDepCurrentYear(Double depCurrentYear) {
        this.depCurrentYear = depCurrentYear;
    }

    public Double getDepAsOnEndFinancialYear() {
        return depAsOnEndFinancialYear;
    }

    public void setDepAsOnEndFinancialYear(Double depAsOnEndFinancialYear) {
        this.depAsOnEndFinancialYear = depAsOnEndFinancialYear;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Double getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(Double depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(int assetClassId) {
        this.assetClassId = assetClassId;
    }
}
