package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.AccSaleInvoiceGenerationDao;
import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dto.AccSaleInvoiceGenerationDTO;
import com.spms.mvc.dto.AccSaleInvoiceGenerationListDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.AccSaleInvoiceNoGeneration;
import com.spms.mvc.entity.PartyDetail;
import com.spms.mvc.entity.SaleInvoice;
import com.spms.mvc.entity.SaleInvoiceDetail;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Bcass Sawa on 10/24/2019.
 */
@Service("accSaleInvoiceGenerationService")
public class AccSaleInvoiceGenerationService {


    @Autowired
    private AccSaleInvoiceGenerationDao accSaleInvoiceGenerationDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AddItemDao addItemDao;

    public String getMaxCountOfInvoiceNo(CurrentUser currentUser) {

        if (accSaleInvoiceGenerationDao.isCompanyInvoiceNoExists(currentUser.getCompanyId())) {
            AccSaleInvoiceNoGeneration accSaleInvoiceNoGeneration = new AccSaleInvoiceNoGeneration();
            accSaleInvoiceNoGeneration.setSaleInvoiceNoCounter(String.format("%03d", 1));
            accSaleInvoiceNoGeneration.setCompanyId(currentUser.getCompanyId());
            accSaleInvoiceGenerationDao.saveInvoiceNoCounterForLoginCompany(accSaleInvoiceNoGeneration);
        }
        return accSaleInvoiceGenerationDao.getMaxCountOfInvoiceNo(currentUser.getCompanyId());
    }

//    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(AccSaleInvoiceGenerationDTO accSaleInvoiceGenerationDTO, CurrentUser currentUser) throws ParseException {

        Integer partyId = null;
        ResponseMessage responseMessage = new ResponseMessage();

        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.SALES.getValue());
        voucherDTO.setVoucherEntryDate(accSaleInvoiceGenerationDTO.getInvoiceDate()); //system date
        voucherDTO.setVoucherNo(voucherCreationService
                .getCurrentVoucherNo(VoucherTypeEnum.SALES.getValue(),
                        currentUser.getCompanyId(),currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Sale Invoice Entry");

        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

//        //This will take care of services charges are paid in cash or in credit.
//        if (accSaleInvoiceGenerationDTO.getIsCash() != null) {
//            VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
//            if (Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
//                voucherDetailDTO.setBankLedgerId(accSaleInvoiceGenerationDTO.getBankLedgerId());
//            }
//            voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(accSaleInvoiceGenerationDTO.getAmount()));
//            voucherDetailDTO.setIsCash(accSaleInvoiceGenerationDTO.getIsCash());
//            voucherDetailDTOs.add(voucherDetailDTO);
//        } else {
//            //auto create ledger for party
//            //Dr
//            VoucherDetailDTO partyVoucherDTO = new VoucherDetailDTO();
//            partyVoucherDTO.setDrcrAmount(AccountingUtil.drAmount(accSaleInvoiceGenerationDTO.getAmount()));
//            partyVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(accSaleInvoiceGenerationDTO.getPartyName(), currentUser, AccountTypeEnum.RECEIVABLE.getValue()));
//            voucherDetailDTOs.add(partyVoucherDTO);
//
//            //Save party Detail
//        }

        //check party name already exists
        if (Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue()) || (accSaleInvoiceGenerationDTO.getPartyName() != null && !accSaleInvoiceGenerationDTO.getPartyName().equals(""))) { //applies to only party
            partyId = accSaleInvoiceGenerationDao.getPartyIdIFExists(currentUser.getCompanyId(),
                    accSaleInvoiceGenerationDTO.getPartyName());
            if (partyId == null) {
                partyId = savePartyDetail(accSaleInvoiceGenerationDTO, currentUser, partyId);
            }
        }

        //region Dr
        double amount;

        //Sale in Cash
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        if (Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {
            if (accSaleInvoiceGenerationDTO.getAmtReturn() < 0) {
                amount = accSaleInvoiceGenerationDTO.getAmtReceived();
            } else {
                amount = accSaleInvoiceGenerationDTO.getAmount() - accSaleInvoiceGenerationDTO.getDiscountRate();
            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(amount));
            voucherDetailDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in Bank
        if (Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
            if (accSaleInvoiceGenerationDTO.getAmtReturn() < 0) {
                amount = accSaleInvoiceGenerationDTO.getAmtReceived();
            } else {
                amount = accSaleInvoiceGenerationDTO.getAmount() - accSaleInvoiceGenerationDTO.getDiscountRate();
            }
            voucherDetailDTO = new VoucherDetailDTO();
            voucherDetailDTO.setBankLedgerId(accSaleInvoiceGenerationDTO.getBankLedgerId());
            voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(amount));
            voucherDetailDTO.setIsCash(accSaleInvoiceGenerationDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in credit
        /* Whether the sale is made either from cash or bank, if there is remaining amount left,
         system will take remaining amount as credit for particular party*/

        if ((accSaleInvoiceGenerationDTO.getPartyName() != null && !accSaleInvoiceGenerationDTO.getPartyName().equals("")) || Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())
                || accSaleInvoiceGenerationDTO.getAmtReturn() < 0) { // for credit party ledger
            //create ledger for party
            double creditAmount;
            voucherDetailDTO = new VoucherDetailDTO();

            voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                    accSaleInvoiceGenerationDTO.getPartyName(), currentUser, AccountTypeEnum.RECEIVABLE.getValue()));

            if (accSaleInvoiceGenerationDTO.getAmtReturn() < 0) {
                creditAmount = Math.abs(accSaleInvoiceGenerationDTO.getAmtReturn());
            } else {
                creditAmount = (accSaleInvoiceGenerationDTO.getAmount() - accSaleInvoiceGenerationDTO.getDiscountRate());
            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(creditAmount));
            voucherDetailDTO.setIsCash(accSaleInvoiceGenerationDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //voucher entry for both cash and bank
        if (Objects.equals(accSaleInvoiceGenerationDTO.getIsCash(), PaymentModeTypeEnum.BANK_AND_CASH.getValue())) {
            //Bank
            VoucherDetailDTO cashBankDTO = new VoucherDetailDTO();
            cashBankDTO.setBankLedgerId(accSaleInvoiceGenerationDTO.getBankLedgerId());
            cashBankDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(accSaleInvoiceGenerationDTO.getAmount() - (accSaleInvoiceGenerationDTO.getDiscountRate() + accSaleInvoiceGenerationDTO.getAmtReceived()))));
            cashBankDTO.setIsCash(accSaleInvoiceGenerationDTO.getIsCash());
            voucherDetailDTOs.add(cashBankDTO);

            //Compound voucher Cash
            cashBankDTO = new VoucherDetailDTO();
            cashBankDTO.setDrcrAmount(AccountingUtil.drAmount(accSaleInvoiceGenerationDTO.getAmount() - (accSaleInvoiceGenerationDTO.getDiscountRate() + accSaleInvoiceGenerationDTO.getAmountReceivedInBank())));
            cashBankDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
            voucherDetailDTOs.add(cashBankDTO);
        }
        //endregion

        //region Dr for Discount Amount Voucher Entry
        if (accSaleInvoiceGenerationDTO.getDiscountRate() > 0) {
            VoucherDetailDTO voucherDiscountDetailDTO = new VoucherDetailDTO();
            voucherDiscountDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                    "Discount", currentUser,
                    AccountTypeEnum.INDIRECT_COST.getValue()));
            voucherDiscountDetailDTO.setDrcrAmount(AccountingUtil.drAmount(accSaleInvoiceGenerationDTO.getDiscountRate()));
            voucherDiscountDetailDTO.setIsCash(accSaleInvoiceGenerationDTO.getIsCash());
            voucherDetailDTOs.add(voucherDiscountDetailDTO);
        }

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);

        //save invoice detail of a party
        SaleInvoice saleInvoice = new SaleInvoice();
        saleInvoice.setPartyId(partyId);
        saleInvoice.setInvoiceNo(accSaleInvoiceGenerationDTO.getInvoiceNo());
        saleInvoice.setInvoiceDate(accSaleInvoiceGenerationDTO.getInvoiceDate());
        saleInvoice.setPhysicalInvoiceNo(accSaleInvoiceGenerationDTO.getPhysicalInvoiceNo());
        saleInvoice.setSetDate(currentUser.getCreatedDate());
        saleInvoice.setCreatedBy(currentUser.getLoginId());
        Integer saleInvoiceId = accSaleInvoiceGenerationDao.saveToSaleInvoiceTable(saleInvoice);

        for (AccSaleInvoiceGenerationListDTO accSaleInvoiceGenerationListDTO : accSaleInvoiceGenerationDTO.getAccSaleInvoiceGenerationListDTOList()) {
            //CR
            voucherDetailDTO = new VoucherDetailDTO();
            if (accSaleInvoiceGenerationListDTO.getParticular() != null) {
                voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(accSaleInvoiceGenerationListDTO.getParticular(), currentUser, AccountTypeEnum.DIRECT_INCOME.getValue()));
            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(accSaleInvoiceGenerationListDTO.getAmount()));
            voucherDetailDTOs.add(voucherDetailDTO);

            SaleInvoiceDetail saleInvoiceDetail = new SaleInvoiceDetail();
            saleInvoiceDetail.setParticular(accSaleInvoiceGenerationListDTO.getParticular());
            saleInvoiceDetail.setSaleInvoiceId(saleInvoiceId);
            saleInvoiceDetail.setAmount(accSaleInvoiceGenerationListDTO.getAmount());
            accSaleInvoiceGenerationDao.saveToSaleInvoiceDetailTable(saleInvoiceDetail);

        }

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        accSaleInvoiceGenerationDao.updateSaleInvoiceSerialNo(String.format("%03d", Integer.parseInt(accSaleInvoiceGenerationDTO.getInvoiceNo()) + 1),
                currentUser.getCompanyId());

        accSaleInvoiceGenerationDTO.setSaleInvoiceId(saleInvoiceId);
        responseMessage.setDTO(accSaleInvoiceGenerationDTO);

        responseMessage.setStatus(1);
        responseMessage.setText("Invoice Generated Successfully.");

        return responseMessage;
    }

    public Integer savePartyDetail(AccSaleInvoiceGenerationDTO accSaleInvoiceGenerationDTO, CurrentUser currentUser, Integer partyId) {
        //check party name already exists
        partyId = accSaleInvoiceGenerationDao.getPartyIdIFExists(currentUser.getCompanyId(),
                accSaleInvoiceGenerationDTO.getPartyName());

        if (partyId == null) {
            PartyDetail partyDetail = new PartyDetail();
            partyId = accSaleInvoiceGenerationDao.getMaxPartyId() + 1;
            partyDetail.setPartyId(partyId);
            partyDetail.setPartyName(accSaleInvoiceGenerationDTO.getPartyName());
            partyDetail.setPartyAddress(accSaleInvoiceGenerationDTO.getPartyAddress());
            partyDetail.setPartyContactNo(accSaleInvoiceGenerationDTO.getPartyContactNo());
            partyDetail.setPartyEmail(accSaleInvoiceGenerationDTO.getPartyEmail());
            partyDetail.setCompanyId(currentUser.getCompanyId());
            partyDetail.setSetDate(currentUser.getCreatedDate());
            partyDetail.setCreatedBy(currentUser.getLoginId());
            accSaleInvoiceGenerationDao.savePartyDetail(partyDetail);
        }
        return partyId;
    }

    public List<AccSaleInvoiceGenerationListDTO> getInvoiceDetailsList(Integer saleInvoiceId, Integer companyId) {
        return accSaleInvoiceGenerationDao.getInvoiceDetailsList(saleInvoiceId, companyId);
    }

    public AccSaleInvoiceGenerationDTO getInvoiceDetails(Integer saleInvoiceId, Integer companyId) {
        return accSaleInvoiceGenerationDao.getInvoiceDetails(saleInvoiceId, companyId);
    }

    public List<AccSaleInvoiceGenerationListDTO> getLedgerList(CurrentUser currentUser) {
        return accSaleInvoiceGenerationDao.getLedgerList(currentUser.getCompanyId());
    }

    public List<AccSaleInvoiceGenerationDTO> getPartyList(CurrentUser currentUser) {
        return accSaleInvoiceGenerationDao.getPartyList(currentUser.getCompanyId());
    }

    public AccSaleInvoiceGenerationDTO getPartyDetail(CurrentUser currentUser, String partyName) {
        return accSaleInvoiceGenerationDao.getPartyDetail(currentUser.getCompanyId(), partyName);

    }
}
