package com.spms.mvc.service;

import com.spms.mvc.dao.PendingBillDao;
import com.spms.mvc.dto.PendingBillDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SonamPC on 13-Jan-19.
 */
@Service("pendingBillService")
public class PendingBillService {

    @Autowired
    private PendingBillDao pendingBillDao;
    ResponseMessage responseMessage = new ResponseMessage();


    public List<PendingBillDTO> getAllThePendingBill() {
        List<PendingBillDTO> pendingBillDTOList = new ArrayList<>();
        Double totalAmount = 0.00;
        for (PendingBillDTO pendingBillDTO : pendingBillDao.getAllThePendingBill()) {
            totalAmount = (pendingBillDTO.getTotalBillAmt() * 0.98) + totalAmount;
            pendingBillDTOList.add(pendingBillDTO);
        }
        pendingBillDTOList.get(0).setTotalAmount(totalAmount);
        return pendingBillDTOList;
    }

    public ResponseMessage updateBill(PendingBillDTO pendingBillDTO) {
        pendingBillDao.updateBill(pendingBillDTO);
        responseMessage.setStatus(1);
        responseMessage.setText("Successfully Deleted.");
        return responseMessage;
    }

    public List<PendingBillDTO> searchBill(String agencyId, Character status) {
        List<PendingBillDTO> pendingBillDTOList = new ArrayList<>();
        Double totalAmount = 0.00;
        for (PendingBillDTO pendingBillDTO : pendingBillDao.searchBill(agencyId, status)) {
            totalAmount = (pendingBillDTO.getTotalBillAmt() * 0.98) + totalAmount;
            pendingBillDTOList.add(pendingBillDTO);
        }
        if (pendingBillDTOList.size() >= 1) {
            pendingBillDTOList.get(0).setTotalAmount(totalAmount);
        }
        return pendingBillDTOList;
    }
}
