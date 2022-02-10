package com.spms.mvc.service;

import com.spms.mvc.dao.PurchaseCreditReportDao;
import com.spms.mvc.dto.CreditPaymentForReceiveItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 13-Feb-18.
 */
@Service("purchaseCreditReportService")
public class PurchaseCreditReportService {

    @Autowired
    private PurchaseCreditReportDao purchaseCreditReportDao;

    public List<CreditPaymentForReceiveItemDTO> getAgentWisePurchaseCreditSummaryDetail(String agentId, Date fromDate, Date toDate) {
        if (agentId.equals("All")) {
            return purchaseCreditReportDao.getAllAgentWisePurchaseCreditSummaryDetail(fromDate, toDate);
        } else {
            return purchaseCreditReportDao.getAgentWisePurchaseCreditSummaryDetail(agentId, fromDate, toDate);
        }
    }

    public List<CreditPaymentForReceiveItemDTO> getAgentWisePurchaseCreditDetail(String agentId, Date fromDate, Date toDate) {
        if (agentId.equals("All")) {
            return purchaseCreditReportDao.getAllAgentWisePurchaseCreditDetail(fromDate, toDate);
        } else {
            return purchaseCreditReportDao.getAgentWisePurchaseCreditDetail(agentId, fromDate, toDate);
        }
    }

    public List<CreditPaymentForReceiveItemDTO> getPurchaseCashReport(Date fromDate, Date toDate) {
        return purchaseCreditReportDao.getPurchaseCashReport(fromDate, toDate);
    }
}
