package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dao.LedgerDao;
import com.spms.mvc.dto.AccTypeDTO;
import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.entity.Bank;
import com.spms.mvc.entity.Ledger;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jigmePc on 5/5/2019.
 */
@Service("ledgerService")
public class LedgerService {
    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private AutoVoucherService autoVoucherService;

    public ResponseMessage save(LedgerDTO ledgerDTO, CurrentUser currentUser) {

        Ledger ledger = new Ledger();
        ResponseMessage responseMessage = new ResponseMessage();

        if (ledgerDTO.getAccTypeId().equals(AccountTypeEnum.CASH.getValue())) {
            String cashLedgerName = getCashLedgerByCashAccountHead(currentUser);
            if (cashLedgerName != null) {
                if (!cashLedgerName.equals(ledgerDTO.getLedgerName())) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Cash ledger already exists.");
                    return responseMessage;
                }
            }
        }


        if (ledgerDTO.getLedgerId().equals("")) {
            String maxLedgerId = ledgerDao.getMaxLedgerId();
            Integer id = Integer.parseInt(maxLedgerId) + 1;
            ledger.setLedgerId(id.toString());
        } else {
            ledger.setLedgerId(ledgerDTO.getLedgerId());
        }

        Integer bankId = null;
        if (!ledgerDTO.getAccNo().equals("")) {
            bankId = saveBankDetail(ledgerDTO);
        }

        ledger.setBankId(bankId);
        ledger.setLedgerName(ledgerDTO.getLedgerName());
        ledger.setAccTypeId(ledgerDTO.getAccTypeId());



        if(ledger.getAccTypeId()>=12 && ledger.getAccTypeId()<=17){
            ledger.setOpeningBal(0.0);
        }else {
            ledger.setOpeningBal(ledgerDTO.getOpeningBal());
        }

        ledger.setCompanyId(currentUser.getCompanyId());
        ledger.setCreatedBy(currentUser.getLoginId());
        ledger.setSetDate(currentUser.getCreatedDate());
        ledgerDao.save(ledger);

        //Save to ledger wise mapping table
        if (AccountTypeEnum.INDIRECT_COST.getValue().equals(ledgerDTO.getAccTypeId())) {
            autoVoucherService.mapToLedgerWiseTable(currentUser, ledger.getLedgerId(), 1);
        }
        if (AccountTypeEnum.DIRECT_COST.getValue().equals(ledgerDTO.getAccTypeId())) {
            autoVoucherService.mapToLedgerWiseTable(currentUser, ledger.getLedgerId(), 2);
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Successfully saved.");
        return responseMessage;

    }

    private Integer saveBankDetail(LedgerDTO ledgerDTO) {
        Bank bank = new Bank();
        if (ledgerDTO.getBankId() == null) {
            bank.setBankId(ledgerDao.getMaxBankId() + 1);
        } else {
            bank.setBankId(ledgerDTO.getBankId());
        }
        bank.setReconciliationDate(ledgerDTO.getReconciliationDate());
        bank.setBankAccHolderDetail(ledgerDTO.getBankAccHolderDetail());
        bank.setAccHolderName(ledgerDTO.getAccHolderName());
        bank.setAccNo(ledgerDTO.getAccNo());
        bank.setBankName(ledgerDTO.getBankName());
        bank.setBranch(ledgerDTO.getBranch());
        return ledgerDao.saveBankDetail(bank);
    }

    public List<DropdownDTO> getAccTypeList() {
        return ledgerDao.getAccTypeList();

    }

    public Boolean isBankAccType(Integer accTypeId) {
        AccTypeDTO accTypeDTO = ledgerDao.isBankAccType(accTypeId);
        return accTypeDTO.getIsBankAccLedger() ? true : false;
    }

    public List<LedgerDTO> getLedgerList(Integer companyId) {
        return ledgerDao.getLedgerList(companyId);
    }

    public LedgerDTO getLedgerDetails(Integer ledgerId) {
        return ledgerDao.getLedgerDetails(ledgerId);
    }

    public ResponseMessage updateLedgerDetails(LedgerDTO ledgerDTO, CurrentUser currentUser) {

        Ledger ledger = new Ledger();
        ResponseMessage responseMessage = new ResponseMessage();

        ledger.setLedgerId(ledgerDTO.getLedgerId());
        ledger.setLedgerName(ledgerDTO.getLedgerName());
        ledger.setAccTypeId(ledgerDTO.getAccTypeId());

        Integer bankId = null;
        if (ledgerDTO.getAccNo() != null && !ledgerDTO.getAccNo().equals("")) {
            bankId = saveBankDetail(ledgerDTO);
        }
        ledger.setBankId(bankId);

        if(ledger.getAccTypeId()>=12 && ledger.getAccTypeId()<=17){
            ledger.setOpeningBal(0.0);
        }else {
            ledger.setOpeningBal(ledgerDTO.getOpeningBal());
        }


        ledger.setCompanyId(currentUser.getCompanyId());
        ledger.setCreatedBy(currentUser.getLoginId());
        ledger.setSetDate(currentUser.getCreatedDate());
        ledgerDao.updateLedgerDetails(ledger);
        responseMessage.setStatus(1);
        responseMessage.setText("Successfully Updated.");
        return responseMessage;
    }

    /**
     * auto create ledger base on the user input
     *
     * @param currentUser   currentUser
     * @param ledgerName    ledgerName
     * @param accountTypeId accountTypeId
     * @return String
     */
    public String dynamicLedgerCreationService(CurrentUser currentUser, String ledgerName,
                                               Integer accountTypeId) {
        Ledger ledger = new Ledger();
        Integer ledgerMaxId = Integer.valueOf(ledgerDao.getMaxLedgerId());
        ledgerMaxId = ledgerMaxId + 1;
        ledger.setLedgerId(ledgerMaxId.toString());
        ledger.setLedgerName(ledgerName);
        ledger.setAccTypeId(accountTypeId);
        ledger.setOpeningBal(0.0);
        ledger.setCompanyId(currentUser.getCompanyId());
        ledger.setCreatedBy(currentUser.getLoginId());
        ledger.setSetDate(currentUser.getCreatedDate());
        return ledgerDao.saveLedgerWithReturnLedgerId(ledger);

    }

    public ResponseMessage isLedgerNameExists(String ledgerName, Integer companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (!ledgerDao.isLedgerNameExists(ledgerName, companyId)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Ledger name already exists. Please try with different name.");
        } else {
            responseMessage.setStatus(1);
        }
        return responseMessage;
    }

    public ResponseMessage deleteLedgerDetails(Integer ledgerId, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        ledgerDao.deleteLedgerByLedgerId(ledgerId);
        responseMessage.setStatus(1);
        responseMessage.setText("Successfully deleted.");
        return responseMessage;
    }


    /**
     * fetch ledger ID if exists, if not it will create new ledger
     *
     * @param ledgerName    ledgerName
     * @param currentUser   currentUser
     * @param accountTypeId accountTypeId
     * @return ledgerId
     */
    public String getLedgerIdByLedgerName(String ledgerName, CurrentUser currentUser, Integer accountTypeId) {
        String ledgerId;

        if (isLedgerNameExists(ledgerName, currentUser.getCompanyId()).getStatus() == 1) {
            ledgerId = dynamicLedgerCreationService(currentUser,
                    ledgerName, accountTypeId);
        } else {
            ledgerId = ledgerDao.getLedgerIdByLedgerName(currentUser.getCompanyId(),
                    ledgerName);
        }
        return ledgerId;
    }

    public String getLedgerIdByAccountTypeId(Integer accountTypeId, Integer companyId) {
        return ledgerDao.getLedgerIdByAccountTypeId(accountTypeId, companyId);
    }

    public String getNameByLedgerId(String ledgerId, Integer companyId, Integer financialYearId) {
        return ledgerDao.getNameByLedgerId(ledgerId, companyId, financialYearId);
    }

    public String getCashLedgerByCashAccountHead(CurrentUser currentUser) {
        return ledgerDao.getCashLedgerByCashAccountHead(AccountTypeEnum.CASH.getValue(),
                currentUser.getCompanyId());
    }

    public ResponseMessage isLedgerUsed(Integer ledgerId, Integer companyId) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (ledgerDao.isLedgerUsed(ledgerId, companyId)) {
            responseMessage.setStatus(0);
            responseMessage.setText("You cannot delete this ledger.");
        } else if (ledgerDao.checkIsOpeningBalance(ledgerId, companyId) > 0) {
            responseMessage.setStatus(0);
            responseMessage.setText("You cannot delete this ledger.");
        } else {
            responseMessage.setStatus(1);
        }
        return responseMessage;
    }


    public void updateOpeningBalance(String ledgerId, Integer companyId, Double amount) {
        ledgerDao.updateOpeningBalance(ledgerId, companyId, amount);
    }

    public void subtractLedgerAmount(String ledgerId, Integer companyId, Double amount) {
        ledgerDao.subtractLedgerAmount(ledgerId, companyId, amount);
    }

    public String getAccountTypeNameByAccType(Integer accTypeId) {
        return ledgerDao.getAccountTypeNameByAccType(accTypeId);
    }
}
