package com.spms.mvc.web.fileUpload.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FilesBundleDTO {
    private CommonsMultipartFile file;
    private String fileNameToReplace;

    public FilesBundleDTO(CommonsMultipartFile file, String fileNameToReplace) {
        this.file = file;
        this.fileNameToReplace = fileNameToReplace;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public String getFileNameToReplace() {
        return fileNameToReplace;
    }

    public void setFileNameToReplace(String fileNameToReplace) {
        this.fileNameToReplace = fileNameToReplace;
    }
}
