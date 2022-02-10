/**
 * Component Name: Spare part management
 * Name: WebAuthenticationProvider
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 04-May-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.security;

import com.spms.mvc.Enumeration.LoginErrorCode;
import com.spms.mvc.Enumeration.Permission;
import com.spms.mvc.dto.UserAccessPermissionListDTO;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.service.auth.UserAccessPermissionService;
import com.spms.mvc.service.auth.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.security.Security;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class WebAuthenticationProvider implements AuthenticationProvider {
    //region private variable
    private PasswordEncoder passwordEncoder;
    //endregion

    //region private service
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserAccessPermissionService userAccessPermissionService;
    //endregion

    //region setter
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    //endregion


    //region public method

    /**
     * It processes authentication information
     *
     * @param authentication authentication
     * @return Authentication
     * @throws org.springframework.security.core.AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

        String username = String.valueOf(auth.getPrincipal());
        username = username.trim().toUpperCase();
        String password = String.valueOf(auth.getCredentials());

        UserDTO userDTO = userLoginService.login(username,((BcsWebAuthenticationDetails) auth.getDetails()).getCompanyId());

        if (userDTO == null) {
            throw new UsernameNotFoundException(LoginErrorCode.FAILED.getCode());
        } else if (!userDTO.getUserStatus().equals('A')) {
            throw new LockedException(LoginErrorCode.LOCKED.getCode());
        } else if (passwordEncoder.matches(userDTO.getSaltValue() + password, userDTO.getUserPassword())) {
            Collection<GrantedAuthority> authorities = getAccessRight(userDTO);
            return new UsernamePasswordAuthenticationToken(userDTO, userDTO.getUserPassword(), authorities);
        } else {
            throw new BadCredentialsException(LoginErrorCode.FAILED.getCode());
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * It supplies authorization information according to user group
     *
     * @return Set
     */
    private Collection<GrantedAuthority> getAccessRight(UserDTO userDTO) {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        List<UserAccessPermissionListDTO> userAccessPermissionListDTOList = userAccessPermissionService
                .getUserAccessPermissionDetails(userDTO.getUserRoleTypeId());
        userAccessPermissionListDTOList.forEach(userAccessPermissionListDTO -> {
            Integer screenId = userAccessPermissionListDTO.getScreenId();
//
//            if (Objects.equals(ChildMenuEnum.ITEM_RECEIVE_IN_CASH.getValue(), screenId)
//                    || Objects.equals(ChildMenuEnum.ITEM_RECEIVE_IN_CREDIT.getValue(), screenId)
//                    || Objects.equals(ChildMenuEnum.PURCHASE_REPORT.getValue(), screenId)) {
//                if (userAccessPermissionListDTO.getIsScreenAccessAllowed()) {
//                    authorities.add(new SimpleGrantedAuthority(ParentMenuEnum.ITEM_RECEIVE.getValue() + "-" + Permission.VIEW));
//                }
//            }

            //Screen permission
            if (userAccessPermissionListDTO.getIsScreenAccessAllowed() != null && userAccessPermissionListDTO.getIsScreenAccessAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.VIEW));
            }
            if (userAccessPermissionListDTO.getIsDeleteAccessAllowed() != null && userAccessPermissionListDTO.getIsDeleteAccessAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.DELETE));

            }
            if (userAccessPermissionListDTO.getIsSaveAccessAllowed() != null && userAccessPermissionListDTO.getIsSaveAccessAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.ADD));
            }
            if (userAccessPermissionListDTO.getIsEditAccessAllowed() != null && userAccessPermissionListDTO.getIsEditAccessAllowed() == 'Y') {
                authorities.add(new SimpleGrantedAuthority(screenId + "-" + Permission.EDIT));
            }
        });
        return authorities;
    }
    //endregion
}
