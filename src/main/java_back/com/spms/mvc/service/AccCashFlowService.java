package com.spms.mvc.service;

import com.spms.mvc.dao.VoucherCreationDao;
import com.spms.mvc.dao.VoucherGroupListDao;
import com.spms.mvc.dto.AccCashFlowDTO;
import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 8/4/2019.
 */
@Service("accCashFlowService")
public class AccCashFlowService {


    @Autowired
    private VoucherGroupListDao voucherGroupListDao;
    @Autowired
    private VoucherCreationDao voucherCreationDao;

    public List<AccCashFlowDTO> getCashFlow(CurrentUser currentUser, Date fromDate, Date toDate) {
        return voucherGroupListDao.getCashOutFlowDetails(currentUser.getCompanyId(),fromDate, toDate);
    }

    private Double getTotalCashInFlowAmount(CurrentUser currentUser, Date fromDate, Date toDate) {
        Double netCashInFlowAmount = 0.0;
        for (AccCashFlowDTO accCashFlowDTO : voucherGroupListDao.getCashInFlowDetails(currentUser.getCompanyId(), fromDate, toDate)) {
            netCashInFlowAmount = netCashInFlowAmount + accCashFlowDTO.getAmount();
        }

        return netCashInFlowAmount;
    }

    private Double getTotalCashOutFlowAmount(CurrentUser currentUser, Date fromDate, Date toDate) {
        Double netCashOutFlowAmount = 0.0;
        for (AccCashFlowDTO accCashFlowDTO : voucherGroupListDao.getCashOutFlowDetails(currentUser.getCompanyId(), fromDate, toDate)) {
            netCashOutFlowAmount = netCashOutFlowAmount + accCashFlowDTO.getAmount();
        }
        return netCashOutFlowAmount;
    }
}
