package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dao.LedgerDao;
import com.spms.mvc.dao.SaleRecordDao;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleRecordDTO;
import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by SonamPC on 18-Dec-16.
 */
@Service("saleRecordService")
public class SaleRecordService {

    @Autowired
    private SaleRecordDao saleRecordDao;

    @Autowired
    private LedgerDao ledgerDao;

    public List<SaleRecordDTO> getSaleRecordList(Integer itemId, Date fromDate, Date toDate) {
        if (itemId == 0) {
            return saleRecordDao.getSaleRecordListALL(itemId, fromDate, toDate);
        } else {
            return saleRecordDao.getSaleRecordList(itemId, fromDate, toDate);
        }

    }

    public List<SaleItemDTO> getSaleRecordListSummary(Date fromDate, Date toDate, Integer companyId) {
        List<SaleItemDTO> saleItemDTOs = saleRecordDao.getSaleRecordListSummaryAll(fromDate, toDate,companyId);
        List<SaleItemDTO> saleItemDTOs1 = saleItemDTOs.stream().filter(saleDto -> saleDto.getSumQty().compareTo(BigDecimal.ZERO) != 0)
                .collect(Collectors.toList());
        return saleItemDTOs1;
    }

    public SaleRecordDTO getItemName(Integer itemId) {
        return saleRecordDao.getItemName(itemId);

    }


    public Double getTotalDiscount(Date fromDate, Date toDate, CurrentUser currentUser) {
        String discountLedgerId = ledgerDao.getLedgerIdByLedgerName(currentUser.getCompanyId(), "Discount");
        return saleRecordDao.getTotalDiscount(fromDate, toDate, discountLedgerId);
    }

    public Double getSaleReplaceDiffAmt(Date fromDate, Date toDate) {
        Double saleReplaceDiffAmt = saleRecordDao.getSaleReplaceDiffAmt(fromDate, toDate);
        return saleReplaceDiffAmt != 0 ? saleReplaceDiffAmt * -1 : saleReplaceDiffAmt;//*-1 means money return to shop else return to customer
    }

    public Double getTotalAmtBank(Date fromDate, Date toDate, CurrentUser currentUser) {
        return saleRecordDao.getTotalAmtCashOrBank(fromDate, toDate, currentUser.getCompanyId(), AccountTypeEnum.BANK.getValue());
    }

    public Double getTotalCash(Date fromDate, Date toDate, CurrentUser currentUser) {
        return saleRecordDao.getTotalAmtCashOrBank(fromDate, toDate, currentUser.getCompanyId(), AccountTypeEnum.CASH.getValue());
    }

    public Double getTotalCredit(Date fromDate, Date toDate, CurrentUser currentUser) {
        return saleRecordDao.getTotalAmtCashOrBank(fromDate, toDate, currentUser.getCompanyId(), AccountTypeEnum.RECEIVABLE.getValue());
    }

    public Double getTotalInvoiceSale(Date fromDate, Date toDate, CurrentUser currentUser) {
        return saleRecordDao.getTotalAmtByAccType(fromDate, toDate, currentUser.getCompanyId(), AccountTypeEnum.DIRECT_INCOME.getValue());
    }
}
