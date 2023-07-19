package com.spms.mvc.web.fileUpload.services;

import com.spms.mvc.entity.FileParams;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.web.fileUpload.dao.FileParamsDao;
import com.spms.mvc.web.fileUpload.dto.FilesBundleDTO;
import com.spms.mvc.web.fileUpload.fileOrFolderOperation.FileFolderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileUploadService {
    @Autowired
    private FileParamsDao fileParamsDao;

    public ResponseMessage saveFilesUploaded(CurrentUser currentUser, HttpServletRequest request, HttpSession session, List<FilesBundleDTO> filesBundleDTOS) throws IOException {

        if (saveEachFile(currentUser, request, session, filesBundleDTOS)) {

            return new ResponseMessage(1, "File successfully uploaded");
        } else {
            return new ResponseMessage(0, "Failed to save files");
        }

    }

    //    File Saving Logic Service Method
    private boolean saveEachFile(CurrentUser currentUser, HttpServletRequest request, HttpSession session, List<FilesBundleDTO> filesBundleDTOS) throws IOException {
        boolean isFileSaved = false;
        for (FilesBundleDTO file : filesBundleDTOS) {
            FileFolderOperation fileFolderOperation = new FileFolderOperation();
            String originalFilename = file.getFile().getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
            String datePatch = formatter.format(new Date());
            String renamedFilename;


            if (file.getFileNameToReplace().equals("null")) {
                String fileName = file.getFile().getOriginalFilename();
                String result = fileName.substring(0, fileName.lastIndexOf('.'));
                renamedFilename = result + "_" + fileParamsDao.getLastCountVal(currentUser.getCompanyId()) + "_" + datePatch + fileExtension;

            } else {

//                System.out.println(originalFilename.lastIndexOf("."));
                String fileEx = file.getFileNameToReplace().substring(file.getFileNameToReplace().lastIndexOf(".") + 1);
//                System.out.println(fileEx);

                if (isFileExtensionDuplicateExists(file.getFileNameToReplace(), fileExtension)) {
                    String originalString = file.getFileNameToReplace();
                    int endIndex = originalString.indexOf(fileExtension);
                    String extractedString = originalString.substring(0, endIndex);
                    renamedFilename = extractedString + "_" + fileParamsDao.getLastCountVal(currentUser.getCompanyId()) + "_" + datePatch+fileExtension;
                } else {
                    renamedFilename = file.getFileNameToReplace() + "_" + fileParamsDao.getLastCountVal(currentUser.getCompanyId()) + "_" + datePatch + fileExtension;
                }


            }


            FileParams fileParams = new FileParams();
            fileParams.setFileName(renamedFilename);
            fileParams.setFolderName("companyId_" + currentUser.getCompanyId());
            fileParams.setIsNew(true);
            fileParams.setIsCompleted(false);
            fileParams.setContextPath(request.getContextPath());
            fileParams.setCompanyId(currentUser.getCompanyId());
            fileParams.setCreatedBy(currentUser.getLoginId());
            fileParams.setCreatedDate(new Date());
            fileParams.setIsMovedToBin(0);
            fileParams.setIsRetrieved(0);


            if (fileExtension.equals(".pdf")) {
                fileParams.setIsPdfFile(1);
            } else {
                fileParams.setIsPdfFile(0);
            }
            fileParamsDao.saveFileParams(fileParams);

            isFileSaved = fileFolderOperation.saveOperation(file.getFile(), renamedFilename, session, currentUser.getCompanyId());
            if (!isFileSaved) {
                return false;
            }
        }
        return isFileSaved;
    }

    private boolean isFileExtensionDuplicateExists(String fileName, String strFileExtension) {
        // Add a dot prefix to the file extension

        // Check if the file name ends with the given file extension
        if (fileName.endsWith(strFileExtension)) {
            // Extract the substring representing the file extension from the file name
            String extractedExtension = fileName.substring(fileName.lastIndexOf("."));
            // Compare the extracted file extension with the given file extension
            return extractedExtension.equalsIgnoreCase(strFileExtension); // The file extension already exists in the file name
        }

        return false; // The file extension does not exist in the file name
    }


}


/*
   id,fileName,folderName,isNew,IsCompleted,contextPath,companyId,createdBy,
 */
