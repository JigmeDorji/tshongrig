
package com.spms.mvc.web.auth;

import com.spms.mvc.Enumeration.LoginErrorCode;
import com.spms.mvc.dto.CreateUserDTO;
import com.spms.mvc.service.CompanyCreationService;
import com.spms.mvc.service.auth.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "")
public class LoginController {
    //region private service
    @Autowired
    UserLoginService userLoginService;

    @Autowired
    private CompanyCreationService companyCreationService;
    //endregion

    //region public method

    /**
     * login loader
     *
     * @param error   error
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpServletRequest request, Model model) {
        if (error != null) {
            model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            model.addAttribute("companyList", companyCreationService.loadCompanyList());
            return "login";
        } else {
            return "home";
        }
    }

    /**
     * logout loader
     *
     * @param request  request
     * @param response response
     * @return ModelAndView
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        model.addAttribute("companyList", companyCreationService.loadCompanyList());
        return "redirect:/login";
    }

    /**
     * authentication processing path
     *
     * @return ModelAndView
     */
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ModelAndView auth() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }


    /**
     * access denied path
     *
     * @param request request
     * @return ModelAndView
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView model = new ModelAndView();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CreateUserDTO userLogin = (CreateUserDTO) auth.getPrincipal();
            model.addObject("username", userLogin.getTxtUserName());
        }
        model.setViewName("403");
        return model;
    }
    //endregion


    //region private method

    /**
     * to generate authentication error message
     *
     * @param request request
     * @param key     key
     * @return String
     */
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        if (exception != null) {
            String message = exception.getMessage();
            if (message.equals(LoginErrorCode.FAILED.getCode()) || message.equals(LoginErrorCode.LOCKED.getCode())) {
                return message;
            } else {
                return LoginErrorCode.MAX_SESSION.getCode();
            }
        } else {
            return null;
        }
    }
    //endregion
}
