package com.spms.mvc.service;

import com.spms.mvc.Enumeration.CommonStatus;
import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.dao.CompanyCreationDao;
import com.spms.mvc.dao.FinancialYearSetupDao;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.entity.FinancialYear;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Bikash Rai on 5/5/2020.
 */

@Service
public class FinancialYearSetupService {
    @Autowired
    private FinancialYearSetupDao financialYearSetupDao;

    @Autowired
    private CompanyCreationDao companyCreationDao;

    ResponseMessage responseMessage = new ResponseMessage();


    public ResponseMessage save(CurrentUser currentUser, FinancialYearDTO financialYearDTO) {
//        if (financialYearDTO.getStatus() == 'A' && !financialYearSetupDao.checkActiveFinancialYear(currentUser.getCompanyId())) {
//            responseMessage.setStatus(0);
//            responseMessage.setText("There can be only one active financial year.");
//            return responseMessage;
//        }
        financialYearSetupDao.makeInactiveFinancialYear(currentUser.getCompanyId());

        if (financialYearSetupDao.isFinancialYearExists(financialYearDTO.getFinancialYearId(), currentUser.getCompanyId())) {
            financialYearSetupDao.update(convertEntity(currentUser, financialYearDTO));
        } else {
            financialYearSetupDao.save(convertEntity(currentUser, financialYearDTO));
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Data saved successfully.");

        return responseMessage;
    }

    public Integer creatFinancialYear(CurrentUser currentUser, Date newFromDate, Date newToDate) {
        FinancialYear financialYear = new FinancialYear();
        financialYear.setFinancialYearId(companyCreationDao.getMaxFinancialYearId() + 1);
        financialYear.setFinancialYearFrom(newFromDate);
        financialYear.setFinancialYearTo(newToDate);
        financialYear.setStatus(CommonStatus.Active.getValue());
        financialYear.setCreatedBy(currentUser.getLoginId());
        financialYear.setCreatedDate(currentUser.getCreatedDate());
        financialYear.setCompanyId(currentUser.getCompanyId());
        Integer financialYearId = financialYearSetupDao.save(financialYear);
        return financialYearId;

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage makeInactiveFinancialYear(CurrentUser currentUser) {
        financialYearSetupDao.makeInactiveFinancialYear(currentUser.getCompanyId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;

    }

    private FinancialYear convertEntity(CurrentUser currentUser, FinancialYearDTO financialYearDTO) {
        FinancialYear financialYear = new FinancialYear();
        financialYear.setFinancialYearId(financialYearDTO.getFinancialYearId());
        financialYear.setFinancialYearFrom(financialYearDTO.getFinancialYearFrom());
        financialYear.setFinancialYearTo(financialYearDTO.getFinancialYearTo());
        financialYear.setStatus(CommonStatus.Active.getValue());
        financialYear.setCreatedBy(currentUser.getLoginId());
        financialYear.setCreatedDate(currentUser.getCreatedDate());
        financialYear.setCompanyId(currentUser.getCompanyId());
        return financialYear;
    }

    public List<FinancialYearDTO> getFinancialYearList(Integer companyId) {
        return financialYearSetupDao.getFinancialYearList(companyId);
    }

    public String getFinancialYearId(Integer companyId) {
        Integer financialYearId = financialYearSetupDao.getFinancialYearId(companyId);
        financialYearId = financialYearId == null ? 1 : financialYearId + 1;
        return Objects.equals(financialYearId, 1) ? "0" + 1 : (financialYearId.compareTo(10) == -1)
                ? "0" + financialYearId.toString() : financialYearId.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage activateFinancialYear(HttpServletRequest request, CurrentUser currentUser, BigInteger financialYearId) {
        financialYearSetupDao.makeInactiveFinancialYear(currentUser.getCompanyId());
        financialYearSetupDao.activateFinancialYear(financialYearId, currentUser.getCompanyId());
        FinancialYearDTO financialYearDTO = financialYearSetupDao.getFinancialYearDetail(financialYearId);

        //save to voucher count if it is not there for current financial year
        if (companyCreationDao.checkIsFinancialYearExistsForCompany(currentUser.getCompanyId(), currentUser.getFinancialYearId())) {
            companyCreationDao.saveCompanyRelatedVoucherCountDetail(currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(financialYearDTO.getFinancialYearFrom());
        Date fromDate = calendar.getTime();

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(financialYearDTO.getFinancialYearTo());
        Date toDate = calendarTo.getTime();

        currentUser.setFinancialYearFrom(DateUtil.format(fromDate, DateUtil.DD_MMM_YYYY));
        currentUser.setFinancialYearTo(DateUtil.format(toDate, DateUtil.DD_MMM_YYYY));

        currentUser.setFinancialYearId(financialYearId.intValue());
        request.getSession().setAttribute("currentUser", currentUser);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Activated successfully.");

        return responseMessage;
    }

    public List<DropdownDTO> getCurrentFinancialYear() {
        return financialYearSetupDao.getCurrentFinancialYear();
    }

    public Integer getCurrentFinancialYearId() {
        return financialYearSetupDao.getCurrentFinancialYearId();
    }

    public String getCurFinYear() {
        return financialYearSetupDao.getCurFinYear();
    }

    public FinancialYearDTO getPreviousFinancialYearDetail(Integer companyId, Integer financialYearId) {
        return financialYearSetupDao.getPreviousFinancialYearDetail(companyId);
    }

    public boolean checkIsFinancialYearAlreadyExist(Date newFromDate, Integer companyId) {
        return financialYearSetupDao.checkIsFinancialYearAlreadyExist(newFromDate, companyId);
    }

    public void makeDefaultActiveYear(Date newFromDate, Date newToDate, CurrentUser currentUser) {
//        FinancialYear currentYear = new FinancialYear();
//        currentYear.setFinancialYearId(companyCreationDao.getMaxFinancialYearId()+1);
//        currentYear.setFinancialYearFrom(newFromDate);
//        currentYear.setFinancialYearTo(newToDate);
//        currentYear.setStatus(CommonStatus.Active.getValue());
//        currentYear.setCreatedBy(currentUser.getLoginId());
//        currentYear.setCreatedDate(currentUser.getCreatedDate());
//        currentYear.setCompanyId(currentUser.getCompanyId());
//        financialYearSetupDao.save(currentYear);
        financialYearSetupDao.makeDefaultActiveYear(newFromDate, currentUser.getCompanyId());
    }
}
