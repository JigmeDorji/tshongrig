package com.spms.mvc.web.fileUpload;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FilesArray {
    private CommonsMultipartFile[] file;
    private String[] fileNameToReplace;

    public CommonsMultipartFile[] getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile[] file) {
        this.file = file;
    }

    public String[] getFileNameToReplace() {
        return fileNameToReplace;
    }

    public void setFileNameToReplace(String[] fileNameToReplace) {
        this.fileNameToReplace = fileNameToReplace;
    }
}