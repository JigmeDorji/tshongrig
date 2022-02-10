package com.spms.mvc.service;

import com.spms.mvc.dao.ReturnItemDao;
import com.spms.mvc.dao.SaleItemDao;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.entity.ReplaceItem;
import com.spms.mvc.entity.ReturnItem;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 8/31/2019.
 */
@Service("returnItemService")
public class ReturnItemService {
    ResponseMessage responseMessage = new ResponseMessage();

    @Autowired
    private ReturnItemDao returnItemDao;

    @Autowired
    private SaleItemDao saleItemDao;

    public List<SaleItemListDTO> getReceiptItemDetails(String receiptNo) {
        return returnItemDao.getReceiptItemDetails(receiptNo);
    }

    public ResponseMessage saveReturnQty(SaleItemDTO saleItemDTO, CurrentUser currentUser) {

        for (SaleItemListDTO saleItemListDTO : saleItemDTO.getSaleItemListDTO()) {
            if (saleItemListDTO.getReturnItem() != null) {
                if (saleItemListDTO.getReturnItem() == 0) {
                    ReturnItem returnItem = new ReturnItem();
                    returnItem.setItemCode(saleItemListDTO.getItemCode());
                    returnItem.setReturnQty(saleItemListDTO.getQty());
                    returnItem.setSetDate(new Date());
                    returnItem.setCreatedBy(currentUser.getLoginId());
                    returnItemDao.saveReturnQty(returnItem);
                    returnItemDao.updateReturnQtyInPurchase(saleItemListDTO.getQty(), saleItemListDTO.getItemCode());
                }
            }

            /**
             * update qty in stock
             */
            if (saleItemListDTO.getReplaceItem() != null) {
                if (saleItemListDTO.getReplaceItem() == 1) {
                    ReplaceItem replaceItem = new ReplaceItem();
                    replaceItem.setItemCode(saleItemListDTO.getItemCode());
                    replaceItem.setReplaceQty(saleItemListDTO.getQty());
                    replaceItem.setSetDate(new Date());
                    replaceItem.setCreatedBy(currentUser.getLoginId());
                    returnItemDao.saveReplaceQty(replaceItem);
                    saleItemDao.updateAvailableQty(saleItemListDTO.getItemCode(), saleItemListDTO.getQty(), currentUser.getCompanyId());
                }
            }
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Updated successfully");
        return responseMessage;
    }
}
