package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dao.EmployeeAdvanceDao;
import com.spms.mvc.dto.EmployeeAdvanceDTO;
import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.EmployeeAdvance;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 4/18/2021.
 */
@Service("employeeAdvanceService")
public class EmployeeAdvanceService {


    @Autowired
    private EmployeeAdvanceDao employeeAdvanceDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AddItemDao addItemDao;

    @Autowired
    private VoucherGroupListService voucherGroupListService;


    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(EmployeeAdvanceDTO employeeAdvanceDTO, CurrentUser currentUser) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();
        if (employeeAdvanceDTO.getIsCash().equals(PaymentModeTypeEnum.CASH.getValue())) {

            if (addItemDao.isCashLedgerExist(currentUser, AccountTypeEnum.CASH.getValue())) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("No cash ledger.");
                return responseMessage;
            }


            //check for cash availability
            Double txnAmount = employeeAdvanceDTO.getAmount();
            Double totalCashAmount = 0.0;
            Double totalCashOutFlow = 0.0;
            Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
            Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());

            LedgerDTO ledgerDTO = voucherGroupListService.getOpeningBalance(ledgerService.getLedgerIdByAccountTypeId(AccountTypeEnum.CASH.getValue(), currentUser.getCompanyId()), currentPeriodFrom, currentPeriodTo, currentUser);

            totalCashAmount = addItemDao.getTotalCash(AccountTypeEnum.CASH.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
            totalCashOutFlow = addItemDao.getTotalCashOutFlow(AccountTypeEnum.CASH.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
            totalCashOutFlow = totalCashOutFlow == null ? 0.0 : totalCashOutFlow;
            totalCashAmount = totalCashAmount == null ? 0.0 + Math.abs(ledgerDTO.getOpeningBal()) : (Math.abs(totalCashAmount) + Math.abs(ledgerDTO.getOpeningBal())) - totalCashOutFlow;

            if (employeeAdvanceDTO.getIsCash().equals(PaymentModeTypeEnum.CASH.getValue())) {
                if (checkCashBalance(employeeAdvanceDTO.getIsCash(), employeeAdvanceDTO.getAmount(), currentUser).getStatus() == 0) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Insufficient cash balance.");
                    return responseMessage;
                }
            }


//            if (totalCashAmount < txnAmount) {
//                responseMessage.setText("Insufficient cash balance.");
//                responseMessage.setStatus(0);
//                return responseMessage;
//            }
        }

        //region accounting Region
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
        voucherDTO.setVoucherEntryDate(employeeAdvanceDTO.getAdvanceDate());
        voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Salary Advance");
        employeeAdvanceDTO.setVoucherNo(voucherDTO.getVoucherNo());


        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

        //Dr employee name
        VoucherDetailDTO employeeAdvanceVoucherDTO = new VoucherDetailDTO();

        employeeAdvanceVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(employeeAdvanceDTO.getEmpName(),
                currentUser, AccountTypeEnum.EMPLOYEE_ADVANCE.getValue()));
        employeeAdvanceVoucherDTO.setDrcrAmount(AccountingUtil.drAmount(employeeAdvanceDTO.getAmount()));
        employeeAdvanceVoucherDTO.setIsCash(employeeAdvanceDTO.getIsCash());
        voucherDetailDTOs.add(employeeAdvanceVoucherDTO);


        //Cr cash/bank
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        voucherDetailDTO.setIsCash(employeeAdvanceDTO.getIsCash());
        voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(employeeAdvanceDTO.getAmount()));
        voucherDetailDTO.setBankLedgerId(employeeAdvanceDTO.getBankLedgerId());
        voucherDetailDTOs.add(voucherDetailDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        if (responseMessage.getStatus() == 0) {
            return responseMessage;
        }
        //endregion


        //inter the employee advance details
        EmployeeAdvance employeeAdvance = new EmployeeAdvance();
        employeeAdvance.setId(employeeAdvanceDao.getMaxEmployeeAdvanceId() + 1);
        employeeAdvance.setAdvanceDate(employeeAdvanceDTO.getAdvanceDate());
        employeeAdvance.setVoucherNo(employeeAdvanceDTO.getVoucherNo());
        employeeAdvance.setEmpId(employeeAdvanceDTO.getEmpId());
        employeeAdvance.setAmount(employeeAdvanceDTO.getAmount());
        employeeAdvance.setPaidIn(employeeAdvanceDTO.getIsCash());
        employeeAdvance.setCreatedBy(currentUser.getLoginId());
        employeeAdvance.setCreatedDate(new Date());
        employeeAdvance.setCompanyId(currentUser.getCompanyId());
        Integer id = employeeAdvanceDao.save(employeeAdvance);

        responseMessage.setStatus(1);
        employeeAdvanceDTO.setId(id);
        responseMessage.setDTO(employeeAdvanceDTO);
        responseMessage.setText("Employee advance successfully saved.");
        return responseMessage;
    }

    public ResponseMessage checkCashBalance(Integer cash, double txnAmount, CurrentUser currentUser) throws ParseException {

        ResponseMessage responseMessage = new ResponseMessage();

        Double openingBalance = 0.0;
        Double totalCashAmount = 0.0;
        Double totalCashOutFlow = 0.0;

        Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
        Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());

        if (cash.equals(PaymentModeTypeEnum.CASH.getValue())) {

            openingBalance = voucherGroupListService.getOpeningBalance(
                    ledgerService.getLedgerIdByAccountTypeId(AccountTypeEnum.CASH.getValue(),
                            currentUser.getCompanyId()),
                    currentPeriodFrom, currentPeriodTo, currentUser).getOpeningBal();

            totalCashAmount = addItemDao.getTotalCash(AccountTypeEnum.CASH.getValue(),
                    currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());

            totalCashOutFlow = addItemDao.getTotalCashOutFlow(AccountTypeEnum.CASH.getValue(),
                    currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());

            totalCashAmount = totalCashAmount == null ? 0.0 : totalCashAmount;
            totalCashOutFlow = totalCashOutFlow == null ? 0.0 : totalCashOutFlow;

            totalCashAmount = (Math.abs(totalCashAmount) + Math.abs(openingBalance)) - totalCashOutFlow;

            if (totalCashAmount < txnAmount) {
                responseMessage.setStatus(0);
                return responseMessage;
            }
        }
        responseMessage.setStatus(1);
        return responseMessage;
    }

}
