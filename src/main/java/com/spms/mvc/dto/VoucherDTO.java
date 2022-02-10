package com.spms.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 5/8/2019.
 */
public class VoucherDTO {
    private Integer voucherId;
    private Integer voucherTypeId;
    private Integer voucherNo;
    private Date voucherEntryDate;
    private Integer companyId;
    private String totalDebit;
    private String totalCredit;
    private Date setDate;
    private String createdBy;

    private List<VoucherDetailDTO> voucherDetailDTOList;
    private List<DepreciationDTO> depreciationDTOList;
    private String narration;


    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(Integer voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherEntryDate() {
        return voucherEntryDate;
    }

    public void setVoucherEntryDate(Date voucherEntryDate) {
        this.voucherEntryDate = voucherEntryDate;
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

    public List<VoucherDetailDTO> getVoucherDetailDTOList() {
        return voucherDetailDTOList;
    }

    public void setVoucherDetailDTOList(List<VoucherDetailDTO> voucherDetailDTOList) {
        this.voucherDetailDTOList = voucherDetailDTOList;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<DepreciationDTO> getDepreciationDTOList() {
        return depreciationDTOList;
    }

    public void setDepreciationDTOList(List<DepreciationDTO> depreciationDTOList) {
        this.depreciationDTOList = depreciationDTOList;
    }


}
