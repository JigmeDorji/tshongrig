package com.spms.mvc.service;

import com.spms.mvc.Enumeration.*;
import com.spms.mvc.dao.AutoVoucherDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.LedgerWiseCostType;
import com.spms.mvc.entity.Loan;
import com.spms.mvc.global.service.BaseService;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: AutoVoucherService
 * Date:  2021-May-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-08
 * Change Description:
 * Search Tag:
 */

@Service("autoVoucherService")
public class AutoVoucherService extends BaseService {

    @Autowired
    private AutoVoucherDao autoVoucherDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AddItemService addItemService;

    @Autowired
    private AccSaleInvoiceGenerationService accSaleInvoiceGenerationService;

    public List<DropdownDTO> getPaidForTypeList() {
        List<DropdownDTO> dropdownDTOList = new ArrayList<>();
//        for (List list : PaidForTypeEnum.values()) {
//
//        }
        return null;
    }

    public List<AutoVoucherDTO> fetchPaidToList(Integer companyId) {
        return null;
    }

    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(AutoVoucherDTO autoVoucherDTO, CurrentUser currentUser) throws ParseException {

        Integer voucherNo = null;
        VoucherDTO voucherDTO = new VoucherDTO();
        ResponseMessage responseMessage = new ResponseMessage();
        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();

        String cashLedgerName = ledgerService.getCashLedgerByCashAccountHead(currentUser);

        if (cashLedgerName == null && autoVoucherDTO.getIsCash() != null) {
            cashLedgerName = "Cash in hand";
            getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue());
        }

        if (autoVoucherDTO.getDeductedAmount() != null) {
            if (autoVoucherDTO.getDeductedAmount() > 0) {
                if (ledgerService.isLedgerNameExists(autoVoucherDTO.getDeductedFrom(), currentUser.getCompanyId()).getStatus() == 1) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("No advance record.");
                    return responseMessage;
                }
            }
        }
        if (autoVoucherDTO.getIsCash() != null && autoVoucherDTO.getTypeId() != 3 && autoVoucherDTO.getTypeId() != 2) {
            if (autoVoucherDTO.getIsCash().equals(PaymentModeTypeEnum.CASH.getValue())) {

                double deductedAmount = autoVoucherDTO.getDeductedAmount() == null ? 0.0 : autoVoucherDTO.getDeductedAmount();

                double totalAmount = 0.0;
                if (autoVoucherDTO.getTypeId() == 1) {
                    for (MultiVoucherDTO multiVoucherDTO : autoVoucherDTO.getMultiVoucherDTO()) {
                        if (multiVoucherDTO.getCostAmount() != null) {
                            totalAmount = totalAmount + multiVoucherDTO.getCostAmount();
                        }
                    }
                } else {
                    totalAmount = deductedAmount == 0.0
                            ? autoVoucherDTO.getAmount() == null ? autoVoucherDTO.getAmountReceived()
                            : autoVoucherDTO.getAmount() : autoVoucherDTO.getDeductedAmount();
                }
                if (addItemService.checkCashBalance(PaymentModeTypeEnum.CASH.getValue(),
                        totalAmount, currentUser).getStatus() == 0) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Insufficient balance.");
                    return responseMessage;
                }
            }
        }

        if (autoVoucherDTO.getTypeId() == 1) {

            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
            voucherDTO.setNarration("Payment Entry");
            voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
            voucherDTO.setVoucherNo(voucherNo);

            if (autoVoucherDTO.getPaidForTypeId() == 1) {
                //region dr voucher preparation
                String ledgerId;
                for (MultiVoucherDTO multiVoucherDTO : autoVoucherDTO.getMultiVoucherDTO()) {

                    VoucherDetailDTO voucherCostDRDTO = new VoucherDetailDTO();
                    voucherCostDRDTO.setDrcrAmount(AccountingUtil.drAmount(
                            Math.abs(multiVoucherDTO.getCostAmount())));

                    if (multiVoucherDTO.getCostId().equals(CostEnum.GENERAL.getValue())) {
                        ledgerId = ledgerService.getLedgerIdByLedgerName(multiVoucherDTO.getCostDescription(),
                                currentUser, AccountTypeEnum.INDIRECT_COST.getValue());
                    } else {
                        ledgerId = getLedgerId(multiVoucherDTO.getCostDescription(), currentUser,
                                AccountTypeEnum.DIRECT_COST.getValue());
                    }
                    voucherCostDRDTO.setLedgerId(ledgerId);
                    voucherDetailDTOList.add(voucherCostDRDTO);

                    mapToLedgerWiseTable(currentUser, ledgerId, multiVoucherDTO.getCostId());

                }
                //region cr voucher preparation
                if (!autoVoucherDTO.getTdsType().equals(TDSTypeEnum.NOT_APPLICABLE.getValue())) {

                    VoucherDetailDTO voucherCRTdsDTo = new VoucherDetailDTO();
                    voucherCRTdsDTo.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getTdsAmount())));
                    voucherCRTdsDTo.setLedgerId(getLedgerId(LedgerType.TDS_PAYABLE.getText(), currentUser, AccountTypeEnum.PAYABLE.getValue()));
                    voucherDetailDTOList.add(voucherCRTdsDTo);
                }

                if (autoVoucherDTO.getDeductedAmount() > 0) {
                    //region cr Deduction voucher preparation
                    VoucherDetailDTO voucherAdvanceDeductionCRDTO = new VoucherDetailDTO();
                    voucherAdvanceDeductionCRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getDeductedAmount())));
                    voucherAdvanceDeductionCRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDeductedFrom(), currentUser, AccountTypeEnum.PARTY_ADVANCE_PAID.getValue()));
                    voucherDetailDTOList.add(voucherAdvanceDeductionCRDTO);
                }

                //region cr bank voucher preparation
                VoucherDetailDTO voucherBankOrCashDTO = new VoucherDetailDTO();
                voucherBankOrCashDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountPaid())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherBankOrCashDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherBankOrCashDTO.setLedgerId(getLedgerId(cashLedgerName,
                            currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherBankOrCashDTO);


            }

            if (autoVoucherDTO.getPaidForTypeId() == 2) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmount())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(), currentUser, AccountTypeEnum.PARTY_ADVANCE_PAID.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmount())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherCRDTo.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherCRDTo);
            }

            if (autoVoucherDTO.getPaidForTypeId() == 3) {
                //region cr voucher preparation
                VoucherDetailDTO voucherRepaymentCRDTO = new VoucherDetailDTO();
                voucherRepaymentCRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmount())));
                voucherRepaymentCRDTO.setLedgerId(autoVoucherDTO.getLedgerId());
                voucherDetailDTOList.add(voucherRepaymentCRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherRepaymentDRDTO = new VoucherDetailDTO();
                voucherRepaymentDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmount())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherRepaymentDRDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherRepaymentDRDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherRepaymentDRDTO);
            }

            //Remittance
            if (autoVoucherDTO.getPaidForTypeId() == 4) {
                //region cr voucher preparation
                VoucherDetailDTO voucherRemittanceCRDTO = new VoucherDetailDTO();
                voucherRemittanceCRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmount())));
                voucherRemittanceCRDTO.setLedgerId(autoVoucherDTO.getLedgerId());
                voucherDetailDTOList.add(voucherRemittanceCRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherRemittanceDRDTO = new VoucherDetailDTO();
                voucherRemittanceDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmount())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherRemittanceDRDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherRemittanceDRDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherRemittanceDRDTO);
            }

            //Payable
            if (autoVoucherDTO.getPaidForTypeId() == 5) {
                //region cr voucher preparation
                VoucherDetailDTO voucherRemittanceCRDTO = new VoucherDetailDTO();
                voucherRemittanceCRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmount())));
                voucherRemittanceCRDTO.setLedgerId(autoVoucherDTO.getLedgerId());
                voucherDetailDTOList.add(voucherRemittanceCRDTO);

                //region cr voucher preparation
                if (!autoVoucherDTO.getTdsType().equals(TDSTypeEnum.NOT_APPLICABLE.getValue())) {
                    VoucherDetailDTO voucherCRTdsDTo = new VoucherDetailDTO();
                    voucherCRTdsDTo.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getTdsAmount())));
                    voucherCRTdsDTo.setLedgerId(getLedgerId(LedgerType.TDS_PAYABLE.getText(), currentUser, AccountTypeEnum.PAYABLE.getValue()));
                    voucherDetailDTOList.add(voucherCRTdsDTo);
                }

                if (autoVoucherDTO.getDeductedAmount() > 0) {
                    //region cr Deduction voucher preparation
                    VoucherDetailDTO voucherAdvanceDeductionCRDTO = new VoucherDetailDTO();
                    voucherAdvanceDeductionCRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getDeductedAmount())));
                    voucherAdvanceDeductionCRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDeductedFrom(), currentUser,
                            AccountTypeEnum.PARTY_ADVANCE_PAID.getValue()));
                    voucherDetailDTOList.add(voucherAdvanceDeductionCRDTO);
                }
                //region cr bank voucher preparation
                VoucherDetailDTO voucherBankOrCashDTO = new VoucherDetailDTO();
                voucherBankOrCashDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountPaid())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherBankOrCashDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherBankOrCashDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherBankOrCashDTO);
            }
        }

        if (autoVoucherDTO.getTypeId() == 2) {

            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.CONTRA.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.CONTRA.getValue());
            voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
            voucherDTO.setVoucherNo(voucherNo);
            voucherDTO.setNarration("Withdrawal or Deposit to Bank Entry");

            if (autoVoucherDTO.getCashDepositWithdrawalType() == 1) {
                //region cr voucher preparation
                VoucherDetailDTO voucherCashDepWithdrawalCRDTO = new VoucherDetailDTO();
                voucherCashDepWithdrawalCRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getDepositedAmount())));
                voucherCashDepWithdrawalCRDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                voucherDetailDTOList.add(voucherCashDepWithdrawalCRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCashDepWithdrawalDRDTO = new VoucherDetailDTO();
                voucherCashDepWithdrawalDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getDepositedAmount())));
                voucherCashDepWithdrawalDRDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                voucherDetailDTOList.add(voucherCashDepWithdrawalDRDTO);
            } else {
                //region cr voucher preparation
                VoucherDetailDTO voucherCashDepWithdrawalCRDTO = new VoucherDetailDTO();
                voucherCashDepWithdrawalCRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getDepositedAmount())));
                voucherCashDepWithdrawalCRDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                voucherDetailDTOList.add(voucherCashDepWithdrawalCRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCashDepWithdrawalDRDTO = new VoucherDetailDTO();
                voucherCashDepWithdrawalDRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getDepositedAmount())));
                voucherCashDepWithdrawalDRDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                voucherDetailDTOList.add(voucherCashDepWithdrawalDRDTO);
            }
        }

        if (autoVoucherDTO.getTypeId() == 3) {

            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.RECEIPT.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.RECEIPT.getValue());
            voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
            voucherDTO.setVoucherNo(voucherNo);
            voucherDTO.setNarration("Receipt Entry");

            if (autoVoucherDTO.getReceivedFor() == 1) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(), currentUser,
                        AccountTypeEnum.PARTY_ADVANCE_RECEIVED.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherCRDTo.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherCRDTo);
            }

            if (autoVoucherDTO.getReceivedFor() == 2) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(), currentUser,
                        AccountTypeEnum.SECURED_LOAN.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherCRDTo.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherCRDTo);
            }

            if (autoVoucherDTO.getReceivedFor() == 3) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getCapitalLedgerName(), currentUser,
                        AccountTypeEnum.EQUITY_OR_CAPITAL.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherCRDTo.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherCRDTo);
            }
            if (autoVoucherDTO.getReceivedFor() == 4) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(), currentUser,
                        AccountTypeEnum.MATERIAL_ADV.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                voucherDetailDTOList.add(voucherCRDTo);
            }

            if (autoVoucherDTO.getReceivedFor() == 5) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(), currentUser,
                        AccountTypeEnum.MOBILIZATION_ADV.getValue()));
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                voucherDetailDTOList.add(voucherCRDTo);
            }

        }

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        responseMessage.setDTO(voucherDTO);
        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public void mapToLedgerWiseTable(CurrentUser currentUser, String ledgerId, Integer costType) {
        BigInteger id;
        LedgerWiseCostType ledgerWiseCostType = new LedgerWiseCostType();

        if (autoVoucherDao.checkLedgerExists(ledgerId)) {
            id = autoVoucherDao.getMaxId(currentUser.getCompanyId());
            id = id == null ? BigInteger.ONE : id.add(BigInteger.ONE);
        } else {
            id = autoVoucherDao.getIdByLedgerId(ledgerId);
        }
        ledgerWiseCostType.setId(id);
        ledgerWiseCostType.setLedgerId(ledgerId);
        ledgerWiseCostType.setCompanyId(currentUser.getCompanyId());
        ledgerWiseCostType.setCostTypeId(costType);
        ledgerWiseCostType.setCreatedBy(currentUser.getLoginId());
        ledgerWiseCostType.setCreatedDate(currentUser.getCreatedDate());
        autoVoucherDao.saveOrUpdate(ledgerWiseCostType);
    }

    public String getLedgerId(String ledgerName, CurrentUser currentUser, Integer accountTypeId) {
        return ledgerService.getLedgerIdByLedgerName(ledgerName, currentUser, accountTypeId);
    }

    public List<DropdownDTO> getLedgerUnderPayableForRepayment(CurrentUser currentUser) {
        return autoVoucherDao.getLedgerUnderPayableForRepayment(currentUser);
    }

    public List<DropdownDTO> getAllLedgerUnderExpenseForCost(CurrentUser currentUser) {
        return autoVoucherDao.getAllLedgerUnderExpenseForCost(currentUser);
    }

    public List<DropdownDTO> getAllLedgerUnderAccountType(CurrentUser currentUser, Integer accountTypeId) {
        return autoVoucherDao.getAllLedgerUnderAccountType(currentUser, accountTypeId);
    }

    public List<AutoVoucherDTO> getAllLedgerUnderAdvanceReceived(CurrentUser currentUser) {
        return autoVoucherDao.getAllLedgerUnderAdvanceReceived(currentUser);
    }

    public ResponseMessage saveLoanDetail(LoanDTO loanDTO, CurrentUser currentUser) {
        Loan loan = new Loan();
        Integer loanId;
        ResponseMessage responseMessage = new ResponseMessage();

        if (autoVoucherDao.checkLoanIdExists(loanDTO.getLoanId())) {
            loanId = autoVoucherDao.getMaxLoanId();
            loanId = loanId == null ? 1 : loanId + 1;

            ledgerService.getLedgerIdByLedgerName(loanDTO.getLoanLedgerName(), currentUser,
                    AccountTypeEnum.SECURED_LOAN.getValue());
        } else {
            loanId = loanDTO.getLoanId();
        }
        loan.setLoanId(loanId);
        loan.setLoanLedgerName(loanDTO.getLoanLedgerName());
        loan.setLoanAccNo(loanDTO.getLoanAccNo());
        loan.setBank(loanDTO.getBank());
        loan.setBranch(loanDTO.getBranch());
        loan.setMonthlyEmi(loanDTO.getMonthlyEmi());
        loan.setCompanyId(currentUser.getCompanyId());
        loan.setCreatedBy(currentUser.getLoginId());
        loan.setCreatedDate(currentUser.getCreatedDate());
        autoVoucherDao.saveLoanDetail(loan);

        responseMessage.setText("Saved successfully.");
        responseMessage.setStatus(1);
        return responseMessage;
    }

    public List<LoanDTO> getLoanDetails(CurrentUser currentUser) {
        return autoVoucherDao.getLoanDetails(currentUser.getCompanyId());
    }

    public List<AutoVoucherDTO> getLoanLedgerList(CurrentUser currentUser) {
        return autoVoucherDao.getLoanLedgerList(currentUser);
    }

    public ResponseMessage bankTransferDetail(AutoVoucherDTO autoVoucherDTO, CurrentUser currentUser) throws ParseException {

        Integer voucherNo = null;
        VoucherDTO voucherDTO = new VoucherDTO();
        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
        ResponseMessage responseMessage = new ResponseMessage();

        voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.CONTRA.getValue(),
                currentUser.getCompanyId(), currentUser.getFinancialYearId());
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.CONTRA.getValue());
        voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
        voucherDTO.setVoucherNo(voucherNo);
        voucherDTO.setNarration("Bank to bank Transfer Entry");

        VoucherDetailDTO voucherCRDTO = new VoucherDetailDTO();
        voucherCRDTO.setDrcrAmount(autoVoucherDTO.getAmount());
        voucherCRDTO.setLedgerId(autoVoucherDTO.getBankLedgerFromId());
        voucherDetailDTOList.add(voucherCRDTO);

        VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
        voucherDRDTO.setDrcrAmount(AccountingUtil.drAmount(autoVoucherDTO.getAmount()));
        voucherDRDTO.setLedgerId(autoVoucherDTO.getBankLedgerToId());
        voucherDetailDTOList.add(voucherDRDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public ResponseMessage adjustmentDetail(AutoVoucherDTO autoVoucherDTO, CurrentUser currentUser) throws ParseException {

        Integer voucherNo;
        String ledgerId;
        VoucherDTO voucherDTO = new VoucherDTO();
        ResponseMessage responseMessage = new ResponseMessage();
        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();

        voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                currentUser.getCompanyId(), currentUser.getFinancialYearId());
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
        voucherDTO.setVoucherNo(voucherNo);
        voucherDTO.setNarration("Adjustment Entry");

        for (MultiVoucherDTO multiVoucherDTO : autoVoucherDTO.getMultiVoucherDTO()) {

            VoucherDetailDTO voucherCostDRDTO = new VoucherDetailDTO();
            voucherCostDRDTO.setDrcrAmount(AccountingUtil.drAmount(multiVoucherDTO.getCostAmount()));

            if (multiVoucherDTO.getCostId().equals(CostEnum.GENERAL.getValue())) {
                ledgerId = ledgerService.getLedgerIdByLedgerName(multiVoucherDTO.getAdjustedAgainst(), currentUser,
                        AccountTypeEnum.INDIRECT_COST.getValue());
            } else {
                ledgerId = getLedgerId(multiVoucherDTO.getAdjustedAgainst(),
                        currentUser, AccountTypeEnum.DIRECT_COST.getValue());
            }
            voucherCostDRDTO.setLedgerId(ledgerId);
            voucherDetailDTOList.add(voucherCostDRDTO);
            mapToLedgerWiseTable(currentUser, ledgerId, multiVoucherDTO.getCostId());
        }

        if (autoVoucherDTO.getTdsAmount() > 0) {
            VoucherDetailDTO voucherCRTdsDTo = new VoucherDetailDTO();
            voucherCRTdsDTo.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getTdsAmount())));
            voucherCRTdsDTo.setLedgerId(getLedgerId(LedgerType.TDS_PAYABLE.getText(), currentUser,
                    AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOList.add(voucherCRTdsDTo);
        }

      /*  if (autoVoucherDTO.getCostId() != null) {
            if (autoVoucherDTO.getCostId().equals(CostEnum.GENERAL.getValue())) {
                ledgerId = ledgerService.getLedgerIdByLedgerName(autoVoucherDTO.getAdjustedAgainst(), currentUser,
                        AccountTypeEnum.INDIRECT_COST.getValue());
            } else {
                ledgerId = getLedgerId(autoVoucherDTO.getAdjustedAgainst(),
                        currentUser, AccountTypeEnum.DIRECT_COST.getValue());
            }
        } else {
            ledgerId = autoVoucherDTO.getLedgerId();
        }

        VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
        voucherDRDTO.setDrcrAmount(AccountingUtil.drAmount(autoVoucherDTO.getAmount()));
        voucherDRDTO.setLedgerId(ledgerId);
        voucherDetailDTOList.add(voucherDRDTO);*/

        VoucherDetailDTO voucherCRDTO = new VoucherDetailDTO();
        voucherCRDTO.setDrcrAmount(autoVoucherDTO.getAmountPaid());
        voucherCRDTO.setLedgerId(autoVoucherDTO.getAdjustedFrom());
        voucherDetailDTOList.add(voucherCRDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherNo(voucherNo);

        responseMessage.setStatus(1);
        responseMessage.setDTO(voucherDTO);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public ResponseMessage payableDetail(AutoVoucherDTO autoVoucherDTO, CurrentUser currentUser) throws ParseException {
        String ledgerId;
        VoucherDTO voucherDTO = new VoucherDTO();
        ResponseMessage responseMessage = new ResponseMessage();
        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();

        Integer voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                currentUser.getCompanyId(), currentUser.getFinancialYearId());
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
        voucherDTO.setVoucherNo(voucherNo);
        voucherDTO.setNarration("Payable Entry");

        if (autoVoucherDTO.getCostId() != null) {
            if (autoVoucherDTO.getCostId().equals(CostEnum.GENERAL.getValue())) {
                ledgerId = ledgerService.getLedgerIdByLedgerName(autoVoucherDTO.getExpenditure(), currentUser,
                        AccountTypeEnum.INDIRECT_COST.getValue());
            } else {
                ledgerId = getLedgerId(autoVoucherDTO.getExpenditure(),
                        currentUser, AccountTypeEnum.DIRECT_COST.getValue());
            }
        } else {
            ledgerId = autoVoucherDTO.getLedgerId();
        }

        VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
        voucherDRDTO.setDrcrAmount(AccountingUtil.drAmount(autoVoucherDTO.getAmount()));
        voucherDRDTO.setLedgerId(ledgerId);
        voucherDetailDTOList.add(voucherDRDTO);

        VoucherDetailDTO voucherCRDTO = new VoucherDetailDTO();
        voucherCRDTO.setDrcrAmount(autoVoucherDTO.getAmount());
        if (autoVoucherDTO.getPartyLedgerId().equals("")) {
            voucherCRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getPartyName(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
        } else {
            voucherCRDTO.setLedgerId(autoVoucherDTO.getPartyLedgerId());
        }
        voucherDetailDTOList.add(voucherCRDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public List<AutoVoucherDTO> getPayableList(CurrentUser currentUser) {
        return autoVoucherDao.getPayableList(currentUser);
    }

    public ResponseMessage fetchTDSPayableList(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (autoVoucherDao.fetchTDSPayableList(currentUser, LedgerType.TDS_PAYABLE.getText())) {
            responseMessage.setText("No TDS Payable ledger.");
            responseMessage.setStatus(0);
            return responseMessage;
        }
        String ledgerId = getLedgerId(LedgerType.TDS_PAYABLE.getText(),
                currentUser, AccountTypeEnum.PAYABLE.getValue());

        Double tdsPayableAmount = autoVoucherDao.getAmountByLedgerId(ledgerId);

        tdsPayableAmount = tdsPayableAmount == null ? 0.0 : tdsPayableAmount;

        if (tdsPayableAmount <= 0) {
            responseMessage.setText("TDS payable balance zero.");
            responseMessage.setStatus(0);
            return responseMessage;
        }
        responseMessage.setStatus(1);
        responseMessage.setText(ledgerId);
        return responseMessage;
    }

    public List<DropdownDTO> getAllLPayableLedgerExcludingTds(CurrentUser currentUser) {
        return autoVoucherDao.getAllLPayableLedgerExcludingTds(currentUser, getLedgerId(LedgerType.TDS_PAYABLE.getText(),
                currentUser, AccountTypeEnum.PAYABLE.getValue()));
    }

    public Double getAmountByLedgerId(CurrentUser currentUser, String ledgerId) {
        return autoVoucherDao.getAmountByLedgerId(ledgerId);
    }


    public Integer getCostTypeByLedgerId(CurrentUser currentUser, String ledgerId) {
        return autoVoucherDao.getCostTypeByLedgerId(currentUser, ledgerId);
    }

    public List<VoucherDetailDTO> getVoucherDetail(Integer voucherNo, CurrentUser currentUser, Integer type) {

        List<VoucherDetailDTO> voucherDetailDTO = new ArrayList<>();
        List<VoucherDetailDTO> voucherDetailDTOList = autoVoucherDao.getVoucherDetail(voucherNo,
                currentUser, type);
        for (VoucherDetailDTO vDTO : voucherDetailDTOList) {
            VoucherDetailDTO voucherDetailDTO1 = new VoucherDetailDTO();
            if (vDTO.getDrcrAmount() < 0) {
                double amount = Math.abs(vDTO.getDrcrAmount());
                voucherDetailDTO1.setDrAmount(amount);
            } else {
                voucherDetailDTO1.setCrAmount(vDTO.getDrcrAmount());
            }
            voucherDetailDTO1.setDescription(vDTO.getDescription());
            voucherDetailDTO1.setDrcrAmount(vDTO.getDrcrAmount());
            voucherDetailDTO1.setAccTypeId(vDTO.getAccTypeId());
            voucherDetailDTO.add(voucherDetailDTO1);
        }
        return voucherDetailDTO;
    }

    public Date getVoucherEntryDate(Integer voucherNo, CurrentUser currentUser, Integer type) {
        return autoVoucherDao.getVoucherEntryDate(voucherNo, currentUser, type);
    }
}
