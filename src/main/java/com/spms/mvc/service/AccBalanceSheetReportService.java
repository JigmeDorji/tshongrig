package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.dao.AccBalanceSheetReportDao;
import com.spms.mvc.dao.AccProfitAndLossReportDao;
import com.spms.mvc.dto.AccBalanceSheetDTO;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * Created by jigmePc on 5/22/2019.
 */
@Service("accBalanceSheetReportService")
public class AccBalanceSheetReportService {

    @Autowired
    AccBalanceSheetReportDao accBalanceSheetReportDao;

    @Autowired
    AccProfitAndLossReportDao accProfitAndLossReportDao;

    @Autowired
    AccProfitAndLossReportService accProfitAndLossReportService;

    @Autowired
    FinancialYearSetupService financialYearSetupService;


    public List<AccBalanceSheetDTO> getAccBalanceSheetReport(CurrentUser currentUser, Date asOnDate) throws ParseException {

        List<AccBalanceSheetDTO> accBalanceSheetDTOs = new ArrayList<>();

        /*Inventory*/
        AccBalanceSheetDTO inventory = new AccBalanceSheetDTO();
        if(currentUser.getBusinessType().equals(BusinessType.Construction.getValue())){
            inventory.setParticular("Material");
        }else {
            inventory.setParticular("Inventory");
        }
        Double costOfGoodsSold = accProfitAndLossReportDao.getTotalGoodSoldAmount(currentUser.getCompanyId(), null, asOnDate);
        Double totalPurchase = accBalanceSheetReportDao.getTotalPurchase(currentUser.getCompanyId(), null, asOnDate);

        inventory.setAmount(totalPurchase - costOfGoodsSold);
        inventory.setGroupLevel(2);

        //To get Net profit from PNL
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOs = accProfitAndLossReportService.getProfitAndLossDetails(currentUser.getCompanyId(), null, asOnDate, currentUser.getBusinessType(), currentUser.getFinancialYearId());


        Double profitAndLossAmount = accProfitAndLossReportDTOs.get(accProfitAndLossReportDTOs.size() - 1).getReturnPNLAmount();
        AccBalanceSheetDTO incomeAndExpenditure = new AccBalanceSheetDTO();
        incomeAndExpenditure.setParticular("Income & Expenditure");
        incomeAndExpenditure.setAmount(profitAndLossAmount);
        incomeAndExpenditure.setGroupLevel(1);


        AccBalanceSheetDTO totalAsset = new AccBalanceSheetDTO();
        totalAsset.setParticular("Total Assets");
        totalAsset.setGroupLevel(1);

        AccBalanceSheetDTO totalLiability = new AccBalanceSheetDTO();
        totalLiability.setParticular("Total Liability");
        totalLiability.setGroupLevel(1);

        AccBalanceSheetDTO openingBalDif = new AccBalanceSheetDTO();
        openingBalDif.setParticular("Opening Balance Difference");
        openingBalDif.setGroupLevel(1);

        Double totalAssetAmt = 0.00;
        Double totalLiabilityAmt = 0.00;


        List<AccBalanceSheetDTO> accBalanceSheetDTOs1 = accBalanceSheetReportDao.getAccBalanceSheetReport(currentUser, asOnDate);
        Double openingBalDifAmt = accBalanceSheetReportDao.getOpenBalDiffAmt(currentUser.getCompanyId(), null, asOnDate) * -1;
//        Double openingBalDifAmt = calculateOpeningBalDiff(accBalanceSheetDTOs1, inventory,profitAndLossAmount);
        openingBalDif.setAmount(Math.abs(openingBalDifAmt));

        //region this will convert the total amount sign opposite if acc group is capital,NON_CURRENT_LIABILITY, CURRENT_LIABILITY
        List<AccBalanceSheetDTO> accBalanceSheetDTOS = new ArrayList<>();

        for (AccBalanceSheetDTO accBalanceSheetDTO : accBalanceSheetDTOs1) {

            if (accBalanceSheetDTO.getGroupId().equals(AccountTypeEnum.CAPITAL.getValue()) ||
                    accBalanceSheetDTO.getGroupId().equals(AccountTypeEnum.NON_CURRENT_LIABILITY.getValue()) ||
                    accBalanceSheetDTO.getGroupId().equals(AccountTypeEnum.CURRENT_LIABILITY.getValue())) {

                accBalanceSheetDTO.setAmount(accBalanceSheetDTO.getAmount() * -1);


            }
            //This will adjust previous year profit or loss amount to capital
            FinancialYearDTO preFinancialYearDTO = financialYearSetupService.getPreviousFinancialYearDetail(currentUser.getCompanyId());
            if (preFinancialYearDTO != null && accBalanceSheetDTO.getGroupId().equals(AccountTypeEnum.CAPITAL.getValue())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(preFinancialYearDTO.getFinancialYearFrom());
                Date preFromDate = calendar.getTime();

                Calendar calendarTo = Calendar.getInstance();
                calendarTo.setTime(preFinancialYearDTO.getFinancialYearTo());
                Date preToDate = calendarTo.getTime();
                List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOsPrevious = accProfitAndLossReportService.getProfitAndLossDetails(currentUser.getCompanyId(), preFromDate, preToDate, currentUser.getBusinessType(), currentUser.getFinancialYearId());
                Double profitAndLossAmountPrevious = accProfitAndLossReportDTOsPrevious.get(accProfitAndLossReportDTOsPrevious.size() - 1).getAmount();
                accBalanceSheetDTO.setAmount(accBalanceSheetDTO.getAmount() + profitAndLossAmountPrevious);
            }
            accBalanceSheetDTOS.add(accBalanceSheetDTO);
        }
        //endregion


        Integer iterator = accBalanceSheetDTOS.size();
        for (AccBalanceSheetDTO accBalanceSheetDTO : accBalanceSheetDTOS) {
            iterator = iterator - 1;

            if (accBalanceSheetDTO.getGroupLevel() == 1 && accBalanceSheetDTO.getGroupId() < 3) {
                totalAssetAmt = totalAssetAmt + accBalanceSheetDTO.getAmount();
            }
            if (accBalanceSheetDTO.getGroupLevel() == 1 && accBalanceSheetDTO.getGroupId() >= 3) {
                totalLiabilityAmt = totalLiabilityAmt + accBalanceSheetDTO.getAmount();
            }

            /*Add income and expenditure*/
            if (accBalanceSheetDTO.getGroupId() != null && Objects.equals(accBalanceSheetDTO.getGroupId(), AccountTypeEnum.CURRENT_LIABILITY.getValue())) {
//                if (incomeAndExpenditure.getAmount() != 0) {
//                    accBalanceSheetDTOs.add(incomeAndExpenditure);
//                }
                accBalanceSheetDTOs.add(accBalanceSheetDTO);
            } else if (accBalanceSheetDTO.getGroupId() != null && Objects.equals(accBalanceSheetDTO.getGroupId(), AccountTypeEnum.CAPITAL.getValue()) && accBalanceSheetDTO.getGroupLevel() == 1) {
                totalAsset.setAmount(totalAssetAmt + inventory.getAmount());//adding inventory to total assets grand

                if (inventory.getAmount() != 0) {
                    accBalanceSheetDTOs.add(inventory);
                }
                if (openingBalDifAmt != 0 && openingBalDifAmt < 0) {
                    accBalanceSheetDTOs.add(openingBalDif);
                    totalAsset.setAmount(totalAsset.getAmount() + Math.abs(openingBalDifAmt));
                }
                accBalanceSheetDTOs.add(totalAsset);
                accBalanceSheetDTOs.add(accBalanceSheetDTO);
            } else if (accBalanceSheetDTO.getGroupId() != null && Objects.equals(accBalanceSheetDTO.getGroupId(), AccountTypeEnum.CURRENT_ASSET.getValue()) && accBalanceSheetDTO.getGroupLevel() == 1) {
                accBalanceSheetDTO.setAmount(accBalanceSheetDTO.getAmount() + inventory.getAmount());//adding inventory to current asset total
                accBalanceSheetDTOs.add(accBalanceSheetDTO);
                /*if (inventory.getAmount() != 0) {
                    accBalanceSheetDTOs.add(inventory);
                }*/
            } else {
                accBalanceSheetDTOs.add(accBalanceSheetDTO);

            }
            if (iterator == 0) {
                totalLiabilityAmt = totalLiabilityAmt + profitAndLossAmount;
                accBalanceSheetDTOs.add(incomeAndExpenditure);
                if (openingBalDifAmt != 0 && openingBalDifAmt > 0) {

                    accBalanceSheetDTOs.add(openingBalDif);
                    totalLiability.setAmount(totalLiabilityAmt + Math.abs(openingBalDifAmt));
                } else {
                    totalLiability.setAmount(totalLiabilityAmt);
                }
                accBalanceSheetDTOs.add(totalLiability);//add total
            }
        }
        return accBalanceSheetDTOs;
    }

    private Double calculateOpeningBalDiff(List<AccBalanceSheetDTO> accBalanceSheetDTOS, AccBalanceSheetDTO inventory, Double profitAndLossAmount) {


        Double totalAssetAmt = 0.0;
        Double totalLiabilityAmt = 0.0;
        for (AccBalanceSheetDTO accBalanceSheetDTO : accBalanceSheetDTOS) {
            if (accBalanceSheetDTO.getGroupLevel() == 1 && accBalanceSheetDTO.getGroupId() < 3) {
                totalAssetAmt = totalAssetAmt + accBalanceSheetDTO.getAmount();
            }
            if (accBalanceSheetDTO.getGroupLevel() == 1 && accBalanceSheetDTO.getGroupId() >= 3) {
                totalLiabilityAmt = totalLiabilityAmt + accBalanceSheetDTO.getAmount();
            }
        }
        if (profitAndLossAmount > 0) {
            totalLiabilityAmt = totalLiabilityAmt + (profitAndLossAmount * -1);
        } else {
            totalAssetAmt = totalAssetAmt + (profitAndLossAmount * -1);
        }
        return Math.abs(totalAssetAmt) + inventory.getAmount() - Math.abs(totalLiabilityAmt);
    }
}