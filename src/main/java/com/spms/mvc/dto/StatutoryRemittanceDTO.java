package com.spms.mvc.dto;

import java.util.List;

/**
 * Description: StatutoryRemittanceDTO
 * Date:  2021-Apr-17
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-17
 * Change Description:
 * Search Tag:
 */
public class StatutoryRemittanceDTO {
    private Integer month;
    private Integer cost;
    private Double totalAmount;
    private double tDS;
    private double hC;
    private double pF;
    private double gIS;
    private String empName;
    private String bankLedgerId;
    private List<StatutoryRemittanceListDTO> statutoryRemittanceListDTO;

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

    public double gettDS() {
        return tDS;
    }

    public void settDS(double tDS) {
        this.tDS = tDS;
    }

    public double gethC() {
        return hC;
    }

    public void sethC(double hC) {
        this.hC = hC;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public List<StatutoryRemittanceListDTO> getStatutoryRemittanceListDTO() {
        return statutoryRemittanceListDTO;
    }

    public void setStatutoryRemittanceListDTO(List<StatutoryRemittanceListDTO> statutoryRemittanceListDTO) {
        this.statutoryRemittanceListDTO = statutoryRemittanceListDTO;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public double getpF() {
        return pF;
    }

    public void setpF(double pF) {
        this.pF = pF;
    }

    public double getgIS() {
        return gIS;
    }

    public void setgIS(double gIS) {
        this.gIS = gIS;
    }
}
