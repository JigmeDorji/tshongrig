package com.spms.mvc.web.fileUpload.controller;

import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import com.spms.mvc.web.fileUpload.services.ViewFilesService;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = "/viewNewFiles")
public class ViewNewFilesController {

    @Autowired
    private ViewFilesService filesViewService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewNewFiles(HttpServletRequest request, Model model) throws XmlPullParserException, IOException, URISyntaxException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<FileParamsDTO> newFiles = filesViewService.getNewFiles(currentUser.getCompanyId());
        model.addAttribute("files", newFiles);
        return "viewNewFiles";
    }

    @ResponseBody
    @RequestMapping(value = "getFileById", method = RequestMethod.POST)
    public FileParamsDTO getFileById(HttpServletRequest request, Integer fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return filesViewService.getFileById(fileId, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "onMarkedAsCompletedBtn", method = RequestMethod.POST)
    public ResponseMessage onMarkedAsCompletedBtn(HttpServletRequest request, Integer fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return filesViewService.onMarkedAsCompletedBtn(fileId, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "onDeleteBtn", method = RequestMethod.POST)
    public ResponseMessage onDeleteBtn(HttpServletRequest request, Integer fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return filesViewService.onMoveFileToBin(fileId, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "onPermanentlyDelete", method = RequestMethod.POST)
    public ResponseMessage onPermanentlyDelete(HttpServletRequest request, Integer fileId) throws XmlPullParserException, IOException, URISyntaxException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return filesViewService.onPermanentlyDelete(fileId, currentUser.getCompanyId());
    }


//    @ResponseBody
//    @RequestMapping(value = "onPermanentlyDelete", method = RequestMethod.POST)
//    public ResponseMessage onPermanentlyDelete(HttpServletRequest request, Integer fileId) throws XmlPullParserException, IOException, URISyntaxException {
//        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
//        return filesViewService.onPermanentlyDelete(fileId, currentUser.getCompanyId(), request);
//    }



}
