package com.spms.mvc.web.fileUpload.controller;

import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import com.spms.mvc.web.fileUpload.services.ViewFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/filesBin")
public class FilesBinController {


    @Autowired
    private ViewFilesService filesViewService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String fileUploadScreen(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<FileParamsDTO> filesMovedToBin = filesViewService.filesMovedToBin(currentUser.getCompanyId());

        Collections.sort(filesMovedToBin, (o1, o2) -> {
            // Compare based on the private 'movedToBinDate' property using the getter method
            return o2.getMovedToBinDate().compareTo(o1.getMovedToBinDate());
        });

        model.addAttribute("files", filesMovedToBin);
        return "filesBin";
    }

    @ResponseBody
    @RequestMapping(value = "onRetrieval", method = RequestMethod.POST)
    public ResponseMessage onRetrieval(HttpServletRequest request,Integer fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        return filesViewService.onRetrieval(fileId, currentUser.getCompanyId());
    }

}
