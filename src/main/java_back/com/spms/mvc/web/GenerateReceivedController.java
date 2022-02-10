package com.spms.mvc.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SonamPC on 25-Dec-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/generateReceived")
public class GenerateReceivedController {


}
