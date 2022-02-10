package com.spms.mvc.service;

import com.spms.mvc.dao.LocationCreationDao;
import com.spms.mvc.dto.LocationCreationDTO;
import com.spms.mvc.entity.Location;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project Name: Spare part management system
 * Description: <Replace description>
 * Date:12/2/2016
 * Year :2016
 *
 * @author: vcass
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:
 * Author:
 * Date:
 * Change Description:
 * Search Tag:
 **/
@Service("locationCreationService")
public class LocationCreationService {
    
    
    @Autowired
    private LocationCreationDao locationCreationDao;
    
    
    public List<LocationCreationDTO> getLocationList() {
        return locationCreationDao.getLocationList();
    }

    public LocationCreationDTO getLocationDetail(Integer location_id) {
        return locationCreationDao.getLocationDetail(location_id);
    }

    public ResponseMessage save(LocationCreationDTO locationCreationDTO, CurrentUser currentUser) {
        Location location = new Location();
        ResponseMessage responseMessage = new ResponseMessage();

        if (locationCreationDTO.getLocation_id() == null) {

            location.setLocation(locationCreationDTO.getLocation());
            location.setUploaded(currentUser.getCreatedDate());
            locationCreationDao.save(location);

            responseMessage.setStatus(1);
            responseMessage.setText("You have successfully saved the location.");

        } else {

            if (locationCreationDao.locationExists(locationCreationDTO.getLocation_id())) {

                location.setLocation(locationCreationDTO.getLocation());
                location.setUploaded(currentUser.getCreatedDate());
                location.setLocation_id(locationCreationDTO.getLocation_id());
                locationCreationDao.save(location);
                responseMessage.setStatus(1);
                responseMessage.setText("You have successfully updated the location changes.");

            } else {
                location.setLocation(locationCreationDTO.getLocation());
                location.setUploaded(currentUser.getCreatedDate());
                locationCreationDao.save(location);
                responseMessage.setStatus(1);
                responseMessage.setText("You have successfully saved the location.");

            }

        }

        return responseMessage;
    }
}
