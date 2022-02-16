package com.spms.mvc.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Description: BOQDetails
 * Date:  2022-Jan-06
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-06
 * Change Description:
 * Search Tag:
 */
public class BOQDetailsDTO {

    private BigInteger boqId;
    private BigInteger raBillId;
    private String workOrderNo;
    private String nameOfWork;
    private String employingAgency;
    private Date workOrderDate;
    private Date workStartDate;
    private Date completionDate;
    private Date billDate;
    private Integer voucherNo;
    private String raBillNo;
    private Integer raSerialNo;
    private Double totalBillAmount;
    private MultipartFile excelMultipartFile;
    private List<BOQDetailsListDTO> boqDetailsListDTO;

    public BigInteger getBoqId() {
        return boqId;
    }

    public void setBoqId(BigInteger boqId) {
        this.boqId = boqId;
    }

    public BigInteger getRaBillId() {
        return raBillId;
    }

    public void setRaBillId(BigInteger raBillId) {
        this.raBillId = raBillId;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getNameOfWork() {
        return nameOfWork;
    }

    public void setNameOfWork(String nameOfWork) {
        this.nameOfWork = nameOfWork;
    }

    public String getEmployingAgency() {
        return employingAgency;
    }

    public void setEmployingAgency(String employingAgency) {
        this.employingAgency = employingAgency;
    }

    public Date getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(Date workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getRaBillNo() {
        return raBillNo;
    }

    public void setRaBillNo(String raBillNo) {
        this.raBillNo = raBillNo;
    }

    public Integer getRaSerialNo() {
        return raSerialNo;
    }

    public void setRaSerialNo(Integer raSerialNo) {
        this.raSerialNo = raSerialNo;
    }

    public Double getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(Double totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public MultipartFile getExcelMultipartFile() {
        return excelMultipartFile;
    }

    public void setExcelMultipartFile(MultipartFile excelMultipartFile) {
        this.excelMultipartFile = excelMultipartFile;
    }

    public List<BOQDetailsListDTO> getBoqDetailsListDTO() {
        return boqDetailsListDTO;
    }

    public void setBoqDetailsListDTO(List<BOQDetailsListDTO> boqDetailsListDTO) {
        this.boqDetailsListDTO = boqDetailsListDTO;
    }
}
