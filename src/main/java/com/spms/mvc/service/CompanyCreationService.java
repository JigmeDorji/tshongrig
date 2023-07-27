package com.spms.mvc.service;

import com.spms.mvc.Enumeration.CommonStatus;
import com.spms.mvc.Enumeration.UserRoleType;
import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dao.CompanyCreationDao;
import com.spms.mvc.dao.FinancialYearSetupDao;
import com.spms.mvc.dao.auth.UserDao;
import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.entity.CompanyCreation;
import com.spms.mvc.entity.*;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.auth.UserService;
import com.spms.mvc.web.BaseController;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class CompanyCreationService extends BaseController {

    @Autowired
    private CompanyCreationDao companyCreationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FinancialYearSetupDao financialYearSetupDao;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public List<DropdownDTO> getBusinessTypeDropdown() {
        return companyCreationDao.getBusinessTypeDropdown();
    }

    public List<DropdownDTO> loadCompanyList() {
        return companyCreationDao.loadCompanyList();
    }

    public List<CompanyCreationDTO> getCompanyDetailList(CurrentUser currentUser) {


        if (currentUser.getUserRoleTypeId().equals(UserRoleType.Owner.getValue())) {

            List<CompanyCreationDTO> list = companyCreationDao.getCompanyDetailList();
            List<CompanyCreationDTO> filteredList = new ArrayList<>();

            for (CompanyCreationDTO companyCreationDTO : list) {
                String companyName = companyCreationDTO.getCompanyName();

                int status = companyName.indexOf("@");
//                System.out.println(companyName + ":=> " + status);
                if (status == -1) {
                    CompanyCreationDTO creationDTO = new CompanyCreationDTO();
                    creationDTO.setCompanyId(companyCreationDTO.getCompanyId());
                    creationDTO.setMailingAddress(companyCreationDTO.getMailingAddress());
                    creationDTO.setMobileNo(companyCreationDTO.getMobileNo());
                    creationDTO.setEmail(companyCreationDTO.getEmail());
                    creationDTO.setWebsite(companyCreationDTO.getWebsite());
                    creationDTO.setFnYrStart(companyCreationDTO.getFnYrStart());
                    creationDTO.setBusinessType(companyCreationDTO.getBusinessType());
                    creationDTO.setStatus(companyCreationDTO.getStatus());
                    creationDTO.setContactPerson(companyCreationDTO.getContactPerson());
                    creationDTO.setRemarks(companyCreationDTO.getRemarks());
                    creationDTO.setTotalSale(companyCreationDTO.getTotalSale());
                    creationDTO.setPfPercentage(companyCreationDTO.getPfPercentage());
                    creationDTO.setTotalListSale(companyCreationDTO.getTotalListSale());
                    creationDTO.setSaleDate(companyCreationDTO.getSaleDate());
                    creationDTO.setTrialExpiryDate(companyCreationDTO.getTrialExpiryDate());
                    creationDTO.setSaleListDate(companyCreationDTO.getSaleListDate());
                    creationDTO.setCompanyName(companyName);
                    filteredList.add(creationDTO);
                }

            }
            return companyCreationDao.getCompanyDetailList();
//            return filteredList;
        } else {
            return companyCreationDao.getCompanyLoginCompany(currentUser.getCompanyId());
        }

    }

    public CompanyCreationDTO populateCompanyDetail(Integer companyId) {
//        Boolean isCompanyExist = companyCreationDao.isCompanyExist(companyId);

        return companyCreationDao.populateCompanyDetail(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage saveCompanyDetails(CompanyCreationDTO companyCreationDTO, CurrentUser currentUser, Boolean isSignup) {


        ResponseMessage responseMessage = new ResponseMessage();
        CompanyCreation companyCreation = new CompanyCreation();

        if (companyCreationDTO.getCompanyId() == null) {
            if (companyCreationDao.isCompanyNameExists(companyCreationDTO.getCompanyName())) {
                responseMessage.setStatus(0);
                responseMessage.setText("Company name already exist.");
                return responseMessage;
            }
        }

        Integer companyId = null;
        BigInteger userId = null;
        if (companyCreationDTO.getCompanyId() == null) {//During signup time
            companyCreation.setCompanyName(companyCreationDTO.getCompanyName());
            companyCreation.setMailingAddress(companyCreationDTO.getMailingAddress());
            companyCreation.setMobileNo(companyCreationDTO.getMobileNo());
            companyCreation.setEmail(companyCreationDTO.getEmail());
            companyCreation.setWebsite(companyCreationDTO.getWebsite());
            companyCreation.setFnYrStart(companyCreationDTO.getFnYrStart());
            companyCreation.setBusinessType(companyCreationDTO.getBusinessType());
            companyCreation.setPfPercentage(companyCreationDTO.getPfPercentage());
            companyCreation.setStatus(CommonStatus.Pending.getValue());
            companyCreation.setContactPerson(companyCreationDTO.getContactPerson());
            companyId = companyCreationDao.getCompanyId();
            companyCreationDao.saveCompanyDetails(companyCreation);
        } else {//during approval time


            CompanyCreationDTO companyDetailPrevious = companyCreationDao.populateCompanyDetail(companyCreationDTO.getCompanyId());

            companyId = companyCreationDTO.getCompanyId();

            //Auto add user details
            if (currentUser.getUserRoleTypeId().equals(UserRoleType.Owner.getValue()) && companyCreationDTO.getStatus().equals(CommonStatus.Approve.getValue())) {
                User user = new User();
                BigInteger lastUserId = userDao.getLastUserId();
                lastUserId = lastUserId == null ? BigInteger.ONE : lastUserId.add(BigInteger.ONE);
                user.setUserId(lastUserId);

                Integer lastCompanyId = userDao.getLastCompanyId();

                String companyAbbreviation = companyCreationDTO.getCompanyName().replaceAll("\\B.|\\P{L}", "").toUpperCase();
                companyAbbreviation = companyAbbreviation.concat(lastCompanyId.toString());


                user.setUsername(companyAbbreviation);
                user.setUserFullName("Administrator");
                String saltValue = generateSaltValue(6);
                user.setSaltValue(saltValue);
                user.setUserPassword(passwordEncoder.encode(saltValue + "1234".trim()));
                user.setUserMobileNo(companyCreation.getMobileNo());
                user.setUserMobileNo(companyCreation.getMobileNo());
                user.setUserStatus(CommonStatus.Active.getValue());
                user.setUserRoleTypeId(UserRoleType.Administrator.getValue());
                user.setEmailId(companyCreation.getEmail());
                user.setCompanyId(companyId);
                user.setUpdatedBy(null);
                user.setUpdatedDate(null);
                user.setCreatedBy(currentUser.getLoginId());
                user.setCreatedDate(new Date());
                userId = userDao.addUser(user);


//                Saving the Brand For General Trading Company
                if (companyCreationDTO.getBusinessType() == 8) {
//                    savingBrandDetailOnCompanyApprovalOnce(companyAbbreviation, companyCreationDTO.getCompanyName());
                    savingBrandDetailOnCompanyApprovalOnce(companyAbbreviation, companyCreationDTO);
                }

                Boolean isCompanyExist = companyCreationDao.isCompanyExist(companyCreationDTO.getCompanyId());
                if (!isCompanyExist) {
//                Updating the User Login ID in Common Company Table
                    companyCreationDao.updateCommonCompanyTableForUserLoginId(companyId, companyAbbreviation);
//                updating the User Login Detail in  commonCompanyLoginId table
                    CommonCompanyLoginId commonCompanyLoginId = new CommonCompanyLoginId();
                    commonCompanyLoginId.setCompanyLoginId(companyAbbreviation);
                    commonCompanyLoginId.setCompany(companyCreationDTO.getCompanyName());
                    commonCompanyLoginId.setCompanyId(companyCreationDTO.getCompanyId());
                    companyCreationDao.saveCompanyLoginDetail(commonCompanyLoginId);

                }


            }


            if (companyDetailPrevious.getStatus().equals(CommonStatus.Pending.getValue()) && companyCreationDTO.getStatus().equals(CommonStatus.Approve.getValue())) {
                companyCreationDao.saveCompanyRelatedVoucherCountDetail(companyCreationDTO.getCompanyId(), currentUser.getFinancialYearId());
                FinancialYear financialYear = new FinancialYear();
                financialYear.setFinancialYearId(companyCreationDao.getMaxFinancialYearId() + 1);
                financialYear.setFinancialYearFrom(companyCreationDTO.getFnYrStart());
                financialYear.setFinancialYearTo(DateUtil.lastDayOfYear(companyCreationDTO.getFnYrStart()));
                financialYear.setStatus(CommonStatus.Active.getValue());
                financialYear.setCreatedBy(currentUser.getLoginId());
                financialYear.setCreatedDate(currentUser.getCreatedDate());
                financialYear.setCompanyId(companyId);
                financialYearSetupDao.save(financialYear);

                //mapping all the company for the super admin user
                UserDTO userDTO = new UserDTO();
                List<Integer> mappingCompany = new ArrayList<>();
                mappingCompany.add(companyId);
                userId = companyCreationDao.getUserIdOfSuperAdmin();
                userDTO.setCompanyMappingId(mappingCompany);
                userDTO.setUserId(userId);
                userService.saveUserWiseCompanyMapping(userDTO, userId);
            }
            companyCreation.setStatus(companyCreationDTO.getStatus());
            if (companyCreationDTO.getStatus().equals(CommonStatus.Approve.getValue())) {
                companyCreation.setTrialExpiryDate(DateUtils.addMonths(new Date(), 1));
            }

            companyCreation.setId(companyCreationDTO.getCompanyId());
            companyCreation.setMailingAddress(companyCreationDTO.getMailingAddress());
            companyCreation.setMobileNo(companyCreationDTO.getMobileNo());
            companyCreation.setContactPerson(companyCreationDTO.getContactPerson());
            companyCreation.setEmail(companyCreationDTO.getEmail());
            companyCreation.setWebsite(companyCreationDTO.getWebsite());
            companyCreation.setFnYrStart(companyCreationDTO.getFnYrStart());
            if (currentUser.getUserRoleTypeId().equals(UserRoleType.Owner)) {
                companyCreation.setBusinessType(companyCreationDTO.getBusinessType());
                companyCreation.setCompanyName(companyCreationDTO.getCompanyName());
            } else {
                companyCreation.setBusinessType(companyDetailPrevious.getBusinessType());
                companyCreation.setCompanyName(companyDetailPrevious.getCompanyName());
            }
            companyCreation.setPfPercentage(companyCreationDTO.getPfPercentage());
            companyCreation.setRemarks(companyCreationDTO.getRemarks());
            companyCreationDao.updateCompanyDetails(companyCreation);


        }
        if (isSignup) {
//            responseMessage.setText("Successfully submitted, trail will be approved within 24 hrs.");
            responseMessage.setText("Trial will be approved in 24 hours.");
            responseMessage.setStatus(1);
            responseMessage.setCompanyId(companyId);
        } else {
            responseMessage.setText("Company Detail save successfully.");
            responseMessage.setStatus(1);
            responseMessage.setCompanyId(companyId);
        }

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

    public List<CommonCompanyLoginId> getCompanyLoginDetail(Integer companyId) {
        return companyCreationDao.getCompanyLoginDetail(companyId);
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

    public List<DropdownDTO> getScreenList(BigInteger userId) {
        return companyCreationDao.getScreenList(userId);
    }

    public void updateTheUserStatus(Character status, Integer companyId) {

        List<BigInteger> userIds = companyCreationDao.getUserAt(companyId);
        if (status.equals('A')) {
            updateTheUserStatus(userIds, 'A');
        }
        if (status.equals('N')) {
            updateTheUserStatus(userIds, 'N');
        }
    }

    private void updateTheUserStatus(List<BigInteger> userIds, Character status) {
        for (BigInteger userId : userIds) {
            companyCreationDao.updateTheUserStatus(status, userId);
        }
    }


//   Added on 21/06/2023 For General Trading Business Brand Creation

    @Autowired
    private AddItemDao addItemDao;


    private void savingBrandDetailOnCompanyApprovalOnce(String companyAbbreviation, CompanyCreationDTO companyCreationDTO) {
        String brandName = companyAbbreviation + "GT" + companyAbbreviation;
        Boolean noBrandExist = companyCreationDao.checkInBrandExistsOnCompanyApprovalOnce(companyCreationDTO.getCompanyId());
        System.out.println(noBrandExist);

////        System.out.println(companyCreationDao.checkInBrandExistsOnCompanyApprovalOnce(companyCreationDao.getCompanyId()));
        if (noBrandExist) {//adding  New Brand Detail once on approval time
            BrandWiseItemCode brandWiseItemCode = new BrandWiseItemCode();
            brandWiseItemCode.setBrandId(addItemDao.getMaxBrandId() + 1);
            brandWiseItemCode.setBrandName(brandName);
            brandWiseItemCode.setBrandPrefix(companyAbbreviation);
            brandWiseItemCode.setSerialNo(1);
            brandWiseItemCode.setRemarks("Brand For General Trading : " + companyCreationDTO.getCompanyName());
            brandWiseItemCode.setCompanyId(companyCreationDTO.getCompanyId());
            brandWiseItemCode.setSetDate(new Date());
            brandWiseItemCode.setCreatedBy(companyAbbreviation);
            companyCreationDao.savingBrandDetailOnCompanyApprovalOnce(brandWiseItemCode);
        }
    }

    //Auto Brand Creation For only Trading Company
    public void autoBrandCreationForGeneralTrading(CompanyCreationDTO companyCreationDTO,CurrentUser currentUser) {



    }

}
