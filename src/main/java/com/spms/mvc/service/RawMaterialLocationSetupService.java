package com.spms.mvc.service;

import com.spms.mvc.dao.RawMaterialLocationSetupDao;
import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.dto.RawMaterialLocationSetupDTO;
import com.spms.mvc.entity.LocationSetUp;
import com.spms.mvc.entity.RawMaterialLocationSetup;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("rawMaterialLocationSetUpService")
public class RawMaterialLocationSetupService {
    @Autowired
    private RawMaterialLocationSetupDao rawMaterialLocationSetupDao;


    public ResponseMessage save(RawMaterialLocationSetupDTO rawMaterialLocationSetupDTO, CurrentUser currentUser) {



//        if (rawMaterialLocationSetupDTO.getLocationSetUpId() != null) {
        if (rawMaterialLocationSetupDTO.getRawMaterialLocationSetUpId() != null) {
            ResponseMessage responseMessage = new ResponseMessage();
            RawMaterialLocationSetup rawMaterialLocationSetup=new RawMaterialLocationSetup();
            rawMaterialLocationSetup.setLocationSetUpId(rawMaterialLocationSetupDTO.getRawMaterialLocationSetUpId());
            rawMaterialLocationSetup.setLocationId(rawMaterialLocationSetupDTO.getRawMaterialLocationId());
            rawMaterialLocationSetup.setDescription(rawMaterialLocationSetupDTO.getRawMaterialLocationIdDescription());
            rawMaterialLocationSetup.setCompanyId(currentUser.getCompanyId());
            rawMaterialLocationSetupDao.update(rawMaterialLocationSetup);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully Updated.");
            return responseMessage;
        }else {
            ResponseMessage responseMessage = new ResponseMessage();
            RawMaterialLocationSetup rawMaterialLocationSetup=new RawMaterialLocationSetup();
            rawMaterialLocationSetup.setLocationSetUpId(rawMaterialLocationSetupDTO.getRawMaterialLocationSetUpId());
            rawMaterialLocationSetup.setLocationId(rawMaterialLocationSetupDTO.getRawMaterialLocationId());
            rawMaterialLocationSetup.setDescription(rawMaterialLocationSetupDTO.getRawMaterialLocationIdDescription());
            rawMaterialLocationSetup.setCompanyId(currentUser.getCompanyId());
            rawMaterialLocationSetupDao.save(rawMaterialLocationSetup);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully saved.");
            return responseMessage;
        }

    }

    public List<LocationSetUpDTO> getLocationList(Integer companyId) {
        return rawMaterialLocationSetupDao.getLocationList(companyId);
    }

    public List<DropdownDTO> getLocationSetUpList(CurrentUser currentUser) {
        return rawMaterialLocationSetupDao.getLocationSetUpList(currentUser.getCompanyId());
    }

    public List<LocationSetUpDTO> getAllLocationList() {
        return rawMaterialLocationSetupDao.getAllLocationList();
    }
}
