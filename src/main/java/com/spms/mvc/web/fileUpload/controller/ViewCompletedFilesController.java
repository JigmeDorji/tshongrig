package com.spms.mvc.web.fileUpload.controller;

import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import com.spms.mvc.web.fileUpload.services.ViewFilesService;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = "/viewCompletedFiles")
public class ViewCompletedFilesController {
    @Autowired
    private ViewFilesService filesViewService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewNewFiles(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<FileParamsDTO> newFiles = filesViewService.getCompletedFiles(currentUser.getCompanyId());
        model.addAttribute("files", newFiles);
        return "viewCompletedFiles";
    }
}
