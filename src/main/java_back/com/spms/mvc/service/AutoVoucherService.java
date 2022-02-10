package com.spms.mvc.service;

import com.spms.mvc.Enumeration.*;
import com.spms.mvc.dao.AutoVoucherDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.Loan;
import com.spms.mvc.global.service.BaseService;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

        if (autoVoucherDTO.getDeductedAmount() != null) {
            if (autoVoucherDTO.getDeductedAmount() > 0) {
                if (ledgerService.isLedgerNameExists(autoVoucherDTO.getDeductedFrom(), currentUser.getCompanyId()).getStatus() == 1) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("No advance record.");
                    return responseMessage;
                }
            }
        }
        if (autoVoucherDTO.getIsCash() != null && autoVoucherDTO.getTypeId() != 3) {
            if (autoVoucherDTO.getIsCash().equals(PaymentModeTypeEnum.CASH.getValue())) {

                double totalAmount = autoVoucherDTO.getDeductedAmount() == 0.0
                        ? autoVoucherDTO.getAmount() == null ? autoVoucherDTO.getAmountReceived()
                        : autoVoucherDTO.getAmount() : autoVoucherDTO.getDeductedAmount();

                if (addItemService.checkCashBalance(PaymentModeTypeEnum.CASH.getValue(),
                        totalAmount, currentUser).getStatus() == 0) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Insufficient balance.");
                    return responseMessage;
                }
            }
        } else {
            if (autoVoucherDTO.getTypeId() == 2 && autoVoucherDTO.getDepositedAmount() > 0) {
                if (addItemService.checkCashBalance(PaymentModeTypeEnum.CASH.getValue(), autoVoucherDTO.getDepositedAmount(), currentUser).getStatus() == 0) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Insufficient balance.");
                    return responseMessage;
                }
            }
        }

        String cashLedgerName = ledgerService.getCashLedgerByCashAccountHead(currentUser);

        if (cashLedgerName == null) {
            cashLedgerName = "Cash in Hand";
        }

        if (autoVoucherDTO.getTypeId() == 1) {

            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                    currentUser.getCompanyId(),currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
            voucherDTO.setNarration("Payment Entry");
            voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
            voucherDTO.setVoucherNo(voucherNo);

            if (autoVoucherDTO.getPaidForTypeId() == 1) {
                //region dr voucher preparation
                VoucherDetailDTO voucherCostDRDTO = new VoucherDetailDTO();
                voucherCostDRDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getAmount())));
                if (autoVoucherDTO.getCostId().equals(CostEnum.GENERAL.getValue())) {
                    voucherCostDRDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(autoVoucherDTO.getDescription(), currentUser,
                            AccountTypeEnum.INDIRECT_COST.getValue()));
                } else {
                    voucherCostDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getDescription(),
                            currentUser, AccountTypeEnum.DIRECT_COST.getValue()));
                }
                voucherDetailDTOList.add(voucherCostDRDTO);

                //region cr voucher preparation
                if (!autoVoucherDTO.getTdsType().equals(TDSTypeEnum.NOT_APPLICABLE.getValue())) {

                    VoucherDetailDTO voucherCRTdsDTo = new VoucherDetailDTO();
                    voucherCRTdsDTo.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getTdsAmount())));
                    voucherCRTdsDTo.setLedgerId(getLedgerId(LedgerType.TDS.getText(), currentUser, AccountTypeEnum.PAYABLE.getValue()));
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
                if (!autoVoucherDTO.getBankLedgerId().equals("")) {
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
                if (autoVoucherDTO.getBankLedgerId() != null) {
                    voucherRepaymentDRDTO.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherRepaymentDRDTO.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherRepaymentDRDTO);
            }
        }

        if (autoVoucherDTO.getTypeId() == 2) {

            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.CONTRA.getValue(),
                    currentUser.getCompanyId(),currentUser.getFinancialYearId());
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
                    currentUser.getCompanyId(),currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.RECEIPT.getValue());
            voucherDTO.setVoucherEntryDate(autoVoucherDTO.getAutoVoucherDate());
            voucherDTO.setVoucherNo(voucherNo);
            voucherDTO.setNarration("Receipt Entry");

            if (autoVoucherDTO.getReceivedFor() == 1) {
                //region cr voucher preparation
                VoucherDetailDTO voucherDRDTO = new VoucherDetailDTO();
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getAmountReceived())));
                voucherDRDTO.setLedgerId(getLedgerId(autoVoucherDTO.getReceiveFrom(), currentUser, AccountTypeEnum.PARTY_ADVANCE_RECEIVED.getValue()));
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
                voucherDRDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(autoVoucherDTO.getReceiptAmount())));
                voucherDRDTO.setLedgerId(autoVoucherDTO.getLedgerId());
                voucherDetailDTOList.add(voucherDRDTO);

                //region dr voucher preparation
                VoucherDetailDTO voucherCRDTo = new VoucherDetailDTO();
                voucherCRDTo.setDrcrAmount(AccountingUtil.drAmount(Math.abs(autoVoucherDTO.getReceiptAmount())));
                if (autoVoucherDTO.getBankLedgerId() != null && !autoVoucherDTO.getBankLedgerId().equals("")) {
                    voucherCRDTo.setLedgerId(autoVoucherDTO.getBankLedgerId());
                } else {
                    voucherCRDTo.setLedgerId(getLedgerId(cashLedgerName, currentUser, AccountTypeEnum.CASH.getValue()));
                }
                voucherDetailDTOList.add(voucherCRDTo);
            }

        }

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
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

    public List<DropdownDTO> getAllLedgerUnderAdvancePaid(CurrentUser currentUser) {
        return autoVoucherDao.getAllLedgerUnderAdvancePaid(currentUser);
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
}
