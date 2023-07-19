package com.spms.mvc.web.fileUpload.dto;

import java.util.Date;

public class FileParamsDTO {

    private Integer id;

    private String fileName;
    private String folderName;

    private int isNew;


    private int isCompleted;


    private String contextPath;


    private Integer companyId;


    private String createdBy;


    private Date createdDate;
    private Integer isPdfFile;

    private int isMovedToBin;
    private Date movedToBinDate;
    private Integer isRetrieved;


    private Date RetrievedDate;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getIsPdfFile() {
        return isPdfFile;
    }

    public void setIsPdfFile(Integer isPdfFile) {
        this.isPdfFile = isPdfFile;
    }

    public int getIsMovedToBin() {
        return isMovedToBin;
    }

    public void setIsMovedToBin(int isMovedToBin) {
        this.isMovedToBin = isMovedToBin;
    }

    public Date getMovedToBinDate() {
        return movedToBinDate;
    }

    public void setMovedToBinDate(Date movedToBinDate) {
        this.movedToBinDate = movedToBinDate;
    }

    public Integer getIsRetrieved() {
        return isRetrieved;
    }

    public void setIsRetrieved(Integer isRetrieved) {
        this.isRetrieved = isRetrieved;
    }


    public Date getRetrievedDate() {
        return RetrievedDate;
    }

    public void setRetrievedDate(Date retrievedDate) {
        RetrievedDate = retrievedDate;
    }

    @Override
    public String toString() {
        return "FileParamsDTO{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", folderName='" + folderName + '\'' +
                ", isNew=" + isNew +
                ", isCompleted=" + isCompleted +
                ", contextPath='" + contextPath + '\'' +
                ", companyId=" + companyId +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", isPdfFile=" + isPdfFile +
                ", isMovedToBin=" + isMovedToBin +
                ", movedToBinDate=" + movedToBinDate +
                '}';
    }
}
