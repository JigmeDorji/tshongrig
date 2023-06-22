package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.VoucherCreationDao;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.Voucher;
import com.spms.mvc.entity.VoucherDetail;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by jigmePc on 5/8/2019.
 */
@Service()
public class VoucherCreationService {

    @Autowired
    VoucherCreationDao voucherCreationDao;

    @Autowired
    AddItemService addItemService;

    public List<DropdownDTO> getAccTypeList() {
        return voucherCreationDao.getAccTypeList();
    }

    public List<DropdownDTO> getLedgerList(Integer companyId) {
        return voucherCreationDao.getLedgerList(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(VoucherDTO voucherDTO, CurrentUser currentUser) throws ParseException {

        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        format.setMaximumFractionDigits(2);

        Integer voucherId;
        ResponseMessage responseMessage = new ResponseMessage();

        if (!Objects.equals(voucherDTO.getTotalCredit(), voucherDTO.getTotalDebit())) {
            responseMessage.setStatus(0);
            responseMessage.setText("Debit Credit not equal.");
            return responseMessage;
        }
        if (checkCashBalance(voucherDTO.getVoucherDetailDTOList(), currentUser, format, voucherDTO.getVoucherTypeId()).getStatus() == 0) {
            responseMessage.setStatus(0);
            responseMessage.setText("Cash Insufficient.");
            return responseMessage;
        }

        if (voucherCreationDao.isVoucherIdExists(voucherDTO.getVoucherNo(), currentUser.getCompanyId(), currentUser.getFinancialYearId(), voucherDTO.getVoucherTypeId())) {
            //delete from voucher details
            voucherCreationDao.getVoucherIdByVoucherNo(voucherDTO.getVoucherNo(), currentUser.getCompanyId(), currentUser.getFinancialYearId(),
                    voucherDTO.getVoucherTypeId()).forEach(voucherCreationDao::deleteVoucherDetailList);
            //delete from voucher
            voucherCreationDao.deleteVoucherItemsFromVoucherTable(
                    voucherCreationDao.getVoucherIdFromVoucherTable(
                            voucherDTO.getVoucherNo(),
                            currentUser.getCompanyId(), currentUser.getFinancialYearId(),
                            voucherDTO.getVoucherTypeId()));
        }
        voucherId = voucherCreationDao.getCurrentVoucherId() == null ? 1 : voucherCreationDao.getCurrentVoucherId() + 1;
        for (VoucherDetailDTO voucherDetailDTO : voucherDTO.getVoucherDetailDTOList()) {
            VoucherDetail voucherDetail = new VoucherDetail();
            voucherDetail.setVoucherId(voucherId);
            voucherDetail.setLedgerId(voucherDetailDTO.getLedgerId());
            voucherDetail.setDrcrAmount(voucherDetailDTO.getDebitAmount() == null ? format.parse(voucherDetailDTO.getCreditAmount()).doubleValue() : (format.parse(voucherDetailDTO.getDebitAmount()).doubleValue()) * -1);
            voucherCreationDao.saveDetail(voucherDetail);
        }
        saveToVoucherTable(voucherDTO, currentUser, voucherId);

        responseMessage.setStatus(1);
        responseMessage.setDTO(voucherDTO);
        responseMessage.setText("Successfully saved.");
        return responseMessage;
    }

    /**
     * check balance
     *
     * @param voucherDetailDTOList
     * @param currentUser
     * @param format
     * @param voucherTypeId
     * @return ResponseMessage ResponseMessage
     */
    private ResponseMessage checkCashBalance(List<VoucherDetailDTO> voucherDetailDTOList, CurrentUser currentUser, NumberFormat format, Integer voucherTypeId) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();
        if (VoucherTypeEnum.PAYMENT.getValue().equals(voucherTypeId)) {
            for (VoucherDetailDTO voucherDetailDTO : voucherDetailDTOList) {
                if (voucherCreationDao.getAccTypeId(voucherDetailDTO.getLedgerId(), currentUser.getCompanyId()).equals(AccountTypeEnum.CASH.getValue())) {
                    if (addItemService.checkCashBalance(PaymentModeTypeEnum.CASH.getValue(), format.parse(voucherDetailDTO.getCreditAmount()).doubleValue(), currentUser).getStatus() == 0) {
                        responseMessage.setStatus(0);
                        return responseMessage;
                    }
                }
            }
        }
        responseMessage.setStatus(1);
        return responseMessage;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveToVoucherTable(VoucherDTO voucherDTO, CurrentUser currentUser, Integer voucherId) {

        Integer currentVoucherNo = voucherCreationDao.getCurrentVoucherNo(voucherDTO.getVoucherTypeId(),
                currentUser.getCompanyId(), currentUser.getFinancialYearId());

        voucherCreationDao.updateVoucherNo((currentVoucherNo + 1),
                currentUser.getCompanyId(), voucherDTO.getVoucherTypeId(),
                currentUser.getFinancialYearId());

        Voucher voucher = new Voucher();
        voucher.setVoucherId(voucherId);
        voucher.setVoucherTypeId(voucherDTO.getVoucherTypeId());
        voucher.setVoucherNo(voucherDTO.getVoucherNo());
        voucher.setVoucherEntryDate(voucherDTO.getVoucherEntryDate());
        voucher.setCompanyId(currentUser.getCompanyId());
        voucher.setFinancialYearId(currentUser.getFinancialYearId());
        voucher.setNarration(voucherDTO.getNarration());
        voucher.setSetDate(new Date());
        voucher.setCreatedBy(currentUser.getLoginId());
        voucherCreationDao.save(voucher);
    }

    public Integer getVoucherId() {
        return voucherCreationDao.getCurrentVoucherId() == null ? 1
                : voucherCreationDao.getCurrentVoucherId() + 1;
    }


    public Integer getCurrentVoucherNo(Integer voucherTypeId, Integer companyId, Integer financialYearId) {
        return voucherCreationDao.getCurrentVoucherNo(voucherTypeId, companyId, financialYearId) == 0 ? 1
                : voucherCreationDao.getCurrentVoucherNo(voucherTypeId, companyId, financialYearId) + 1;
    }


    public List<VoucherDetailDTO> getVoucherDetailsByVoucherNo(Integer voucherNo, Integer voucherTypeId, CurrentUser currentUser) {
        return voucherCreationDao.getVoucherDetailsByVoucherNo(voucherNo, voucherTypeId,
                currentUser.getCompanyId());
    }

    public ResponseMessage deleteVoucherDetail(Integer voucherDetailId) {
        ResponseMessage responseMessage = new ResponseMessage();
        voucherCreationDao.deleteVoucherDetail(voucherDetailId);
        responseMessage.setStatus(1);
        responseMessage.setText("Successfully deleted.");
        return responseMessage;
    }

    public Boolean checkAccountHeadType(Integer ledgerId, Integer companyId) {
        Boolean status = false;
        if (voucherCreationDao.checkAccountHeadType(ledgerId, companyId) == 12) {
            status = true;
        }
        return status;
    }

    public List<DropdownDTO> getParticularList(Integer companyId) {
        return voucherCreationDao.getParticularList(companyId);
    }

    public Integer getRateOfDepreciation(Integer particularId, Integer companyId) {
        return voucherCreationDao.getRateOfDepreciation(particularId, companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage performPurchaseAndSaleVoucherEntry(VoucherDTO voucherDTO, CurrentUser currentUser) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage = validateDrCrAmount(voucherDTO);
        if (responseMessage.getStatus().equals(0)) {
            return responseMessage;
        }

        Integer voucherId = getVoucherId();
        for (VoucherDetailDTO voucherDetailDTO : voucherDTO.getVoucherDetailDTOList()) {
            VoucherDetail voucherDetail = new VoucherDetail();
            if (voucherDetailDTO.getLedgerId() == null) {
                if (Objects.equals(voucherDetailDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {//for cash voucher inv 1=cash
                    voucherDetail.setVoucherId(voucherId);
                    voucherDetail.setLedgerId(voucherCreationDao.getCashLedgerId(currentUser.getCompanyId()));
                    voucherDetail.setDrcrAmount(voucherDetailDTO.getDrcrAmount());
                    voucherCreationDao.saveDetail(voucherDetail);
                }
                if (Objects.equals(voucherDetailDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue()) || Objects.equals(voucherDetailDTO.getIsCash(),
                        PaymentModeTypeEnum.BANK_AND_CASH.getValue())) {//for bank voucher inv 2= bank
                    voucherDetail.setVoucherId(voucherId);
                    voucherDetail.setLedgerId(voucherDetailDTO.getBankLedgerId());
                    voucherDetail.setDrcrAmount(voucherDetailDTO.getDrcrAmount());
                    voucherCreationDao.saveDetail(voucherDetail);
                }
                if (Objects.equals(voucherDetailDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())) {//for credit purchase inv 3=Credit
                    voucherDetail.setVoucherId(voucherId);
                    voucherDetail.setLedgerId(voucherDetailDTO.getLedgerId());
                    voucherDetail.setDrcrAmount(voucherDetailDTO.getDrcrAmount());
                    voucherCreationDao.saveDetail(voucherDetail);
                }

            } else {
                //Purchase Ledger Voucher Entry
                voucherDetail.setVoucherId(voucherId);
                voucherDetail.setLedgerId(voucherDetailDTO.getLedgerId());
                voucherDetail.setDrcrAmount(voucherDetailDTO.getDrcrAmount());
                voucherCreationDao.saveDetail(voucherDetail);
            }
        }
        saveToVoucherTable(voucherDTO, currentUser, voucherId);
        return responseMessage;
    }

    public ResponseMessage validateDrCrAmount(VoucherDTO voucherDTO) {
        ResponseMessage responseMessage = new ResponseMessage();

        double totalDRAmount = 0.0;
        double totalCRAmount = 0.0;

        for (VoucherDetailDTO voucherDetailDTO : voucherDTO.getVoucherDetailDTOList()) {
            if (voucherDetailDTO.getDrcrAmount() > 0) {
                totalDRAmount = totalDRAmount + voucherDetailDTO.getDrcrAmount();
            }
            if (voucherDetailDTO.getDrcrAmount() < 0) {
                totalCRAmount = totalCRAmount + AccountingUtil.drAmount(voucherDetailDTO.getDrcrAmount());
            }
        }

        totalCRAmount = Math.round(totalCRAmount * 100.0) / 100.0;
        totalDRAmount = Math.round(totalDRAmount * 100.0) / 100.0;

        if (totalCRAmount != totalDRAmount) {
            responseMessage.setStatus(0);
            responseMessage.setText("Dr Cr amount not equal.");
        } else {
            responseMessage.setStatus(1);
        }

        return responseMessage;
    }


    public List<DropdownDTO> getLedgerListForContraVoucherType(Integer companyId) {
        return voucherCreationDao.getLedgerListForContraVoucherType(companyId);
    }
}
