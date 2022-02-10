package com.spms.mvc.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 4/8/2021.
 */
public class SalarySheetDTO {
    private Integer monthId;
    private Integer cost;
    private Double totalBasicSalary;
    private Double totalAllowance;
    private Double totalDeduction;
    private Double totalGrossSalary;
    private Double totalPF;
    private Double totalGIS;
    private Double totalNetSalary;
    private Double totalTDS;
    private Double totalHC;
    private Double totalAdvance;
    private Double totalTotalRecovery;
    private Double totalTakeHome;
    private Double totalAmount;

    List<EmployeeSetupDTO> employeeSetupDTOS;

    public List<EmployeeSetupDTO> getEmployeeSetupDTOS() {
        return employeeSetupDTOS;
    }

    public void setEmployeeSetupDTOS(List<EmployeeSetupDTO> employeeSetupDTOS) {
        this.employeeSetupDTOS = employeeSetupDTOS;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }


    public Double getTotalBasicSalary() {
        return totalBasicSalary;
    }

    public void setTotalBasicSalary(Double totalBasicSalary) {
        this.totalBasicSalary = totalBasicSalary;
    }

    public Double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(Double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public Double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(Double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public Double getTotalGrossSalary() {
        return totalGrossSalary;
    }

    public void setTotalGrossSalary(Double totalGrossSalary) {
        this.totalGrossSalary = totalGrossSalary;
    }

    public Double getTotalPF() {
        return totalPF;
    }

    public void setTotalPF(Double totalPF) {
        this.totalPF = totalPF;
    }

    public Double getTotalGIS() {
        return totalGIS;
    }

    public void setTotalGIS(Double totalGIS) {
        this.totalGIS = totalGIS;
    }

    public Double getTotalNetSalary() {
        return totalNetSalary;
    }

    public void setTotalNetSalary(Double totalNetSalary) {
        this.totalNetSalary = totalNetSalary;
    }

    public Double getTotalTDS() {
        return totalTDS;
    }

    public void setTotalTDS(Double totalTDS) {
        this.totalTDS = totalTDS;
    }

    public Double getTotalHC() {
        return totalHC;
    }

    public void setTotalHC(Double totalHC) {
        this.totalHC = totalHC;
    }

    public Double getTotalAdvance() {
        return totalAdvance;
    }

    public void setTotalAdvance(Double totalAdvance) {
        this.totalAdvance = totalAdvance;
    }

    public Double getTotalTotalRecovery() {
        return totalTotalRecovery;
    }

    public void setTotalTotalRecovery(Double totalTotalRecovery) {
        this.totalTotalRecovery = totalTotalRecovery;
    }

    public Double getTotalTakeHome() {
        return totalTakeHome;
    }

    public void setTotalTakeHome(Double totalTakeHome) {
        this.totalTakeHome = totalTakeHome;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
