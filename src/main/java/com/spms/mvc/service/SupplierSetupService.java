package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dao.SupplierSetupDao;
import com.spms.mvc.dto.SupplierSetupDTO;
import com.spms.mvc.entity.SupplierSetup;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jigmePc on 10/20/2019.
 */
@Service("supplierSetupService")
public class SupplierSetupService {

    @Autowired
    private SupplierSetupDao supplierSetupDao;

    @Autowired
    private LedgerService ledgerService;

    public ResponseMessage save(SupplierSetupDTO supplierSetupDTO, CurrentUser currentUser) {
        SupplierSetup supplierSetup = new SupplierSetup();
        ResponseMessage responseMessage = new ResponseMessage();

        if (supplierSetupDTO.getId() == null) {
            supplierSetup.setSupplierName(supplierSetupDTO.getSupplierName());
            supplierSetup.setAddress(supplierSetupDTO.getAddress());
            supplierSetup.setEmail(supplierSetupDTO.getEmail());
            supplierSetup.setContactNo(supplierSetupDTO.getContactNo());
            supplierSetup.setCompanyId(currentUser.getCompanyId());
            supplierSetup.setFinancialYearId(currentUser.getFinancialYearId());
            supplierSetupDao.save(supplierSetup);

            responseMessage.setStatus(1);
            responseMessage.setText("Supplier details saved successfully");

        } else {
            supplierSetup.setId(supplierSetupDTO.getId());
            supplierSetup.setSupplierName(supplierSetupDTO.getSupplierName());
            supplierSetup.setAddress(supplierSetupDTO.getAddress());
            supplierSetup.setEmail(supplierSetupDTO.getEmail());
            supplierSetup.setContactNo(supplierSetupDTO.getContactNo());
            supplierSetup.setCompanyId(currentUser.getCompanyId());
            supplierSetup.setFinancialYearId(currentUser.getFinancialYearId());
            supplierSetupDao.save(supplierSetup);

            responseMessage.setStatus(1);
            responseMessage.setText("Supplier details updated successfully.");
        }

        //create ledger
        ledgerService.getLedgerIdByLedgerName(supplierSetup.getSupplierName(), currentUser,
                AccountTypeEnum.PAYABLE.getValue());

        return responseMessage;
    }

    public List<SupplierSetupDTO> getSupplierList(Integer companyId) {
        return supplierSetupDao.getSupplierList(companyId);
    }

    public SupplierSetupDTO getSupplierDetails(Integer id) {
        return supplierSetupDao.getSupplierDetails(id);
    }

    public List<DropdownDTO> getSupplierListDropDown(CurrentUser currentUser) {
        return supplierSetupDao.getSupplierListDropDown(currentUser.getCompanyId());
    }

    public Integer getSupplierIdByName(String ledgerName, CurrentUser currentUser) {
        return supplierSetupDao.getSupplierIdByName(ledgerName, currentUser);
    }
}
