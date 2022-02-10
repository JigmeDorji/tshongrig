package com.spms.mvc.dto;

/**
 * Created by Bcass Sawa on 4/12/2019.
 */
public class ViewPrintCopyDTOList {
    private Integer id;
    private String text;
    private boolean children;

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

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }
}
