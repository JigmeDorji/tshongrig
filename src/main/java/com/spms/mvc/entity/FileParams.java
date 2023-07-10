package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_file_params")
public class FileParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "folderName")
    private String folderName;

    @Column(name = "isNew")
    private Boolean isNew;

    @Column(name = "isCompleted")
    private Boolean isCompleted;

    @Column(name = "contextPath")
    private String contextPath;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "isPdfFile")
    private Integer isPdfFile;

    @Column(name = "isMovedToBin")
    private Integer isMovedToBin;;

    @Column(name = "movedToBinDate")
    private Date movedToBinDate;;





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

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
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

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getIsPdfFile() {
        return isPdfFile;
    }

    public void setIsPdfFile(Integer isPdfFile) {
        this.isPdfFile = isPdfFile;
    }

    public Integer getIsMovedToBin() {
        return isMovedToBin;
    }

    public void setIsMovedToBin(Integer isMovedToBin) {
        this.isMovedToBin = isMovedToBin;
    }

    public Date getMovedToBinDate() {
        return movedToBinDate;
    }

    public void setMovedToBinDate(Date movedToBinDate) {
        this.movedToBinDate = movedToBinDate;
    }
}
