package com.spms.mvc.web.fileUpload.controller;

import com.spms.mvc.dto.FilesArray;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.web.fileUpload.dao.FileParamsDao;
import com.spms.mvc.web.fileUpload.dto.FilesBundleDTO;
import com.spms.mvc.web.fileUpload.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Autowired
    private FileParamsDao fileParamsDao;


    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String fileUploadScreen() {
        return "fileUploadScreen";
    }


    @ResponseBody
    @RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
    public ResponseMessage uploadFiles(HttpServletRequest request, HttpSession session, FilesArray filesArray) throws IOException {

        List<FilesBundleDTO> filesBundleDTOS = this.filesBundleDTOS(filesArray.getFile(), filesArray.getFileNameToReplace());

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        return fileUploadService.saveFilesUploaded(currentUser, request, session, filesBundleDTOS);


    }


    private List<FilesBundleDTO> filesBundleDTOS(CommonsMultipartFile[] file, String[] names) {
        List<FilesBundleDTO> filesBundleDTOS = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {

            FilesBundleDTO filesBundleDTO = new FilesBundleDTO(file[i], names[i]);
            filesBundleDTOS.add(filesBundleDTO);
        }
        return filesBundleDTOS;
    }

}

