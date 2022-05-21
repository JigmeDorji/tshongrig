package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.dao.AccBalanceSheetReportDao;
import com.spms.mvc.dao.AccProfitAndLossReportDao;
import com.spms.mvc.dao.AccTrialBalanceDao;
import com.spms.mvc.dao.FinancialPositionDao;
import com.spms.mvc.dto.AccBalanceSheetDTO;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.AccTrialBalanceDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 5/21/2022.
 */
@Service("financialPositionService")
public class FinancialPositionService {
    @Autowired
    private FinancialPositionDao financialPositionDao;

    @Autowired
    private AccProfitAndLossReportDao accProfitAndLossReportDao;

    @Autowired
    private AccBalanceSheetReportDao accBalanceSheetReportDao;

    @Autowired
    private AccProfitAndLossReportService accProfitAndLossReportService;

    @Autowired
    private FinancialYearSetupService financialYearSetupService;

    public List<AccTrialBalanceDTO> getTrialBalance(CurrentUser currentUser, Date fromDate, Date toDate) {
        return financialPositionDao.getTrialBalance(currentUser.getCompanyId(), currentUser.getFinancialYearId(),
                fromDate, toDate);
    }

    private List<AccTrialBalanceDTO> openingBalance(Integer companyId, Date fromDate, Date toDate) {
        AccTrialBalanceDTO accTrialBalDTO = new AccTrialBalanceDTO();
        List<AccTrialBalanceDTO> accTrialBalanceDTOs = new ArrayList<>();
        accTrialBalDTO.setGroupLevel(1);
        accTrialBalDTO.setParticular("Opening Balance Difference");
        Double openingBalance = accProfitAndLossReportDao.getOpeningBalanceDifference(companyId, fromDate, toDate);
        if (openingBalance < 0) {
            accTrialBalDTO.setCrAmount(Math.abs(openingBalance));

        } else {
            accTrialBalDTO.setDrAmount(openingBalance);

        }
        if (openingBalance != 0) {
            accTrialBalanceDTOs.add(accTrialBalDTO);
        }
        return accTrialBalanceDTOs;
    }


    public List<AccTrialBalanceDTO> formatDataList(List<AccTrialBalanceDTO> accTrialBalanceDTOList, Date fromDate, Date toDate, CurrentUser currentUser) {

        List<AccTrialBalanceDTO> accTrialBalanceDTOs = new ArrayList<>();

        for (AccTrialBalanceDTO accTrialBalanceDTO : accTrialBalanceDTOList) {
            AccTrialBalanceDTO accTrialBalDTO = new AccTrialBalanceDTO();

            //Adding inventory
            if (AccountTypeEnum.CURRENT_ASSET.getValue().equals(accTrialBalanceDTO.getGroupId()) && accTrialBalanceDTO.getGroupLevel().equals(1)) {
                accTrialBalDTO = new AccTrialBalanceDTO();
                Double costOfGoodsSold = accProfitAndLossReportDao.getTotalGoodSoldAmount(currentUser.getCompanyId(), null, toDate);
                Double totalPurchase = accBalanceSheetReportDao.getTotalPurchase(currentUser.getCompanyId(), null, toDate);

                double totalInventoryClosingBalance = (totalPurchase - costOfGoodsSold);

                accTrialBalDTO.setGroupLevel(accTrialBalanceDTO.getGroupLevel());
                accTrialBalDTO.setParticular(accTrialBalanceDTO.getParticular());
                accTrialBalDTO.setAccTypeId(accTrialBalanceDTO.getAccTypeId());

                if (accTrialBalanceDTO.getDrAmount() != null) {
                    if (accTrialBalanceDTO.getDrAmount() < 0) {
                        accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(AccountingUtil.crAmount(Math.abs(accTrialBalanceDTO.getDrAmount()) + totalInventoryClosingBalance)));
                    }
                }
                if (accTrialBalanceDTO.getCrAmount() != null) {
                    if (accTrialBalanceDTO.getCrAmount() > 0) {
                        accTrialBalanceDTO.setCrAmount(accTrialBalanceDTO.getCrAmount() + totalInventoryClosingBalance);
                    }
                }

                addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);

                if (totalInventoryClosingBalance > 0) {
                    accTrialBalDTO = new AccTrialBalanceDTO();
                    accTrialBalDTO.setGroupLevel(2);
                    accTrialBalDTO.setParticular(BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) ? "Material" : "Inventory");
                    accTrialBalDTO.setAccTypeId(BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) ? AccountTypeEnum.MATERIAL.getValue() : AccountTypeEnum.INVENTORY.getValue());
                    accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(totalInventoryClosingBalance));
                    addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);
                }

            } else {

                if (AccountTypeEnum.PARENT_SALE.getValue().equals(accTrialBalanceDTO.getGroupId()) && accTrialBalanceDTO.getGroupLevel().equals(1)) {
                    accTrialBalDTO.setGroupLevel(accTrialBalanceDTO.getGroupLevel());
                    accTrialBalDTO.setParticular(accTrialBalanceDTO.getParticular());
                    accTrialBalDTO.setAccTypeId(accTrialBalanceDTO.getAccTypeId());
                    accTrialBalanceDTO.setDrAmount(accTrialBalanceDTO.getDrAmount());
                    addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);
                } else {

                    accTrialBalDTO.setGroupLevel(accTrialBalanceDTO.getGroupLevel());
                    accTrialBalDTO.setParticular(accTrialBalanceDTO.getParticular());
                    accTrialBalDTO.setAccTypeId(accTrialBalanceDTO.getAccTypeId());

                    //Adjust profit and loss to a capital
                    if (AccountTypeEnum.CAPITAL.getValue().equals(accTrialBalanceDTO.getGroupId()) && accTrialBalanceDTO.getGroupLevel().equals(1)) {
                        if (accTrialBalanceDTO.getDrAmount() != null) {
                            if (accTrialBalanceDTO.getDrAmount() < 0) {
                                accTrialBalanceDTO.setCrAmount(Math.abs(accTrialBalanceDTO.getDrAmount()) + fetchPreviousYearCapitalClosingBalance(currentUser));
                                accTrialBalanceDTO.setDrAmount(null);
                            }
                        } else {
                            accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(accTrialBalanceDTO.getCrAmount() + fetchPreviousYearCapitalClosingBalance(currentUser)));
                        }
                    }
                    //adjust the capital sign
                    if (AccountTypeEnum.CAPITAL.getText().equals(accTrialBalanceDTO.getParticular().trim()) && accTrialBalanceDTO.getGroupLevel().equals(2)) {
                        if (accTrialBalanceDTO.getDrAmount() != null) {
                            if (accTrialBalanceDTO.getDrAmount() < 0) {
                                accTrialBalanceDTO.setCrAmount(Math.abs(accTrialBalanceDTO.getDrAmount()));
                                accTrialBalanceDTO.setDrAmount(null);
                            }
                        } else {
                            accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(accTrialBalanceDTO.getCrAmount()));
                        }
                    }
                    //Adjust to sub parent account
                    if (accTrialBalanceDTO.getAccTypeId() != null) {
                        if (accTrialBalanceDTO.getAccTypeId().equals(AccountTypeEnum.INCOME.getValue()) && accTrialBalanceDTO.getGroupLevel().equals(2)) {
                            accTrialBalanceDTO.setCrAmount(accTrialBalanceDTO.getCrAmount() + fetchPreviousYearCapitalClosingBalance(currentUser));
                        }
                    }
                    addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);

                    // cost of good sold
                    if (accTrialBalanceDTO.getAccTypeId() != null) {
                        if (accTrialBalanceDTO.getAccTypeId().equals(AccountTypeEnum.SALES.getValue()) && accTrialBalanceDTO.getGroupLevel().equals(2)) {
                            accTrialBalDTO = new AccTrialBalanceDTO();
                            Double totalCostOfGoodAmt = accProfitAndLossReportDao.getTotalGoodSoldAmount(currentUser.getCompanyId(), fromDate, toDate);
                            accTrialBalDTO.setGroupLevel(1);
                            accTrialBalDTO.setParticular("Cost of Goods Sold");
                            accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(totalCostOfGoodAmt));
                            addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);

                            accTrialBalDTO = new AccTrialBalanceDTO();
                            accTrialBalDTO.setGroupLevel(2);
                            accTrialBalDTO.setParticular("Cost of Goods Sold");
                            accTrialBalanceDTO.setDrAmount(AccountingUtil.drAmount(totalCostOfGoodAmt));
                            addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);
                        }
                    }
                }
            }
        }
        return accTrialBalanceDTOs;
    }

    private Double fetchPreviousYearCapitalClosingBalance(CurrentUser currentUser) {
        FinancialYearDTO preFinancialYearDTO = financialYearSetupService.getPreviousFinancialYearDetail(currentUser.getCompanyId());
        if (preFinancialYearDTO != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(preFinancialYearDTO.getFinancialYearTo());
            Date preToDate = calendarTo.getTime();

            List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOs = accProfitAndLossReportService.getProfitAndLossDetails(
                    currentUser.getCompanyId(), null, preToDate, currentUser.getBusinessType());

            return accProfitAndLossReportDTOs.get(accProfitAndLossReportDTOs.size() - 1).getAmount();
        }
        return 0.0;
    }

    private void addToList(List<AccTrialBalanceDTO> accTrialBalanceDTOs, AccTrialBalanceDTO accTrialBalanceDTO, AccTrialBalanceDTO accTrialBalDTO) {

        if (accTrialBalanceDTO.getAmount() != null) {
            if (accTrialBalanceDTO.getAmount() < 0) {
                accTrialBalDTO.setDrAmount(Math.abs(accTrialBalanceDTO.getAmount()));
                accTrialBalanceDTOs.add(accTrialBalDTO);
                return;
            }
            if (accTrialBalanceDTO.getAmount() > 0) {
                accTrialBalDTO.setCrAmount(accTrialBalanceDTO.getAmount());
                accTrialBalanceDTOs.add(accTrialBalDTO);
                return;
            }
        }

        if (accTrialBalanceDTO.getDrAmount() != null) {
            if (accTrialBalanceDTO.getDrAmount() < 0) {
                accTrialBalDTO.setDrAmount(Math.abs(accTrialBalanceDTO.getDrAmount()));
                accTrialBalanceDTOs.add(accTrialBalDTO);
                return;
            }
        }

        if (accTrialBalanceDTO.getCrAmount() != null) {
            if (accTrialBalanceDTO.getCrAmount() > 0) {
                accTrialBalDTO.setCrAmount(accTrialBalanceDTO.getCrAmount());
                accTrialBalanceDTOs.add(accTrialBalDTO);
            }
        }
    }


    public List<AccTrialBalanceDTO> formatDataListForAccountType(CurrentUser currentUser, Integer accTypeId, Date fromDate, Date toDate) {

        List<AccTrialBalanceDTO> accTrialBalanceDTOs = new ArrayList<>();

        AccTrialBalanceDTO accTrialBalDTO = new AccTrialBalanceDTO();

        List<AccTrialBalanceDTO> accTrialBalanceDTOListII = financialPositionDao.getSubTrialBalance(currentUser.getCompanyId(),
                currentUser.getFinancialYearId(), fromDate, toDate, accTypeId);

        if (accTrialBalanceDTOListII.size() > 0) {

            AccTrialBalanceDTO accTrialBalanceDTO = financialPositionDao.totalAmountByAccType(currentUser.getCompanyId(),
                    currentUser.getFinancialYearId(), fromDate, toDate, accTypeId);

            accTrialBalDTO.setParticular(accTrialBalanceDTO.getParticular());
            accTrialBalDTO.setGroupLevel(1);
            accTrialBalDTO.setAccTypeId(accTypeId);
            addToList(accTrialBalanceDTOs, accTrialBalanceDTO, accTrialBalDTO);

            for (AccTrialBalanceDTO accTrialBalanceDTOII : accTrialBalanceDTOListII) {
                accTrialBalDTO = new AccTrialBalanceDTO();
                accTrialBalDTO.setParticular(accTrialBalanceDTOII.getParticular());
                accTrialBalDTO.setGroupLevel(accTrialBalanceDTOII.getGroupLevel());
                accTrialBalDTO.setAccTypeId(accTrialBalanceDTOII.getAccTypeId());

                addToList(accTrialBalanceDTOs, accTrialBalanceDTOII, accTrialBalDTO);
            }
        }
        return accTrialBalanceDTOs;
    }

    public Double getPNLAmt(CurrentUser currentUser, Date fromDate, Date toDate) {
        //To get Net profit from PNL
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOs = accProfitAndLossReportService.getProfitAndLossDetails(currentUser.getCompanyId(), null, toDate, currentUser.getBusinessType());
        return accProfitAndLossReportDTOs.get(accProfitAndLossReportDTOs.size() - 1).getReturnPNLAmount();
    }
}
