package com.spms.mvc.dto;

import java.util.List;

/**
 * Created by Bcass Sawa on 4/12/2019.
 */
public class ViewPrintCopyDTO {
    private Integer id;
    private String text;
    private String type;

    private List<ViewPrintCopyDTOList> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ViewPrintCopyDTOList> getChildren() {
        return children;
    }

    public void setChildren(List<ViewPrintCopyDTOList> children) {
        this.children = children;
    }
}
