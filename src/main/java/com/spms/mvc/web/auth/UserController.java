package com.spms.mvc.web.auth;

import com.spms.mvc.Enumeration.CommonStatus;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CompanyCreationService;
import com.spms.mvc.service.auth.UserAccessPermissionService;
import com.spms.mvc.service.auth.UserService;
import com.spms.mvc.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
public class UserController extends BaseController {
    //private region service
    @Autowired
    private UserService userService;

    @Autowired
    private UserAccessPermissionService userAccessPermissionService;

    @Autowired
    private CompanyCreationService companyCreationService;
    //endregion

    //region public method

    /**
     * Index page
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("statusList", userService.getStatusList());
        DateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
        Date now = new Date();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("currentDate", currentDate.format(now));
        model.addAttribute("userRoleList", userAccessPermissionService.getUserRoleList());
        model.addAttribute("statusActive", CommonStatus.Active.getValue());
        model.addAttribute("statusInactive", CommonStatus.Inactive.getValue());
        model.addAttribute("loginCompany", companyCreationService.getLoginCompany(getCurrentUser(request).getCompanyId()));
        model.addAttribute("companyList", companyCreationService.loadCompanyList());
        model.addAttribute("currentUser",currentUser);

        return "user";
    }


    /**
     * To check if login id already exists or not
     *
     * @param username username
     * @return ResponseMessage
     */
    @ResponseBody
    @RequestMapping(value = "/isLoginIdAlreadyExists", method = RequestMethod.GET)
    public ResponseMessage isLoginIdAlreadyExists(String username, HttpServletRequest request) {
        return userService.isLoginIdAlreadyExists(username, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/loadMappingCompany", method = RequestMethod.GET)
    public List<Integer> loadMappingCompany(BigInteger userId) {
        return companyCreationService.loadMappedCompany(userId);
    }

    /**
     * To get the list of data from the database
     *
     * @return List<UserDTO>
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public List<UserDTO> getUserList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return userService.getUserList(getCurrentUser(request).getCompanyId(),currentUser);
    }


    /**
     * To add new user
     *
     * @param userDTO userDTO
     * @return ResponseMessage
     */
    @ResponseBody
    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseMessage addUser(HttpServletRequest request, UserDTO userDTO) {
        return userService.addUser(userDTO, getCurrentUser(request));
    }

    /**
     * to update user
     *
     * @param request -- HttpServletRequest
     * @param userDTO -- UserDTO
     * @return -- ResponseMessage
     */
    @ResponseBody
    @PreAuthorize("hasAuthority('1-EDIT')")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseMessage updateUser(HttpServletRequest request, UserDTO userDTO) {
        return userService.addUser(userDTO, getCurrentUser(request));
    }

    /**
     * To get the grid list to field
     *
     * @param username username
     * @return UserDTO
     */
    @ResponseBody
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
    public UserDTO getUserDetail(HttpServletRequest request, String username) {
        return (UserDTO) userService.getUserDetail(username, getCurrentUser(request)).getDTO();
    }

    @ResponseBody
//    @PreAuthorize("hasAuthority('1-DELETE')")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ResponseMessage deleteUser(HttpServletRequest request, Integer companyId, BigInteger userId) {
        return userService.deleteUser(companyId, userId, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMappedCompany", method = RequestMethod.GET)
    public ResponseMessage deleteMappedCompany(BigInteger userId, Integer companyId) {
        return userService.deleteMappedCompany(userId, companyId);
    }

    @ResponseBody
    @RequestMapping(value = "/isCompanyMappedAlready", method = RequestMethod.GET)
    public Boolean isCompanyMappedAlready(BigInteger userId, Integer companyId) {
        return userService.isCompanyMappedAlready(userId, companyId);
    }
    //endregion

}
