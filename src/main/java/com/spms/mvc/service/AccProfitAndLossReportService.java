package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.dao.AccProfitAndLossReportDao;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.DepreciationDTO;
import com.spms.mvc.library.helper.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Bcass Sawa on 5/14/2019.
 */
@Service("accProfitAndLossReportService")
public class AccProfitAndLossReportService {

    @Autowired
    private AccProfitAndLossReportDao accProfitAndLossReportDao;

    private Double getDepreciationAmount(Integer companyId, Double depreciationAmount) {
        List<DepreciationDTO> depreciationDTOList = accProfitAndLossReportDao.getDepreciationAmount(companyId);
        if (depreciationDTOList.size() > 0) {
            for (DepreciationDTO depreciationDTO : depreciationDTOList) {

                Calendar gregorianCalendarI = new GregorianCalendar();
                gregorianCalendarI.setTime(DateUtil.toDate(depreciationDTO.getDateOfPurchase(), DateUtil.YYYY_MM_DD));

                Calendar gregorianCalendarII = new GregorianCalendar();
                gregorianCalendarII.setTime(new Date());

                Integer monthOfPurchase = gregorianCalendarI.get(Calendar.MONTH);
                Integer currentMonth = gregorianCalendarII.get(Calendar.MONTH);
                Integer differenceInMonth = currentMonth - monthOfPurchase;
                depreciationAmount = depreciationAmount + (((depreciationDTO.getRateOfDepreciation() * 0.01)
                        * (depreciationDTO.getCost() * depreciationDTO.getQty())) / 12) * differenceInMonth;
            }
        }
        return depreciationAmount;
    }

 /*   public Double getNetProfitOrLoss(Integer companyId) {
        Double totalSales = accProfitAndLossReportDao.getTotalSales(companyId, fromDate, toDate);
        Double totalClosingValue = viewItemService.getTotalStockValue();
        totalSales = totalSales + totalClosingValue;//this is done in order to add closing stock value where ever the total sales amt is there
        Double totalPurchase = null;

        Double totalDirectIncome = accProfitAndLossReportDao.getTotalDirectIncome(companyId);
        totalDirectIncome = totalDirectIncome == null ? 0.00 + totalSales : totalDirectIncome + totalSales;

        Double totalDirectExpense = accProfitAndLossReportDao.getTotalDirectExpense(companyId);
        totalDirectExpense = totalDirectExpense == null ? 0.00 + totalPurchase : totalDirectExpense + totalPurchase;

        Double broughtForwardAmount = totalDirectIncome - totalDirectExpense;
        Double totalInDirectIncome = accProfitAndLossReportDao.getTotalInDirectIncome(companyId);
        totalInDirectIncome = totalInDirectIncome == null ? 0.00 : totalInDirectIncome;
        Double totalInDirectExpense = accProfitAndLossReportDao.getTotalInDirectExpense(companyId);
        totalInDirectExpense = totalInDirectExpense == null ? 0.00 : totalInDirectExpense;

        return broughtForwardAmount + (totalInDirectIncome - totalInDirectExpense);
    }*/

    /**
     * get profit and lost details for running format
     *
     * @param companyId       companyId
     * @param fromDate
     * @param toDate          @return List<AccProfitAndLossReportDTO>  List<AccProfitAndLossReportDTO>
     * @param businessType
     * @param financialYearId
     */
    public List<AccProfitAndLossReportDTO> getProfitAndLossDetails(Integer companyId, Date fromDate, Date toDate, Integer businessType, Integer financialYearId, Boolean calFrom) {

        Double profitOrLossAmt;
        Double totalSales = 0.0;
        Double totalCostOfGoodAmt = 0.0;

        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();

        AccProfitAndLossReportDTO accProfitAndLossReportDTO = new AccProfitAndLossReportDTO();

        if (BusinessType.Trading.getValue().equals(businessType)) {
            //Sale
            totalSales = getTotalAmountByAccountTypeId(companyId, AccountTypeEnum.SALES.getValue(), fromDate, toDate, financialYearId, calFrom);
            accProfitAndLossReportDTOList.addAll(getSaleDetailList(totalSales));
            //Cost of Good Sold
            totalCostOfGoodAmt = accProfitAndLossReportDao.getTotalGoodSoldAmount(companyId, fromDate, toDate);
            accProfitAndLossReportDTOList.addAll(getTotalGoodSoldDetail(totalCostOfGoodAmt));

        }

        //Direct Income
        Double totalDirectIncome = getTotalAmountByAccountTypeId(companyId, AccountTypeEnum.DIRECT_INCOME.getValue(),
                fromDate, toDate, financialYearId, calFrom);

        if (totalDirectIncome != 0) {
            accProfitAndLossReportDTOList.addAll(getDirectIncomeList(totalDirectIncome));
        }

        //Direct Expense/Cost
        Double totalDirectCost = getTotalAmountByAccountTypeId(companyId, AccountTypeEnum.DIRECT_COST.getValue(),
                fromDate, toDate, financialYearId, calFrom);
        accProfitAndLossReportDTOList.addAll(getDirectCostList(totalDirectCost));


        //region calculate the profit
        totalDirectIncome = totalDirectIncome == null ? 0.00 : totalDirectIncome;
        totalDirectCost = totalDirectCost == null ? 0.00 : totalDirectCost;
        profitOrLossAmt = (totalSales + totalDirectIncome) - (totalDirectCost + totalCostOfGoodAmt);

        accProfitAndLossReportDTO.setIsTopParent(Boolean.FALSE);
        accProfitAndLossReportDTO.setIsNotLedger(Boolean.TRUE);
        accProfitAndLossReportDTO.setLedgerName("Gross Profit");
        accProfitAndLossReportDTO.setAmount(profitOrLossAmt);
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDTO);
        //endregion

        //Indirect/other Income
        Double totalOtherIncome = getTotalAmountByAccountTypeId(companyId, AccountTypeEnum.OTHER_INCOME.getValue(),
                fromDate, toDate, financialYearId, calFrom);
        accProfitAndLossReportDTOList.addAll(getOtherIncomeList(totalOtherIncome));

        //Indirect/other Cost
        Double totalOtherCost = getTotalAmountByAccountTypeId(companyId, AccountTypeEnum.INDIRECT_COST.getValue(),
                fromDate, toDate, financialYearId, calFrom);
        accProfitAndLossReportDTOList.addAll(getOtherCostList(totalOtherCost));

        //region net total profit or loss
        accProfitAndLossReportDTO = new AccProfitAndLossReportDTO();
        accProfitAndLossReportDTO.setIsTopParent(Boolean.FALSE);
        accProfitAndLossReportDTO.setIsNotLedger(Boolean.TRUE);
        Double pLAMount = (totalOtherIncome + (profitOrLossAmt)) - totalOtherCost;
        if (pLAMount < 0) {
            accProfitAndLossReportDTO.setLedgerName("Net Loss");
        } else {
            accProfitAndLossReportDTO.setLedgerName("Net Profit");
        }
        accProfitAndLossReportDTO.setAmount(Math.abs(pLAMount));
        accProfitAndLossReportDTO.setReturnPNLAmount(pLAMount);
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDTO);
        //endregion

        return accProfitAndLossReportDTOList;
    }


    /**
     * get total amount by account type
     *
     * @param companyId       companyId
     * @param accountTypeId   accountTypeId
     * @param fromDate        fromDate
     * @param toDate          toDate
     * @param financialYearId
     * @return Double
     */
    private Double getTotalAmountByAccountTypeId(Integer companyId, Integer accountTypeId, Date fromDate,
                                                 Date toDate, Integer financialYearId, Boolean calFrom) {
        if (calFrom) {
            return Math.abs(accProfitAndLossReportDao.getDetailWithFinancialYear(companyId, accountTypeId,
                    fromDate, toDate, financialYearId));
        } else {
            return Math.abs(accProfitAndLossReportDao.getTotalAmountForSelectedAccountType(companyId, accountTypeId,
                    fromDate, toDate, financialYearId));
        }

    }


    private List<AccProfitAndLossReportDTO> getSaleDetailList(Double totalSales) {
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        AccProfitAndLossReportDTO accProfitAndLossReportDTO = new AccProfitAndLossReportDTO();
        accProfitAndLossReportDTO.setAmount(totalSales);
        accProfitAndLossReportDTO.setIsTopParent(Boolean.TRUE);
        accProfitAndLossReportDTO.setIsNotLedger(Boolean.FALSE);
        accProfitAndLossReportDTO.setLedgerName(AccountTypeEnum.SALES.getText());
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDTO);
        accProfitAndLossReportDTO.setAccTypeId(AccountTypeEnum.SALES.getValue());
        return accProfitAndLossReportDTOList;

    }

    private List<AccProfitAndLossReportDTO> getTotalGoodSoldDetail(Double totalCostOfGoodAmt) {
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        AccProfitAndLossReportDTO accProfitAndLossReportDTO = new AccProfitAndLossReportDTO();
        accProfitAndLossReportDTO.setLedgerName("Cost of Goods Sold");
        accProfitAndLossReportDTO.setAmount(totalCostOfGoodAmt);
        accProfitAndLossReportDTO.setIsTopParent(Boolean.TRUE);
        accProfitAndLossReportDTO.setIsNotLedger(Boolean.FALSE);
        accProfitAndLossReportDTO.setAccTypeId(null);
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDTO);
        return accProfitAndLossReportDTOList;
    }

    private List<AccProfitAndLossReportDTO> getDirectIncomeList(Double totalDirectIncome) {
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        AccProfitAndLossReportDTO accProfitAndLossReportDirectIncomeDTO = new AccProfitAndLossReportDTO();
        accProfitAndLossReportDirectIncomeDTO.setLedgerName(AccountTypeEnum.DIRECT_INCOME.getText());
        accProfitAndLossReportDirectIncomeDTO.setIsTopParent(Boolean.TRUE);
        accProfitAndLossReportDirectIncomeDTO.setIsNotLedger(Boolean.TRUE);
        accProfitAndLossReportDirectIncomeDTO.setAmount(totalDirectIncome);
        accProfitAndLossReportDirectIncomeDTO.setAccTypeId(AccountTypeEnum.DIRECT_INCOME.getValue());
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDirectIncomeDTO);

        return accProfitAndLossReportDTOList;
    }

    private List<AccProfitAndLossReportDTO> getDirectCostList(Double totalDirectCost) {

        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        AccProfitAndLossReportDTO accProfitAndLossReportDirectExpenseDTO = new AccProfitAndLossReportDTO();
        accProfitAndLossReportDirectExpenseDTO.setLedgerName(AccountTypeEnum.DIRECT_COST.getText());
        accProfitAndLossReportDirectExpenseDTO.setIsTopParent(Boolean.TRUE);
        accProfitAndLossReportDirectExpenseDTO.setIsNotLedger(Boolean.TRUE);
        accProfitAndLossReportDirectExpenseDTO.setAmount(totalDirectCost);
        accProfitAndLossReportDirectExpenseDTO.setAccTypeId(AccountTypeEnum.DIRECT_COST.getValue());
        accProfitAndLossReportDTOList.add(accProfitAndLossReportDirectExpenseDTO);

        return accProfitAndLossReportDTOList;
    }

    private List<AccProfitAndLossReportDTO> getOtherIncomeList(Double totalOtherIncome) {

        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        if (totalOtherIncome > 0) {
            AccProfitAndLossReportDTO accProfitAndLossReportIndirectIncomeDTO = new AccProfitAndLossReportDTO();

            accProfitAndLossReportIndirectIncomeDTO.setLedgerName(AccountTypeEnum.OTHER_INCOME.getText());
            accProfitAndLossReportIndirectIncomeDTO.setAccTypeId(AccountTypeEnum.OTHER_INCOME.getValue());
            accProfitAndLossReportIndirectIncomeDTO.setAccTypeId(AccountTypeEnum.OTHER_INCOME.getValue());
            accProfitAndLossReportIndirectIncomeDTO.setAmount(totalOtherIncome);
            accProfitAndLossReportIndirectIncomeDTO.setIsTopParent(Boolean.TRUE);
            accProfitAndLossReportDTOList.add(accProfitAndLossReportIndirectIncomeDTO);
        }
        return accProfitAndLossReportDTOList;
    }

    private List<AccProfitAndLossReportDTO> getOtherCostList(Double totalOtherCost) {

        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        AccProfitAndLossReportDTO accProfitAndLossReportIndirectIncomeDTO = new AccProfitAndLossReportDTO();

        accProfitAndLossReportIndirectIncomeDTO.setLedgerName(AccountTypeEnum.INDIRECT_COST.getText());
        accProfitAndLossReportIndirectIncomeDTO.setAccTypeId(AccountTypeEnum.INDIRECT_COST.getValue());
        accProfitAndLossReportIndirectIncomeDTO.setAccTypeId(AccountTypeEnum.INDIRECT_COST.getValue());
        accProfitAndLossReportIndirectIncomeDTO.setAmount(totalOtherCost);
        accProfitAndLossReportIndirectIncomeDTO.setIsTopParent(Boolean.TRUE);
        accProfitAndLossReportDTOList.add(accProfitAndLossReportIndirectIncomeDTO);

        return accProfitAndLossReportDTOList;
    }

}


