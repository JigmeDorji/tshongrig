package com.spms.mvc.service;

import com.spms.mvc.dao.LocationSetUpDao;
import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.entity.LocationSetUp;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SonamPC on 12-Jun-17.
 */
@Service("locationSetUpService")
public class LocationSetUpService {
    @Autowired
    private LocationSetUpDao locationSetUpDao;

    public ResponseMessage save(LocationSetUpDTO locationSetUpDTO, CurrentUser currentUser) {

        if (locationSetUpDTO.getLocationSetUpId() != null) {
            LocationSetUp locationSetUp = new LocationSetUp();
            ResponseMessage responseMessage = new ResponseMessage();
            locationSetUp.setLocationSetUpId(locationSetUpDTO.getLocationSetUpId());
            locationSetUp.setLocationId(locationSetUpDTO.getLocationId());
            locationSetUp.setDescription(locationSetUpDTO.getDescription());
            locationSetUp.setCompanyId(currentUser.getCompanyId());

            locationSetUpDao.update(locationSetUp);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully Updated.");
            return responseMessage;
        } else {

            LocationSetUp locationSetUp = new LocationSetUp();
            ResponseMessage responseMessage = new ResponseMessage();
            locationSetUp.setLocationId(locationSetUpDTO.getLocationId());
            locationSetUp.setDescription(locationSetUpDTO.getDescription());
            locationSetUp.setCompanyId(currentUser.getCompanyId());
            locationSetUpDao.save(locationSetUp);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully saved.");
            return responseMessage;
        }
    }

    public List<LocationSetUpDTO> getLocationList(Integer companyId) {
        return locationSetUpDao.getLocationList(companyId);
    }

    public List<DropdownDTO> getLocationSetUpList(CurrentUser currentUser) {
        return locationSetUpDao.getLocationSetUpList(currentUser.getCompanyId());
    }
}
