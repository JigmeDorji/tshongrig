package com.spms.mvc.dto;

/**
 * Created by SonamPC on 12-Jun-17.
 */
public class LocationSetUpDTO {
    private Integer locationSetUpId;
    private String locationId;
    private String description;


    public Integer getLocationSetUpId() {
        return locationSetUpId;
    }

    public void setLocationSetUpId(Integer locationSetUpId) {
        this.locationSetUpId = locationSetUpId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
