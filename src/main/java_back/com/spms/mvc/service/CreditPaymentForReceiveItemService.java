package com.spms.mvc.service;

import com.spms.mvc.dao.CreditPaymentForReceiveItemDao;
import com.spms.mvc.dto.CreditPaymentForReceiveItemDTO;
import com.spms.mvc.entity.CreditPaymentForReceiveItem;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bcass Sawa on 2/13/2018.
 */
@Service("creditPaymentForReceiveItemService")
public class CreditPaymentForReceiveItemService {
    @Autowired
    private CreditPaymentForReceiveItemDao creditPaymentForReceiveItemDao;

    public ResponseMessage saveCreditPaymentDetail(CreditPaymentForReceiveItemDTO creditPaymentForReceiveItemDTO, CurrentUser currentUser) {
        CreditPaymentForReceiveItem creditPaymentForReceiveItem = new CreditPaymentForReceiveItem();
        ResponseMessage responseMessage = new ResponseMessage();

        creditPaymentForReceiveItem.setId(creditPaymentForReceiveItemDao.getMaxSerialId() + 1);
        creditPaymentForReceiveItem.setAgencyId(creditPaymentForReceiveItemDTO.getAgencyId());
        creditPaymentForReceiveItem.setBill_reference(creditPaymentForReceiveItemDTO.getBill_reference());
        creditPaymentForReceiveItem.setTotal_amount_paid(creditPaymentForReceiveItemDTO.getAmount());
        creditPaymentForReceiveItem.setSetDate(currentUser.getCreatedDate());
        creditPaymentForReceiveItemDao.saveCreditPaymentDetail(creditPaymentForReceiveItem);
        responseMessage.setStatus(1);
        responseMessage.setText("Saved successfully.");
        return responseMessage;
    }
}
