package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.LedgerType;
import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dao.LedgerDao;
import com.spms.mvc.dao.MoneyReceiptDao;
import com.spms.mvc.dto.MoneyReceiptDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.MoneyReceipt;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jigme.dorji on 10/27/2020.
 */
@Service("moneyReceiptService")
public class MoneyReceiptService {

    @Autowired
    private MoneyReceiptDao moneyReceiptDao;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private AddItemDao addItemDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    public List<DropdownDTO> getPartyLedgerList(Integer companyId) {
        return moneyReceiptDao.getPartyLedgerList(companyId);
    }

    public String getMoneyReceiptNo(CurrentUser currentUser) {
        String currentReceiptNo = moneyReceiptDao.getCurrentReceiptNo(currentUser.getCompanyId());
        Integer intReceiptNo = currentReceiptNo == null ? 0 : Integer.parseInt(currentReceiptNo);
        String stringReceiptNo = String.format("%03d", intReceiptNo + 1);
        return stringReceiptNo;
    }

   /* @Transactional(rollbackOn = Exception.class)*/
    public ResponseMessage save(MoneyReceiptDTO moneyReceiptDTO, CurrentUser currentUser) throws ParseException {
        //region accounting Region
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(2);
        voucherDTO.setVoucherEntryDate(moneyReceiptDTO.getReceiptDate());
        voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(2, currentUser.getCompanyId(),
                currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Money Received Entry");


        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

        //CR party name
        VoucherDetailDTO partyVoucher = new VoucherDetailDTO();
        partyVoucher.setLedgerId(moneyReceiptDTO.getPartyLedgerId());
        partyVoucher.setDrcrAmount(AccountingUtil.crAmount(moneyReceiptDTO.getAmount()));
        partyVoucher.setIsCash(moneyReceiptDTO.getIsCash());
        voucherDetailDTOs.add(partyVoucher);

        String ledgerName = ledgerDao.getNameByLedgerId(moneyReceiptDTO.getPartyLedgerId(), currentUser.getCompanyId(), currentUser.getFinancialYearId());

        //CR TDS voucher
        VoucherDetailDTO tdsVoucher = new VoucherDetailDTO();
        tdsVoucher.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.TDS_RECEIVABLE.getText(),
                currentUser, AccountTypeEnum.RECEIVABLE.getValue()));
        tdsVoucher.setDrcrAmount(AccountingUtil.drAmount(moneyReceiptDTO.gettDSAmount()));
        tdsVoucher.setIsCash(moneyReceiptDTO.getIsCash());
        voucherDetailDTOs.add(tdsVoucher);

        //CR Retention
        if (moneyReceiptDTO.getRetentionAmount() == null) {
            moneyReceiptDTO.setRetentionAmount(0.0);
        }
        if (moneyReceiptDTO.getRetentionAmount() > 0) {
            VoucherDetailDTO retentionVoucher = new VoucherDetailDTO();
            retentionVoucher.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                    ledgerName + "(Retention)", currentUser, AccountTypeEnum.RETENTION.getValue()));
            retentionVoucher.setDrcrAmount(AccountingUtil.drAmount(moneyReceiptDTO.getRetentionAmount()));
            retentionVoucher.setIsCash(moneyReceiptDTO.getIsCash());
            voucherDetailDTOs.add(retentionVoucher);
        }


        //CR Mobilization Advance
        if (moneyReceiptDTO.getMobilizationAdvAmount() == null) {
            moneyReceiptDTO.setMobilizationAdvAmount(0.0);
        }
        if (moneyReceiptDTO.getMobilizationAdvAmount() > 0) {
            VoucherDetailDTO mobilizationAdvVoucher = new VoucherDetailDTO();
            mobilizationAdvVoucher.setLedgerId(moneyReceiptDTO.getMobilizationAdvPartyLedgerId());
            mobilizationAdvVoucher.setDrcrAmount(AccountingUtil.drAmount(moneyReceiptDTO.getMobilizationAdvAmount()));
            mobilizationAdvVoucher.setIsCash(moneyReceiptDTO.getIsCash());
            voucherDetailDTOs.add(mobilizationAdvVoucher);
        }


        //CR Material Advance
        if (moneyReceiptDTO.getMaterialAdvAmount() == null) {
            moneyReceiptDTO.setMaterialAdvAmount(0.0);
        }
        if (moneyReceiptDTO.getMaterialAdvAmount() > 0) {
            VoucherDetailDTO materialAdvVoucher = new VoucherDetailDTO();
            materialAdvVoucher.setLedgerId(moneyReceiptDTO.getMaterialAdvPartyLedgerId());
            materialAdvVoucher.setDrcrAmount(AccountingUtil.drAmount(moneyReceiptDTO.getMaterialAdvAmount()));
            materialAdvVoucher.setIsCash(moneyReceiptDTO.getIsCash());
            voucherDetailDTOs.add(materialAdvVoucher);
        }


        //DR cash/bank
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        voucherDetailDTO.setIsCash(moneyReceiptDTO.getIsCash());
        voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(moneyReceiptDTO.getAmount() - (moneyReceiptDTO.gettDSAmount() + moneyReceiptDTO.getRetentionAmount() + moneyReceiptDTO.getMobilizationAdvAmount() + moneyReceiptDTO.getMaterialAdvAmount())));
        voucherDetailDTO.setBankLedgerId(moneyReceiptDTO.getBankLedgerId());
        voucherDetailDTOs.add(voucherDetailDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        ResponseMessage responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        if (responseMessage.getStatus() == 0) {
            return responseMessage;
        }
        //endregion

        MoneyReceipt moneyReceipt = new MoneyReceipt();
        moneyReceipt.setReceiptNo(moneyReceiptDTO.getReceiptNo());
        moneyReceipt.setPartyLedgerId(moneyReceiptDTO.getPartyLedgerId());
        moneyReceipt.setAmount(moneyReceiptDTO.getAmount() - (moneyReceiptDTO.gettDSAmount() + moneyReceiptDTO.getRetentionAmount() + moneyReceiptDTO.getMobilizationAdvAmount() + moneyReceiptDTO.getMaterialAdvAmount()));
        moneyReceipt.setReceivedIn(moneyReceiptDTO.getIsCash());
        moneyReceipt.setReceiptDate(moneyReceiptDTO.getReceiptDate());
        moneyReceipt.setCompanyId(currentUser.getCompanyId());
        moneyReceipt.setCreatedBy(currentUser.getLoginId());
        moneyReceipt.setCreatedDate(currentUser.getCreatedDate());

        moneyReceiptDao.saveMoneyReceipt(moneyReceipt);


        responseMessage.setStatus(1);
        responseMessage.setText("Money received generated Successfully.");
        return responseMessage;
    }

    public List<DropdownDTO> getBankList(Integer companyId) {
        return moneyReceiptDao.getBankList(companyId);
    }

    public List<MoneyReceiptDTO> getMoneyReceiptDetails(String moneyReceiptNo, Integer companyId) {
        return moneyReceiptDao.getMoneyReceiptDetails(moneyReceiptNo, companyId);
    }

    public Double getReceivableAmt(Integer partyLedgerId, CurrentUser currentUser) {
        return moneyReceiptDao.getReceivableAmt(partyLedgerId, currentUser.getCompanyId());
    }

    public List<DropdownDTO> getMobilizationPartyLedgerList(Integer companyId) {
        return moneyReceiptDao.getMobilizationPartyLedgerList(companyId);
    }

    public List<DropdownDTO> getMaterialAdvPartyLedgerList(Integer companyId) {
        return moneyReceiptDao.getMaterialAdvPartyLedgerList(companyId);
    }
}
