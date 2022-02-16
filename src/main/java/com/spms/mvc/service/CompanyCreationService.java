package com.spms.mvc.service;

import com.spms.mvc.Enumeration.CommonStatus;
import com.spms.mvc.dao.CompanyCreationDao;
import com.spms.mvc.dao.FinancialYearSetupDao;
import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.entity.CompanyCreation;
import com.spms.mvc.entity.FinancialYear;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/3/2019.
 */

//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
@Service("companyCreationService")
public class CompanyCreationService {

    @Autowired
    private CompanyCreationDao companyCreationDao;

    @Autowired
    private FinancialYearSetupDao financialYearSetupDao;

    @Autowired
    private UserService userService;


    public List<DropdownDTO> getBusinessTypeDropdown() {
        return companyCreationDao.getBusinessTypeDropdown();
    }

    public List<DropdownDTO> loadCompanyList() {
        return companyCreationDao.loadCompanyList();
    }

    public List<CompanyCreationDTO> getCompanyDetailList() {
        return companyCreationDao.getCompanyDetailList();
    }

    public CompanyCreationDTO populateCompanyDetail(Integer companyId) {
        return companyCreationDao.populateCompanyDetail(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage saveCompanyDetails(CompanyCreationDTO companyCreationDTO, CurrentUser currentUser) {
        CompanyCreation companyCreation = new CompanyCreation();
        ResponseMessage responseMessage = new ResponseMessage();

        if (companyCreationDTO.getCompanyId() == null) {
            if (companyCreationDao.isCompanyNameExists(companyCreationDTO.getCompanyName())) {
                responseMessage.setStatus(0);
                responseMessage.setText("Company name already exist.");
                return responseMessage;
            }
        }

        Integer companyId;
        if (companyCreationDTO.getCompanyId() == null) {
            companyCreation.setCompanyName(companyCreationDTO.getCompanyName());
            companyCreation.setMailingAddress(companyCreationDTO.getMailingAddress());
            companyCreation.setMobileNo(companyCreationDTO.getMobileNo());
            companyCreation.setEmail(companyCreationDTO.getEmail());
            companyCreation.setWebsite(companyCreationDTO.getWebsite());
            companyCreation.setFnYrStart(companyCreationDTO.getFnYrStart());
            companyCreation.setBusinessType(companyCreationDTO.getBusinessType());
            companyCreation.setPfPercentage(companyCreationDTO.getPfPercentage());

            companyCreationDao.saveCompanyDetails(companyCreation);
            companyId = companyCreationDao.getCompanyId();

            companyCreationDao.saveCompanyRelatedVoucherCountDetail(companyId, currentUser.getFinancialYearId());

            FinancialYear financialYear = new FinancialYear();
            financialYear.setFinancialYearId(companyCreationDao.getMaxFinancialYearId() + 1);
            financialYear.setFinancialYearFrom(companyCreation.getFnYrStart());
            financialYear.setFinancialYearTo(DateUtil.lastDayOfYear(companyCreation.getFnYrStart()));
            financialYear.setStatus(CommonStatus.Active.getValue());
            financialYear.setCreatedBy(currentUser.getLoginId());
            financialYear.setCreatedDate(currentUser.getCreatedDate());
            financialYear.setCompanyId(companyId);
            financialYearSetupDao.save(financialYear);

            //mapping all the company for the super admin user
            UserDTO userDTO = new UserDTO();
            List<Integer> mappingCompany = new ArrayList<>();
            mappingCompany.add(companyId);
            BigInteger userId = companyCreationDao.getUserIdOfSuperAdmin();
            userDTO.setCompanyMappingId(mappingCompany);
            userDTO.setUserId(userId);
            userService.saveUserWiseCompanyMapping(userDTO, userId);
        } else {
            companyId = companyCreationDTO.getCompanyId();
            companyCreation.setId(companyCreationDTO.getCompanyId());
            companyCreation.setCompanyName(companyCreationDTO.getCompanyName());
            companyCreation.setMailingAddress(companyCreationDTO.getMailingAddress());
            companyCreation.setMobileNo(companyCreationDTO.getMobileNo());
            companyCreation.setEmail(companyCreationDTO.getEmail());
            companyCreation.setWebsite(companyCreationDTO.getWebsite());
            companyCreation.setFnYrStart(companyCreationDTO.getFnYrStart());
            companyCreation.setBusinessType(companyCreationDTO.getBusinessType());
            companyCreation.setPfPercentage(companyCreationDTO.getPfPercentage());

            companyCreationDao.updateCompanyDetails(companyCreation);
        }

        responseMessage.setText("Company Detail save successfully.");
        responseMessage.setStatus(1);
        responseMessage.setCompanyId(companyId);
        return responseMessage;
    }


    public ResponseMessage deleteCompanyDetails(Integer companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        companyCreationDao.deleteCompanyDetails(companyId);
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    public CompanyCreationDTO getSelectedCompanyDetails(Integer companyId) {
        return companyCreationDao.getSelectedCompanyDetails(companyId);
    }

    public CompanyCreationDTO getTotalSale(Integer companyId, Integer financialYearId) {

        List<Double> finalTotalSaleList = new ArrayList<>();
        List<Date> finalDateList = new ArrayList<>();
        CompanyCreationDTO companyCreationFinalDTO = new CompanyCreationDTO();

        for (CompanyCreationDTO companyCreationDTO : companyCreationDao.getTotalSale(companyId, financialYearId)) {
            finalTotalSaleList.add(companyCreationDTO.getTotalSale());
            finalDateList.add(companyCreationDTO.getSaleDate());
        }
        companyCreationFinalDTO.setTotalListSale(finalTotalSaleList);
        companyCreationFinalDTO.setSaleListDate(finalDateList);

        return companyCreationFinalDTO;
    }

    public FinancialYearDTO getCurrentFinancialYearIdByCompany(Integer companyId) {
        return companyCreationDao.getCurrentFinancialYearIdByCompany(companyId);
    }

    public ResponseMessage performYearClosing(Integer companyId, Integer financialYearId) {
        ResponseMessage responseMessage = new ResponseMessage();
        companyCreationDao.performYearClosing(companyId, financialYearId);
        responseMessage.setStatus(1);
        responseMessage.setText("Year closing successful.");
        return responseMessage;
    }

    public List<DropdownDTO> getLoginCompany(Integer companyId) {
        return companyCreationDao.getLoginCompany(companyId);
    }

    public List<Integer> loadMappedCompany(BigInteger userId) {
        return companyCreationDao.loadMappedCompany(userId);
    }
}
