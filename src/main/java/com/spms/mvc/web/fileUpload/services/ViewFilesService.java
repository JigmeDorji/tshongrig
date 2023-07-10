package com.spms.mvc.web.fileUpload.services;


import com.spms.mvc.web.fileUpload.dao.ViewFilesDao;
import com.spms.mvc.web.fileUpload.dto.FileParamsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewFilesService {
    @Autowired
    private ViewFilesDao viewFilesDao;

    public List<FileParamsDTO> getOnlyNewFilesList(Integer companyId) {
        return viewFilesDao.getViewFileOnCondition(companyId, 1, 0);
    }
}
