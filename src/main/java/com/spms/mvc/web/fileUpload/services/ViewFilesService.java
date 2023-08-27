package com.spms.mvc.web.fileUpload.services;


import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.web.fileUpload.dao.ViewFilesDao;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewFilesService {
    @Autowired
    private ViewFilesDao viewFilesDao;

    public List<FileParamsDTO> getNewFiles(Integer companyId) {
        return viewFilesDao.getViewFileOnCondition(companyId, 1, 0, 0, 0);
    }

    public List<FileParamsDTO> getCompletedFiles(Integer companyId) {
        return viewFilesDao.getViewFileOnCondition(companyId, 0, 1, 0, 0);
    }

    public List<FileParamsDTO> filesMovedToBin(Integer companyId) {
        return viewFilesDao.getViewFileOnCondition(companyId, 0, 0, 1, 0);
    }

    public List<FileParamsDTO> getRetrievedFiles(Integer companyId) {
        return viewFilesDao.getViewFileOnCondition(companyId, 0, 0, 0, 1);

    }

    public FileParamsDTO getFileById(Integer fileId, Integer companyId) {
        return viewFilesDao.getFileById(fileId, companyId);
    }


    public ResponseMessage onMarkedAsCompletedBtn(Integer fileId, Integer companyId) {
        if (viewFilesDao.isMarkedCompleted(fileId, companyId)) {
            return new ResponseMessage(1, "Successfully marked as completed!");
        } else {
            return new ResponseMessage(0, "Failed to mark as completed!");
        }

    }


    public ResponseMessage onMoveFileToBin(Integer fileId, Integer companyId) {
        if (viewFilesDao.isMoveFileToBin(fileId, companyId)) {
            return new ResponseMessage(1, "File is moved to Bin");
        } else {
            return new ResponseMessage(0, "Fail to Delete!");
        }
    }



    public ResponseMessage onRetrieval(Integer fileId, Integer companyId) {
        if (viewFilesDao.isRetrieved(fileId, companyId)) {
            return new ResponseMessage(1, "File Successfully retrieved!");
        } else {
            return new ResponseMessage(0, "Retrieving Process Failed!");
        }
    }

    public ResponseMessage onPermanentlyDelete(Integer fileId, Integer companyId) throws XmlPullParserException, IOException, URISyntaxException {
        FileParamsDTO fileDetail = viewFilesDao.getFileById(fileId, companyId);
        ArrayList<String> resourcePaths = new ArrayList<String>();
        resourcePaths.add("resources");//src
        resourcePaths.add("fileCenter");//section
        resourcePaths.add(fileDetail.getFolderName());//folder/file
        resourcePaths.add(fileDetail.getFileName());//fileName
        FilePath filePath = new FilePath();

        File file = new File(filePath.filePath(resourcePaths));
        if (file.exists()) {
            file.delete();
            viewFilesDao.onDeleteBtn(fileDetail.getId(), companyId);
            return new ResponseMessage(1, "Permanently Deleted!");
        } else {
            return new ResponseMessage(0, "Failed to locate file at " + filePath.filePath(resourcePaths));
        }

    }


}
