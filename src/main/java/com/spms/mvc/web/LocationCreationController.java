package com.spms.mvc.web;

import com.spms.mvc.dto.LocationCreationDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.LocationCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/locationCreation")
public class LocationCreationController {

    //region private dao
    @Autowired
    private LocationCreationService locationCreationService;
    //endregion

    /**
     * index method.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "locationCreation";
    }


    @ResponseBody
    @RequestMapping(value = "/getLocationList", method = RequestMethod.GET)
    public List<LocationCreationDTO> getLocationList() {
        return locationCreationService.getLocationList();
    }

    @ResponseBody
    @RequestMapping(value = "/getLocationDetail", method = RequestMethod.GET)
    public LocationCreationDTO getLocationDetail(Integer location_id) {
        return locationCreationService.getLocationDetail(location_id);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(LocationCreationDTO locationCreationDTO, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return locationCreationService.save(locationCreationDTO, currentUser);
    }
}
