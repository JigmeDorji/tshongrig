/**
 * Component Name: Spare part management
 * Name: ResponseMessage
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author: bikash.rai
 * Creation: 29-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.library.helper;


public class ResponseMessage {
    //region private declaration
    private Integer status = 1;
    private Integer companyId;
    private String text;
    private Object dto;
    //endregion

    //region empty constructor
    public ResponseMessage() {
    }
    //endregion


    public ResponseMessage(Integer status, String text) {
        this.status = status;
        this.text = text;
    }
    public ResponseMessage(Integer status) {
        this.status = status;
    }


    //region getter and setter
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getDTO() {
        return dto;
    }

    public void setDTO(Object dto) {
        this.dto = dto;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    //endregion
}
