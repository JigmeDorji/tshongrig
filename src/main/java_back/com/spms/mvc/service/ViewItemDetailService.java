package com.spms.mvc.service;

import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.ViewItemDetailDao;
import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 8/25/2019.
 */
@Service("viewItemDetailService")
public class ViewItemDetailService {
    @Autowired
    private ViewItemDetailDao viewItemDetailDao;

    @Autowired
    private VoucherGroupListService voucherGroupListService;

    public List<PurchaseDTO> getItemDetail(Integer companyId, String itemCode, Integer financialYearId, Date asOnDate) {
        return viewItemDetailDao.getItemDetail(companyId, itemCode, financialYearId, asOnDate);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deletePurchaseRelatedVoucher(Integer voucherNo, String itemCode, BigDecimal qty, CurrentUser currentUser) {
        viewItemDetailDao.deletePurchaseRelatedVoucher(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId(), itemCode);
        viewItemDetailDao.updateQtyInPurcahse(currentUser.getCompanyId(), currentUser.getFinancialYearId(), itemCode, qty);
        if (voucherNo != 0) {//0 is opening bal which is not require to delete
            voucherGroupListService.deleteLedgerVoucherDetails(voucherNo, VoucherTypeEnum.PURCHASE.getValue(), currentUser);
        }
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(1);
        responseMessage.setText("Deleted Successfully.");
        return responseMessage;
    }
}
