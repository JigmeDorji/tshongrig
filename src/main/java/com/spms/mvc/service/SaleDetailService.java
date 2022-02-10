package com.spms.mvc.service;

import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.SaleDetailDao;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 3/27/2021.
 */
@Service("saleItemDetailService")
public class SaleDetailService {

    @Autowired
    private SaleDetailDao saleDetailDao;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private VoucherGroupListService voucherGroupListService;


    public List<SaleItemListDTO> getSaleItemDetailList(Integer companyId, Date fromDate, Date toDate) {
        return saleDetailDao.getSaleItemDetailList(companyId, fromDate, toDate);
    }

    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteSaleRelatedVoucher(String receiptMemoNo, CurrentUser currentUser) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();
        Integer voucherType;

        if (currentUser.getBusinessType().equals(BusinessType.Construction.getValue()) || currentUser.getBusinessType().equals(BusinessType.Manufacturing.getValue())) {
            voucherType = VoucherTypeEnum.JOURNAL.getValue();
        } else {
            voucherType = VoucherTypeEnum.SALES.getValue();
        }
        voucherGroupListService.deleteLedgerVoucherDetails(getVoucherNoByReceiptMemoNo(receiptMemoNo, currentUser),
                voucherType, currentUser);

        //update stock
        List<SaleItemListDTO> saleItemListDTOList = saleDetailDao.getSaleItemList(receiptMemoNo, currentUser.getCompanyId());
        for (SaleItemListDTO itemListDTO : saleItemListDTOList) {
            saleDetailDao.updateQtyInPurchase(itemListDTO.getItemCode(), itemListDTO.getQty(), currentUser.getCompanyId());
        }

        //delete sale record from detail
        for (SaleItemListDTO itemListDTO : saleItemListDTOList) {
            saleDetailDao.deleteSaleDetail(itemListDTO.getId());
        }
        //Delete from main table
        saleDetailDao.deleteSaleRecord(receiptMemoNo, currentUser.getCompanyId(), currentUser.getFinancialYearId());

        responseMessage.setStatus(1);
        responseMessage.setText("Sale detail deleted successfully");
        return responseMessage;
    }

    public Integer getVoucherNoByReceiptMemoNo(String receiptMemoNo, CurrentUser currentUser) {
        return saleDetailDao.getVoucherNoByReceiptMemoNo(receiptMemoNo, currentUser);
    }
}
