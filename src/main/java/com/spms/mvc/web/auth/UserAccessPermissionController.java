package com.spms.mvc.web.auth;

import com.spms.mvc.dto.UserAccessPermissionDTO;
import com.spms.mvc.dto.UserAccessPermissionListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.auth.UserAccessPermissionService;
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
 * Created by jigme.dorji on 23/04/2020.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/userAccessPermission")
public class UserAccessPermissionController {

    @Autowired
    private UserAccessPermissionService userAccessPermissionService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("userRoleList", userAccessPermissionService.getUserRoleList());
        return "userAccessPermissionSetup";
    }

    @ResponseBody
    @RequestMapping(value = "/getScreenList", method = RequestMethod.GET)
    public List<UserAccessPermissionListDTO> getScreenList(Integer userRoleTypeId) {
        return userAccessPermissionService.getScreenList(userRoleTypeId);
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(UserAccessPermissionDTO userAccessPermissionDTO, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return userAccessPermissionService.save(userAccessPermissionDTO, currentUser);
    }

}
