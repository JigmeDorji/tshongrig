package com.spms.mvc.web;

import com.spms.mvc.dto.CreateUserDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CreateUserService;
import com.spms.mvc.service.auth.UserAccessPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by :Jigme
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/createUser")
public class CreateUserController {
    //private region service
    private CreateUserService createUserService;

    @Autowired
    private UserAccessPermissionService userAccessPermissionService;
    //endregion

    //region setter and getter
    public void setCreateUserService(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }
    //endregion

    //region public method

    /**
     * Index page
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
//        model.addAttribute("statusList", createUserService.getStatusList());
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateFormat todaysDate;
        todaysDate = new SimpleDateFormat("dd-MMM-yyyy");
        Date now = new Date();
        model.addAttribute("createdDate", todaysDate.format(now));
       model.addAttribute("userRoleList", userAccessPermissionService.getUserRoleList());

        return "frmCreateUsers";
    }

    /**
     * To check if login id already exists or not
     *
     * @param loginValue loginValue
     * @return ResponseMessage
     */
    @ResponseBody
    @RequestMapping(value = "/isLoginIdAlreadyExists", method = RequestMethod.GET)
    public ResponseMessage isLoginIdAlreadyExists(String loginValue) {
        return createUserService.isLoginIdAlreadyExists(loginValue);
    }

    /**
     * To get the list of data from the database
     *
     * @return List<CreateUserDTO>
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public List<CreateUserDTO> getUserList() {
        return createUserService.getUserList();
    }


    /**
     * To save the new users
     *
     * @param createUserDTO createUserDTO
     * @return ResponseMessage
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage saveUsers(HttpServletRequest request, CreateUserDTO createUserDTO) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        System.out.print(createUserDTO.getLoginId());
        return createUserService.saveUsers(createUserDTO, currentUser);
    }

    /**
     * To get the grid list to field
     *
     * @param loginId loginId
     * @return CreateUserDTO
     */
    @ResponseBody
    @RequestMapping(value = "/getGridListToField", method = RequestMethod.GET)
    public CreateUserDTO getGridListToField(String loginId) {
        return (CreateUserDTO) createUserService.getGridListToField(loginId).getDTO();
    }

    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ResponseMessage deleteUser(String userId) {
        return createUserService.deleteUser(userId);
    }
    //endregion

}
