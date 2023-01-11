package com.spms.mvc.dto;

public class RawMaterialLocationSetupDTO {
    private Integer rawMaterialLocationSetUpId;
    private String rawMaterialLocationId;
    private String rawMaterialLocationIdDescription;

    public Integer getRawMaterialLocationSetUpId() {
        return rawMaterialLocationSetUpId;
    }

    public void setRawMaterialLocationSetUpId(Integer rawMaterialLocationSetUpId) {
        this.rawMaterialLocationSetUpId = rawMaterialLocationSetUpId;
    }

    public String getRawMaterialLocationId() {
        return rawMaterialLocationId;
    }

    public void setRawMaterialLocationId(String rawMaterialLocationId) {
        this.rawMaterialLocationId = rawMaterialLocationId;
    }

    public String getRawMaterialLocationIdDescription() {
        return rawMaterialLocationIdDescription;
    }

    public void setRawMaterialLocationIdDescription(String rawMaterialLocationIdDescription) {
        this.rawMaterialLocationIdDescription = rawMaterialLocationIdDescription;
    }
}
