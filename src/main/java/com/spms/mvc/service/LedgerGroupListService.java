package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dao.LedgerGroupListDao;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2/29/2020.
 */
@Service("ledgerGroupListService")
public class LedgerGroupListService {
    @Autowired
    private LedgerGroupListDao ledgerGroupListDao;

    @Autowired
    private AccProfitAndLossReportService accProfitAndLossReportService;

    @Autowired
    private FinancialYearSetupService financialYearSetupService;

    public List<AccProfitAndLossReportDTO> getLedgerGroupList(CurrentUser currentUser, Integer accountTypeId) {
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOS = new ArrayList<>();

        for (AccProfitAndLossReportDTO accProfitAndLossReportDTO : ledgerGroupListDao.getLedgerGroupList(currentUser.getCompanyId(), accountTypeId, currentUser)) {

            if (accProfitAndLossReportDTO.getGroupId().equals(AccountTypeEnum.CAPITAL.getValue()) || accProfitAndLossReportDTO.getGroupId().equals(AccountTypeEnum.NON_CURRENT_LIABILITY.getValue()) ||
                    accProfitAndLossReportDTO.getGroupId().equals(AccountTypeEnum.CURRENT_LIABILITY.getValue())
                    || accProfitAndLossReportDTO.getGroupId().equals(AccountTypeEnum.INCOME.getValue())) {
                accProfitAndLossReportDTO.setAmount(accProfitAndLossReportDTO.getAmount() * -1);
            }

            accProfitAndLossReportDTOS.add(accProfitAndLossReportDTO);
            //Add Previous year Income & expenditure
            FinancialYearDTO preFinancialYearDTO = financialYearSetupService.getPreviousFinancialYearDetail(currentUser.getCompanyId(), currentUser.getFinancialYearId());
            if (preFinancialYearDTO != null && accProfitAndLossReportDTO.getParticular().equals(AccountTypeEnum.CAPITAL.getText())) {
                AccProfitAndLossReportDTO accProfitAndLossReportDTOParticular = new AccProfitAndLossReportDTO();
                accProfitAndLossReportDTOParticular.setParticular("Previous year Income & expenditure");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(preFinancialYearDTO.getFinancialYearFrom());
                Date preFromDate = calendar.getTime();

                Calendar calendarTo = Calendar.getInstance();
                calendarTo.setTime(preFinancialYearDTO.getFinancialYearTo());
                Date preToDate = calendarTo.getTime();
                List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOsPrevious = accProfitAndLossReportService.getProfitAndLossDetails(currentUser.getCompanyId(),
                        preFromDate, preToDate, currentUser.getBusinessType(), currentUser.getFinancialYearId());
                Double profitAndLossAmountPrevious = accProfitAndLossReportDTOsPrevious.get(accProfitAndLossReportDTOsPrevious.size() - 1).getAmount();
                accProfitAndLossReportDTOParticular.setAmount(profitAndLossAmountPrevious);
                accProfitAndLossReportDTOS.add(accProfitAndLossReportDTOParticular);
            }
        }

        return accProfitAndLossReportDTOS;
    }
}
