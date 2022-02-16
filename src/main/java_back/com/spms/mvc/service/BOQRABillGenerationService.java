package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.BOQRABillGenerationDao;
import com.spms.mvc.dao.VoucherCreationDao;
import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.RABill;
import com.spms.mvc.entity.RABillDetail;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: BOQRABillGenerationService
 * Date:  2022-Jan-14
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-14
 * Change Description:
 * Search Tag:
 */
@Service
public class BOQRABillGenerationService {

    @Autowired
    private BOQRABillGenerationDao boqraBillGenerationDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private VoucherCreationDao voucherCreationDao;

    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        return boqraBillGenerationDao.getBOQList(boqId);
    }

    public Integer getRASerialNo(Integer boqId) {
        Integer raSerialNo = boqraBillGenerationDao.getRASerialNo(boqId);
        return raSerialNo == null ? 1 : raSerialNo + 1;
    }

    public List<DropdownDTO> getWorkOrderList(Integer companyId) {
        return boqraBillGenerationDao.getWorkOrderList(companyId);
    }

    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage saveRABillDetail(BOQDetailsDTO boqDetailsDTO, CurrentUser currentUser) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();

        Integer voucherNo;
        BigInteger raBillId;

        if (boqDetailsDTO.getVoucherNo() == null) {
            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
        } else {
            voucherNo = boqDetailsDTO.getVoucherNo();
            //delete from voucher details
            voucherCreationDao.getVoucherIdByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId(),
                    VoucherTypeEnum.JOURNAL.getValue()).forEach(voucherCreationDao::deleteVoucherDetailList);
            //delete from voucher
            voucherCreationDao.deleteVoucherItemsFromVoucherTable(voucherCreationDao.getVoucherIdFromVoucherTable(voucherNo, currentUser.getCompanyId(),
                    currentUser.getFinancialYearId(), VoucherTypeEnum.JOURNAL.getValue()));
        }

        RABill raBill = new ModelMapper().map(boqDetailsDTO, RABill.class);

        if (boqDetailsDTO.getRaBillId() == null) {
            raBillId = boqraBillGenerationDao.getMaxRaBillId();
            raBillId = raBillId == null ? BigInteger.ONE : raBillId.add(BigInteger.ONE);
        } else {
            raBillId = boqDetailsDTO.getRaBillId();
        }
        raBill.setRaBillId(raBillId);
        raBill.setVoucherNo(voucherNo);
        raBill.setCreatedBy(currentUser.getLoginId());
        raBill.setCreatedDate(currentUser.getCreatedDate());
        boqraBillGenerationDao.save(raBill);

        for (BOQDetailsListDTO boqDetailsListDTO : boqDetailsDTO.getBoqDetailsListDTO()) {
            if (boqDetailsListDTO.getQty() != null) {
                if (boqDetailsListDTO.getQty().compareTo(BigDecimal.ZERO) > 0) {
                    RABillDetail raBillDetail = new ModelMapper().map(boqDetailsListDTO, RABillDetail.class);
                    BigInteger raBillDetailId;

                    if (boqDetailsListDTO.getRaBillDetailId() == null) {
                        raBillDetailId = boqraBillGenerationDao.getMaxRaBillDetailId();
                        raBillDetailId = raBillDetailId == null ? BigInteger.ONE : raBillDetailId.add(BigInteger.ONE);
                    } else {
                        raBillDetailId = boqDetailsListDTO.getRaBillDetailId();
                    }
                    raBillDetail.setRaBillId(raBillId);
                    raBillDetail.setRaBillDetailId(raBillDetailId);
                    boqraBillGenerationDao.saveDetail(raBillDetail);
                }
            }
        }

        //Voucher Entry
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherNo(voucherNo);
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        voucherDTO.setVoucherEntryDate(currentUser.getCreatedDate());
        voucherDTO.setNarration("RA Bill Entry");

        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        voucherDetailDTO.setDrcrAmount(boqDetailsDTO.getTotalBillAmount());
        voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName("RA Bill", currentUser,
                AccountTypeEnum.DIRECT_INCOME.getValue()));
        voucherDetailDTOs.add(voucherDetailDTO);

        VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
        voucherDrDTo.setDrcrAmount(AccountingUtil.drAmount(boqDetailsDTO.getTotalBillAmount()));
        voucherDrDTo.setLedgerId(ledgerService.getLedgerIdByLedgerName(boqraBillGenerationDao
                        .getEmployingAgency(boqDetailsDTO.getBoqId()), currentUser,
                AccountTypeEnum.RECEIVABLE.getValue()));
        voucherDetailDTOs.add(voucherDrDTo);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        responseMessage.setText("RA Bills Generated Successfully.");
        responseMessage.setStatus(1);
        return responseMessage;
    }

    public List<BOQDetailsListDTO> getRABillList(BigInteger boqId, Integer raSerialNo) {
        return boqraBillGenerationDao.getRABillList(boqId,raSerialNo);
    }

    public String getRABillNo(BigInteger boqId, Integer raSerialNo) {
        return boqraBillGenerationDao.getRABillNo(boqId,raSerialNo);
    }

    public List<BOQDetailsDTO> getGeneratedRAList(Integer companyId) {
        return boqraBillGenerationDao.getGeneratedRAList(companyId);
    }

    public BOQDetailsDTO getBOQListForUpdate(BigInteger boqId, Integer raSerialNo) {
        BOQDetailsDTO boqDetailsDTO = boqraBillGenerationDao.getRaDetail(boqId, raSerialNo);
        boqDetailsDTO.setBoqDetailsListDTO(boqraBillGenerationDao.getBOQListForUpdate(boqId, raSerialNo));
        return boqDetailsDTO;
    }

    public ResponseMessage deleteRaBillDetail(BigInteger boqId, Integer raSerialNo, Integer voucherNo, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        //delete from voucher details
        voucherCreationDao.getVoucherIdByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId(),
                VoucherTypeEnum.JOURNAL.getValue()).forEach(voucherCreationDao::deleteVoucherDetailList);
        //delete from voucher
        voucherCreationDao.deleteVoucherItemsFromVoucherTable(voucherCreationDao.getVoucherIdFromVoucherTable(voucherNo, currentUser.getCompanyId(),
                currentUser.getFinancialYearId(), VoucherTypeEnum.JOURNAL.getValue()));


        BigInteger raBillId = boqraBillGenerationDao.getRaBillId(boqId, raSerialNo);
        boqraBillGenerationDao.deleteFromRaDetail(raBillId);
        boqraBillGenerationDao.deleteFromRaMainTable(raBillId);
        responseMessage.setStatus(1);
        responseMessage.setText("Successfully deleted the RA Bill.");
        return responseMessage;
    }
}
