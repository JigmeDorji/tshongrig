package com.spms.mvc.dto;

import java.util.List;

/**
 * Description: SalaryRemittanceDTO
 * Date:  2021-Apr-09
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-09
 * Change Description:
 * Search Tag:
 */
public class SalaryRemittanceDTO {

    private Integer month;
    private Integer cost;
    private Double totalAmount;
    private String empName;
    private Double takeHome;
    private String accNo;
    private Integer salarySheetId;
    private String bankLedgerId;
    private Boolean isGenerated;
    private List<SalaryRemittanceListDTO> salaryRemittanceListDTOList;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getTakeHome() {
        return takeHome;
    }

    public void setTakeHome(Double takeHome) {
        this.takeHome = takeHome;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Integer getSalarySheetId() {
        return salarySheetId;
    }

    public void setSalarySheetId(Integer salarySheetId) {
        this.salarySheetId = salarySheetId;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public List<SalaryRemittanceListDTO> getSalaryRemittanceListDTOList() {
        return salaryRemittanceListDTOList;
    }

    public void setSalaryRemittanceListDTOList(List<SalaryRemittanceListDTO> salaryRemittanceListDTOList) {
        this.salaryRemittanceListDTOList = salaryRemittanceListDTOList;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Boolean getGenerated() {
        return isGenerated;
    }

    public void setGenerated(Boolean generated) {
        isGenerated = generated;
    }
}
